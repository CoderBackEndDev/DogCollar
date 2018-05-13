package com.example.admin.dogcollar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class editUserProfile extends AppCompatActivity {
private EditText fName;
private EditText lName;
private EditText password;
private Button applyChanges;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        fName=(EditText)findViewById(R.id.txt_editUser_fName);
        lName=(EditText)findViewById(R.id.txt_editUser_lName);
        password=(EditText)findViewById(R.id.txt_editUser_pass);
        applyChanges=(Button)findViewById(R.id.btn_editUser_confirm);


    }
}
