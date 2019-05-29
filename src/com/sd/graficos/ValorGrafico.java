package com.sd.graficos;

public class ValorGrafico {
    private Long valor;
    private Long diferenca;
    private String tempo;

    public ValorGrafico(Long valor, Long diferenca, String tempo) {
        this.valor = valor;
        this.diferenca = diferenca;
        this.tempo = tempo;
    }

    public Long getValor() {
        return valor;
    }

    public String getTempo() {
        return tempo;
    }

    public Long getDiferenca() {
        return diferenca;
    }
}