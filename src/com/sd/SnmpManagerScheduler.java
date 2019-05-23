package com.sd;

import java.io.IOException;
import java.util.TimerTask;

public class SnmpManagerScheduler extends TimerTask {

    private SnmpManager snmpManager;

    public SnmpManagerScheduler(SnmpManager snmpManager) {
        this.snmpManager = snmpManager;
    }

    @Override
    public void run() {
        try {
            System.out.println("NOVA RODADA DE AVALAICAO DA INFA");
            this.snmpManager.manageNetwork();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
