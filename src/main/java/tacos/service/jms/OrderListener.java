package tacos.service.jms;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tacos.model.TacoOrder;


@Profile("jms-listener")
@Component
public class OrderListener
{
    Logger log = LoggerFactory.getLogger(OrderListener.class);

//    @JmsListener(destination = "tacocloud.order.queue")
//    public void receiveOrder(TacoOrder order) {
//        log.info(order.toString());
//    }

//    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
//    public void receiveOrder(TacoOrder order) {
//        log.info(order.toString());
//    }

    @KafkaListener(topics="tacocloud.orders.topic")
    public void handle(
            TacoOrder order, ConsumerRecord<String, TacoOrder> record) {
        log.info("Received from partition {} with timestamp {}",
                record.partition(), record.timestamp());
    }
}
