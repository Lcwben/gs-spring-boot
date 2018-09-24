package Service.Impl;

import Service.MqSenderService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

@Service
public class MqSenderServiceImpl implements MqSenderService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void send(String msg) {
        //amqpTemplate.convertAndSend("log.exchange", "log.queue1", msg);
        RetryTemplate retryTemplate = new RetryTemplate();
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(500);
        backOffPolicy.setMultiplier(10.0);
        backOffPolicy.setMaxInterval(10000);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        rabbitTemplate.setRetryTemplate(retryTemplate);
        rabbitTemplate.convertAndSend("log.exchange", null, msg);
    }
}
