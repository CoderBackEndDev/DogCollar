package com.example.admin.dogcollar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String username;
    //variables are declared below
    private EditText email;
    private EditText pass;
    private EditText rePass;
    private Button register;
    private TextView signIn;
    // an object reference of the ProgressDialog is created
    private ProgressDialog await;
    //an object reference of the FirebaseAuth is created
    private FirebaseAuth AuthDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // gets the firebase Instance
        AuthDB = FirebaseAuth.getInstance();
        // for HCI we have innitialised the await reference
        await = new ProgressDialog(this);
        // here the variables are assigned to the xml elements in the page of activity_main
        email = (EditText) findViewById(R.id.txt_log_email);
        pass = (EditText) findViewById(R.id.ptxt_log_pass);
        rePass = (EditText) findViewById(R.id.ptxt_repass);
        register = (Button) findViewById(R.id.btn_log_register);
        signIn = (TextView) findViewById(R.id.txt_log_loginredirect);
        // the SetOnclick listeners are added
        register.setOnClickListener(this);
        signIn.setOnClickListener(this);

    }

    @Override
    public void onClick(View compare) {
        // here the lisnters are listened to and an if state ment is used to check
        // which button action is click so if true the functions below are executed
        if (compare == register) {
            newUser();

        }
        if (compare == signIn) {
            //redirects to login page
            startActivity(new Intent(MainActivity.this,loadout.class));
        }
    }

    private void newUser() {
        //Strings created below assign the values gotten from the elements in the after user enters the value
        String emailAddress = email.getText().toString().trim();
        String Password = pass.getText().toString().trim();
        String reenterpass =rePass.getText().toString().trim();

        if (emailAddress.equals(" ")) {//checks if email is empty
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return;//if false stops the function executing further
        }
        if (Password.equals(" ")) {//checks if password is empty
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;//if false stops the function executing further
        }
        if (reenterpass.equals(" ")) {//checks if re-password is empty
            Toast.makeText(this, "Please Enter re-Password", Toast.LENGTH_SHORT).show();
            return;//if false stops the function executing further
        }
        //after above validations are undertaken
        // the values are passsed to firebase
        if (Password.equals(reenterpass)) {
            await.setMessage("Registering User");
            await.show();
            AuthDB.createUserWithEmailAndPassword(emailAddress, Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    await.dismiss();
                    if (task.isSuccessful()) {// sending User registration details
                        Toast.makeText(MainActivity.this, "Registered User", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,PetSaveFbase.class));//type here the code for user registration


                    } else {
                        // user registration has failed an error message is sent through toast message
                        Toast.makeText(MainActivity.this, "User Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }else{
            // the password is compared and an error is sent saying password mismatch
            if(emailAddress.equals(" ")){
                Toast.makeText(this,"Email not entered",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Password mismatch",Toast.LENGTH_SHORT).show();
            }

        }
    }
}