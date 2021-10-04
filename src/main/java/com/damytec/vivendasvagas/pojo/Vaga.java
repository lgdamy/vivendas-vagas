package com.damytec.vivendasvagas.pojo;

/**
 * @author lgdamy@raiadrogasil.com on 29/09/2021
 */
public abstract class Vaga implements Comparable {

    int x;
    int y;
    int width;
    int height;
    private String nome;
    private Corredor corredor;
    private Estado estado = new Estado();

    public Vaga(int x, int y, int width, int height, String nome, Corredor corredor) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height= height;
        this.nome = nome;
        this.corredor =  corredor;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getNome() {
        return nome;
    }

    public Corredor getCorredor() {
        return corredor;
    }

    public String getDescription() {
        if (this.estado.isOcupada()) {
            return String.format("%s (%s)", this.nome, this.estado.getApartamento());
        }
        return nome;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Estado getEstado() {
        return estado;
    }

    public boolean isSelecionada() {
        return this.getEstado().isSelecionada();
    }
    public void select() {
        this.getEstado().setSelecionada(true);
    }
    public void unselect() {
        this.getEstado().setSelecionada(false);
    }

    public enum Corredor {
        ESQUERDA,
        DIREITA
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Vaga)) {
            return -1;
        }
        return Integer.valueOf((this).getNome().replaceAll("[^\\d]", "")).compareTo(
                Integer.valueOf(((Vaga) o).getNome().replaceAll("[^\\d]", ""))
        );
    }
}
