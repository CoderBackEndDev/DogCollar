package com.example.admin.dogcollar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.icu.text.AlphabeticIndex;
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
import com.google.firebase.auth.FirebaseUser;

public class loadout extends AppCompatActivity implements View.OnClickListener {
    private String username;
    //a the varibalea are initialised
    private EditText txt_log_email;
    private EditText ptxt_log_pwd;
    private Button btn_login;
    private TextView lbl_register;
    private TextView lbl_log_chngpass;
    // an variable of the abstract firebase Auth is created
    private FirebaseAuth dbAuth;
    //// this is agined used for HcI purpose
    private ProgressDialog await;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadout);
        // the variables created above are initialised
        txt_log_email = (EditText)findViewById(R.id.txt_log_email);
        ptxt_log_pwd = (EditText)findViewById(R.id.ptxt_log_pwd);
        btn_login= (Button)findViewById(R.id.btn_login);
        lbl_register=(TextView)findViewById(R.id.lbl_register);
        lbl_log_chngpass=(TextView)findViewById(R.id.lbl_log_chngpass);
        // this gets the instance of the auth
        dbAuth = FirebaseAuth.getInstance();
        FirebaseUser user = dbAuth.getCurrentUser();
        // this loop checks
//       if (user!= null) {
//            finish();
//            startActivity(new Intent(loadout.this, loadout.class));
//        }
        // an action lister is added to the change password and the activity change password is invoked
        lbl_log_chngpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loadout.this, ChangePass.class));
            }
        });


        await = new ProgressDialog(this);
        await.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        await.setIndeterminate(true);
        // message of the progressdialog is set to attempting to login
        await.setMessage("Attempt Logging in");
        lbl_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);

    }
    private void login(){
        // here the Strings are assigned then values from the xml
        String email = txt_log_email.getText().toString().trim();
        String pass =ptxt_log_pwd.getText().toString().trim();
        // checking if the email is null
        if(email.equals(" ")){
            //prompting a Toast to give the user feedback
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            //to give no action after the message
            return;
        }
        // checking if password is null
        if(pass.equals(" ")){
            //prompting a Toast to give the user feedback
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            //to give no action after the message
            return;
        }

        await.show();// here the progressdialog message is shown with the action

            // here the user is logged in if he has entered the correct username abnd password
            dbAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    await.dismiss();
                    // the task completed is retrived to give the suer a feeback if it has passed
                    if (task.isSuccessful()) {
                        Toast.makeText(loadout.this, "logging in", Toast.LENGTH_SHORT).show();
                        // Home is loaded
                        finish();
                        startActivity(new Intent(getApplicationContext(), Content.class));
                    } else {
                        Toast.makeText(loadout.this, "please rerty with an appropriate email and loggin", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }
// the handler for the onclick function
    @Override
    public void onClick(View innitiate) {
        //the even listener is listening and is finding which button user has clicked
        if(innitiate==btn_login){
            //fucntion is innitated
            login();
        }
        if(innitiate==lbl_register){
            finish();
            // the mainactivity is done
            startActivity(new Intent(this,MainActivity.class));
        }
    }
}
