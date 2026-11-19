package br.ucsal.delivery.pagamento;

public interface Pagamento {
	boolean processar(double valor);

	String getDescricao();
}
