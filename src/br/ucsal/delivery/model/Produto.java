package br.ucsal.delivery.model;

public class Produto implements ItemCardapio {
	private final String descricao;
	private final double preco;

	public Produto(String descricao, double preco) {
		this.descricao = descricao;
		this.preco = preco;
	}

	@Override
	public String getDescricao() {
		return descricao;
	}

	@Override
	public double getPreco() {
		return preco;
	}

	@Override
	public String toString() {
		return String.format("%s - R$ %.2f", getDescricao(), getPreco());
	}
}
