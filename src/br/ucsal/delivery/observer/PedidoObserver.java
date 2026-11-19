package br.ucsal.delivery.observer;

import br.ucsal.delivery.model.Pedido;

public interface PedidoObserver {
	void atualizar(Pedido pedido);
}
