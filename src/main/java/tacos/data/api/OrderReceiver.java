package tacos.data.api;

import org.springframework.stereotype.Repository;
import tacos.model.TacoOrder;

@Repository
public interface OrderReceiver
{
    TacoOrder receiveOrder();
}
