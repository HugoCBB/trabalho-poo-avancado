package br.ucsal.delivery.app;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import br.ucsal.delivery.controller.PedidoController;
import br.ucsal.delivery.exception.PagamentoRecusadoException;
import br.ucsal.delivery.exception.PedidoInvalidoException;
import br.ucsal.delivery.model.ItemCardapio;
import br.ucsal.delivery.relatorio.RelatorioPedidos;

public class Main {
	private static final Scanner sc = new Scanner(System.in);
	private static final PedidoController controller = new PedidoController();

	public static void main(String[] args) {
		int opcao;
		do {
			System.out.println("\n=== DELIVERY ===");
			System.out.println("1 - Ver cardapio");
			System.out.println("2 - Novo pedido");
			System.out.println("3 - Avancar status de pedido");
			System.out.println("4 - Listar pedidos");
			System.out.println("5 - Relatorio do dia");
			System.out.println("0 - Sair");
			opcao = lerInt("Opcao: ");
			try {
				switch (opcao) {
					case 1 -> mostrarCardapio();
					case 2 -> novoPedido();
					case 3 -> System.out.println(controller.avancarStatus(lerInt("ID do pedido: ")));
					case 4 -> controller.listarPedidos().forEach(System.out::println);
					case 5 -> System.out.println(new RelatorioPedidos(controller.listarPedidos()).gerar());
					case 0 -> System.out.println("Ate logo!");
					default -> System.out.println("Opcao invalida");
				}
			} catch (PedidoInvalidoException | PagamentoRecusadoException e) {
				System.out.println("Erro: " + e.getMessage());
			}
		} while (opcao != 0);
	}

	private static void mostrarCardapio() {
		controller.getCardapio().forEach((codigo, item) -> System.out.println(codigo + " - " + item));
	}

	private static void novoPedido() {
		String nome = lerTexto("Nome: ");
		String telefone = lerTexto("Telefone: ");
		String endereco = lerTexto("Endereco: ");
		Map<ItemCardapio, Integer> itens = new LinkedHashMap<>();
		mostrarCardapio();
		int codigo;
		while ((codigo = lerInt("Codigo do item (0 para finalizar): ")) != 0) {
			ItemCardapio item = controller.buscarItem(codigo);
			itens.merge(item, lerInt("Quantidade: "), Integer::sum);
		}
		System.out.println("Criado: " + controller.criarPedido(nome, telefone, endereco, itens));
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