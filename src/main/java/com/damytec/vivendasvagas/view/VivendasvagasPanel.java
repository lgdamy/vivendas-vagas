package com.damytec.vivendasvagas.view;

import com.damytec.vivendasvagas.pojo.Drag;
import com.damytec.vivendasvagas.pojo.Sorteado;
import com.damytec.vivendasvagas.pojo.Vaga;
import com.damytec.vivendasvagas.pojo.Vaga.Corredor;
import com.damytec.vivendasvagas.pojo.VagaM;
import com.damytec.vivendasvagas.pojo.VagaP;
import com.damytec.vivendasvagas.service.ExportService;
import com.damytec.vivendasvagas.service.SorteioService;
import com.damytec.vivendasvagas.ui.BaseWindow;
import com.damytec.vivendasvagas.ui.PlantaGaragem;
import com.damytec.vivendasvagas.util.CursorUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.damytec.vivendasvagas.util.Constant.BORDER;
import static com.damytec.vivendasvagas.util.Constant.COLUNA;
import static com.damytec.vivendasvagas.util.Constant.CORREDOR;
import static com.damytec.vivendasvagas.util.Constant.M_H;
import static com.damytec.vivendasvagas.util.Constant.M_W;
import static com.damytec.vivendasvagas.util.Constant.P_H;
import static com.damytec.vivendasvagas.util.CursorUtil.CURSOR_DRAG;
import static com.damytec.vivendasvagas.util.CursorUtil.CURSOR_INVISIVEL;
import static com.damytec.vivendasvagas.util.CursorUtil.CURSOR_PROIBIDO;
import static com.damytec.vivendasvagas.util.CursorUtil.CURSOR_UNIDADES;

/**
 * @author lgdamy on 25/01/2021
 */
public class VivendasvagasPanel implements BaseWindow.ContentForm {
    private JPanel root;
    private JPanel plantaGaragem;
    private JPanel checkboxPanel;
    private JPanel rightMenuPanel;
    private JButton sortearButton;
    private JLabel sorteadoLabel;
    private JButton exportarButton;

    private final Sorteado sorteado = new Sorteado();

    private Drag drag;

    private final List<Vaga> vagas;


    public VivendasvagasPanel() {
        vagas = Arrays.asList(
                new VagaM(0, 0, "M23", Corredor.ESQUERDA),
                new VagaM(0, M_H, "M24", Corredor.ESQUERDA),
                new VagaM(0, 2 * M_H + COLUNA, "M25", Corredor.ESQUERDA),
                new VagaM(0, 3 * M_H + COLUNA, "M26", Corredor.ESQUERDA),
                new VagaM(0, 4 * M_H + 2 * COLUNA, "M27", Corredor.ESQUERDA),
                new VagaM(0, 5 * M_H + 2 * COLUNA, "M28", Corredor.ESQUERDA),
                new VagaM(0, 6 * M_H + 3 * COLUNA, "M29", Corredor.ESQUERDA),

                new VagaM(M_W + CORREDOR, 0, "M22", Corredor.ESQUERDA),
                new VagaM(M_W + CORREDOR, M_H, "M21", Corredor.ESQUERDA),
                new VagaM(M_W + CORREDOR, 2 * M_H + COLUNA, "M20", Corredor.ESQUERDA),
                new VagaM(M_W + CORREDOR, 3 * M_H + 2 * COLUNA, "M19", Corredor.ESQUERDA),
                new VagaM(M_W + CORREDOR, 4 * M_H + 3 * COLUNA, "M18", Corredor.ESQUERDA),
                new VagaM(M_W + CORREDOR, 5 * M_H + 3 * COLUNA, "M17", Corredor.ESQUERDA),
                new VagaM(M_W + CORREDOR, 6 * M_H + 4 * COLUNA, "M16", Corredor.ESQUERDA),
                new VagaM(M_W + CORREDOR, 7 * M_H + 4 * COLUNA, "M15", Corredor.ESQUERDA),

                new VagaM(2 * M_W + CORREDOR, 0, "M11", Corredor.DIREITA),
                new VagaM(2 * M_W + CORREDOR, M_H, "M12", Corredor.DIREITA),
                new VagaM(2 * M_W + CORREDOR, 2 * M_H + COLUNA, "M13", Corredor.DIREITA),
                new VagaM(2 * M_W + CORREDOR, 3 * M_H + 2 * COLUNA, "M14", Corredor.DIREITA),

                new VagaP(3 * M_W + 2 * CORREDOR, 0, "P10"),
                new VagaP(3 * M_W + 2 * CORREDOR, P_H, "P09"),
                new VagaP(3 * M_W + 2 * CORREDOR, 2 * P_H, "P08"),
                new VagaP(3 * M_W + 2 * CORREDOR, 3 * P_H + COLUNA, "P07"),
                new VagaP(3 * M_W + 2 * CORREDOR, 4 * P_H + 3 * COLUNA, "P06"),
                new VagaP(3 * M_W + 2 * CORREDOR, 5 * P_H + 5 * COLUNA, "P05"),
                new VagaP(3 * M_W + 2 * CORREDOR, 6 * P_H + 5 * COLUNA, "P04"),
                new VagaP(3 * M_W + 2 * CORREDOR, 7 * P_H + 7 * COLUNA, "P03"),
                new VagaP(3 * M_W + 2 * CORREDOR, 8 * P_H + 7 * COLUNA, "P02")
        );

        ((PlantaGaragem) plantaGaragem).draw(vagas, drag);
        plantaGaragem.addMouseMotionListener(this.plantaMotionListener());
        plantaGaragem.addMouseListener(this.plantaClickListener());
        sortearButton.addActionListener(this::sortear);
        exportarButton.addActionListener(this::exportar);
        getCheckBoxes().forEach(cb -> cb.addMouseListener(this.checkBoxClickListener()));
        sorteado.addObserver((o, arg) -> checkboxSupervisor());
    }

