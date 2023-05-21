package br.contatos.contato;


public class Contato {
    private String[] telefones;
    private String nome;

    public Contato() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String[] getTelefones() {
        return telefones;
    }

    public void setTelefone(int indice, String telefone) {
        this.telefones[indice] = telefone;
    }
}

