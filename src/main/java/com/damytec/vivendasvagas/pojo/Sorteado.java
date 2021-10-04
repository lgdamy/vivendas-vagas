package com.damytec.vivendasvagas.pojo;

import java.util.Observable;

/**
 * @author lgdamy@raiadrogasil.com on 03/10/2021
 */
public class Sorteado extends Observable {
    private String unidade;

    public synchronized String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        synchronized (this) {
            this.unidade = unidade;
        }
        setChanged();
        notifyObservers();
    }
}
