package br.ucsal.delivery.strategy;

public class DescontoValorFixo implements DescontoStrategy {
    private final double valor;

    public DescontoValorFixo(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Desconto deve ser maior do que 0");
        }
        this.valor = valor;
    }

    @Override
    public double calcular(double subtotal) {
        return Math.min(subtotal, valor);
    }

    @Override
    public String getDescricao() {
        return String.format("R$ %.2f de desconto", valor);
    }
}