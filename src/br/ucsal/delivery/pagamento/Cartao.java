package br.ucsal.delivery.pagamento;

public class Cartao implements Pagamento {
	private static final double LIMITE = 500.0;

	@Override
	public boolean processar(double valor) {
		if (valor <= 0 || valor > LIMITE) {
			System.out.printf("[CARTAO] Recusado: R$ %.2f (limite R$ %.2f)%n", valor, LIMITE);
			return false;
		}
		System.out.printf("[CARTAO] Aprovado: R$ %.2f%n", valor);
		return true;
	}

	@Override
	public String getDescricao() {
		return "Cartao";
	}

	@Override
	public String toString() {
		return getDescricao();
	}
}
