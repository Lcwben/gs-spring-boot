package hello;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component

public class MqConsumer {

    @RabbitListener(queues = "log.queue1")
    @RabbitHandler
    public void receiveMsg(String msg, Channel channel, Message message) {

        //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            //channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
        } catch (IOException e) {

            e.printStackTrace();
            //丢弃这条消息
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            System.out.println("receiver fail");

        }
        System.out.println("receiver success");
        System.out.println("receive massage:" + msg);

    }
}
