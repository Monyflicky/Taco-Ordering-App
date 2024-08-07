package tacos.data;

import tacos.model.TacoOrder;

public interface OrderReceiver
{
    TacoOrder receiveOrder();
}
