package br.ucsal.delivery.exception;

/** Lancada quando uma regra de negocio do pedido e violada (dados invalidos, status invalido etc.). */
public class PedidoInvalidoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PedidoInvalidoException(String mensagem) {
        super(mensagem);
    }
}