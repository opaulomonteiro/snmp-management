package com.sd;

import com.sd.graficos.ValorGrafico;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class SnmpManager {

    private LinkedHashMap<String, List<ValorGrafico>> hashForMib;
    private Boolean isDone;
    private String ipAddress;
    private String community;

    // A - UTILIZAÇÃ DO LINKG = [ifInOctets, ifOutOctets, ifSpeed]
    private static String ifInOctets = ".1.3.6.1.2.1.2.2.1.10.3";
    private static String ifOutOctets = ".1.3.6.1.2.1.2.2.1.16.3";
    private static String ifSpeed = ".1.3.6.1.2.1.2.2.1.5.1";


    // B - TAXA DE DATAGRAMAS RECEBIDOS E ENVIADOS
    private static String ipInReceives = ".1.3.6.1.2.1.4.3.0";
    private static String ipOutRequests = ".1.3.6.1.2.1.4.10.0";

    // C - TAXA DE TCP RECEBIDOS E ENVIADOS
    private static String tcpInSegs = ".1.3.6.1.2.1.6.10.0";
    private static String tcpOutSegs = ".1.3.6.1.2.1.6.11.0";

    // D -  TAXA DE UDP RECEBIDOS E ENVIADOS
    private static String udpInDatagrams = ".1.3.6.1.2.1.7.1.0";
    private static String udpOutDatagrams = ".1.3.6.1.2.1.7.4.0";

    // E - TAXA DE ICMP RECEBIDOS E ENVIADOS
    private static String icmpInMsgs = ".1.3.6.1.2.1.5.1.0";
    private static String icmpOutMsgs = ".1.3.6.1.2.1.5.14.0";


    // F - TAXA DE SNMP RECEBIDOS E ENVIADOS
    private static String snmpInPkts = ".1.3.6.1.2.1.11.1.0";
    private static String snmpOutPkts = ".1.3.6.1.2.1.11.2.0";

    private ArrayList<VariableBinding> variableBindings = new ArrayList<>(
            Arrays.asList(
                    new VariableBinding(new OID(ipInReceives)),
                    new VariableBinding(new OID(ipOutRequests)),
                    new VariableBinding(new OID(tcpInSegs)),
                    new VariableBinding(new OID(tcpOutSegs)),
                    new VariableBinding(new OID(udpInDatagrams)),
                    new VariableBinding(new OID(udpOutDatagrams)),
                    new VariableBinding(new OID(icmpInMsgs)),
                    new VariableBinding(new OID(icmpOutMsgs)),
                    new VariableBinding(new OID(snmpInPkts)),
                    new VariableBinding(new OID(snmpOutPkts)),
                    new VariableBinding(new OID(ifInOctets)),
                    new VariableBinding(new OID(ifOutOctets)),
                    new VariableBinding(new OID(ifSpeed))
            )
    );

    public SnmpManager(String ipAddress, String community, LinkedHashMap hashForMib) {
        this.ipAddress = ipAddress;
        this.community = community;
        this.hashForMib = hashForMib;
    }

    public void manageNetwork(Integer round) throws IOException {
        PDU pduForGET = createPDU(PDU.GET, variableBindings);

        ResponseEvent eventGET = getReponseFor(pduForGET);
        printResponse(eventGET, round);
    }

    private void printResponse(ResponseEvent responseEvent, Integer round) {
        // Process Agent Response
        if (responseEvent != null) {
            PDU responsePDU = responseEvent.getResponse();

            if (responsePDU != null) {
                int errorStatus = responsePDU.getErrorStatus();
                int errorIndex = responsePDU.getErrorIndex();
                String errorStatusText = responsePDU.getErrorStatusText();

                if (errorStatus == PDU.noError) {
                    for (VariableBinding variableBinding : responsePDU.getVariableBindings()) {
                        String oid = variableBinding.getOid().toString();
                        Long value = variableBinding.getVariable().toLong();

                        if (hashForMib.containsKey(oid)) {
                            List<ValorGrafico> listValues = hashForMib.get(oid);
                            ValorGrafico valorAnterior;
                            if (listValues.size() == 1) {
                                valorAnterior = listValues.get(0);
                            } else {
                                valorAnterior = listValues.get(round - 1);
                            }
                            System.out.println("Valor Atual: " + value);
                            System.out.println("Valor Anterior: " + valorAnterior.getValor());
                            System.out.println("Diferenca: " + (value - valorAnterior.getValor()));
                            listValues.add(new ValorGrafico(value, value - valorAnterior.getValor(), round.toString()));
                            hashForMib.put(oid, listValues);
                        } else {
                            List<ValorGrafico> newList = new ArrayList<>();
                            newList.add(new ValorGrafico(value, value, round.toString()));
                            hashForMib.put(oid, newList);
                        }


                    }
                    responsePDU.getVariableBindings().forEach(System.out::println);
                } else {
                    System.out.println("Error: Request Failed");
                    System.out.println("Error Status = " + errorStatus);
                    System.out.println("Error Index = " + errorIndex);
                    System.out.println("Error Status Text = " + errorStatusText);
                }
            } else {
                System.out.println("Error: Response PDU is null");
            }
        } else {
            System.out.println("Error: Agent Timeout... ");
        }

    }


    private PDU createPDU(int pduType, ArrayList<VariableBinding> variableBindings) {
        PDU pdu = new PDU();
        pdu.addAll(variableBindings);
        pdu.setType(pduType);
        pdu.setRequestID(new Integer32(1));

        return pdu;
    }

    private ResponseEvent getReponseFor(PDU pdu) throws IOException {
        TransportMapping transport = new DefaultUdpTransportMapping();
        transport.listen();

        // Create Target Address object
        CommunityTarget comtarget = new CommunityTarget();
        comtarget.setCommunity(new OctetString(community));
        comtarget.setVersion(SnmpConstants.version1);
        String port = "161";
        comtarget.setAddress(new UdpAddress(ipAddress + "/" + port));
        comtarget.setRetries(2);
        comtarget.setTimeout(1000);

        Snmp snmp = new Snmp(transport);

        ResponseEvent response;
        System.out.println("\n ***************** Gerando respota para PDU.GET *****************");
        response = snmp.get(pdu, comtarget);
        System.out.println("***************** Got Response from Agent *****************");
        snmp.close();

        return response;
    }

    public LinkedHashMap<String, List<ValorGrafico>> getHashForMib() {
        return hashForMib;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }
}