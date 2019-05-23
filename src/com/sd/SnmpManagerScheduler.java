package com.sd;

import java.io.IOException;
import java.util.TimerTask;

public class SnmpManagerScheduler extends TimerTask {

    private SnmpManager snmpManager;
    private Long startTime; // In Miliseconds
    private Integer count;

    public SnmpManagerScheduler(SnmpManager snmpManager, Long startTime) {
        this.snmpManager = snmpManager;
        this.startTime = startTime;
        this.count = 0;
    }

    @Override
    public void run() {
        try {
            if (System.currentTimeMillis() - startTime > 60000) {
                System.out.println("ACABOU O TEMPO DE ANALISE");
                this.cancel();
            } else {
                count++;
                System.out.println("\n ---------------------------------------------- RODADA NUMERO: " + count + " DE AVALAICAO DA INFA ----------------------------------------------");
                this.snmpManager.manageNetwork();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
