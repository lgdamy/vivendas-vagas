package com.damytec.vivendasvagas.ui;

import com.damytec.vivendasvagas.pojo.Drag;
import com.damytec.vivendasvagas.pojo.Vaga;
import com.damytec.vivendasvagas.pojo.Vaga.Corredor;
import com.damytec.vivendasvagas.util.Constant;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.damytec.vivendasvagas.util.Constant.BORDER;
import static com.damytec.vivendasvagas.util.Constant.CANVAS_H;
import static com.damytec.vivendasvagas.util.Constant.CANVAS_W;
import static com.damytec.vivendasvagas.util.Constant.COLUNA;
import static com.damytec.vivendasvagas.util.Constant.CORREDOR;
import static com.damytec.vivendasvagas.util.Constant.M_H;
import static com.damytec.vivendasvagas.util.Constant.M_W;
import static com.damytec.vivendasvagas.util.Constant.P_H;
import static com.damytec.vivendasvagas.util.Constant.P_W;
import static com.damytec.vivendasvagas.util.CursorUtil.PONTA_SETA;

/**
 * @author lgdamy@raiadrogasil.com on 29/09/2021
 */
public class PlantaGaragem extends JPanel {

    JLabel label;

    public PlantaGaragem() {
        super();
        this.label = new JLabel("");
    }

    public void draw(List<Vaga> vagas, Drag drag) {
        setLayout(new FlowLayout());
        add(this.label);
        setBorder(null);
        label.setIcon(new ImageIcon(toImage(vagas, drag)));
    }

