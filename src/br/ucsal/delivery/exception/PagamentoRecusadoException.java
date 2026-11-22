package br.ucsal.delivery.exception;

/** Lancada quando a forma de pagamento recusa a transacao ou o pagamento e invalido. */
public class PagamentoRecusadoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PagamentoRecusadoException(String mensagem) {
        super(mensagem);
    }
}