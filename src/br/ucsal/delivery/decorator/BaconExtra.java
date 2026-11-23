package br.ucsal.delivery.decorator;

import br.ucsal.delivery.model.ItemCardapio;

public class BaconExtra extends ExtraDecorator {
	public BaconExtra(ItemCardapio item) {
		super(item);
	}

	@Override
	public String getDescricao() {
		return super.getDescricao() + " + bacon";
	}

	@Override
	public double getPreco() {
		return super.getPreco() + 4.0;
	}
}
