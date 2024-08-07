package tacos.data;

import tacos.model.TacoOrder;

public interface OrderMessagingService
{
    void sendOrder(TacoOrder order);
}
