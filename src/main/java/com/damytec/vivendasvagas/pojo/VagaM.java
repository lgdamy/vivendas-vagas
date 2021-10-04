package com.damytec.vivendasvagas.pojo;

import static com.damytec.vivendasvagas.util.Constant.M_H;
import static com.damytec.vivendasvagas.util.Constant.M_W;

/**
 * @author lgdamy@raiadrogasil.com on 29/09/2021
 */
public class VagaM extends Vaga {

    public VagaM(int x, int y, String nome, Corredor corredor) {
        super(x, y, M_W, M_H, nome, corredor);
    }
}
