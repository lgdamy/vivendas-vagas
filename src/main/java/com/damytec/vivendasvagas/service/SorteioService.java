package com.damytec.vivendasvagas.service;

import com.damytec.vivendasvagas.pojo.Vaga;

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

    public String sortearNumero (List<String> unidades, List<Vaga> vagas) {
        List<String> availables = unidades.stream().filter(ap -> vagas.stream().noneMatch(v -> ap.equals(v.getEstado().getApartamento()))).collect(Collectors.toList());
        if (availables.isEmpty()) {
            throw new NoSuchElementException("Nenhuma unidade sorte\u00e1vel");
        }
        return availables.get(new Random().nextInt(availables.size()));
    }

}
