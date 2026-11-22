package br.ucsal.delivery.strategy;

public class FreteExpresso implements CalculoFreteStrategy {
    private static final double VALOR_FRETE = 12.0;

    @Override
    public double calcular(double subtotal) {
        return VALOR_FRETE;
    }

    @Override
    public String getDescricao() {
        return String.format("Entrega expressa (R$ %.2f)", VALOR_FRETE);
    }
}