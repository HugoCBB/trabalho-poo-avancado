package br.ucsal.delivery.app;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import br.ucsal.delivery.controller.PedidoController;
import br.ucsal.delivery.exception.PagamentoRecusadoException;
import br.ucsal.delivery.exception.PedidoInvalidoException;
import br.ucsal.delivery.model.ItemCardapio;
import br.ucsal.delivery.model.Pedido;
import br.ucsal.delivery.relatorio.RelatorioPedidos;
import br.ucsal.delivery.strategy.CalculoFreteStrategy;
import br.ucsal.delivery.strategy.DescontoStrategy;

public class Main {
	private static final Scanner sc = new Scanner(System.in);
	private static final PedidoController controller = new PedidoController();

	public static void main(String[] args) {
		int opcao;
		do {
			hub();
			opcao = lerInt("Opcao: ");
			try {
				switch (opcao) {
					case 1 -> mostrarCardapio();
					case 2 -> novoPedido();
					case 3 -> System.out.println(controller.avancarStatus(lerInt("ID do pedido: ")));
					case 4 -> controller.listarPedidos().forEach(System.out::println);
					case 5 -> System.out.println(new RelatorioPedidos(controller.listarPedidos()).gerar());
					case 6 -> mostrarFilaCozinha();
					case 0 -> System.out.println("Ate logo!");
					default -> System.out.println("Opcao invalida");
				}
			} catch (PedidoInvalidoException | PagamentoRecusadoException e) {
				System.out.println("Erro: " + e.getMessage());
			}
		} while (opcao != 0);
	}

	private static void hub() {
		System.out.println("\n=== DELIVERY ===");
		System.out.println("1 - Ver cardapio");
		System.out.println("2 - Novo pedido");
		System.out.println("3 - Avancar status de pedido");
		System.out.println("4 - Listar pedidos");
		System.out.println("5 - Relatorio do dia");
		System.out.println("6 - Fila da cozinha");
		System.out.println("0 - Sair");
	}
	private static void mostrarFilaCozinha() {
		if (controller.getCozinha().getFila().isEmpty()) {
			System.out.println("Cozinha sem pedidos na fila.");
		} else {
			controller.getCozinha().getFila().forEach(System.out::println);
		}
	}

	private static void mostrarCardapio() {
		controller.getCardapio().forEach((codigo, item) -> System.out.println(codigo + " - " + item));
	}

	private static void mostrarFretes() {
		controller.getOpcoesFrete()
				.forEach((opcao, frete) -> System.out.println(opcao + " - " + frete.getDescricao()));
	}

	private static CalculoFreteStrategy escolherFrete() {
		mostrarFretes();
		while (true) {
			try {
				return controller.buscarFrete(lerInt("Insira a opcao de frete: "));
			} catch (IllegalArgumentException e) {
				System.out.println("Erro: " + e.getMessage());
			}
		}
	}

	private static int lerQuantidade() {
		while (true) {
			int quantidade = lerInt("Quantidade: ");
			if (quantidade > 0) {
				return quantidade;
			}
			System.out.println("Erro: quantidade deve ser maior que zero");
		}
	}

	private static DescontoStrategy escolherCupom() {
		while (true) {
			try {
				return controller.buscarCupom(lerTexto("Cupom (Enter para nenhum): "));
			} catch (IllegalArgumentException e) {
				System.out.println("Erro: " + e.getMessage());
			}
		}
	}

	private static void mostrarResumo(Pedido pedido) {
		System.out.println("\n--- Resumo do pedido #" + pedido.getId() + " ---");
		System.out.printf("Subtotal: R$ %.2f%n", pedido.calcularSubtotal());
		System.out.printf("Desconto: -R$ %.2f%n", pedido.calcularDesconto());
		System.out.printf("Frete: R$ %.2f%n", pedido.calcularFrete());
		System.out.printf("TOTAL: R$ %.2f%n", pedido.calcularTotal());
	}

	private static void novoPedido() {
		String nome = lerTexto("Nome: ");
		String telefone = lerTexto("Telefone: ");
		String endereco = lerTexto("Endereco: ");
		Map<ItemCardapio, Integer> itens = new LinkedHashMap<>();
		mostrarCardapio();
		int codigo = lerInt("Codigo do item (0 para finalizar): ");
		while (codigo != 0) {
			try {
				ItemCardapio item = controller.buscarItem(codigo);
				itens.merge(item, lerQuantidade(), Integer::sum);
			} catch (PedidoInvalidoException e) {
				System.out.println("Erro: " + e.getMessage());
			}
			codigo = lerInt("Codigo do item (0 para finalizar): ");
		}
		CalculoFreteStrategy frete = escolherFrete();
		DescontoStrategy desconto = escolherCupom();
		Pedido pedido = controller.criarPedido(nome, telefone, endereco, itens, frete, desconto);
		mostrarResumo(pedido);
	}

	private static String lerTexto(String rotulo) {
		System.out.print(rotulo);
		return sc.nextLine().trim();
	}

	private static int lerInt(String rotulo) {
		while (true) {
			try {
				return Integer.parseInt(lerTexto(rotulo));
			} catch (NumberFormatException e) {
				System.out.println("Digite um numero");
			}
		}
	}
}