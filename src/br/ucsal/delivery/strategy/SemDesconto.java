package br.ucsal.delivery.strategy;

public class SemDesconto implements DescontoStrategy {
    @Override
    public double calcular(double subtotal) {
        return 0.0;
    }

    @Override
    public String getDescricao() {
        return "Sem cupom";
    }
}