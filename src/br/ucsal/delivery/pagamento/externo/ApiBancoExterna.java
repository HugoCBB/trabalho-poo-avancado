package br.ucsal.delivery.pagamento.externo;

// Simula o SDK de um banco de terceiros: interface diferente da nossa e que não podemos alterar
public class ApiBancoExterna {
	public static final int APROVADO = 0;
	public static final int LIMITE_EXCEDIDO = 51;
	private static final long LIMITE_CENTAVOS = 50_000;

	public int cobrar(long valorEmCentavos) {
		if (valorEmCentavos <= 0 || valorEmCentavos > LIMITE_CENTAVOS) {
			System.out.printf("[BANCO] Transacao negada (codigo %d)%n", LIMITE_EXCEDIDO);
			return LIMITE_EXCEDIDO;
		}
		System.out.printf("[BANCO] Transacao aprovada: %d centavos%n", valorEmCentavos);
		return APROVADO;
	}
}
