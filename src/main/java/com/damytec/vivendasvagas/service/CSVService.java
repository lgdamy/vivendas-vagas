package com.damytec.vivendasvagas.service;

import com.damytec.vivendasvagas.pojo.Vaga;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.List;

/**
 * @author lgdamy on 25/01/2021
 */
public class CSVService {
    private static CSVService INSTANCE;

    private CSVService() {}

    public static CSVService getInstance() {
        return INSTANCE = INSTANCE == null ? new CSVService() : INSTANCE;
    }

    public void gerarCSV(List<Vaga> vagas) {
        try {
            vagas.sort(Comparator.naturalOrder());
            FileOutputStream fos = new FileOutputStream("vagas_vivendas.csv");
            fos.write("\"Vaga\";\"Unidade\"".getBytes(Charset.defaultCharset()));
            fos.write(System.lineSeparator().getBytes(Charset.defaultCharset()));
            for (Vaga v : vagas) {
                fos.write(String.format("\"%s\";\"%s\"", v.getNome(), (v.getEstado().isOcupada() ? v.getEstado().getApartamento() : "N/A")).getBytes(Charset.defaultCharset()));
                fos.write(System.lineSeparator().getBytes(Charset.defaultCharset()));
            }
            fos.close();
            Desktop.getDesktop().open(new File("vagas_vivendas.csv"));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
