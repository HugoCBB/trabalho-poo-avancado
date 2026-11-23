package br.ucsal.delivery.pagamento;

import br.ucsal.delivery.pagamento.externo.ApiBancoExterna;
import br.ucsal.delivery.pagamento.externo.GatewayPagamentoAdapter;

public abstract class PagamentoFactory {

	// Factory Method: subclasses ou esta fabrica decidem qual produto instanciar
	protected abstract Pagamento criarPagamento();

	public Pagamento obter() {
		return criarPagamento();
	}

	
	public static Pagamento criar(TipoPagamento tipo) {
		if (tipo == null) {
			throw new IllegalArgumentException("Tipo de pagamento obrigatorio");
		}
		return switch (tipo) {
		case PIX -> new PixFactory().obter();
		case CARTAO -> new CartaoFactory().obter();
		case DINHEIRO -> new DinheiroFactory().obter();
		};
	}

	public static class PixFactory extends PagamentoFactory {
		@Override
		protected Pagamento criarPagamento() {
			return new Pix();
		}
	}

	public static class CartaoFactory extends PagamentoFactory {
		@Override
		protected Pagamento criarPagamento() {
			return new GatewayPagamentoAdapter(new ApiBancoExterna());
		}
	}

	public static class DinheiroFactory extends PagamentoFactory {
		@Override
		protected Pagamento criarPagamento() {
			return new Dinheiro();
		}
	}
}
