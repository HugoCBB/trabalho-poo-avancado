package br.ucsal.delivery.repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import br.ucsal.delivery.model.Pedido;

public class PedidoRepository {
	private final Map<Integer, Pedido> pedidos = new LinkedHashMap<>();

	public void salvar(Pedido pedido) {
		pedidos.put(pedido.getId(), pedido);
	}

	public Optional<Pedido> buscarPorId(int id) {
		return Optional.ofNullable(pedidos.get(id));
	}

	public List<Pedido> listarTodos() {
		return new ArrayList<>(pedidos.values());
	}
}
