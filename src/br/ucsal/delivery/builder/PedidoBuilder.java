package br.ucsal.delivery.builder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.ucsal.delivery.model.Cliente;
import br.ucsal.delivery.model.ItemCardapio;
import br.ucsal.delivery.model.Pedido;
import br.ucsal.delivery.observer.PedidoObserver;
import br.ucsal.delivery.strategy.CalculoFreteStrategy;
import br.ucsal.delivery.strategy.DescontoStrategy;

public class PedidoBuilder {
	private Cliente cliente;
	private String endereco;
	private String observacao = "";
	private CalculoFreteStrategy frete = subtotal -> 0;
	private DescontoStrategy desconto = subtotal -> 0;
	private final Map<ItemCardapio, Integer> itens = new LinkedHashMap<>();
	private final List<PedidoObserver> observers = new ArrayList<>();

	public PedidoBuilder comCliente(Cliente cliente) {
		this.cliente = cliente;
		return this;
	}

	public PedidoBuilder comCliente(String nome, String telefone) {
		if (nome == null || nome.isBlank()) {
			throw new IllegalArgumentException("Nome do cliente obrigatorio");
		}
		if (telefone == null || telefone.isBlank()) {
			throw new IllegalArgumentException("Telefone do cliente obrigatorio");
		}
		return comCliente(new Cliente(nome, telefone));
	}

	public PedidoBuilder comEndereco(String endereco) {
		this.endereco = endereco;
		return this;
	}

	public PedidoBuilder comObservacao(String observacao) {
		this.observacao = observacao == null ? "" : observacao;
		return this;
	}

	public PedidoBuilder comFrete(CalculoFreteStrategy frete) {
		if (frete == null) {
			throw new IllegalArgumentException("Frete obrigatorio");
		}
		this.frete = frete;
		return this;
	}

	public PedidoBuilder comDesconto(DescontoStrategy desconto) {
		if (desconto == null) {
			throw new IllegalArgumentException("Desconto obrigatorio");
		}
		this.desconto = desconto;
		return this;
	}

	public PedidoBuilder comObserver(PedidoObserver observer) {
		if (observer != null) {
			observers.add(observer);
		}
		return this;
	}

	public PedidoBuilder adicionarItem(ItemCardapio item) {
		return adicionarItem(item, 1);
	}

	public PedidoBuilder adicionarItem(ItemCardapio item, int quantidade) {
		if (item == null) {
			throw new IllegalArgumentException("Item invalido");
		}
		if (quantidade <= 0) {
			throw new IllegalArgumentException("Quantidade deve ser maior que zero");
		}
		itens.merge(item, quantidade, Integer::sum);
		return this;
	}

	public Pedido build() {
		if (cliente == null) {
			throw new IllegalArgumentException("Pedido sem cliente");
		}
		if (endereco == null || endereco.isBlank()) {
			throw new IllegalArgumentException("Pedido sem endereco");
		}
		if (itens.isEmpty()) {
			throw new IllegalArgumentException("Pedido sem itens");
		}
		Pedido pedido = new Pedido(cliente, endereco, observacao, frete, desconto);
		itens.forEach(pedido::adicionarItem);
		observers.forEach(pedido::adicionarObserver);
		return pedido;
	}
}
