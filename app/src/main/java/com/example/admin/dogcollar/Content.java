package com.example.admin.dogcollar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class Content  extends AppCompatActivity implements View.OnClickListener{
    // variables of the xml are initiated here
    private ImageButton schedule;
    private ImageButton location;
    private ImageButton tasks;
    private ImageButton notifications;
    private ImageButton gym;
    private ImageButton antibark;
    private ImageButton petProfile;
    private Button editPetProfile;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        //the variables are initialised to the readings from the xml
        schedule=(ImageButton)findViewById(R.id.img_btn_home_schedule);
        location=(ImageButton)findViewById(R.id.img_btn_home_location);
        tasks=(ImageButton)findViewById(R.id.img_btn_home_tasks);
        notifications=(ImageButton)findViewById(R.id.img_btn_home_notifications);
        gym=(ImageButton)findViewById(R.id.img_btn_home_gym);
        antibark=(ImageButton)findViewById(R.id.img_btn_home_antibark);
        petProfile=(ImageButton) findViewById(R.id.img_btn_petPic);
        editPetProfile=(Button) findViewById(R.id.btn_home_ePetProfile);
        logout=(Button) findViewById(R.id.btn_content_logout);
        // here the action listeners for the buttons are set
        schedule.setOnClickListener(this);
        location.setOnClickListener(this);
        tasks.setOnClickListener(this);
        notifications.setOnClickListener(this);
        gym.setOnClickListener(this);
        antibark.setOnClickListener(this);
        petProfile.setOnClickListener(this);
        editPetProfile.setOnClickListener(this);
        logout.setOnClickListener(this);
    }
    @Override
    public void onClick(View initiate) {
        //in this fucntion the requests are handled and are determined which click was initiated
        if(initiate==schedule){// redirected to schedule activity
            startActivity(new Intent(this,Schedule.class));
        }
        if(initiate==location){// redirected to Location activity
            startActivity(new Intent(this,MapsLocation.class));
        }
        if(initiate==tasks){// redirected to Tasks activity
            startActivity(new Intent(this,RecTasks.class));
        }


        if(initiate==notifications){// redirected to NOtification activity
            startActivity(new Intent(this,Notification.class));
        }
        if(initiate==gym){// redirected to Gym activity
            startActivity(new Intent(this,Gym.class));
        }
        if(initiate==antibark){// redirected to Antibark activity
            startActivity(new Intent(this,AntiBark.class));
        }
        if (initiate==petProfile){// redirected to Petprofile activity
            startActivity(new Intent(this,Status.class));
        }
        if(initiate==editPetProfile){// redirected to Userprofile activity
            startActivity(new Intent(this,editPetProfile.class));
        }
        if (initiate==logout){
            startActivity(new Intent(this,loadout.class));
        }
    }
}


