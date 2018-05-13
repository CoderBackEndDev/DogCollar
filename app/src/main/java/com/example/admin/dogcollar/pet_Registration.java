package com.example.admin.dogcollar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class pet_Registration extends AppCompatActivity implements View.OnClickListener {
    // variables declared here to be initialised with the value gotten from xml
    private EditText txt_regis_petName;
    private EditText txt_regis_petAge;
    private EditText txt_regis_petGender;
    private EditText txt_regis_petBreed;
    private Button btn_regis_petRegister;
    // declaration of the firebase auth variable
    private FirebaseAuth dbAuth;
    private ProgressDialog await;
    // declaration of the firebase realtime database variable
    private DatabaseReference DBref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet__registration);
//a if condition is run to check and get the current user from the firebase auth and
// checked if not null. it is run else the code below it runs to add the registration
        if (dbAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), profile.class));
        }
        // gets the instance from the abstract  firebase auth class
        dbAuth = FirebaseAuth.getInstance();
        // the variables declared above are innitialised with the elements in the xml
        txt_regis_petName=(EditText)findViewById(R.id.txt_regis_petName);
        txt_regis_petAge=(EditText)findViewById(R.id.txt_regis_petAge);
        txt_regis_petGender=(EditText)findViewById(R.id.txt_regis_petGender);
        txt_regis_petBreed=(EditText)findViewById(R.id.txt_regis_petBreed);
        // the realtime database instance is gotten and the reference of that instance is retrieved from that instance
         DBref = FirebaseDatabase.getInstance().getReference();
         // progressdialog for hcI this is done with any internet operation to
        // provide the user with a action until the daata are transfered into the firebase
        await = new ProgressDialog(this);
        await.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        await.setIndeterminate(true);

        // the variables declared above are innitialised with the elements in the xml
        btn_regis_petRegister=(Button) findViewById(R.id.btn_regis_petRegister);

        // onclick listners are added and are handled within the class thats why this keyword is used
        btn_regis_petRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View innitiator) {
        // the onclick listner listens for actions and when either the pet
        // registration or the petskip button is pressed
        if(innitiator==btn_regis_petRegister ){// this becomes true if the user selects the btn regis_petRegister
            // functions below are executed if the boolean becomes true
            newPet();
            startActivity(new Intent(this,Content.class));
        }

    }
// a private method created to register new pet
    private void newPet() {
        //here the values from the user inputs are added to a string
        // the use of the trim is to remove and trailing whitespaces. that occur during byte stream to firebase.
        String petName =txt_regis_petName.getText().toString().trim();
        String petAge =txt_regis_petAge.getText().toString().trim();
        String petGender =txt_regis_petGender.getText().toString().trim();
        String petBreed =txt_regis_petBreed.getText().toString().trim();
        // verification is done to the code to prevent null from being entered into the system
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

        }else if(petBreed.equals("")){// the dot equals is used to check if the string is null
            // for purpose of HCI a toast is printed at this point
            Toast.makeText(this, "Please Enter Pet's Breed", Toast.LENGTH_SHORT).show();
            return;

        }
        // object of the pet is created and is passed into the class with the gotten pet parameters

        Pet_Register Pet = new Pet_Register(petName,petAge,petGender,petBreed);
        FirebaseUser send = dbAuth.getCurrentUser();// gets the currentUser and saves it to the send
        // here the reltime database a child is created with the value of the
        // Uid of the authentication and a value is set and the refernce is passed into the set value
        DBref.child(send.getUid()).setValue(Pet);
        // below for HCI purposes ive added the a informative message called adding new pet
        await.setMessage("Adding new Pet");
        await.show();// then its showed
    }
}


