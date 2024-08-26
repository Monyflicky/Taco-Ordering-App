package tacos.service.jms;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Service;
import tacos.data.OrderMessagingService;
import tacos.model.TacoOrder;


@Service
public class JmsOrderMessagingService implements OrderMessagingService
{
    private final JmsTemplate jms;

   @Autowired
   JmsOrderMessagingService(@Lazy JmsTemplate jms){
       this.jms = jms;
   }

   //###This section to be commented too###
//   public void sendOrder(TacoOrder order){
//       jms.send(session -> session.createObjectMessage(order));
//   }
    @Override
    public void sendOrder(TacoOrder order) {
        //jms.convertAndSend("tacocloud.order.queue", order);
        jms.convertAndSend("tacocloud.order.queue", order, new MessagePostProcessor()
        {
            @Override
            public Message postProcessMessage(Message message) throws JMSException {
                message.setStringProperty("X_ORDER_SOURCE", "WEB");
                return message;
            }
        });

        //#####The below section to be commented#####
//        jms.convertAndSend("tacocloud.order.queue", order,
//                message -> {
//                    message.setStringProperty("X_ORDER_SOURCE", "WEB");
//                    return message;
//                });
    }
}
