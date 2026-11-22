package br.ucsal.delivery.pagamento;

public enum TipoPagamento {
	PIX, CARTAO, DINHEIRO;

	public static TipoPagamento deCodigo(int codigo) {
		return switch (codigo) {
		case 1 -> PIX;
		case 2 -> CARTAO;
		case 3 -> DINHEIRO;
		default -> throw new IllegalArgumentException("Forma de pagamento invalida: " + codigo);
		};
	}
}
