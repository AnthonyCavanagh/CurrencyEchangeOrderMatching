package com.cav.currencyexchange.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CurrencyOrder {

	private String OrderId;
	private String partnerId;
	private String orderType;
	private String currencyPair;
	private BigDecimal amount;
	private BigDecimal exchangeRate;
	private LocalDateTime expirationDate;
	private LocalDateTime publishDate;
	private Status status;
	
	public CurrencyOrder(String orderId, String partnerId, String orderType, String currencyPair, BigDecimal amount,
			BigDecimal exchangeRate, LocalDateTime expirationDate, LocalDateTime publishDate, Status status) {
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
	public String getPartnerId() {
		return partnerId;
	}
	public String getOrderType() {
		return orderType;
	}
	public String getCurrencyPair() {
		return currencyPair;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}
	public LocalDateTime getExpirationDate() {
		return expirationDate;
	}
	
	
	public LocalDateTime getPublishDate() {
		return publishDate;
	}
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "CurrencyOrder [OrderId=" + OrderId + ", partnerId=" + partnerId + ", orderType=" + orderType
				+ ", currencyPair=" + currencyPair + ", amount=" + amount + ", exchangeRate=" + exchangeRate
				+ ", expirationDate=" + expirationDate + ", publishDate=" + publishDate + ", status=" + status + "]";
	}

}