package org.kainos.ea.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderRequest {
    private int customerId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp orderDate;

    public Timestamp getOrderDate() {
        return this.orderDate;
    }

    public void setOrderDate(Date orderDate) {
//        Timestamp timestamp;
//        try {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            Date parsedDate = dateFormat.parse(orderDate);
//            timestamp = new Timestamp(parsedDate.getTime());
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
        this.orderDate = (Timestamp) orderDate;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @JsonCreator
    public OrderRequest(
            @JsonProperty("customerId") int customerId,
            @JsonProperty("orderDate") Timestamp orderDate) {
        this.customerId = customerId;
        this.orderDate = orderDate;
    }
}
