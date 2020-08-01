package com.example.easynaukri;

import java.util.stream.Stream;

public class PaymentDetails {
    public String Amount;
    public String TransactionId;
    public String Date;
    public String Time;
    public String PaymentMethod;
    public String Cancel;
    public String Payment;
    public String Status;

    public PaymentDetails(String amount, String transactionId, String date, String time, String paymentMethod,String cancel,String payment,String status) {
        Amount = amount;
        TransactionId = transactionId;
        Date = date;
        Time = time;
        PaymentMethod = paymentMethod;
        Cancel=cancel;
        Payment=payment;
        Status=status;
    }

    public PaymentDetails() {
    }
}
