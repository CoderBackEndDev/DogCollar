package com.example.admin.dogcollar;
// an class created to help passing data into a firebase
public class UserRegister {
// instance varibales declared
        public String fName;
        public String lName;
// overloaded constructor
        public UserRegister(String firstname, String lastname){

            this.fName=firstname;// the declared instance is assigned to the parameter input firstname
            this.lName=lastname;// the declared instance is assigned to the parameter input lastname
        }
        // default constructor
        public UserRegister(){

        }

}
