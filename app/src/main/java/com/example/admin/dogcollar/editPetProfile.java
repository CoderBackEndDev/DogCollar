package com.example.admin.dogcollar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class editPetProfile extends AppCompatActivity implements View.OnClickListener {
    // here the variables are innitialiesed of the xml
    private EditText PName;
    private EditText PGender;
    private EditText PAge;
    private EditText PBreed;
    private Button ApplyChanges;
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
        setContentView(R.layout.activity_edit_pet_profile);
        // this is to get the instance of the firebaseauth absract class
        dbAuth = FirebaseAuth.getInstance();
        // arraylist innitalised
        arr= new ArrayList<>();
        // the current user from auth is assigned to the fireabse user this is done to determine the authenticated user is online
        Pet=dbAuth.getCurrentUser();
        getKey=Pet.getUid();// it is saved to the string
        // the reference is taken
        DBref= FirebaseDatabase.getInstance().getReference();
        // the xml components are innitailiased to the variables declared above
        PName=(EditText)findViewById(R.id.txt_editProfile_petName);
        PGender=(EditText)findViewById(R.id.txt_editProfile_petGender);
        PAge=(EditText)findViewById(R.id.txt_editProfile_petAge);
        PBreed=(EditText)findViewById(R.id.txt_editProfile_petBreed);
        ApplyChanges=(Button) findViewById(R.id.btn_editProfile_ApplyChanges);
        // the progressdialog is Done to give the user a reactions after the saving to firebase is innitiated
        await = new ProgressDialog(this);
        await.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        await.setIndeterminate(true);
        // the onclick action for the button
        ApplyChanges.setOnClickListener(this);
        // this allocates the individual keys and values of the database reference.
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

// on th onclick the data is updated to the firebase
    @Override
    public void onClick(View initiate) {
        if(initiate==ApplyChanges){
            update();
            await.setMessage("Updated Data");
            await.show();
        }

    }

    private void update() {
        String petName= PName.getText().toString().trim();
        String petAge = PAge.getText().toString().trim();
        String petGender =PGender.getText().toString().trim();
        String PetBreed = PBreed.getText().toString().trim();
        if(petName.equals(" ")){// the dot equals is used to check if the string is null
            // for purpose of HCI a toast is printed at this point
            Toast.makeText(this, "Please Enter Pet's name", Toast.LENGTH_SHORT).show();
            return;
        }else if(petAge.equals(" ")){// the dot equals is used to check if the string is null
            // for purpose of HCI a toast is printed at this point
            Toast.makeText(this, "Please Enter Pet's Age", Toast.LENGTH_SHORT).show();
            return;

        }else if(petGender.equals(" ")){// the dot equals is used to check if the string is null
            // for purpose of HCI a toast is printed at this point
            Toast.makeText(this, "Please Enter Pet's Gender", Toast.LENGTH_SHORT).show();
            return;

        }else if(PetBreed.equals("")){// the dot equals is used to check if the string is null
            // for purpose of HCI a toast is printed at this point
            Toast.makeText(this, "Please Enter Pet's Breed", Toast.LENGTH_SHORT).show();
            return;

        }
        // object of the pet is created and is passed into the class with the gotten pet parameters
        Pet_Register pet = new Pet_Register(petName,petAge,petGender,PetBreed);
        // here the reltime database a child is created with the value of the
        // Uid of the authentication and a value is set and the refernce is passed into the set value
        DBref.child(getKey).setValue(pet);
        // below for HCI purposes ive added the a informative message called adding new pet and information saved
        Toast.makeText(this,"information Updated",Toast.LENGTH_LONG).show();
        await.dismiss();

    }
}
