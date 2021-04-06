package com.cav.currencyexchange.livemessaging;


import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.cav.currencyexchange.service.processorders.ProcessOrdersServices;
import com.cav.currencyexchange.service.processorders.ProcessOrdersServicesImpl;
import com.cav.currencyexchange.utils.Constants;
import com.cav.currencyexchangebroker.generated.Orders;

@Component
public class LiveMessageStream {
	
	private static final Logger log = LoggerFactory.getLogger(LiveMessageStream.class);
	
	/**
	 *  Spawns a new thread for each message recieved
	 * @param message
	 * @throws JMSException
	 */
	@JmsListener(destination = "${activemq.topic}", containerFactory = "topicListenerFactory")
	public void receiveOrdersFromBrokers(Orders message) throws JMSException {
		log.info("receiveOrdersFromBrokers Enter");
		
		
		ProcessOrdersServices service = new  ProcessOrdersServicesImpl(message.getOrder());
		Constants.EXECUTOR.submit(service);
		
		
		
	}

}
