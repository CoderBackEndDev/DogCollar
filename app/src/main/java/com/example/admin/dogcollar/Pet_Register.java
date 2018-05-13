package com.example.admin.dogcollar;

import android.widget.EditText;
// an class is created help sending data to the firebase using objects of this class
public class Pet_Register {
    //instance variables of the class where the data is being stored before transfered into the firebase
    public String petName;// contains the instance of the pets name
    public String petAge;// contains the instance of the pets Age
    public String petGender;// contains the instance of the pets gender
    public String petBreed;// contains the instance of the Pets breed
// overloaded constructor
    public Pet_Register(String petName,String petAge,String petGender,String petBreed){
        this.petName=petName;// the instance is initialised with the petName parameter
        this.petAge=petAge;// the instance is initialised with the petAge parameter
        this.petGender=petGender;// the instance is initialised with the petGender parameter
        this.petBreed=petBreed;// the instance is initialised with the petBreed parameter
    }
    // default constructor
    public Pet_Register(){

    }
}