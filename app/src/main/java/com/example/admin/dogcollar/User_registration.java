package com.example.admin.dogcollar;

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

public class User_registration extends AppCompatActivity implements View.OnClickListener {
    // declared variables to be assigned to elements in the xml
private EditText Fname;
private EditText Lname;
private Button btn_User_skip;
private Button btn_logout;
private Button  btn_regis_user;
// declared an variable from the abstract firebase auth
private FirebaseAuth AuthDB;
    // declared an variable from the realtime database
    private DatabaseReference DbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        // an instance of the fireauth is assigned to the AuthDB
        AuthDB=FirebaseAuth.getInstance();
//        if(AuthDB.getCurrentUser()==null){// checks if there is a current user already inside
//            startActivity(new Intent(this,pet_Registration.class));// Implement content
//        }
        // the database is initialised and the reference is taken and its saved for this varibale DbRef
        DbRef = FirebaseDatabase.getInstance().getReference();
        Fname=(EditText)findViewById(R.id.txt_regis_fName);
        Lname = (EditText)findViewById(R.id.txt_regis_lName);

// the declared variables are attached to the elements of the corresponding
// class in this case its connected to the activity_user_registration
        btn_User_skip= (Button)findViewById(R.id.btn_User_skip);
        btn_regis_user= (Button)findViewById(R.id.btn_regiUser);
        btn_logout= (Button)findViewById(R.id.btn_logout);
        // the onclicklisteners are added and it is referenced to this document
        // and its handled later into the document
        btn_regis_user.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
        btn_User_skip.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v== btn_regis_user){
            // if the user clicks register a new user is added
            NewUser();
        }
        if(v==btn_logout){
/// the user can choose to leave the signed in account
            AuthDB.signOut();
            finish();
            startActivity(new Intent(this,loadout.class));// connect login page
        }
        if(v==btn_User_skip){
            startActivity(new Intent(this,pet_Registration.class));// connect to pet registration
        }
    }

    private void NewUser() {
        // The values from the user input are assigned to strings below
        String firstname = Fname.getText().toString().trim();
        String lastname = Lname.getText().toString().trim();
        // the class UserRegister is used to innitialise and an reference object is created
        UserRegister Owner = new UserRegister(firstname,lastname);
        FirebaseUser use = AuthDB.getCurrentUser();// the Firebase auth current user is assigned to the FirebaseUser use
        // we use the database reference and the firebase use and pass the set value as the object reference we created ealier
        DbRef.child(use.getUid()).setValue(Owner);
        //for HCI purpose a text is displayied displaying information saved
        Toast.makeText(this,"Information saved",Toast.LENGTH_SHORT);


    }

}
