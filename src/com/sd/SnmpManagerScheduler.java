package com.sd;

import java.util.LinkedHashMap;
import java.util.TimerTask;

public class SnmpManagerScheduler extends TimerTask {

    private SnmpManager snmpManager;
    private Long startTime; // In Miliseconds
    private Integer count = 0;

    public SnmpManagerScheduler(Long startTime, SnmpManager snmpManager) {
        this.snmpManager = snmpManager;
        this.startTime = startTime;
    }

    @Override
    public void run() {
        try {
            if (System.currentTimeMillis() - startTime > 2 * 60000) {
                System.out.println("ACABOU O TEMPO DE ANALISE");
                LinkedHashMap teste = this.snmpManager.getHashForMib();
                System.out.println(teste.toString());
                this.cancel();
                snmpManager.setDone(true);
            } else {
                System.out.println("\n ---------------------------------------------- RODADA NUMERO: " + count + " DE AVALAICAO DA INFA ----------------------------------------------");
                this.snmpManager.manageNetwork(count);
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
