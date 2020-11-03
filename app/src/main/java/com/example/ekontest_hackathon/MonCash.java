package com.example.ekontest_hackathon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.digicelgroup.moncash.exception.MonCashRestException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class MonCash extends AppCompatActivity {

    static ImageButton submitButton;
    static Activity MC;
    int amount;
    TextView textViewProviderName, textViewProviderEmail, textViewProviderPhone,
            textViewProductType, textViewProductName, textViewProductPrice;
    static TextView textViewSuccess;
    static String transaction;
    static String invoiceId;
    static  String idDocument;
    String receiver;
    String sender;
    static ValueEventListener mListener;
    MonCashPayment payment;
    static public ProgressBar progressBar;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moncash);
        MC = this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Payment");

        submitButton = (ImageButton) findViewById(R.id.submitAmount);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        textViewProductName = (TextView) findViewById(R.id.product_name);
        textViewProviderName = (TextView) findViewById(R.id.provider_name);
        textViewProviderEmail = (TextView) findViewById(R.id.provider_email);
        textViewProviderPhone = (TextView) findViewById(R.id.provider_phone);
        textViewProductType = (TextView) findViewById(R.id.product_type);
        textViewProductPrice = (TextView) findViewById(R.id.product_price);
        textViewSuccess = (TextView) findViewById(R.id.textViewSuccess);

        Intent data = getIntent();
        transaction = data.getStringExtra("transaction");
        receiver = data.getStringExtra("receiver");
        sender = data.getStringExtra("sender");

        submitButton.setVisibility(View.VISIBLE);
        textViewSuccess.setVisibility(View.GONE);


        if(transaction.equals("freelance")) {
            submitButton.setVisibility(View.VISIBLE);
            textViewSuccess.setVisibility(View.VISIBLE);

            invoiceId = data.getStringExtra("invoiceId");
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Invoice").child(invoiceId);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    final InvoiceModel invoice = snapshot.getValue(InvoiceModel.class);
                    amount = invoice.getAmount();
                    textViewProductName.setText(invoice.getFreelanceName());
                    textViewProductPrice.setText(invoice.getAmount() + " gdes");
                    textViewProductType.setText("Freelance");

                    if(invoice.getStatus().equals("New")) {
                        textViewSuccess.setVisibility(View.GONE);
                        if(invoice.getSenderId().equals(user.getUid())) {
                            submitButton.setVisibility(View.GONE);
                        } else {
                            submitButton.setVisibility(View.VISIBLE);
                        }
                    } else {
                        textViewSuccess.setVisibility(View.VISIBLE);
                        submitButton.setVisibility(View.GONE);
                    }

                    final DatabaseReference provider = FirebaseDatabase.getInstance().getReference("Users").child(invoice.getSenderId()).child("personalInformationModel");
                    provider.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            final PersonalInformationModel providerInfo = snapshot.getValue(PersonalInformationModel.class);
                            textViewProviderName.setText(providerInfo.getFirstname() + " " + providerInfo.getLastname());
                            textViewProviderEmail.setText(providerInfo.getEmail());
                            textViewProviderPhone.setText(providerInfo.getPhone());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        if(transaction.equals("document")) {
            idDocument = data.getStringExtra("idDocument");
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Documents").child("PaidDoc").child(idDocument);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    final PaidDocModel doc = snapshot.getValue(PaidDocModel.class);
                    amount = (int) doc.getPrice();
                    textViewProductName.setText(doc.getTitle());
                    textViewProductPrice.setText(amount + " gdes");
                    textViewProductType.setText("Document");
                    final DatabaseReference provider = FirebaseDatabase.getInstance().getReference("Users").child(doc.getIdUser()).child("personalInformationModel");
                    provider.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            final PersonalInformationModel providerInfo = snapshot.getValue(PersonalInformationModel.class);
                            textViewProviderName.setText(providerInfo.getFirstname() + " " + providerInfo.getLastname());
                            textViewProviderEmail.setText(providerInfo.getEmail());
                            textViewProviderPhone.setText(providerInfo.getPhone());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        progressBar.setVisibility(View.GONE);
        payment = new MonCashPayment("f472319a575420082d2aee7a226c2865",
                                                    "_WDlVgFcbMw19kMEGLjhvev36WQBWVmcRDwgZ3Mlk8dBM8Hn-TpeRjuj-RtqJwgD");
    }

    public void submitAmountAction(View view) {
        progressBar.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> createdPayment = payment.createPayment(amount, sender, receiver, transaction);
                final String text = "Payment status: " + createdPayment.get("status") + "\n" +
                        "Return URL: " + createdPayment.get("message");
                System.out.println(text);
                if(createdPayment.get("status").compareTo("202")==0) {
                    Intent intent = new Intent(getApplicationContext(),MoncashGateway.class);
                    intent.putExtra("url", createdPayment.get("message"));
                    startActivity(intent);
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "There was an error.", Toast.LENGTH_LONG);
                }
            }
        }, 500);
    }

    public static void  successPaymentMessage(String transaction) throws MonCashRestException {
        submitButton.setVisibility(View.GONE);
        textViewSuccess.setVisibility(View.VISIBLE);
        System.out.println("Inside success payment...");
        System.out.println("Transaction type is: " + transaction);
        if(transaction.equals("freelance")) {
            FirebaseDatabase.getInstance().getReference("Invoice").child(invoiceId).child("status").setValue("Paid");
            DatabaseReference db = FirebaseDatabase.getInstance().getReference("Invoice").child(invoiceId);
            db.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Date c = Calendar.getInstance().getTime();
                    System.out.println("Current time => " + c);
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                    String formattedDate = df.format(c);

                    InvoiceModel inv = snapshot.getValue(InvoiceModel.class);
                    MyFreelancerModel.InsertMyFreelancer(inv.getSenderId());
                    FirebaseDatabase.getInstance().getReference("Users").child(inv.getSenderId()).child("Students").push().child(inv.getReceiverId());
                    FirebaseDatabase.getInstance().getReference("Invoice").child(inv.getInvoiceId()).child("date").setValue(c);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        if (transaction.equals("document")) {
            System.out.println("We are inside type : " + transaction + " of transaction");
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            System.out.println("Current user: " + user.getUid());
            FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("docPurchased").push().setValue(idDocument);
        }
    }
}
