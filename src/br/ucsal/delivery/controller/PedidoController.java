package br.ucsal.delivery.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.ucsal.delivery.exception.PedidoInvalidoException;
import br.ucsal.delivery.model.*;
import br.ucsal.delivery.observer.ClienteNotificador;
import br.ucsal.delivery.observer.CozinhaPainel;
import br.ucsal.delivery.repository.PedidoRepository;
import br.ucsal.delivery.strategy.CalculoFreteStrategy;
import br.ucsal.delivery.strategy.DescontoPercentual;
import br.ucsal.delivery.strategy.DescontoStrategy;
import br.ucsal.delivery.strategy.DescontoValorFixo;
import br.ucsal.delivery.strategy.FreteExpresso;
import br.ucsal.delivery.strategy.FreteNormal;
import br.ucsal.delivery.strategy.FreteRetirada;
import br.ucsal.delivery.strategy.SemDesconto;

public class PedidoController {
	private final PedidoRepository repository = new PedidoRepository();
	private final Map<Integer, ItemCardapio> cardapio = new LinkedHashMap<>();
	private final Map<Integer, CalculoFreteStrategy> opcoesFrete = new LinkedHashMap<>();
	private final Map<String, DescontoStrategy> cupons = new LinkedHashMap<>();
	private final CozinhaPainel cozinha = new CozinhaPainel();

	public PedidoController() {
		cardapio.put(1, new Produto("X-Burguer", 18.0));
		cardapio.put(2, new Produto("X-Salada", 20.0));
		cardapio.put(3, new Produto("Batata Frita", 12.0));
		cardapio.put(4, new Produto("Refrigerante", 6.0));
		opcoesFrete.put(1, new FreteRetirada());
		opcoesFrete.put(2, new FreteNormal());
		opcoesFrete.put(3, new FreteExpresso());
		cupons.put("BEMVINDO10", new DescontoPercentual(10));
		cupons.put("DELIVERY5", new DescontoValorFixo(5.0));
	}

	public Map<Integer, ItemCardapio> getCardapio() {
		return cardapio;
	}

	public Map<Integer, CalculoFreteStrategy> getOpcoesFrete() {
		return opcoesFrete;
	}

	public DescontoStrategy buscarCupom(String cupom) {
		if (cupom == null || cupom.isBlank()) {
			return new SemDesconto();
		}
		DescontoStrategy desconto = cupons.get(cupom.trim().toUpperCase());
		if (desconto == null) {
			throw new IllegalArgumentException("Cupom invalido: " + cupom);
		}
		return desconto;
	}

	public ItemCardapio buscarItem(int codigo) {
		ItemCardapio item = cardapio.get(codigo);
		if (item == null) {
			throw new PedidoInvalidoException("Item inexistente: " + codigo);
		}
		return item;
	}

	public CozinhaPainel getCozinha() {
		return cozinha;
	}

	public CalculoFreteStrategy buscarFrete(int opcao) {
		CalculoFreteStrategy frete = opcoesFrete.get(opcao);
		if (frete == null) {
			throw new IllegalArgumentException("Opcao de entrega invalida: " + opcao);
		}
		return frete;
	}

	public Pedido criarPedido(String nome, String telefone, String endereco, Map<ItemCardapio, Integer> itens, CalculoFreteStrategy frete, DescontoStrategy desconto) {
		if (itens.isEmpty()) {
			throw new PedidoInvalidoException("Pedido sem itens");
		}
		Pedido pedido = new Pedido(new Cliente(nome, telefone), endereco, "", frete, desconto);
		itens.forEach(pedido::adicionarItem);
		pedido.adicionarObserver(new ClienteNotificador());
		pedido.adicionarObserver(cozinha);
		repository.salvar(pedido);
		pedido.alterarStatus(StatusPedido.RECEBIDO);
		return pedido;
	}

	public Pedido avancarStatus(int id) {
		Pedido pedido = repository.buscarPorId(id)
				.orElseThrow(() -> new PedidoInvalidoException("Pedido inexistente: " + id));
		pedido.avancarStatus();
		return pedido;
	}

	public List<Pedido> listarPedidos() {
		return repository.listarTodos();
	}
}