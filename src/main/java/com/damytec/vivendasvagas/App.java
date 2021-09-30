package com.damytec.vivendasvagas;

import com.damytec.vivendasvagas.ui.BaseWindow;
import com.damytec.vivendasvagas.view.VivendasvagasPanel;
import com.formdev.flatlaf.FlatLightLaf;

/**
 * @author lgdamy@ on 22/01/2021
 */
public class App {
    public static void main(String[] args) {
        FlatLightLaf.install();
        new BaseWindow(new VivendasvagasPanel(), 735,580);
    }
}
