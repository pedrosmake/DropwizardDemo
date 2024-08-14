package org.kainos.ea.models;

public class CustomerResponse {
	private int customerId;
	private String customerName;

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public CustomerResponse(int customerId, String customerName) {
		this.customerId = customerId;
		this.customerName = customerName;
	}
}
