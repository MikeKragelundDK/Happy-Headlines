package PublisherService.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMqConfig {

    // Since we only route outbound in this service, i just hardcode it to the keys.
    private static final String ROUTING_KEY = "outbound_publish";
    private static final String QUEUE = "outbound_publisher_queue";
    private static final String EXCHANGE = "publisher_exchange";


    @Bean (QUEUE)
    Queue outboundQueue() {
        return new Queue(QUEUE, true);
    }

    @Bean(EXCHANGE)
    DirectExchange publisherExchange() {return new DirectExchange(EXCHANGE,true, false);}

    @Bean
    Binding outBind(@Qualifier(QUEUE) Queue outBoundQueue, DirectExchange ex){
        return BindingBuilder.bind(outBoundQueue).to(ex).with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter jacksonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public RabbitTemplate  rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jacksonMessageConverter());
        rabbitTemplate.setRoutingKey(ROUTING_KEY);
        rabbitTemplate.setExchange(EXCHANGE);
        return rabbitTemplate;
    }
}
