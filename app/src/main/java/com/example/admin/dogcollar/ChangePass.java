package com.example.admin.dogcollar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ChangePass extends AppCompatActivity implements View.OnClickListener{
    // variables are decalared here
    private EditText txt_chngepass_email;
    private Button btn_chngepass_submit;
    private FirebaseAuth dbAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        // we are obtaining an instance of that abstract FirebaseAuth class after the dependacy is fixed
        dbAuth = FirebaseAuth.getInstance();
        // variables declared earlier are initialised at this point. these are values in the respective activity_change password class
        txt_chngepass_email=(EditText)findViewById(R.id.txt_chngepass_email);
        btn_chngepass_submit=(Button) findViewById(R.id.btn_chngepass_submit);
        // here an onclick lister is set and the location of the listen is set to this class as there is an overriden onclick
        // from the interface View.onclick lister
        btn_chngepass_submit.setOnClickListener(this);
    }
    // the overridden onclick listener
    @Override

    public void onClick(View initiator) {
        // below after an click is innitated ealier this compares to see which button is innnitiated
        //it is identified  by a boolean if  true then this action was invoked so the methods below this are invoked
        if(initiator==btn_chngepass_submit){
            changePassword();
        }

    }
// new provate method to change the class
    private void changePassword() {
        // the gotten from the activity xml are stored into Strings created here
        String Email = txt_chngepass_email.getText().toString().trim();
        if(Email.equals(" ")){// to check if email is null
            //this is done to check for user incorrect submissions
            // here a toast is posted this givers the user feedback about the mistake of an null email field
            Toast.makeText(this, "Please Enter Signed up Email", Toast.LENGTH_SHORT).show();
            //since it is null the action shouldnt be done so return and empty implementation
            // is given so the user have to type an email for this to work
            return;
        }
        else{
            //this is the function we are using from firebase to send an email to the users email address
            //to help them reset the firebase Auth password
            dbAuth.sendPasswordResetEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    // this task object contains methods to see state of this action.

                    if (task.isSuccessful()){// here we check to see if the task is completed successfully
                        //to help with the HCI ive used this message so user gets an action return after the submission to
                        // compromise with the delay from fireabse
                        Toast.makeText(ChangePass.this, "Password Reset Sent",Toast.LENGTH_SHORT).show();
                        // this is done to close this activity in a clean way
                        finish();
                        // here the new activity is innitiated . in this instance the loadout the user is redirected there
                        startActivity(new Intent(ChangePass.this,loadout.class));
                    }else{
                        // this response is given to the user if he hasnt registered firebase or if there are connection issues to firebase the user is given the chance to try again
                        Toast.makeText(ChangePass.this, "Unable to Send reset password Email please retry",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            });
        }
    }
}


