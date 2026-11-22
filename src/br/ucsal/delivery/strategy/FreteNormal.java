package br.ucsal.delivery.strategy;

public class FreteNormal implements CalculoFreteStrategy {
    private static final double LIMITE_FRETE_GRATIS = 50.0;
    private static final double VALOR_FRETE = 7.0;

    @Override
    public double calcular(double subtotal) {
        if (subtotal >= LIMITE_FRETE_GRATIS) {
            return 0.0;
        } else {
            return VALOR_FRETE;
        }
    }

    @Override
    public String getDescricao() {
        return String.format("Entrega normal (R$ %.2f, gratis a partir de R$ %.2f)", VALOR_FRETE,
                LIMITE_FRETE_GRATIS);
    }
}