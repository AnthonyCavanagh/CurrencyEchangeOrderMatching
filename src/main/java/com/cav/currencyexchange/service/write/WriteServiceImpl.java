package com.cav.currencyexchange.service.write;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cav.currencyexchange.livemessaging.LiveMessageStream;
import com.cav.currencyexchange.models.CurrencyOrder;
import com.cav.currencyexchange.utils.Constants;
import com.cav.currencyexchangebroker.generated.Orders.Order;

public class WriteServiceImpl implements WriteService{
	
	List <Order> messages = null;
	List <CurrencyOrder> orders = null;
	String recieved = null;
	String fileTime = null;
	String path = null;;
	String currencyKeyPair = null;
	String orderType = null;
	
	private static final Logger log = LoggerFactory.getLogger(WriteServiceImpl.class);
	
	
	DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm:ss");
	DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");


	public WriteServiceImpl(List<Order> messages, LocalDateTime recievedTime) {
		super();
		this.messages = messages;
		this.recieved = recievedTime.format(formatter1);
		this.fileTime = recievedTime.format(formatter2);
		
	}
	
	
	public WriteServiceImpl(List <CurrencyOrder> orders, LocalDateTime recievedTime, String path, String currencyKeyPair, String orderType) {
		super();
		this.orders = orders;
		this.path = path;
		this.fileTime = recievedTime.format(formatter2);
		this.currencyKeyPair = currencyKeyPair;
		this.orderType = orderType;
	} 
	
	@Override
	public Object call() throws Exception {
		if(orders != null) {
			writeToFile(orders);
		} else if(messages != null){
			writeToFileMessages(messages);
		}
		return null;
	}

	private void writeToFileMessages(List<Order> messages) {
		
		FileWriter myWriter = null;
		 try {
			myWriter = new FileWriter(Constants.RECIEVED_ORDERS+fileTime);
			for(Order order : messages){
				myWriter.write(recieved+"|"+order.toString());
				myWriter.write("\r");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(myWriter != null){
				try {
					myWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private void writeToFile(List <CurrencyOrder> writeToOrders){
		String[] fields = this.currencyKeyPair.split("/");
		StringBuilder path = new StringBuilder(this.path)
				.append(this.orderType)
				.append("/")
				.append(fields[0])
				.append(fields[1])
				.append("/")
				.append("orders_")
				.append(this.fileTime)
		        .append(".txt");
		FileWriter myWriter = null;
		 try {
		    //log.info("write to "+path.toString());
			myWriter = new FileWriter(path.toString());
			for(CurrencyOrder order : writeToOrders){
				myWriter.write(order.toString());
				myWriter.write("\r");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(myWriter != null){
				try {
					myWriter.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	

}
