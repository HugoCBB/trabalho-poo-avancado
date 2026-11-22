package br.ucsal.delivery.pagamento;

public class Pix implements Pagamento {
	@Override
	public boolean processar(double valor) {
		if (valor <= 0) {
			return false;
		}
		System.out.printf("[PIX] Gerando QR Code e confirmando R$ %.2f%n", valor);
		return true;
	}

	@Override
	public String getDescricao() {
		return "Pix";
	}

	@Override
	public String toString() {
		return getDescricao();
	}
}
