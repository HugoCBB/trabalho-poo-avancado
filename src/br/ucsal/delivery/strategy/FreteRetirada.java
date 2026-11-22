package br.ucsal.delivery.strategy;

public class FreteRetirada implements CalculoFreteStrategy {
    @Override
    public double calcular(double subtotal) {
        return 0.0;
    }

    @Override
    public String getDescricao() {
        return "Retirada no balcao (gratis)";
    }
}