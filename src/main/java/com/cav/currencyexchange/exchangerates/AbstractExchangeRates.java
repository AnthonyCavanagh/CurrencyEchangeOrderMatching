package com.cav.currencyexchange.exchangerates;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;



import com.cav.currencyexchange.generated.Channel;


public abstract class AbstractExchangeRates {

	
	protected Channel getItems(String xml){
		JAXBContext jaxbContext;
		Channel channel = null;
		try {
			 jaxbContext = JAXBContext.newInstance(com.cav.currencyexchange.generated.Channel.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			channel = (Channel) jaxbUnmarshaller.unmarshal(new StringReader(xml));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return channel;
	}
	
	
	protected void getExchangeRates(){
	
	}
	
	
	

	
	
	
}
