package com.example.admin.dogcollar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PetSaveFbase extends AppCompatActivity  implements View.OnClickListener{
    // variables declared here to be initialised with the value gotten from xml
    private EditText petName;
    private EditText petAge;
    private EditText petGender;
    private EditText petBreed;
    private Button petRegister;
    // declaration of the firebase realtime database variable
    private DatabaseReference reference;
    // declaration of the firebase auth variable
    private FirebaseAuth dbauth;
    private FirebaseUser user;
    private ProgressDialog await;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_save_fbase);
        // the variables declared above are innitialised with the elements in the xml
        petName = (EditText)findViewById(R.id.txt_regis_petName);
        petAge = (EditText)findViewById(R.id.txt_regis_petAge);
        petGender =(EditText)findViewById(R.id.txt_regis_petBreed);
        petBreed = (EditText)findViewById(R.id.txt_regis_petBreed);
        petRegister=(Button)findViewById(R.id.btn_regis_petRegister);
        // gets the instance from the abstract  firebase auth class
        dbauth=FirebaseAuth.getInstance();
        user = dbauth.getCurrentUser();
        //a if condition is run to check and get the current user from the firebase auth and
        // checked if not null. it is run else the code below it runs to add the registration
        if(user==null){
            finish();
            startActivity(new Intent(this,loadout.class));
        }
        // progressdialog for hcI this is done with any internet operation to
        // provide the user with a action until the daata are transfered into the firebase
        await.setMessage("Saving Pet Data");
        // the realtime database instance is gotten and the reference of that instance is retrieved from that instance
        reference = FirebaseDatabase.getInstance().getReference();
        // onclick listners are added and are handled within the class thats why this keyword is used
        petRegister.setOnClickListener(this);
    }

    // a private method created to register new pet
    private void petuserinfo(){
        //here the values from the user inputs are added to a string
        // the use of the trim is to remove and trailing whitespaces. that occur during byte stream to firebase.
        await.show();
        String petname =petName.getText().toString().trim();
        String petage =petAge.getText().toString().trim();
        String petgender = petGender.getText().toString().trim();
        String petbreed =petBreed.getText().toString().trim();
        // verification is done to the code to prevent null from being entered into the system
        if(petname.equals(" ")){// the dot equals is used to check if the string is null
            // for purpose of HCI a toast is printed at this point
            Toast.makeText(this, "Please Enter Pet's name", Toast.LENGTH_SHORT).show();
            return;
        }else if(petage.equals(" ")){// the dot equals is used to check if the string is null
            // for purpose of HCI a toast is printed at this point
            Toast.makeText(this, "Please Enter Pet's Age", Toast.LENGTH_SHORT).show();
            return;

        }else if(petgender.equals(" ")){// the dot equals is used to check if the string is null
            // for purpose of HCI a toast is printed at this point
            Toast.makeText(this, "Please Enter Pet's Gender", Toast.LENGTH_SHORT).show();
            return;

        }else if(petbreed .equals("")){// the dot equals is used to check if the string is null
            // for purpose of HCI a toast is printed at this point
            Toast.makeText(this, "Please Enter Pet's Breed", Toast.LENGTH_SHORT).show();
            return;

        }
        // object of the pet is created and is passed into the class with the gotten pet parameters
        Pet_Register pet = new Pet_Register(petname,petage,petgender,petbreed);
        // here the reltime database a child is created with the value of the
        // Uid of the authentication and a value is set and the refernce is passed into the set value
        reference.child(user.getUid()).setValue(pet);
        // below for HCI purposes ive added the a informative message called adding new pet and information saved
        Toast.makeText(this,"information saved",Toast.LENGTH_LONG).show();
        await.dismiss();
        startActivity(new Intent(this,Content.class));
    }
    @Override
    public void onClick(View v) {
        // the onclick listner listens for actions and when either the pet
        // registration button is pressed
        if(v==petRegister){// this becomes true if the user selects the btn regis_petRegister
            // functions below are executed if the boolean becomes true
            petuserinfo();
        }
    }
}
