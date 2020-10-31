package com.example.ekontest_hackathon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.digicelgroup.moncash.exception.MonCashRestException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class TransactionModel {
    String idTransaction;
    String orderId;
    String transactionKey;
    String payer;
    String buyer;
    String type;

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private DatabaseReference databaseReference;

    public TransactionModel() {}

    public TransactionModel(String orderId, String transactionKey, String idTransaction, String payer, String buyer, String type) {
        this.orderId = orderId;
        this.transactionKey = transactionKey;
        this.idTransaction = idTransaction;
        this.payer = payer;
        this.buyer = buyer;
        this.type = type;
    }

    public void insertTransaction(TransactionModel model) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        System.out.println("Inserting new transaction...");
        databaseReference = database.getReference("TransactionModel");
        String key = databaseReference.push().getKey();
        model.setIdTransaction(key);
        databaseReference.child(key).setValue(model);

        DatabaseReference transaction = FirebaseDatabase.getInstance().getReference("TransactionModel").child(key);
        transaction.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final int[] i = {1};
                System.out.println("iteration: " + i[0]);
                System.out.println("Value Change...");
                System.out.println(snapshot);
                TransactionModel tModel = snapshot.getValue(TransactionModel.class);
//                Activity context = MoncashGateway.MG;
                if(tModel.getTransactionKey().isEmpty()) {
                    System.out.println("First creation");
                } else {
                    System.out.println("Should be closed");
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            MoncashGateway.MG.finish();
                            try {
                                if(i[0] == 2) {

                                }
                                MonCash.MC.finish();
                                MonCash.MC.startActivity(MonCash.MC.getIntent());
                                MonCash.successPaymentMessage();
                                i[0] += 1;
                            } catch (MonCashRestException e) {
                                e.printStackTrace();
                            }
                        }
                    }, 1000);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Cancelled listenning...");
            }
        });
    }

    public String getOrderId() {
        return orderId;
    }

    public String getTransactionKey() {
        return transactionKey;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setTransactionKey(String transactionKey) {
        this.transactionKey = transactionKey;
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(String idTransaction) {
        this.idTransaction = idTransaction;
    }
}

