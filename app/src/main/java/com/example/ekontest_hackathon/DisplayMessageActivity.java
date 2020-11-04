package com.example.ekontest_hackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ekontest_hackathon.Notifications.Client;
import com.example.ekontest_hackathon.Notifications.Data;
import com.example.ekontest_hackathon.Notifications.MyResponse;
import com.example.ekontest_hackathon.Notifications.Sender;
import com.example.ekontest_hackathon.Notifications.Token;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayMessageActivity extends AppCompatActivity {


    DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference("Chats");
    TextView message ;
    FloatingActionButton fab;
    ImageButton send;
    String receiver;

    private MessageAdapter adapter;
    List <MessageModel>  mChats ;
    RecyclerView recyclerView;
    FirebaseUser user;

    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }

    //Service Notification
    APIService apiService;
    Boolean notify=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        mChats = new ArrayList<>();
        //Creating Api service
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            receiver = extras.getString("receiver");
            //The key argument here must match that used in the other activity
        }
        UserModel model = new UserModel();
        model.getUserName(receiver, toolbar);

        fab = findViewById(R.id.floatingActionButtonInvoice);

        fab.setVisibility(View.GONE);



        user = FirebaseAuth.getInstance().getCurrentUser();
        message = findViewById(R.id.textMessage);
        send = findViewById(R.id.btnSend);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Invoice");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println("other id: " + receiver);
                System.out.println("connected id: " + user.getUid());
                System.out.println(snapshot);
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    final InvoiceModel invoice = dataSnapshot.getValue(InvoiceModel.class);
                    if((invoice.getSenderId().equals(user.getUid()) && invoice.getReceiverId().equals(receiver)) ||
                            (invoice.getSenderId().equals(receiver) && invoice.getReceiverId().equals(user.getUid()))) {
                        System.out.println("Invoice Id ---: " + invoice.getInvoiceId());
                        if(invoice.getStatus().equals("New")) {
                            System.out.println("Invoice Id: " + invoice.getInvoiceId());
                            fab.setOnTouchListener(new View.OnTouchListener() {
                                float dX;
                                float dY;
                                int lastAction;
                                GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new SingleTapConfirm());


                                @Override
                                public boolean onTouch(View view, MotionEvent event) {
                                    if(gestureDetector.onTouchEvent(event)) {
                                        Intent intent_1 = new Intent(getApplicationContext(), MonCash.class);
                                        intent_1.putExtra("receiver", receiver);
                                        intent_1.putExtra("sender", invoice.getSenderId());
                                        intent_1.putExtra("invoiceId", invoice.getInvoiceId());
                                        intent_1.putExtra("transaction", "freelance");
                                        startActivity(intent_1);
                                    } else {
                                        switch (event.getActionMasked()) {
                                            case MotionEvent.ACTION_DOWN:
                                                dX = view.getX() - event.getRawX();
                                                dY = view.getY() - event.getRawY();
                                                lastAction = MotionEvent.ACTION_DOWN;
                                                break;

                                            case MotionEvent.ACTION_MOVE:
                                                view.setY(event.getRawY() + dY);
                                                view.setX(event.getRawX() + dX);
                                                lastAction = MotionEvent.ACTION_MOVE;
                                                break;

                                            case MotionEvent.ACTION_UP:
                                                if (lastAction == MotionEvent.ACTION_DOWN) {
                                                    Toast.makeText(getApplicationContext(), "Reading invoice", Toast.LENGTH_LONG).show();
                                                }
                                                break;

                                            default:
                                                return false;
                                        }
                                    }
                                    return false;
                                }
                            });

                            fab.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        Toast.makeText(getApplicationContext(), "user id: " + user.getEmail(), Toast.LENGTH_LONG).show();


        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                if(s.toString().trim().length()!=0){
                    send.setVisibility(View.VISIBLE);
                }else{
                    send.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().trim().length()!=0){
                    send.setVisibility(View.VISIBLE);
                }else{
                    send.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().trim().length()!=0){
                    send.setVisibility(View.VISIBLE);
                }else{
                    send.setVisibility(View.GONE);
                }
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notify =true;
                sendingMessage(receiver,user.getUid(),message.getText().toString());
                message.setText("");
            }
        });

        setDisplayMessage(receiver,user.getUid());
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final MenuInflater inflater = getMenuInflater();

        //show invoice button if it's a freelancer or teacher
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("personalInformationModel");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                System.out.println(snapshot);
                PersonalInformationModel connectedUser = snapshot.getValue(PersonalInformationModel.class);
                if(connectedUser.getType().equals("Freelancer") || connectedUser.getType().equals("Professor")) {
                    inflater.inflate(R.menu.message_menu, menu);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.invoice_menu:
                Toast.makeText(getApplicationContext(), "Invoice selected", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, CreateInvoice.class);
                intent.putExtra("receiver", receiver);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void sendingMessage(final String receiver, String sender, String mes){

        MessageModel chatMessage = new MessageModel(receiver,sender, mes);

        chatMessage.InsertMessage(receiver, sender, mes);
        final String message = mes;
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Tokens").child(user.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel user1 = snapshot.getValue(UserModel.class);
                if(notify) {
                    sendNotification(receiver, user.getDisplayName(), message);
                }

                notify =false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        /*DocumentReference ref = FirebaseFirestore.getInstance().collection("Users").document(user.getUid());
        ref.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                   // Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                   // Log.d(TAG, "Current data: " + snapshot.getData());
                    User user1 = snapshot.toObject(User.class);
                    sendNotification(receiver,user1.getUserName(), message);
                } else {
                   // Log.d(TAG, "Current data: null");
                }
            }

        });*/
    }

    private void sendNotification(final String receiver, final String userName, final String message) {
        // CollectionReference token= FirebaseFirestore.getInstance().collection("Tokens");
        DatabaseReference tok= FirebaseDatabase.getInstance().getReference("Tokens");
        Query query =tok.orderByKey().equalTo(receiver);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Token token = dataSnapshot.getValue(Token.class);
                    Data data = new Data(user.getUid(),R.mipmap.ic_launcher , userName+": "+ message , "new message", receiver);

                    Sender sender = new Sender(data,token.getToken());
                    apiService.sendNotification(sender)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                    if(response.code()==200){
                                        if(response.body().success != 1){
                                            Toast.makeText(DisplayMessageActivity.this, "Sending notification failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setDisplayMessage(final String receiver, final String sender) {
        recyclerView = findViewById(R.id.displayMessage);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);


        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mChats.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    MessageModel model = dataSnapshot.getValue(MessageModel.class);
                    if((model.getReceiver().equals(receiver) && model.getSender().equals(sender)) || (model.getReceiver().equals(sender) && model.getSender().equals(receiver))){
                        mChats.add(model);

                    }
                }
                adapter= new MessageAdapter(DisplayMessageActivity.this, mChats);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private Date getDate(long time) {
        Calendar calendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getDefault();
        calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        java.util.Date currenTimeZone=new java.util.Date((time*1000L));
        //Toast.makeText(DisplayMessage.this, ""+sdf.format(currenTimeZone), Toast.LENGTH_SHORT).show();

        return currenTimeZone;
    }

    public void userStatus(String status){
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("status", status);
        reference.updateChildren(hashMap);

    }

    public void openInvoice(View view) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //userStatus("online");
    }
    @Override
    protected void onPause() {
        super.onPause();
        //userStatus("offline");
    }

}