    private MouseMotionListener plantaMotionListener() {
        return new MouseAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {
                this.motion(e);
                if (drag != null) {
                    drag = new Drag(drag.getOrigem(), e.getPoint(), drag.getSujeito());
                }
                ((PlantaGaragem) plantaGaragem).draw(vagas, drag);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                this.motion(e);
                ((PlantaGaragem) plantaGaragem).draw(vagas, drag);
            }

            private void motion(MouseEvent e) {
                vagas.forEach(Vaga::unselect);
                vagaSelecionada(e).ifPresent(Vaga::select);
                cursorPlanta();
            }
        };
    }

    private MouseListener plantaClickListener() {
        return new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                Optional<Vaga> de = vagaSelecionada(e).filter(v -> v.getEstado().isOcupada());
                drag = de.isPresent() ? new Drag(new Point(e.getX() + 1, e.getY() + 1), e.getPoint(), de.get()) : null;
                ((PlantaGaragem) plantaGaragem).draw(vagas, drag);
                cursorPlanta();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                cursorPlanta();
                try {
                    Optional<Vaga> vaga = vagaSelecionada(e);
                    if (!vaga.isPresent()) {
                        return;
                    }
                    if (drag != null && vaga.get() != drag.getSujeito()) {
                        if (drag.getSujeito().getEstado().isOcupada() && !vaga.get().getEstado().isOcupada()) {
                            vaga.get().getEstado().setApartamento(drag.getSujeito().getEstado().getApartamento());
                            drag.getSujeito().getEstado().setApartamento(null);
                        }
                        return;
                    }
                    if (sorteado.getUnidade() == null && !vaga.get().getEstado().isOcupada()) {
                        JOptionPane.showMessageDialog(root, "Sorteie uma unidade antes", "ERRO", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (vaga.get().getEstado().isOcupada()) {
                        if (sorteado.getUnidade() != null) {
                            JOptionPane.showMessageDialog(root, String.format("A vaga %s j\u00e1 est\u00e1 ocupada", vaga.get().getNome()), "ERRO", JOptionPane.ERROR_MESSAGE);
                        }
                        return;
                    }
                    vaga.get().getEstado().setApartamento(sorteado.getUnidade());
                    sorteado.setUnidade(null);
                }finally {
                    drag = null;
                    sorteadoLabel.setText(Optional.ofNullable(sorteado.getUnidade()).orElse("--"));
                    vagas.stream().forEach(Vaga::unselect);
                    vagaSelecionada(e).ifPresent(Vaga::select);
                    ((PlantaGaragem) plantaGaragem).draw(vagas, drag);
                    cursorPlanta();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                vagas.stream().forEach(Vaga::unselect);
                ((PlantaGaragem) plantaGaragem).draw(vagas, drag);
            }
        };
    }

    private void cursorPlanta() {
        if (drag != null) {
            plantaGaragem.setCursor(CURSOR_INVISIVEL);
            return;
        }
        if (vagas.stream().anyMatch(v -> v.isSelecionada() && v.getEstado().isOcupada())) {
            plantaGaragem.setCursor(CURSOR_DRAG);
            return;
        }
        if (vagas.stream().anyMatch(Vaga::isSelecionada)) {
            if (sorteado.getUnidade() != null) {
                plantaGaragem.setCursor(CURSOR_UNIDADES.get(sorteado.getUnidade()));
                return;
            }
            plantaGaragem.setCursor(CURSOR_PROIBIDO);
            return;
        }
        plantaGaragem.setCursor(CursorUtil.CURSOS_IDLE);
    }

    private MouseListener checkBoxClickListener() {
        return new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1 && !e.isConsumed()) {
                    e.consume();
                    if (getCheckBoxes().filter(cb -> cb.isSelected() && cb.isEnabled()).count() > 1) {
                        getCheckBoxes().filter(JCheckBox::isEnabled).forEach(cb -> cb.setSelected(false));
                        ((JCheckBox)e.getSource()).setSelected(true);
                    } else {
                        getCheckBoxes().forEach(cb -> cb.setSelected(true));
                    }
                }
            }
        };
    }

    private void exportar(ActionEvent e) {
        ExportService.getInstance().gerarCSV(vagas);
    }

    private void sortear(ActionEvent e) {
        if (sorteado.getUnidade() != null) {
            if (JOptionPane.showConfirmDialog(root, String.format("Unidade %s ainda n\u00e3o escolheu.%nDeseja sortear novamente?", sorteado.getUnidade()), "ATEN\u00c7\u00c3O", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
                return;
            }
            sorteado.setUnidade(null);
        }
        CompletableFuture.runAsync(() -> {
            int i = new Random().nextInt(40) + 20;
            sortearButton.setEnabled(false);
            String sorte = null;
            try {
                while (i-- > 0) {
                    Thread.sleep(50);
                    sorte = SorteioService.getInstance().sortearNumero(getCheckBoxes().filter(JCheckBox::isSelected).map(JCheckBox::getText).collect(Collectors.toList()), vagas);
                    sorteadoLabel.setText(sorte);
                    sorteadoLabel.setEnabled(false);
                }
                sorteado.setUnidade(sorte);
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }).whenComplete((v, throwable) -> {
            if (throwable != null) {
                JOptionPane.showMessageDialog(root, throwable.getCause().getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
            }
            sorteadoLabel.setText(Optional.ofNullable(sorteado.getUnidade()).orElse("--"));
            sortearButton.setEnabled(true);
            sorteadoLabel.setEnabled(true);
            cursorPlanta();
        });
    }


    private void checkboxSupervisor() {
        getCheckBoxes().forEach(cb -> {
            boolean disponivel = vagas.stream().noneMatch(v -> cb.getText().equals(v.getEstado().getApartamento()));
            cb.setEnabled(disponivel);
            cb.setVisible(disponivel);
            cb.setFont(cb.getFont().deriveFont(cb.getText().equals(sorteado.getUnidade()) ? Font.BOLD : Font.PLAIN));
        });
    }

    private Optional<Vaga> vagaSelecionada(MouseEvent e) {
        return vagas.stream().filter(v ->
                e.getX() <= v.getX() + v.getWidth() + 2 * BORDER &&
                        e.getX() >= v.getX() + 2 * BORDER &&
                        e.getY() <= v.getY() + v.getHeight() + 2 * BORDER &&
                        e.getY() >= v.getY() + 2 * BORDER).findFirst();
    }


    @Override
    public JPanel root() {
        return this.root;
    }

    private void createUIComponents() {
        plantaGaragem = new PlantaGaragem();
        checkboxPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2g = ((Graphics2D) g);
                LinearGradientPaint[] colores = new LinearGradientPaint[]{
                        new LinearGradientPaint(0,0,0,getHeight(),
                                new float[]{0f,.15f,.85f,1f},
                                new Color[]{
                                        new Color(226, 226, 226),
                                        new Color(200, 200, 200),
                                        new Color(200, 200, 200),
                                        new Color(169, 169, 169)
                        }),
                        new LinearGradientPaint(0,0,0,getHeight(),
                                new float[]{.0f,.15f,.85f,1f},
                                new Color[]{
                                        new Color(215, 216, 249),
                                        new Color(182, 188, 255),
                                        new Color(182, 188, 255),
                                        new Color(165, 162, 215)
                        }),
                };
                g2g.setPaint(colores[1]);
                g2g.fillRect(0,0, getWidth(), getHeight());
                int x = 0;
                int blockWidth = getWidth() / 7-1;
                int i = 0;
                while (i++ < 7) {
                    g2g.setPaint(colores[i%2]);
                    g2g.fillRect(x, 0, blockWidth, getHeight());
                    x+=blockWidth;
                }
            }
        };
        checkboxPanel.setLayout(new GridLayout(2, 0));
        Stream.of(11, 12, 21, 22, 31, 32, 41, 42, 51, 52, 61, 62, 71, 72, 13, 14, 23, 24, 33, 34, 43, 44, 53, 54, 63, 64, 73, 74)
                .map(i -> {
                            JCheckBox cb = new JCheckBox(String.format("%02d", i), true);
                            cb.setToolTipText("Duplo clique para alternar todos");
                            return cb;
                        }
                ).forEach(checkboxPanel::add);
    }

    private Stream<JCheckBox> getCheckBoxes() {
        return Arrays.stream(checkboxPanel.getComponents()).map(c -> (JCheckBox) c);
    }

//    Sobrescreva esse metodo apenas se sua janela vai mudar de titulo
//    @Override
//    public String title() {
//        return "Meu titulo especial";
//    }
}
