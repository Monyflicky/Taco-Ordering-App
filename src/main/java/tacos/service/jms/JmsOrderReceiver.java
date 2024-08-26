package tacos.service.jms;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import tacos.data.OrderReceiver;
import tacos.model.TacoOrder;


@Component
public class JmsOrderReceiver implements OrderReceiver {
    @Autowired
    JmsTemplate jms;

//    @Autowired
//    public JmsOrderReceiver(@Lazy JmsTemplate jms) {
//        this.jms = jms;
//
//    }
    @Override
    public TacoOrder receiveOrder() {
        return (TacoOrder) jms.receiveAndConvert("tacocloud.order.queue");
    }

}
