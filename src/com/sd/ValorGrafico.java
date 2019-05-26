package com.sd;

public class ValorGrafico {
    private Integer valor;
    private Integer diferenca;
    private String tempo;

    public ValorGrafico(Integer valor, Integer diferenca, String tempo) {
        this.valor = valor;
        this.tempo = tempo;
        this.diferenca = diferenca;
    }

    public Integer getValor() {
        return valor;
    }

    public String getTempo() {
        return tempo;
    }

    public Integer getDiferenca() {
        return diferenca;
    }
}