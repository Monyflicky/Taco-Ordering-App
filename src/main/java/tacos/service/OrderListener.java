package tacos.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import tacos.model.TacoOrder;


@Profile("jms-listener")
@Component
public class OrderListener
{
    Logger log = LoggerFactory.getLogger(OrderListener.class);

    @JmsListener(destination = "tacocloud.order.queue")
    public void receiveOrder(TacoOrder order) {
        log.info(order.toString());
    }
}
