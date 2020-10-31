package com.example.ekontest_hackathon;

import com.digicelgroup.moncash.APIContext;
import com.digicelgroup.moncash.exception.MonCashRestException;
import com.digicelgroup.moncash.http.Constants;
import com.digicelgroup.moncash.payments.OrderId;
import com.digicelgroup.moncash.payments.Payment;
import com.digicelgroup.moncash.payments.PaymentCapture;
import com.digicelgroup.moncash.payments.PaymentCreator;
import com.digicelgroup.moncash.payments.TransactionId;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.logging.Logger;

public class MonCashPayment {
    private String clientID;
    private String clientSecret;
    private APIContext apiContext;
    Logger logger = Logger.getLogger(MonCash.class.getName());
    private DatabaseReference databaseReference;


    public MonCashPayment(String clientID, String clientSecret) {
        this.clientID = clientID;
        this.clientSecret = clientSecret;
        this.apiContext = new APIContext(clientID, clientSecret, Constants.LIVE);
    }

    public HashMap<String, String> createPayment(int amount, String payer, String buyer, String transaction) {
        //creating of payment
        HashMap<String, String> arrayList = new HashMap<String, String>();
        PaymentCreator paymentCreator = new PaymentCreator();
        Payment payment = new Payment();
        String orderId = System.currentTimeMillis()+"";
        payment.setOrderId(orderId);
        payment.setAmount(amount);
        PaymentCreator creator  = null;

        //here we create the transaction in firebase
        TransactionModel model = new TransactionModel(orderId, "", "", payer, buyer, transaction);
        model.insertTransaction(model);

        try {
            System.out.println("Token: "  + apiContext.getAccessToken());
            creator = paymentCreator.execute(apiContext, PaymentCreator.class, payment);
        } catch (MonCashRestException e) {
            e.printStackTrace();
        }
        if(creator.getStatus().compareTo(202+"") == 0
                && creator.getStatus() != null) {
            logger.info("redirect to the link below");
            logger.info(creator.redirectUri());
            arrayList.put("status", creator.getStatus());
            arrayList.put("message", creator.redirectUri());
            return arrayList;
        } else if(creator.getStatus() == null) {
            logger.warning("Error");
            logger.warning(creator.getError());
            logger.warning(creator.getError_description());
            arrayList.put("status", creator.getStatus());
            arrayList.put("message", creator.getError());
            return arrayList;
        } else {
            logger.warning("Error");
            logger.warning(creator.getStatus());
            logger.warning(creator.getError());
            logger.warning(creator.getMessage());
            logger.warning(creator.getPath());
            arrayList.put("status", creator.getStatus());
            arrayList.put("message", creator.getError());
            return arrayList;
        }
    }

    public HashMap<String, String> getPaymentDetailByTransactionId(String transactionId) throws MonCashRestException {
        PaymentCapture paymentCapture = new PaymentCapture();
        TransactionId transactionId_ = new TransactionId();
        transactionId_.setTransactionId(transactionId);
        PaymentCapture capture = paymentCapture.execute(apiContext, PaymentCapture.class, transactionId_);
        HashMap<String, String> transaction = new HashMap<String, String>();

        if(capture.getStatus() !=null && capture.getStatus().compareTo(202+"")==0){
            logger.info("Transaction");
            logger.info(capture.getPayment().getMessage());
            logger.info("transaction_id="+capture.getPayment().getTransaction_id());
            logger.info("Payer="+capture.getPayment().getPayer());
            logger.info("amount="+capture.getPayment().getCost()+"");

            transaction.put("status", capture.getStatus());
            transaction.put("transactionId", capture.getPayment().getTransaction_id());
            transaction.put("payer", capture.getPayment().getPayer());
            transaction.put("amount", capture.getPayment().getCost()+"");
            transaction.put("message", capture.getPayment().getMessage());
        } else {
            logger.info(capture.getStatus());
            transaction.put("status", capture.getStatus());
        }
        return  transaction;
    }

    public HashMap<String, String> getPaymentDetailByOrderId(String orderId) throws MonCashRestException {
        PaymentCapture paymentCapture = new PaymentCapture();
        OrderId orderId_ = new OrderId();
        orderId_.setOrderId(orderId);
        PaymentCapture capture = paymentCapture.execute(apiContext, PaymentCapture.class, orderId_);
        HashMap<String, String> order = new HashMap<String, String>();

        if(capture.getStatus() !=null && capture.getStatus().compareTo(202+"")==0){
            logger.info("Order");
            logger.info(capture.getPayment().getMessage());
            logger.info("order_id="+capture.getPayment().getTransaction_id());
            logger.info("Payer="+capture.getPayment().getPayer());
            logger.info("amount="+capture.getPayment().getCost()+"");

            order.put("status", capture.getStatus());
            order.put("transactionId", capture.getPayment().getTransaction_id());
            order.put("payer", capture.getPayment().getPayer());
            order.put("amount", capture.getPayment().getCost()+"");
            order.put("message", capture.getPayment().getMessage());
        } else {
            logger.info(capture.getStatus());
            order.put("status", capture.getStatus());
        }
        return  order;
    }
}
