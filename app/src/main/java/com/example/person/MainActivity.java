package com.example.person;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private static final String LOG_TAG = MainActivity.class.getName();
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext=this;
            setContentView(R.layout.activity_main);

            AnimateBell();

        Log.i(LOG_TAG,"Created");

    }

    public void AnimateBell(){
        Animation shake = AnimationUtils.loadAnimation(mContext, R.anim.shakeanimation);
        ImageView imgBell= (ImageView) findViewById(R.id.MainImageView);
        imgBell.setImageResource(R.drawable.helth);
        imgBell.setAnimation(shake);
    }

    public void ToList(View view)
    {
        Intent intent = new Intent(this, PersonListActivity.class);
        startActivity(intent);
    }

    public void ToPatient(View view)
    {
        Intent intent = new Intent(this, filtertwoActivity.class);
        startActivity(intent);
    }

    public void ToNewPerson(View view)
    {
        Intent intent = new Intent(this, NewPersonActivity.class);
        startActivity(intent);
    }

    public void ToFilterPerson(View view)
    {
        Intent intent = new Intent(this, FilterActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();

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
        Toast toast = Toast.makeText(MainActivity.this,"Let's see the World!",Toast.LENGTH_SHORT);
        toast.show();
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
        Toast toast = Toast.makeText(MainActivity.this,"Kezdőoldal!",Toast.LENGTH_SHORT);
        toast.show();
        Log.i(LOG_TAG,"onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(LOG_TAG,"onRestart");
    }
}