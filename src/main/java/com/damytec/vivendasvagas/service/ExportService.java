package com.damytec.vivendasvagas.service;

import com.damytec.vivendasvagas.pojo.Vaga;
import com.damytec.vivendasvagas.ui.PlantaGaragem;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.Comparator;
import java.util.List;

import static com.damytec.vivendasvagas.util.Constant.CANVAS_H;
import static com.damytec.vivendasvagas.util.Constant.CANVAS_W;

/**
 * @author lgdamy on 25/01/2021
 */
public class ExportService {
    private static ExportService INSTANCE;

    private ExportService() {}

    public static ExportService getInstance() {
        return INSTANCE = INSTANCE == null ? new ExportService() : INSTANCE;
    }

    public void gerarCSV(List<Vaga> vagas) {
        vagas.stream().forEach(Vaga::unselect);
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
        try {
            BufferedImage buff = new BufferedImage(CANVAS_W, CANVAS_H , BufferedImage.TYPE_INT_RGB);
            Graphics g = buff.getGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0,0,CANVAS_W,CANVAS_H);
            g.drawImage(PlantaGaragem.toImage(vagas,null),0,0,null);
            ImageIO.write(buff,"jpg", new File("vagas_vivendas.jpg"));
            Desktop.getDesktop().open(new File("vagas_vivendas.jpg"));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
