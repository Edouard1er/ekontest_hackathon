package com.example.ekontest_hackathon;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.HashMap;
import java.util.Map;

public class AvisModel  implements Parcelable  {
    private int nStar;
    private String comment;
    private String idUser;
    private long datetime;
    private boolean first_question;
    private boolean second_question;
    private boolean third_question;
    private int [] data;
    DatabaseReference databaseReference;
    FirebaseUser cUser=FirebaseAuth.getInstance().getCurrentUser();

    public AvisModel(int nStar, String comment, boolean first_question, boolean second_question, boolean third_question) {
        this.nStar = nStar;
        this.comment = comment;
        this.first_question = first_question;
        this.second_question = second_question;
        this.third_question = third_question;
    }

    public AvisModel(int nStar, String comment, String idUser, long datetime) {
        this.nStar = nStar;
        this.comment = comment;
        this.idUser = idUser;
        this.datetime = datetime;
    }

    public AvisModel(){}

    public void InsertAvis(String id,String comment, int nStar){
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        .child(id).child("avisModel");
        //databaseReference.push().setValue(model);

       // String key = databaseReference.push().getKey();
        Map<String,Object> avis= new HashMap<>();
        avis.put("idUser", cUser.getUid());
        avis.put("nStar", nStar);
        avis.put("comment", comment);
        avis.put("datetime", ServerValue.TIMESTAMP);
        databaseReference.child(cUser.getUid()).setValue(avis);
    }

    public void InsertAvisForCourse(String idCours,String comment, int nStar){
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                .child("coursModel").child(idCours).child("avisModel");
        //databaseReference.push().setValue(model);

        //String key = databaseReference.push().getKey();
        Map<String,Object> avis= new HashMap<>();
        avis.put("idUser", cUser.getUid());
        avis.put("nStar", nStar);
        avis.put("comment", comment);
        avis.put("datetime", ServerValue.TIMESTAMP);
        databaseReference.child(cUser.getUid()).setValue(avis);
    }


