package com.example.admin.dogcollar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Status extends AppCompatActivity implements View.OnClickListener{
private TextView PName;
private TextView humidityLvl;
private TextView tempLvl;
private TextView mood;
private TextView PBreed;
private TextView PGender;
private TextView PAge;
private Button Btn_location;
    // an variable of firebase auth is created
    private FirebaseAuth dbAuth;
    //A variable from firebase User is declared here
    FirebaseUser Pet;
    String getKey;// string to store the values of the retrived UID from firebase
    private ProgressDialog await;// USed for HCI purposes
    // Database Ref is Created
    private DatabaseReference DBref;
    //an array is created inorder to save before being displayied in the unique fields
    private List<String> arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        dbAuth = FirebaseAuth.getInstance();
        // arraylist innitalised
        arr= new ArrayList<>();
        // the current user from auth is assigned to the fireabse user this is done to determine the authenticated user is online
        Pet=dbAuth.getCurrentUser();
        getKey=Pet.getUid();// it is saved to the string
        // the reference is taken
        DBref= FirebaseDatabase.getInstance().getReference();
        PName = (TextView)findViewById(R.id.lbl_Status_petName);
        humidityLvl=(TextView)findViewById(R.id.lbl_Status_humidity);
        tempLvl=(TextView)findViewById(R.id.lbl_Status_temp);
        mood=(TextView)findViewById(R.id.lbl_Status_mood);
        PBreed=(TextView)findViewById(R.id.lbl_Status_breed);
        PGender=(TextView)findViewById(R.id.lbl_Status_gender);
        PAge = (TextView)findViewById(R.id.lbl_Status_Age);
        Btn_location=(Button)findViewById(R.id.btn_Status_location);
        Btn_location.setOnClickListener(this);
        DBref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // the arr is cleared to remove the null values.
                arr.clear();
                // here the datasnapshot of the data passed in the registration of the dog is taken and is returned in string
                String petName = dataSnapshot.child(getKey).child("petName").getValue(String.class);
                String PetAge = dataSnapshot.child(getKey).child("petAge").getValue(String.class);
                String petGender = dataSnapshot.child(getKey).child("petGender").getValue(String.class);
                String petBreed = dataSnapshot.child(getKey).child("petBreed").getValue(String.class);
                // then these variables are added to an array
                arr.add(petName);
                arr.add(PetAge);
                arr.add(petGender);
                arr.add(petBreed);
                //after the vales are saved into the array these are then assigned to the elements in the xml so the user
                // can change the values and submit again
                PName.setText(arr.get(0));
                PAge.setText(arr.get(1));
                PGender.setText(arr.get(2));
                PBreed.setText(arr.get(3));
            }
            // if an error between transfering is gotten it is handled by the cancel
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // again hci is used to show the user that the action is cannot be performed
                // otherwise he would not know what happned
                Toast.makeText(getApplicationContext(),"Network Error. Please Check Your Connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v==Btn_location){
            startActivity(new Intent(Status.this,MapsLocation.class));
        }
    }
}
