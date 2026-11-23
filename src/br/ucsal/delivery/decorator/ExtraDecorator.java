package br.ucsal.delivery.decorator;

import br.ucsal.delivery.exception.PedidoInvalidoException;
import br.ucsal.delivery.model.ItemCardapio;

// Padrão: Decorator — embrulha um ItemCardapio para somar extras sem criar uma subclasse por combinação
public abstract class ExtraDecorator implements ItemCardapio {
	private final ItemCardapio item;

	protected ExtraDecorator(ItemCardapio item) {
		if (item == null) {
			throw new PedidoInvalidoException("Extra precisa de um item base");
		}
		this.item = item;
	}

	@Override
	public String getDescricao() {
		return item.getDescricao();
	}

	@Override
	public double getPreco() {
		return item.getPreco();
	}

	@Override
	public String toString() {
		return String.format("%s - R$ %.2f", getDescricao(), getPreco());
	}
}
