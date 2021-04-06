package com.cav.currencyexchange;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.cav.currencyexchange.utils.Constants;



@Configuration
@EnableJms
public class ConnectionFactoryConfig {
	   /*
	   * Initial ConnectionFactory
	   */
	    @Bean
	    public ConnectionFactory connectionFactory(){
	        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
	        connectionFactory.setBrokerURL(Constants.ACTIVEMQ_BROKER);
	        connectionFactory.setUserName(Constants.ACTIVEMQ_USER);
	        connectionFactory.setPassword(Constants.ACTIVEMQ_PASSWORD);
	        return connectionFactory;
	    }
	    
	    @Bean // Serialize message content to json using TextMessage
	    public MessageConverter jacksonJmsMessageConverter() {
	        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
	        converter.setTargetType(MessageType.TEXT);
	        converter.setTypeIdPropertyName("_type");
	        return converter;
	    }
	    
	    @Bean
	    public JmsListenerContainerFactory<?> topicListenerFactory(ConnectionFactory connectionFactory,
	                                                    DefaultJmsListenerContainerFactoryConfigurer configurer) {
	        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
	        factory.setMessageConverter(jacksonJmsMessageConverter());
	        configurer.configure(factory, connectionFactory);
	        factory.setClientId(Constants.ACTIVEMQ_CLIENT_ID);
	        factory.setSubscriptionDurable(true);
	        factory.setPubSubDomain(true);
	        return factory;
	    }

}
