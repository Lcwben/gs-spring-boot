package config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;

import javax.jms.ConnectionFactory;

/**
 * ActicveMq配置类
 */
@Configuration
@EnableJms
public class JmsConfiguration {

	private Logger logger = LoggerFactory.getLogger(JmsConfiguration.class);

	/**
	 * 配置activemqConnectionFactory
	 * 
	 * @param brokerUrl 链接url
	 * @param userName  用户名
	 * @param password  密码
	 * @return
	 */
	@Bean(name = "activemqConnectionFactory")
	public ActiveMQConnectionFactory getActivemqConnectionFactory(
            @Value("${spring.activemq.broker-url}") String brokerUrl, @Value("${spring.activemq.user}") String userName,
            @Value("${spring.activemq.password}") String password) {
		logger.info("ActiveMQConnectionFactory 初始化，链接：{}，用户：{}", brokerUrl, userName);
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(brokerUrl);
		connectionFactory.setUserName(userName);
		connectionFactory.setPassword(password);
		connectionFactory.setConsumerFailoverRedeliveryWaitPeriod(2);
		return connectionFactory;
	}

	/**
	 * 配置jmsTemplate的bean对象
	 * 
	 * @param connectionFactory
	 * @return
	 */
	@Bean(name = "activemqJmsTemplate")
	public JmsMessagingTemplate getActivemqJmsTemplate(
			@Qualifier("activemqConnectionFactory") ConnectionFactory connectionFactory) {
		JmsMessagingTemplate template = new JmsMessagingTemplate(connectionFactory);
		return template;
	}

	@Bean(name = "activemqTopicListener")
	public DefaultJmsListenerContainerFactory getActivemqTopicListener(
			@Qualifier("activemqConnectionFactory") ConnectionFactory connectionFactory) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setPubSubDomain(true); // 启用topic（主题）发送方式
		/**
		 * 改变消息的确认机制 ActiveMqConst.AUTO_ACKNOWLEDGE = 1 消息自动确认
		 * ActiveMqConst.CLIENT_ACKNOWLEDGE = 2 客户端调用acknowledge方法手动确认
		 * ActiveMqConst.DUPS_OK_ACKNOWLEDGE = 3 不必必须确认，消息可能会重复发送
		 * ActiveMqConst.SESSION_TRANSACTED = 0 启用事务
		 * ActiveMqConst.INDIVIDUAL_ACKNOWLEDGE = 4 activeMq独有，单条消息确认
		 */
		// factory.setSessionAcknowledgeMode(ActiveMqConst.INDIVIDUAL_ACKNOWLEDGE);
		return factory;
	}

	/**
	 * 配置activemq队列监听器的bean对象（自动确认消息）
	 * 
	 * @param connectionFactory
	 * @return
	 */
	@Bean(name = "activemqQueueListener")
	public DefaultJmsListenerContainerFactory getActivemqQueueListener(
			@Qualifier("activemqConnectionFactory") ConnectionFactory connectionFactory) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		return factory;
	}

	/**
	 * 配置activemq队列监听器的bean对象（需要手动确认消息）
	 * 
	 * @param connectionFactory
	 * @return
	 */
	@Bean(name = "activemqQueueCliAckListener")
	public DefaultJmsListenerContainerFactory getActivemqQueueCliAckListener(
			@Qualifier("activemqConnectionFactory") ConnectionFactory connectionFactory) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setSessionAcknowledgeMode(4);
		return factory;
	}

}
