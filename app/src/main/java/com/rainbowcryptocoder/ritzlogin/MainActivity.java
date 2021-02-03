package com.rainbowcryptocoder.ritzlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextInputLayout id_firstName, id_lastName, id_phoneNumber, id_mail, id_password;
    EditText id_dob;
    RadioButton id_male, id_female, id_rider, id_pool, id_both;

    private DatePickerDialog.OnDateSetListener mDateSetListener;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id_firstName = findViewById(R.id.et_firstName);
        id_lastName = findViewById(R.id.et_lastName);
        id_phoneNumber = findViewById(R.id.et_phoneNumber);
        id_mail = findViewById(R.id.et_email);
        id_dob = findViewById(R.id.et_dob);
        id_password = findViewById(R.id.et_password);
        id_male = findViewById(R.id.rd_male);
        id_female = findViewById(R.id.rd_female);
        id_rider = findViewById(R.id.rd_rider);
        id_pool = findViewById(R.id.rd_pool);
        id_both = findViewById(R.id.rd_both);

    }

    private Boolean validateFirstName() {
        String val = id_firstName.getEditText().getText().toString();
        if (val.isEmpty()){
            id_firstName.setError("Field can't be empty!");
            return false;
        }
        else {
            id_firstName.setError(null);
            return true;
        }
    }

    private Boolean validateLastName() {
        String val = id_lastName.getEditText().getText().toString();
        if (val.isEmpty()){
            id_lastName.setError("Field can't be empty!");
            return false;
        }
        else {
            id_lastName.setError(null);
            return true;
        }
    }

    private Boolean validatePhoneNumber() {
        String val = id_phoneNumber.getEditText().getText().toString();
        if (val.isEmpty()){
            id_phoneNumber.setError("Field can't be empty!");
            return false;
        }
        else {
            id_phoneNumber.setError(null);
            return true;
        }
    }

    private Boolean validateMail() {
        String val = id_mail.getEditText().getText().toString();
        if (val.isEmpty()){
            id_mail.setError("Field can't be empty!");
            return false;
        }
        else {
            id_mail.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = id_password.getEditText().getText().toString();
        if (val.isEmpty()){
            id_password.setError("Field can't be empty!");
            return false;
        }
        else {
            id_password.setError(null);
            return true;
        }
    }

    //Save data in firebase when "go" button is clicked
    public void register(View view) {

        rootNode = FirebaseDatabase.getInstance();

        reference = rootNode.getReference("users");

        //Getting all the values
        String gender = "";
        String type = "";
        String dob = id_dob.getText().toString();
        String firstName = id_firstName.getEditText().getText().toString();
        String lastName = id_lastName.getEditText().getText().toString();
        String phoneNumber = id_phoneNumber.getEditText().getText().toString();
        String email = id_mail.getEditText().getText().toString();
        String password = id_password.getEditText().getText().toString();

        if (id_male.isChecked()){
            gender = "Male";
        }
        if (id_female.isChecked()){
            gender = "Female";
        }
        if (id_rider.isChecked()){
            type = "Rider";
        }
        if (id_pool.isChecked()){
            type = "Pool";
        }
        if (id_both.isChecked()){
            type = "Both";
        }

        validateFirstName();
        validateLastName();
        validatePhoneNumber();
        validateMail();
        validatePassword();

        UserHelperClass helperClass = new UserHelperClass(firstName,
                lastName,
                phoneNumber,
                email,
                password,
                gender,
                type,
                dob
        );

        reference.child(phoneNumber).setValue(helperClass);
        
//        reference.child(firstName).setValue(helperClass);
//        reference.child(lastName).setValue(helperClass);
//        reference.child(countryCode).setValue(helperClass);
//        reference.child(phoneNumber).setValue(helperClass);
//        reference.child(email).setValue(helperClass);
//        reference.child(password).setValue(helperClass);

//        validateFirstName();
    }

    public void dob_action(View view) {

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(MainActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth
                ,mDateSetListener, year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dob1 = dayOfMonth + " / " + month + " / " +year;
                id_dob.setText(dob1);
            }
        };
    }
}