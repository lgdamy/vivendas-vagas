package com.damytec.vivendasvagas.view;

import com.damytec.vivendasvagas.pojo.Vaga;
import com.damytec.vivendasvagas.pojo.VagaM;
import com.damytec.vivendasvagas.pojo.VagaP;
import com.damytec.vivendasvagas.service.CSVService;
import com.damytec.vivendasvagas.service.SorteioService;
import com.damytec.vivendasvagas.ui.BaseWindow;
import com.damytec.vivendasvagas.ui.PlantaGaragem;

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
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.damytec.vivendasvagas.util.Constant.BORDER;
import static com.damytec.vivendasvagas.util.Constant.COLUNA;
import static com.damytec.vivendasvagas.util.Constant.CORREDOR;
import static com.damytec.vivendasvagas.util.Constant.M_H;
import static com.damytec.vivendasvagas.util.Constant.M_W;
import static com.damytec.vivendasvagas.util.Constant.P_H;

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

    private String sorteado = null;

    private final List<Vaga> vagas = Arrays.asList(
            new VagaM(0, 0, "M23"),
            new VagaM(0, M_H, "M24"),
            new VagaM(0, 2 * M_H + COLUNA, "M25"),
            new VagaM(0, 3 * M_H + COLUNA, "M26"),
            new VagaM(0, 4 * M_H + 2 * COLUNA, "M27"),
            new VagaM(0, 5 * M_H + 2 * COLUNA, "M28"),
            new VagaM(0, 6 * M_H + 3 * COLUNA, "M29"),

            new VagaM(M_W + CORREDOR, 0, "M22"),
            new VagaM(M_W + CORREDOR, M_H, "M21"),
            new VagaM(M_W + CORREDOR, 2 * M_H + COLUNA, "M20"),
            new VagaM(M_W + CORREDOR, 3 * M_H + 2 * COLUNA, "M19"),
            new VagaM(M_W + CORREDOR, 4 * M_H + 3 * COLUNA, "M18"),
            new VagaM(M_W + CORREDOR, 5 * M_H + 3 * COLUNA, "M17"),
            new VagaM(M_W + CORREDOR, 6 * M_H + 4 * COLUNA, "M16"),
            new VagaM(M_W + CORREDOR, 7 * M_H + 4 * COLUNA, "M15"),

            new VagaM(2 * M_W + CORREDOR, 0, "M11"),
            new VagaM(2 * M_W + CORREDOR, M_H, "M12"),
            new VagaM(2 * M_W + CORREDOR, 2 * M_H + COLUNA, "M13"),
            new VagaM(2 * M_W + CORREDOR, 3 * M_H + 2 * COLUNA, "M14"),

            new VagaP(3 * M_W + 2 * CORREDOR, 0, "P10"),
            new VagaP(3 * M_W + 2 * CORREDOR, P_H, "P09"),
            new VagaP(3 * M_W + 2 * CORREDOR, 2 * P_H, "P08"),
            new VagaP(3 * M_W + 2 * CORREDOR, 3 * P_H + COLUNA, "P07"),
            new VagaP(3 * M_W + 2 * CORREDOR, 4 * P_H + 3 * COLUNA, "P06"),
            new VagaP(3 * M_W + 2 * CORREDOR, 5 * P_H + 5 * COLUNA, "P05"),
            new VagaP(3 * M_W + 2 * CORREDOR, 6 * P_H + 5 * COLUNA, "P04"),
            new VagaP(3 * M_W + 2 * CORREDOR, 7 * P_H + 6 * COLUNA, "P03"),
            new VagaP(3 * M_W + 2 * CORREDOR, 8 * P_H + 6 * COLUNA, "P02")

    );

    public VivendasvagasPanel() {
        ((PlantaGaragem) plantaGaragem).draw(vagas);
        plantaGaragem.addMouseMotionListener(this.plantaMotionListener());
        plantaGaragem.addMouseListener(this.plantaClickListener());
        sortearButton.addActionListener(this::sortear);
        exportarButton.addActionListener(this::exportar);
        checkboxSupervisor();
    }

    private MouseMotionListener plantaMotionListener() {
        return new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                vagas.forEach(Vaga::unselect);
                vagaSelecionada(e).ifPresent(Vaga::select);
                plantaGaragem.setCursor(vagas.stream().anyMatch(Vaga::isSelecionada) ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) : Cursor.getDefaultCursor());
                ((PlantaGaragem) plantaGaragem).draw(vagas);
            }
        };
    }

    private MouseListener plantaClickListener() {
        return new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Optional<Vaga> vaga = vagaSelecionada(e);
                if (!vaga.isPresent()) {
                    return;
                }
                if (sorteado == null) {
                    JOptionPane.showMessageDialog(root, "Sorteie uma unidade antes", "ERRO", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (vaga.get().getEstado().isOcupada()) {
                    JOptionPane.showMessageDialog(root, String.format("A vaga %s j\u00e1 est\u00e1 ocupada", vaga.get().getNome()), "ERRO", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String question = String.format("Atribuir a vaga %s para a unidade %s?", vaga.get().getNome(), sorteado);
                if (JOptionPane.showConfirmDialog(root, question, "CONFIRMA\u00c7\u00c3O", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    vaga.get().getEstado().setApartamento(sorteado);
                    sorteado = null;
                    sorteadoLabel.setText("--");
                    vagas.stream().forEach(Vaga::unselect);
                    ((PlantaGaragem) plantaGaragem).draw(vagas);
                }
            }
        };
    }

    private void exportar(ActionEvent e) {
        CSVService.getInstance().gerarCSV(vagas);
    }

    private void sortear(ActionEvent e) {
        if (sorteado != null && JOptionPane.showConfirmDialog(root, String.format("Unidade %s ainda n\u00e3o escolheu, deseja sortear novamente?", sorteado), "ATEN\u00c7\u00c3O", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
            return;
        }
        sorteado = null;
        CompletableFuture.runAsync(() -> {
            int i = new Random().nextInt(40) + 20;
            sortearButton.setEnabled(false);
            String sorte = null;
            try {
                while (i-- > 0) {
                    Thread.sleep(50);
                    sorte = SorteioService.getInstance().sortearNumero(Arrays.stream(checkboxPanel.getComponents()).filter(c -> c instanceof JCheckBox).map(c -> (JCheckBox) c).collect(Collectors.toList()), vagas);
                    sorteadoLabel.setText(sorte);
                    sorteadoLabel.setEnabled(false);
                }
                sorteado = sorte;
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
            } finally {
                sorteado = sorte;
            }
        }).whenComplete((v, throwable) -> {
            if (throwable != null) {
                JOptionPane.showMessageDialog(root, throwable.getCause().getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
            }
            sorteadoLabel.setText(Optional.ofNullable(sorteado).orElse("--"));
            sortearButton.setEnabled(true);
            sorteadoLabel.setEnabled(true);
        });
    }


    private void checkboxSupervisor() {
        CompletableFuture.runAsync(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                    vagas.stream().filter(v -> v.getEstado().isOcupada()).forEach(v -> {
                        Arrays.stream(checkboxPanel.getComponents()).map(cb -> (JCheckBox) cb).filter(cb -> cb.getText().equals(v.getEstado().getApartamento())).forEach(cb -> {
                            cb.setEnabled(false);
                        });
                    });
                } catch (InterruptedException ignored) {
                }
            }
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
        checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new GridLayout(2, 0));
        Stream.of(11, 12, 13, 14, 21, 22, 23, 24, 31, 32, 33, 34, 41, 42, 43, 44, 51, 52, 53, 54, 61, 62, 63, 64, 71, 72, 73, 74)
                .map(i -> new JCheckBox(String.format("%02d", i), true))
                .forEach(checkboxPanel::add);
        rightMenuPanel = new JPanel();
        rightMenuPanel.setLayout(new GridLayout(10, 1));
    }

//    Sobrescreva esse metodo apenas se sua janela vai mudar de titulo
//    @Override
//    public String title() {
//        return "Meu titulo especial";
//    }
}
