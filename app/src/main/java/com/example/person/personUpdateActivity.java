package com.example.person;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.Calendar;

public class personUpdateActivity extends AppCompatActivity {

    private static final String LOG_TAG = NewPersonActivity.class.getName();
    private FirebaseFirestore mFirestore;
    private CollectionReference mPerson;

    Person person2;


    EditText mPatientNameEditText;
    EditText mPatientTelecomEditText;
    EditText mPatientAddressEditText;
    RadioGroup mPatientGenderRadioGroup;
    RadioGroup mPatientActiveRadioGroup;

    Button updatePatientButton;

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_update);

        mPatientNameEditText = findViewById(R.id.PatientNameEditText);
        mPatientTelecomEditText = findViewById(R.id.PatientTelecomEditText);
        mPatientAddressEditText = findViewById(R.id.PatientAddressEditText);
        //mPatientBirthDate = findViewById(R.id.PatientBirthDate);
        mPatientGenderRadioGroup = findViewById(R.id.PatientGenderRadioGroup);
       //mPatientGenderRadioGroup.check(R.id.maleRadioButton);
        mPatientActiveRadioGroup = findViewById(R.id.PatientActiveRadioGroup);
        //mPatientActiveRadioGroup.check(R.id.PatientActibeRB);

        mDisplayDate = (TextView) findViewById(R.id.PatientBirthDate);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                    personUpdateActivity.this,
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    mDateSetListener,
                    year,
                    month,
                    day
                );
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = year+"."+month+"."+dayOfMonth+".";
                mDisplayDate.setText(date);
            }
        };

        Gson gson = new Gson();
        person2 = gson.fromJson(getIntent().getStringExtra("person"), Person.class);

        if(person2.getGender().contains("Férfi"))
        {
            mPatientGenderRadioGroup.check(R.id.maleRadioButton);
        }
        else if (person2.getGender().contains("Nő"))
        {
            mPatientGenderRadioGroup.check(R.id.femaleRadioButton);
        }
        else
        {
            mPatientGenderRadioGroup.check(R.id.otherRadioButton);
        }

        if(person2.getActive().contains("Aktív"))
        {
            mPatientActiveRadioGroup.check(R.id.PatientActiveRB);
        }
        else
        {
            mPatientActiveRadioGroup.check(R.id.PatientInactiveRB);
        }

        updatePatientButton = findViewById(R.id.UpdatePatientButton);


        mFirestore = FirebaseFirestore.getInstance();
        mPerson = mFirestore.collection("Person");



        mPatientNameEditText.setText(person2.getName());
        mPatientTelecomEditText.setText(person2.getTelecom());
        mPatientAddressEditText.setText(person2.getAddress());
        mDisplayDate.setText(person2.getBirthDate());

        updatePatientButton.setOnClickListener(update -> updatePersonData());






    }


    private void updatePersonData(){

        String personName = mPatientNameEditText.getText().toString();
        String personMobile = mPatientTelecomEditText.getText().toString();
        String personAddress = mPatientAddressEditText.getText().toString();
        String personDate = mDisplayDate.getText().toString();

        int chechkedId = mPatientGenderRadioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = mPatientGenderRadioGroup.findViewById(chechkedId);
        String GenderType = radioButton.getText().toString();

        int chechkedId2 = mPatientActiveRadioGroup.getCheckedRadioButtonId();
        RadioButton radioButton2 = mPatientActiveRadioGroup.findViewById(chechkedId2);
        String ActiveType = radioButton2.getText().toString();

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.buttonanimation);

        if(personName.isEmpty() || personMobile.isEmpty() || personDate.isEmpty()){
            Toast.makeText(this, "A mezők kitöltése kötelező!", Toast.LENGTH_LONG).show();
            updatePatientButton.startAnimation(animation);

        }
        else
        {

            mPerson.document(person2.getId()).update("name",personName);
            mPerson.document(person2.getId()).update("telecom",personMobile);
            mPerson.document(person2.getId()).update("address",personAddress);
            mPerson.document(person2.getId()).update("birthDate",personDate);
            mPerson.document(person2.getId()).update("gender",GenderType);
            mPerson.document(person2.getId()).update("active",ActiveType);
            startActivity(new Intent(this,PersonListActivity.class));

            Toast t = Toast.makeText(this,"Személy módosítva", Toast.LENGTH_LONG);
            t.show();
        }



    }


    private void startListActivity() {
        Intent intent = new Intent(this, PersonListActivity.class);
        startActivity(intent);
    }

    public void cancel(View view) {
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast toast = Toast.makeText(personUpdateActivity.this,"Módosítás!",Toast.LENGTH_SHORT);
        toast.show();
        Log.i(LOG_TAG,"onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG,"onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG,"onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast toast = Toast.makeText(personUpdateActivity.this,"Módosítás!",Toast.LENGTH_SHORT);
        toast.show();
        Log.i(LOG_TAG,"onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(LOG_TAG,"onRestart");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu,menu);

        return true;


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.backToMain:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

}