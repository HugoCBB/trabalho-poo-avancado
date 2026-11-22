package br.ucsal.delivery.strategy;

public interface CalculoFreteStrategy {
	double calcular(double subtotal);

	default String getDescricao() {
		return getClass().getSimpleName();
	}
}