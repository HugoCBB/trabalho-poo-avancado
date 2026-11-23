package br.ucsal.delivery.decorator;

import br.ucsal.delivery.model.ItemCardapio;

public class QueijoExtra extends ExtraDecorator {
	public QueijoExtra(ItemCardapio item) {
		super(item);
	}

	@Override
	public String getDescricao() {
		return super.getDescricao() + " + queijo";
	}

	@Override
	public double getPreco() {
		return super.getPreco() + 3.0;
	}
}
