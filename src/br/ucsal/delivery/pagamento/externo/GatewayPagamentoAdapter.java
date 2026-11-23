package br.ucsal.delivery.pagamento.externo;

import br.ucsal.delivery.pagamento.Pagamento;

// Padrão: Adapter — faz a ApiBancoExterna (centavos + código de retorno) caber na nossa interface Pagamento
public class GatewayPagamentoAdapter implements Pagamento {
	private final ApiBancoExterna banco;

	public GatewayPagamentoAdapter(ApiBancoExterna banco) {
		this.banco = banco;
	}

	@Override
	public boolean processar(double valor) {
		return banco.cobrar(Math.round(valor * 100)) == ApiBancoExterna.APROVADO;
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
