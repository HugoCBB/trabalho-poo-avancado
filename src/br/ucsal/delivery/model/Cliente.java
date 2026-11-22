package br.ucsal.delivery.model;

import br.ucsal.delivery.exception.PedidoInvalidoException;

public class Cliente {
	private final String nome;
	private final String telefone;

	public Cliente(String nome, String telefone) {
		if (nome == null || nome.isBlank()) {
			throw new PedidoInvalidoException("Nome do cliente nao pode ser vazio");
		}
		if (telefone == null || telefone.isBlank()) {
			throw new PedidoInvalidoException("Telefone do cliente nao pode ser vazio");
		}
		this.nome = nome.trim();
		this.telefone = telefone.trim();
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