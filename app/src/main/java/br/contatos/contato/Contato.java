package br.contatos.contato;

import java.util.ArrayList;
import java.util.List;

public class Contato {
    private List<Telefone> telefones;
    private String nome;

    public Contato() {
        telefones = new ArrayList<>();
        telefones.add(new Telefone());
        telefones.add(new Telefone());
        telefones.add(new Telefone());
    }

    public Contato(String nome, String numero, String tipo) {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    public void setTelefone(int indice, String tipo, String numero) {
        Telefone telefone = telefones.get(indice);
        telefone.setTipo(tipo);
        telefone.setNumero(numero);
    }

    public List<String> getOpcoesTipoTelefone() {
        List<String> opcoesTipoTelefone = new ArrayList<>();
        opcoesTipoTelefone.add("Casa");
        opcoesTipoTelefone.add("Celular");
        opcoesTipoTelefone.add("Trabalho");
        return opcoesTipoTelefone;
    }

    public boolean validarCampos() {
        for (Telefone telefone : telefones) {
            if (telefone.getTipo().isEmpty() || telefone.getNumero().isEmpty()) {
                return false; // Campo obrigatório não preenchido
            }
        }
        return true; // Todos os campos foram preenchidos
    }

    public int getTelefone(int i) {
        return Integer.parseInt(telefones.get(i).getNumero());
    }

    public int getTipo(int i) {
        return Integer.parseInt(telefones.get(i).getTipo());
    }

    public void setTipo(int i, int selectedItemPosition) {
        telefones.get(i).setTipo(String.valueOf(selectedItemPosition));
    }

    public void setTelefone(int i, String toString) {
        telefones.get(i).setNumero(toString);
    }

    public static class Telefone {

        private String tipo;
        private String numero;

        public Telefone() {
        }

        public Telefone(String tipo, String numero) {
            this.tipo = tipo;
            this.numero = numero;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public String getNumero() {
            return numero;
        }

        public void setNumero(String numero) {
            this.numero = numero;
        }
    }
}
