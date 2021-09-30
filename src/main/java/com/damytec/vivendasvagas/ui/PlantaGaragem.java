package com.damytec.vivendasvagas.ui;

import com.damytec.vivendasvagas.pojo.Vaga;
import com.damytec.vivendasvagas.util.Constant;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

import static com.damytec.vivendasvagas.util.Constant.BORDER;
import static com.damytec.vivendasvagas.util.Constant.BORDER;
import static com.damytec.vivendasvagas.util.Constant.CANVAS_H;
import static com.damytec.vivendasvagas.util.Constant.CANVAS_W;
import static com.damytec.vivendasvagas.util.Constant.COLUNA;
import static com.damytec.vivendasvagas.util.Constant.CORREDOR;
import static com.damytec.vivendasvagas.util.Constant.M_H;
import static com.damytec.vivendasvagas.util.Constant.M_W;
import static com.damytec.vivendasvagas.util.Constant.P_H;
import static com.damytec.vivendasvagas.util.Constant.P_W;

/**
 * @author lgdamy@raiadrogasil.com on 29/09/2021
 */
public class PlantaGaragem extends JPanel {

    JLabel label;

    public PlantaGaragem() {
        super();
        this.label = new JLabel("");

    }

    public void draw(List<Vaga> vagas) {
        setLayout(new FlowLayout());
        add(this.label);
        setBorder(null);

        BufferedImage image = new BufferedImage(CANVAS_W, Constant.CANVAS_H, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        vagas.forEach(v -> {
            if (v.isSelecionada() && v.getEstado().isOcupada()) {
                g2d.setColor(Color.ORANGE);
            }else if (v.isSelecionada()) {
                g2d.setColor(Color.GREEN);
            } else if (v.getEstado().isOcupada()) {
                g2d.setColor(Color.PINK);
            } else {
                g2d.setColor(Color.WHITE);
            }
            g2d.fillRect(v.getX() + BORDER, v.getY() + BORDER, v.getWidth(), v.getHeight());
            g2d.setPaint(Color.BLACK);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRect(v.getX() + BORDER, v.getY() + BORDER, v.getWidth(), v.getHeight());
            g2d.drawString(v.getDescription(), v.getX() + 10 + BORDER, v.getY() + BORDER + v.getHeight() / 2);
        });

        g2d.setPaint(Color.DARK_GRAY);
        g2d.fillPolygon(new Polygon(new int[]{BORDER, BORDER + M_W , 3 * M_W / 2 + BORDER, 3 * M_W / 2 + BORDER , BORDER},new int[]{BORDER + 7 * M_H + 4 * COLUNA, BORDER + 7 * M_H + 4 * COLUNA, CANVAS_H - BORDER, CANVAS_H - BORDER, CANVAS_H - BORDER }, 5));
        g2d.fillRect(BORDER + 2 * M_W + CORREDOR + COLUNA, 6 * M_H + 5 * COLUNA + BORDER,M_W - COLUNA,2 * M_H - COLUNA);
        g2d.fillRect(BORDER + 2 * M_W + CORREDOR , 4 * M_H + 2 * COLUNA + BORDER ,M_W,2 * M_H + COLUNA);
        g2d.setPaint(Color.BLACK);
        g2d.drawPolygon(new Polygon(new int[]{BORDER, BORDER + M_W , 3 * M_W / 2 + BORDER, 3 * M_W / 2 + BORDER , BORDER},new int[]{BORDER + 7 * M_H + 4 * COLUNA, BORDER + 7 * M_H + 4 * COLUNA, CANVAS_H - BORDER, CANVAS_H - BORDER, CANVAS_H - BORDER }, 5));
        g2d.drawRect(BORDER + 2 * M_W + CORREDOR + COLUNA, 6 * M_H + 5 * COLUNA + BORDER,M_W - COLUNA,2 * M_H - COLUNA);
        g2d.drawRect(BORDER + 2 * M_W + CORREDOR , 4 * M_H + 2 * COLUNA + BORDER ,M_W,2 * M_H + COLUNA);

        g2d.setPaint(Color.DARK_GRAY);
        g2d.fillOval(BORDER + M_W - COLUNA, BORDER + 2 * M_H, COLUNA, COLUNA);
        g2d.fillOval(BORDER + M_W - COLUNA, BORDER + 4 * M_H + COLUNA, COLUNA, COLUNA);
        g2d.fillOval(BORDER + M_W - COLUNA, BORDER + 6 * M_H + 2 * COLUNA, COLUNA, COLUNA);
        g2d.fillOval(BORDER + M_W + CORREDOR, BORDER + 2 * M_H, COLUNA, COLUNA);
        g2d.fillOval(BORDER + M_W + CORREDOR, BORDER + 3 * M_H + COLUNA, COLUNA, COLUNA);
        g2d.fillOval(BORDER + M_W + CORREDOR, BORDER + 4 * M_H + 2 * COLUNA, COLUNA, COLUNA);
        g2d.fillOval(BORDER + M_W + CORREDOR, BORDER + 6 * M_H + 3 * COLUNA, COLUNA, COLUNA);
        g2d.fillOval(BORDER + M_W + CORREDOR, BORDER + 8 * M_H + 4 * COLUNA, COLUNA, COLUNA);
        g2d.fillOval(BORDER + 2 * M_W + CORREDOR - COLUNA / 2, BORDER + 2 * M_H, COLUNA, COLUNA);
        g2d.fillOval(BORDER + 2 * M_W + CORREDOR - COLUNA / 2, BORDER + 3 * M_H + COLUNA, COLUNA, COLUNA);
        g2d.fillOval(BORDER + 3 * M_W + CORREDOR - COLUNA, BORDER + 2 * M_H, COLUNA, COLUNA);
        g2d.fillOval(BORDER + 3 * M_W + CORREDOR - COLUNA, BORDER + 3 * M_H + COLUNA, COLUNA, COLUNA);
        g2d.fillOval(BORDER + 3 * M_W + 2 * CORREDOR, 3 * P_H + BORDER, COLUNA, COLUNA);
        g2d.fillOval(BORDER + 3 * M_W + 2 * CORREDOR + P_W / 2 - COLUNA / 2, 4 * P_H + BORDER + 3 * COLUNA / 2, COLUNA, COLUNA);
        g2d.fillOval(BORDER + 3 * M_W + 2 * CORREDOR + P_W / 2 - COLUNA / 2, 5 * P_H + BORDER + 7 * COLUNA / 2, COLUNA, COLUNA);


        g2d.fillOval(BORDER + 3 * M_W + 2 * CORREDOR, 7 * P_H + BORDER + 5* COLUNA, COLUNA, COLUNA);
        g2d.fillOval(BORDER + 3 * M_W + 2 * CORREDOR, 9 * P_H + BORDER + 6* COLUNA, COLUNA, COLUNA);


        label.setIcon(new ImageIcon(image));
    }
}