    public void setInfoAvis(String idFreelancer,final RatingBar bar){
       /* AvisModel avisModel = new AvisModel();
        int [] data = avisModel.getAvisData(results.getString("idFreelancer"));
        Toast.makeText(activity, "Voyons si les donnees sont recuperees: "+data[1], Toast.LENGTH_SHORT).show();*/
        final int[] tStars = new int[1];
        final int[] tRate = new int [1];
        tStars[0]=0;
        tRate[0]=0;

        final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference freelancerRef= FirebaseDatabase.getInstance().getReference("Users").child(idFreelancer).child("avisModel");
        freelancerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AvisModel model= new AvisModel();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    model = dataSnapshot.getValue(AvisModel.class);
                    tStars[0] =tStars[0]+ (int)model.getnStar();
                    tRate[0] =tRate[0]+1;

                }
                if(tRate[0]>0){
                    bar.setRating((float) tStars[0]/tRate[0]);
                    // mRatingBar.setNumberOfStars(tStars[0]/tRate[0]);
                }else{
                    bar.setRating(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void setInfoAvis(String idFreelancer,final SimpleRatingBar bar){
       /* AvisModel avisModel = new AvisModel();
        int [] data = avisModel.getAvisData(results.getString("idFreelancer"));
        Toast.makeText(activity, "Voyons si les donnees sont recuperees: "+data[1], Toast.LENGTH_SHORT).show();*/
        final int[] tStars = new int[1];
        final int[] tRate = new int [1];
        tStars[0]=0;
        tRate[0]=0;

        final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference freelancerRef= FirebaseDatabase.getInstance().getReference("Users")
                .child(idFreelancer).child("avisModel");
        freelancerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AvisModel model= new AvisModel();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    model = dataSnapshot.getValue(AvisModel.class);
                    tStars[0] =tStars[0]+ (int)model.getnStar();
                    tRate[0] =tRate[0]+1;

                }
                if(tRate[0]>0){
                    bar.setRating((float) tStars[0]/tRate[0]);
                    // mRatingBar.setNumberOfStars(tStars[0]/tRate[0]);
                }else{
                    bar.setRating(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void setInfoAvis(String idFreelancer, final RatingBar bar, final TextView mRatingValue,
                            final ProgressBar p1, final ProgressBar p2, final ProgressBar p3, final ProgressBar p4, final ProgressBar p5, final TextView tot_avis ){
        final int[] tStars = new int[1];
        final int[] tRate = new int [1];
        final int[] tavis = new int[1];
        tStars[0]=0;
        tRate[0]=0;
        tavis[0]=0;

        final int [] pro1= new int[1];
        final int [] pro2= new int[1];
        final int [] pro3= new int[1];
        final int [] pro4= new int[1];
        final int [] pro5= new int[1];
        pro1[0]=0;
        pro2[0]=0;
        pro3[0]=0;
        pro4[0]=0;
        pro5[0]=0;





        final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference freelancerRef= FirebaseDatabase.getInstance().getReference("Users").child(idFreelancer).child("avisModel");
        freelancerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AvisModel model= new AvisModel();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    model = dataSnapshot.getValue(AvisModel.class);
                    tStars[0] =tStars[0]+ (int)model.getnStar();
                    tRate[0] =tRate[0]+1;
                    tavis[0] = tavis[0] +1;

                    //Progress bar
                    switch (model.getnStar()){
                        case 1:
                            pro1[0]=pro1[0]+1;
                            break;
                        case 2:
                            pro2[0]+=1;
                            break;
                        case 3:
                            pro3[0]+=1;
                            break;
                        case 4:
                            pro4[0]+=1;
                            break;
                        case 5:
                            pro5[0]+=1;
                            break;
                    }

                }
                if(tRate[0]>0){
                    int pV1= (pro1[0]*100)/tRate[0];
                    int pV2= (pro2[0]*100)/tRate[0];
                    int pV3= (pro3[0]*100)/tRate[0];
                    int pV4= (pro4[0]*100)/tRate[0];
                    int pV5= (pro5[0]*100)/tRate[0];


                    float rate = (float) tStars[0]/tRate[0];
                    bar.setRating(rate);
                    mRatingValue.setText(""+arrondiRate(rate));
                    p1.setProgress(pV1);
                    p2.setProgress(pV2);
                    p3.setProgress(pV3);
                    p4.setProgress(pV4);
                    p5.setProgress(pV5);
                   // double d = (double) Math.round(tonDouble * 100) / 100;
                    tot_avis.setText(""+tavis[0]);

                    // mRatingBar.setNumberOfStars(tStars[0]/tRate[0]);
                }else{
                    bar.setRating(0);
                    mRatingValue.setText(""+0);
                    p1.setProgress(0);
                    p2.setProgress(0);
                    p3.setProgress(0);
                    p4.setProgress(0);
                    p5.setProgress(0);
                    tot_avis.setText(""+0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public float arrondiRate (float rate ){
        return (float)Math.round(rate*100)/100;
    }
    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public int getnStar() {
        return nStar;
    }

    public void setnStar(int nStar) {
        this.nStar = nStar;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isFirst_question() {
        return first_question;
    }

    public void setFirst_question(boolean first_question) {
        this.first_question = first_question;
    }

    public boolean isSecond_question() {
        return second_question;
    }

    public void setSecond_question(boolean second_question) {
        this.second_question = second_question;
    }

    public boolean isThird_question() {
        return third_question;
    }

    public void setThird_question(boolean third_question) {
        this.third_question = third_question;
    }

    public int[] getData() {
        return data;
    }

    public void setData(int[] data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.nStar);
        dest.writeString(this.comment);
        dest.writeString(this.idUser);
        dest.writeLong(this.datetime);
    }


    public AvisModel(Parcel in) {
        this.nStar = in.readInt();
        this.comment = in.readString();
        this.idUser = in.readString();
        this.datetime = in.readLong();

    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public AvisModel createFromParcel(Parcel in) {
            return new AvisModel(in);
        }

        public AvisModel[] newArray(int size) {
            return new AvisModel[size];
        }
    };
}
