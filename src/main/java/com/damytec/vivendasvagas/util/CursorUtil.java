package com.damytec.vivendasvagas.util;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author lgdamy@raiadrogasil.com on 03/10/2021
 */
public class CursorUtil {

    public static final Polygon PONTA_SETA = new Polygon();
    public static final Cursor CURSOR_DRAG;
    public static final Cursor CURSOR_INVISIVEL;
    public static final Cursor CURSOR_PROIBIDO;
    public static final Cursor CURSOS_IDLE;
    public static final Map<String, Cursor> CURSOR_UNIDADES;

    private CursorUtil() {
    }

    static {
        PONTA_SETA.addPoint(0, 0);
        PONTA_SETA.addPoint(-5, -12);
        PONTA_SETA.addPoint(0, -10);
        PONTA_SETA.addPoint(5, -12);
    }

    //CURSOR DRAG
    static {
        BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        AffineTransform t1 = g2d.getTransform();
        AffineTransform t2 = (AffineTransform) t1.clone();
        t2.rotate(Math.toRadians(135));
        t2.scale(1.25f, 1.25f);
        g2d.setTransform(t2);
        g2d.fill(PONTA_SETA);
        g2d.setTransform(t1);
        g2d.drawLine(10, 10, 12, 12);
        g2d.drawLine(14, 14, 16, 16);
        CURSOR_DRAG = Toolkit.getDefaultToolkit().createCustomCursor(
                image, new Point(0, 0), "SETA CURSOR");
    }

    //CURSOR INVISIVEL
    static {
        CURSOR_INVISIVEL = Toolkit.getDefaultToolkit().createCustomCursor(
                new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "blank cursor");
    }

    //CURSOR UNIDADES
    static {
        CURSOR_UNIDADES = new HashMap<>();
        Stream.of(11, 12, 21, 22, 31, 32, 41, 42, 51, 52, 61, 62, 71, 72, 13, 14, 23, 24, 33, 34, 43, 44, 53, 54, 63, 64, 73, 74)
                .map(i -> String.format("%02d", i))
                .forEach(i -> {
                    BufferedImage image = new BufferedImage(36, 36, BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2d = image.createGraphics();
                    g2d.setColor(Color.BLACK);
                    g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    AffineTransform t1 = g2d.getTransform();
                    AffineTransform t2 = (AffineTransform) t1.clone();
                    t2.rotate(Math.toRadians(135));
                    t2.scale(1.25f, 1.25f);
                    g2d.setTransform(t2);
                    g2d.fill(PONTA_SETA);
                    g2d.setTransform(t1);
                    g2d.setColor(Color.BLACK);
                    g2d.drawOval(8, 8, 20, 20);
                    g2d.setColor(Color.WHITE);
                    g2d.fillOval(8, 8, 20, 20);
                    g2d.setColor(Color.BLACK);
                    g2d.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
                    g2d.drawString(i, 11, 23);
                    CURSOR_UNIDADES.put(i, Toolkit.getDefaultToolkit().createCustomCursor(
                            image, new Point(0, 0), String.format("CURSOR_UNIDADE_%s", i)));
                    g2d.setTransform(t1);
                });
    }

    //CURSOR IDLE
    static {
        BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        AffineTransform t1 = g2d.getTransform();
        AffineTransform t2 = (AffineTransform) t1.clone();
        t2.rotate(Math.toRadians(135));
        t2.scale(1.25f, 1.25f);
        g2d.setTransform(t2);
        g2d.fill(PONTA_SETA);
        g2d.setTransform(t1);
        g2d.setColor(Color.RED);
        g2d.drawLine(10, 10, 15, 15);
        g2d.drawLine(10, 15, 15, 10);
        CURSOR_PROIBIDO = Toolkit.getDefaultToolkit().createCustomCursor(
                image, new Point(0, 0), "CURSOR_PROIBIDO");
    }

    static {
        BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        AffineTransform t1 = g2d.getTransform();
        AffineTransform t2 = (AffineTransform) t1.clone();
        t2.rotate(Math.toRadians(135));
        t2.scale(1.25f, 1.25f);
        g2d.setTransform(t2);
        g2d.fill(PONTA_SETA);
        g2d.setTransform(t1);
        CURSOS_IDLE = Toolkit.getDefaultToolkit().createCustomCursor(
                image, new Point(0, 0), "CURSOR_IDLE");
    }
}


