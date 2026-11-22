package br.ucsal.delivery.strategy;

public interface DescontoStrategy {
	double calcular(double subtotal);

	default String getDescricao() {
		return getClass().getSimpleName();
	}
}