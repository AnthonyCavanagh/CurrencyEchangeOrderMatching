package com.cav.currencyexchange.models;


public class CurrencyOrderRead {

	private String OrderId;
	private String partnerId;
	private String orderType;
	private String currencyPair;
	private String amount;
	private String exchangeRate;
	private String expirationDate;
	private String publishDate;
	private String status;
	
	
	public CurrencyOrderRead(String orderId, String partnerId, String orderType, String currencyPair, String amount,
			String exchangeRate, String expirationDate, String publishDate, String status) {
		super();
		OrderId = orderId;
		this.partnerId = partnerId;
		this.orderType = orderType;
		this.currencyPair = currencyPair;
		this.amount = amount;
		this.exchangeRate = exchangeRate;
		this.expirationDate = expirationDate;
		this.publishDate = publishDate;
		this.status = status;
	}
	
	


	public String getOrderId() {
		return OrderId;
	}




	public String getPartnerId() {
		return partnerId;
	}




	public String getOrderType() {
		return orderType;
	}




	public String getCurrencyPair() {
		return currencyPair;
	}




	public String getAmount() {
		return amount;
	}




	public String getExchangeRate() {
		return exchangeRate;
	}




	public String getExpirationDate() {
		return expirationDate;
	}




	public String getPublishDate() {
		return publishDate;
	}




	public String getStatus() {
		return status;
	}




	@Override
	public String toString() {
		return "CurrencyOrder [OrderId=" + OrderId + ", partnerId=" + partnerId + ", orderType=" + orderType
				+ ", currencyPair=" + currencyPair + ", amount=" + amount + ", exchangeRate=" + exchangeRate
				+ ", expirationDate=" + expirationDate + ", publishDate=" + publishDate + ", status=" + status + "]";
	}

}