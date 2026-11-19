package br.ucsal.delivery.model;

public enum StatusPedido {
	RECEBIDO, EM_PREPARO, PRONTO, SAIU_PARA_ENTREGA, ENTREGUE;

	public StatusPedido proximo() {
		StatusPedido[] todos = values();
		return ordinal() < todos.length - 1 ? todos[ordinal() + 1] : this;
	}
}
