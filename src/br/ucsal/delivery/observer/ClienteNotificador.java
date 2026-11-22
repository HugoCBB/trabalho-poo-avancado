package br.ucsal.delivery.observer;

import br.ucsal.delivery.model.Pedido;

public class ClienteNotificador implements PedidoObserver {
    @Override
    public void atualizar(Pedido pedido) {

        String mensagem = switch (pedido.getStatus()) {
            case RECEBIDO -> "Pedido recebido.";
            case EM_PREPARO -> "A cozinha comecou o preparo.";
            case PRONTO -> "Pedido pronto.";
            case SAIU_PARA_ENTREGA ->  "Pedido saiu para entrega em "+pedido.getEndereco()+".";
            case ENTREGUE ->  "Pedido entregue.";
        };
        System.out.println("[SMS para "+pedido.getCliente().getTelefone()+"] Pedido #"+pedido.getId()+": "+mensagem);
    }
}