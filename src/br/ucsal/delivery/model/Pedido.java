package br.ucsal.delivery.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.ucsal.delivery.observer.PedidoObserver;
import br.ucsal.delivery.pagamento.Pagamento;
import br.ucsal.delivery.strategy.CalculoFreteStrategy;
import br.ucsal.delivery.strategy.DescontoStrategy;

public class Pedido {
	private static int proximoId = 1;

	private final int id;
	private final Cliente cliente;
	private final String endereco;
	private final String observacao;
	private final CalculoFreteStrategy frete;
	private final DescontoStrategy desconto;
	private final List<ItemCardapio> itens = new ArrayList<>();
	private final List<PedidoObserver> observers = new ArrayList<>();
	private StatusPedido status = StatusPedido.RECEBIDO;
	private Pagamento pagamento;

	public Pedido(Cliente cliente, String endereco, String observacao, CalculoFreteStrategy frete,
			DescontoStrategy desconto) {
		this.id = proximoId++;
		this.cliente = cliente;
		this.endereco = endereco;
		this.observacao = observacao;
		this.frete = frete;
		this.desconto = desconto;
	}

	public void adicionarItem(ItemCardapio item) {
		itens.add(item);
	}

	public void adicionarItem(ItemCardapio item, int quantidade) {
		for (int i = 0; i < quantidade; i++) {
			adicionarItem(item);
		}
	}

	public void adicionarObserver(PedidoObserver observer) {
		observers.add(observer);
	}

	public void removerObserver(PedidoObserver observer) {
		observers.remove(observer);
	}

	public void alterarStatus(StatusPedido novoStatus) {
		this.status = novoStatus;
		observers.forEach(o -> o.atualizar(this));
	}

	public void avancarStatus() {
		alterarStatus(status.proximo());
	}

	public double calcularSubtotal() {
		return itens.stream().mapToDouble(ItemCardapio::getPreco).sum();
	}

	public double calcularFrete() {
		return frete.calcular(calcularSubtotal());
	}

	public double calcularDesconto() {
		return desconto.calcular(calcularSubtotal());
	}

	public double calcularTotal() {
		return calcularSubtotal() - calcularDesconto() + calcularFrete();
	}

	public boolean pagar(Pagamento pagamento) {
		boolean aprovado = pagamento.processar(calcularTotal());
		if (aprovado) {
			this.pagamento = pagamento;
		}
		return aprovado;
	}

	public int getId() {
		return id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public String getEndereco() {
		return endereco;
	}

	public String getObservacao() {
		return observacao;
	}

	public StatusPedido getStatus() {
		return status;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public List<ItemCardapio> getItens() {
		return Collections.unmodifiableList(itens);
	}

	@Override
	public String toString() {
		return String.format("Pedido #%d | %s | %d itens | R$ %.2f | %s", id, cliente.getNome(), itens.size(),
				calcularTotal(), status);
	}
}
