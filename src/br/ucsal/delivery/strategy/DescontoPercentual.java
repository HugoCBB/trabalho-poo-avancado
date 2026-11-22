package br.ucsal.delivery.strategy;

public class DescontoPercentual implements DescontoStrategy {
    private final double percentual;

    public DescontoPercentual(double percentual) {
        if (percentual <= 0 || percentual > 100) {
            throw new IllegalArgumentException("Percentual deve estar entre 0 e 100");
        }
        this.percentual = percentual;
    }

    @Override
    public double calcular(double subtotal) {
        return subtotal * percentual / 100;
    }

    @Override
    public String getDescricao() {
        return String.format("%.0f%% de desconto", percentual);
    }
}