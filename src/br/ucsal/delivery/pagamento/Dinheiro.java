package br.ucsal.delivery.pagamento;

public class Dinheiro implements Pagamento {
	@Override
	public boolean processar(double valor) {
		if (valor <= 0) {
			return false;
		}
		System.out.printf("[DINHEIRO] Pagamento de R$ %.2f na entrega%n", valor);
		return true;
	}

	@Override
	public String getDescricao() {
		return "Dinheiro";
	}

	@Override
	public String toString() {
		return getDescricao();
	}
}
