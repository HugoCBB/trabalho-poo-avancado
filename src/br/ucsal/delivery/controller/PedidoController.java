package br.ucsal.delivery.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.ucsal.delivery.exception.PedidoInvalidoException;
import br.ucsal.delivery.model.Cliente;
import br.ucsal.delivery.model.ItemCardapio;
import br.ucsal.delivery.model.Pedido;
import br.ucsal.delivery.model.Produto;
import br.ucsal.delivery.repository.PedidoRepository;

public class PedidoController {
	private final PedidoRepository repository = new PedidoRepository();
	private final Map<Integer, ItemCardapio> cardapio = new LinkedHashMap<>();

	public PedidoController() {
		cardapio.put(1, new Produto("X-Burguer", 18.0));
		cardapio.put(2, new Produto("X-Salada", 20.0));
		cardapio.put(3, new Produto("Batata Frita", 12.0));
		cardapio.put(4, new Produto("Refrigerante", 6.0));
	}

	public Map<Integer, ItemCardapio> getCardapio() {
		return cardapio;
	}

	public ItemCardapio buscarItem(int codigo) {
		ItemCardapio item = cardapio.get(codigo);
		if (item == null) {
			throw new PedidoInvalidoException("Item inexistente: " + codigo);
		}
		return item;
	}

	public Pedido criarPedido(String nome, String telefone, String endereco, Map<ItemCardapio, Integer> itens) {
		if (itens.isEmpty()) {
			throw new PedidoInvalidoException("Pedido sem itens");
		}
		Pedido pedido = new Pedido(new Cliente(nome, telefone), endereco, "", subtotal -> 0, subtotal -> 0);
		itens.forEach(pedido::adicionarItem);
		repository.salvar(pedido);
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