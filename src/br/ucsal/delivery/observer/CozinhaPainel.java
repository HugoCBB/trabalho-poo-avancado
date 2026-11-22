package br.ucsal.delivery.observer;


import br.ucsal.delivery.model.Pedido;


import java.util.ArrayList;
import java.util.LinkedHashMap;

import java.util.List;
import java.util.Map;

public class CozinhaPainel implements PedidoObserver {
    private final Map<Integer, Pedido> fila = new LinkedHashMap<>();

    @Override
    public void atualizar(Pedido pedido) {
        switch(pedido.getStatus()) {
            case RECEBIDO, EM_PREPARO -> {
                fila.put(pedido.getId(),pedido);
                System.out.println("Pedido #"+pedido.getId()+" em preparo.");
            }
            case PRONTO -> {
                System.out.println("Pedido #"+pedido.getId()+" pronto.");
                fila.remove(pedido.getId());
            }
            case SAIU_PARA_ENTREGA, ENTREGUE -> { }
        }
    }
    public List<Pedido> getFila() {
        return new ArrayList<>(fila.values());
    }

}