package com.damytec.vivendasvagas.pojo;

import java.awt.*;

/**
 * @author lgdamy@raiadrogasil.com on 03/10/2021
 */
public class Drag {
    private Point origem;
    private Point destino;
    private Vaga sujeito;

    public Drag(Point origem, Point destino, Vaga sujeito) {
        this.origem = origem;
        this.destino = destino;
        this.sujeito = sujeito;
    }

    public Point getOrigem() {
        return origem;
    }

    public Point getDestino() {
        return destino;
    }

    public Vaga getSujeito() {
        return sujeito;
    }
}