    public static Image toImage(List<Vaga> vagas, Drag drag) {
        BufferedImage image = new BufferedImage(CANVAS_W, Constant.CANVAS_H, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        vagas(g2d,vagas);
        caixadaguaEscadaElevador(g2d);
        colunas(g2d);
        setasCorredores(g2d,vagas);

        if (drag != null) {
            setaDrag(g2d, drag);
        }

        return image;
    }

    private static void caixadaguaEscadaElevador(Graphics2D g2d) {
        g2d.setPaint(Color.DARK_GRAY);
        g2d.fillPolygon(new Polygon(new int[]{BORDER, BORDER + M_W , 3 * M_W / 2 + BORDER, 3 * M_W / 2 + BORDER , BORDER},new int[]{BORDER + 7 * M_H + 4 * COLUNA, BORDER + 7 * M_H + 4 * COLUNA, CANVAS_H - BORDER, CANVAS_H - BORDER, CANVAS_H - BORDER }, 5));
        g2d.fillRect(BORDER + 2 * M_W + CORREDOR + COLUNA, 6 * M_H + 5 * COLUNA + BORDER,M_W - COLUNA,2 * M_H - COLUNA);
        g2d.fillRect(BORDER + 2 * M_W + CORREDOR , 4 * M_H + 2 * COLUNA + BORDER ,M_W,2 * M_H + COLUNA);
        g2d.setPaint(Color.BLACK);
        g2d.drawPolygon(new Polygon(new int[]{BORDER, BORDER + M_W , 3 * M_W / 2 + BORDER, 3 * M_W / 2 + BORDER , BORDER},new int[]{BORDER + 7 * M_H + 4 * COLUNA, BORDER + 7 * M_H + 4 * COLUNA, CANVAS_H - BORDER, CANVAS_H - BORDER, CANVAS_H - BORDER }, 5));
        g2d.drawRect(BORDER + 2 * M_W + CORREDOR + COLUNA, 6 * M_H + 5 * COLUNA + BORDER,M_W - COLUNA,2 * M_H - COLUNA);
        g2d.drawRect(BORDER + 2 * M_W + CORREDOR , 4 * M_H + 2 * COLUNA + BORDER ,M_W,2 * M_H + COLUNA);
        g2d.setPaint(Color.WHITE);
        g2d.drawString("CAIXA", BORDER * 2, BORDER + 7 * M_H + 5 * COLUNA);
        g2d.drawString("D'\u00c1GUA", BORDER * 2, BORDER + 7 * M_H + 5 * COLUNA + 14);
        g2d.drawString("ESCADARIA", 2 * M_W + CORREDOR + 2 * BORDER, 4 * M_H + BORDER + 3 * COLUNA);
        g2d.drawString("ELEVADOR",  2 * BORDER + 2 * M_W + CORREDOR + COLUNA , 6 * M_H + 6 * COLUNA + BORDER );
    }

    private static void vagas(Graphics2D g2d, List<Vaga> vagas) {
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
            g2d.drawString(v.getNome(), v.getX() + 10 + BORDER, v.getY() + BORDER +  5 + v.getHeight() / 2);
            if (v.getEstado().isOcupada()) {
                g2d.drawString(v.getEstado().getApartamento(), v.getX() + v.getWidth() - g2d.getFontMetrics().stringWidth(v.getEstado().getApartamento()) - BORDER,v.getY() + BORDER +  5 + v.getHeight() / 2 );
            }
        });
    }

    private static void colunas(Graphics2D g2d) {
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

        g2d.fillOval(BORDER + 3 * M_W + 2 * CORREDOR, 7 * P_H + BORDER + 11 * COLUNA / 2, COLUNA, COLUNA);
        g2d.fillOval(BORDER + 3 * M_W + 2 * CORREDOR, 9 * P_H + BORDER + 15 * COLUNA / 2, COLUNA, COLUNA);
    }

    private static void setasCorredores(Graphics2D g2d, List<Vaga> vagas) {
        Optional<Corredor> corredor = vagas.stream().filter(Vaga::isSelecionada).map(Vaga::getCorredor).filter(Objects::nonNull).findFirst();

        if (corredor.isPresent()) {
            setasCorredores(g2d, Collections.emptyList());
        }

        g2d.setStroke(new BasicStroke(8, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setColor(corredor.isPresent() ? Color.GRAY : Color.LIGHT_GRAY);
        AffineTransform t1,t2;
        if (!corredor.isPresent() || Corredor.DIREITA == corredor.get()) {
            g2d.drawArc(CANVAS_W - 276, CANVAS_H - 50, 200, 100, 0, 65);
            g2d.drawArc(CANVAS_W - 160, CANVAS_H - 140, 100, 100, 185, 65);
            t1 = g2d.getTransform();
            t2 = (AffineTransform)t1.clone();
            t2.translate(CANVAS_W-158.5f, CANVAS_H - 110);
            t2.rotate(Math.toRadians(180));
            t2.scale(2.5f,2.5f);
            g2d.setTransform(t2);
            g2d.fill(PONTA_SETA);
            g2d.setTransform(t1);
        }
        if (!corredor.isPresent() || Corredor.ESQUERDA == corredor.get()) {
            g2d.drawArc(CANVAS_W - 169, CANVAS_H - 20, 100, 100, 0, 90);
            g2d.drawLine(CANVAS_W - 122, CANVAS_H - 20, CANVAS_W - 250, CANVAS_H - 20);
            t1 = g2d.getTransform();
            t2 = (AffineTransform)t1.clone();
            t2.translate(CANVAS_W - 278, CANVAS_H - 20);
            t2.rotate(Math.toRadians(90));
            t2.scale(2.5f,2.5f);
            g2d.setTransform(t2);
            g2d.fill(PONTA_SETA);
            g2d.setTransform(t1);
        }
    }

    private static void setaDrag(Graphics2D g2d, Drag drag) {
        double angulo = Math.atan2(drag.getDestino().getY() - drag.getOrigem().getY(),drag.getDestino().getX() - drag.getOrigem().getX());
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,10f,new float[]{2f,8f},0.2f));

        g2d.drawLine((int)drag.getOrigem().getX()- BORDER, (int)drag.getOrigem().getY() - BORDER, (int)(drag.getDestino().getX() - BORDER - 10* Math.cos(angulo)),(int)(drag.getDestino().getY() - BORDER - 10 * Math.sin(angulo)));
        AffineTransform t1 = g2d.getTransform();
        AffineTransform t2 = (AffineTransform)t1.clone();
        t2.translate(drag.getDestino().getX()-BORDER, drag.getDestino().getY()-BORDER);
        t2.rotate(angulo - Math.PI / 2);
        t2.scale(1.25f,1.25f);
        g2d.setTransform(t2);
        g2d.fill(PONTA_SETA);

        t2 = (AffineTransform)t1.clone();
        g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setTransform(t2);
        g2d.translate(drag.getDestino().getX() - BORDER + - 25 * Math.cos(angulo), drag.getDestino().getY() - BORDER - 25 * Math.sin(angulo) );
        g2d.setColor(Color.BLACK);
        g2d.drawOval(-10,-10,20,20);
        g2d.setColor(Color.WHITE);
        g2d.fillOval(-10,-10,20,20);
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
        g2d.drawString(drag.getSujeito().getEstado().getApartamento(),-7, 5);
        g2d.setTransform(t1);

    }
}
