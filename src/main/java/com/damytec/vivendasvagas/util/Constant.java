package com.damytec.vivendasvagas.util;

/**
 * @author lgdamy@raiadrogasil.com on 29/09/2021
 */
public class Constant {
    private Constant() {
    }

    public static final int M_H = 42;
    public static final int M_W = 95;
    public static final int P_H = 28;
    public static final int P_W = 90;
    public static final int CANVAS_H = 480;
    public static final int CANVAS_W = 640;
    public static final int COLUNA = 20;
    public static final int BORDER = 5;
//    public static final int CORREDOR = 120;
    public static final int CORREDOR = (CANVAS_W - 2 * BORDER - 3 * M_W - P_W) / 2;

}
