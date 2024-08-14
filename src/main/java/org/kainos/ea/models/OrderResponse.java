package org.kainos.ea.models;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderResponse {
	private int orderId;
	private Timestamp orderDate;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getOrderDate() {
		return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(this.orderDate);
	}

	public void setOrderDate(String orderDate) {
		Timestamp timestamp;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			Date parsedDate = dateFormat.parse(orderDate);
			timestamp = new Timestamp(parsedDate.getTime());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		this.orderDate = timestamp;
	}

	public OrderResponse(int orderId, Timestamp orderDate) {
		this.orderId = orderId;
		this.orderDate = orderDate;
	}
}
