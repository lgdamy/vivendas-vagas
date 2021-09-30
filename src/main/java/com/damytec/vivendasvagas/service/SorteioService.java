package com.damytec.vivendasvagas.service;

import com.damytec.vivendasvagas.pojo.Vaga;
import javafx.scene.control.CheckBox;

import javax.swing.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author lgdamy on 25/01/2021
 */
public class SorteioService {
    private static SorteioService INSTANCE;

    private SorteioService() {}

    public static SorteioService getInstance() {
        return INSTANCE = INSTANCE == null ? new SorteioService() : INSTANCE;
    }

    public String sortearNumero (List<JCheckBox> checkBoxes, List<Vaga> vagas) {
        List<JCheckBox> availables = checkBoxes.stream().filter(cb -> cb.isSelected() && vagas.stream().noneMatch(v -> cb.getText().equals(v.getEstado().getApartamento()))).collect(Collectors.toList());
        if (availables.isEmpty()) {
            throw new NoSuchElementException("Nenhuma unidade sorte\u00e1vel");
        }
        return availables.get(new Random().nextInt(availables.size())).getText();
    }

}
