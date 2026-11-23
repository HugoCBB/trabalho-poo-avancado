package br.ucsal.delivery.decorator;

import br.ucsal.delivery.model.ItemCardapio;

public class MolhoExtra extends ExtraDecorator {
	public MolhoExtra(ItemCardapio item) {
		super(item);
	}

	@Override
	public String getDescricao() {
		return super.getDescricao() + " + molho especial";
	}

	@Override
	public double getPreco() {
		return super.getPreco() + 2.0;
	}
}
