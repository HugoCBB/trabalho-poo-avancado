package br.ucsal.delivery.relatorio;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.ucsal.delivery.model.ItemCardapio;
import br.ucsal.delivery.model.Pedido;
import br.ucsal.delivery.model.StatusPedido;

/**
 * Relatorio de pedidos do dia.
 *
 * GRASP: Pure Fabrication - classe criada so para gerar relatorios, mantendo
 * Pedido e PedidoRepository coesos. Recebe apenas a lista de pedidos, o que
 * mantem o acoplamento baixo (nao conhece repositorio nem controller).
 */
public class RelatorioPedidos {
    private final List<Pedido> pedidos;

    public RelatorioPedidos(List<Pedido> pedidos) {
        this.pedidos = new ArrayList<>(pedidos);
    }

    /** Quantidade de pedidos em cada status (EnumMap preserva a ordem do fluxo). */
    public Map<StatusPedido, Integer> contarPorStatus() {
        Map<StatusPedido, Integer> contagem = new EnumMap<>(StatusPedido.class);
        for (StatusPedido s : StatusPedido.values()) {
            contagem.put(s, 0);
        }
        pedidos.forEach(p -> contagem.merge(p.getStatus(), 1, Integer::sum));
        return contagem;
    }

    /** Soma dos totais dos pedidos que ja tiveram pagamento aprovado. */
    public double faturamento() {
        return pedidos.stream().filter(p -> p.getPagamento() != null).mapToDouble(Pedido::calcularTotal).sum();
    }

    /** Ticket medio considerando todos os pedidos registrados. */
    public double ticketMedio() {
        return pedidos.isEmpty() ? 0 : pedidos.stream().mapToDouble(Pedido::calcularTotal).average().orElse(0);
    }

    /** Itens vendidos (por descricao) ordenados do mais para o menos vendido. */
    public Map<String, Integer> itensMaisVendidos() {
        Map<String, Integer> vendas = new LinkedHashMap<>();
        for (Pedido p : pedidos) {
            for (ItemCardapio item : p.getItens()) {
                vendas.merge(item.getDescricao(), 1, Integer::sum);
            }
        }
        Map<String, Integer> ordenado = new LinkedHashMap<>();
        vendas.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(e -> ordenado.put(e.getKey(), e.getValue()));
        return ordenado;
    }

    public String gerar() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===== RELATORIO DE PEDIDOS DO DIA =====\n");
        if (pedidos.isEmpty()) {
            return sb.append("Nenhum pedido registrado.\n").toString();
        }
        sb.append("Total de pedidos: ").append(pedidos.size()).append("\n\n");
        sb.append("Pedidos por status:\n");
        contarPorStatus().forEach((status, qtd) -> sb.append(String.format("  %-20s %d%n", status, qtd)));
        sb.append(String.format("%nFaturamento (pedidos pagos): R$ %.2f%n", faturamento()));
        sb.append(String.format("Ticket medio: R$ %.2f%n%n", ticketMedio()));
        sb.append("Itens mais vendidos:\n");
        itensMaisVendidos().forEach((nome, qtd) -> sb.append(String.format("  %-30s %d%n", nome, qtd)));
        return sb.toString();
    }
}