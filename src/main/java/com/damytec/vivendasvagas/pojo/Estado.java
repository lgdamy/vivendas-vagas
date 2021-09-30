package com.damytec.vivendasvagas.pojo;

/**
 * @author lgdamy@raiadrogasil.com on 30/09/2021
 */
public class Estado {
    private boolean selecionada;
    private String apartamento;

    public boolean isSelecionada() {
        return selecionada;
    }

    public void setSelecionada(boolean selecionada) {
        this.selecionada = selecionada;
    }

    public String getApartamento() {
        return apartamento;
    }

    public void setApartamento(String apartamento) {
        this.apartamento = apartamento;
    }

    public boolean isOcupada() {
        return apartamento != null;
    }
}
