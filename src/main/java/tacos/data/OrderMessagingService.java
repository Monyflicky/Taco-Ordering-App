package tacos.data;

import org.springframework.stereotype.Repository;
import tacos.model.TacoOrder;

@Repository
public interface OrderMessagingService
{
    void sendOrder(TacoOrder order);
}
