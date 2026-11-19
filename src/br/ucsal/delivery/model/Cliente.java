package br.ucsal.delivery.model;

public class Cliente {
	private final String nome;
	private final String telefone;

	public Cliente(String nome, String telefone) {
		this.nome = nome;
		this.telefone = telefone;
	}

	public String getNome() {
		return nome;
	}

	public String getTelefone() {
		return telefone;
	}

	@Override
	public String toString() {
		return nome + " (" + telefone + ")";
	}
}
