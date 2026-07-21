package com.example.myapplication;

// Create a data class to store payment information
public class PaymentData {
    public String serviceName, servicePrice, cardNumber, expirationDate, cvv, cardHolderName;

    public PaymentData(String serviceName, String servicePrice, String cardNumber, String expirationDate, String cvv, String cardHolderName) {
        this.serviceName = serviceName;
        this.servicePrice = servicePrice;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.cardHolderName = cardHolderName;
    }
}

