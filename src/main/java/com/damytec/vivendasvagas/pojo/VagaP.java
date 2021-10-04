package com.damytec.vivendasvagas.pojo;

import com.damytec.vivendasvagas.util.Constant;

/**
 * @author lgdamy@raiadrogasil.com on 29/09/2021
 */
public class VagaP extends Vaga {

    public VagaP(int x, int y, String nome) {
        super(x, y, Constant.P_W,Constant.P_H, nome,Corredor.DIREITA);
    }
}
