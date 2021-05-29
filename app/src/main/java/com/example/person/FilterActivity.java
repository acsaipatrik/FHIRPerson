package com.example.person;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;

public class FilterActivity extends AppCompatActivity {

    private static final String TAG = FilterActivity.class.getName();

    private FirebaseFirestore mFirestore;

    private RecyclerView mRecyclervView;
    private ArrayList<Person> mPersonList;
    private PersonAdapter mAdapter;

    private Person person2;

    private CollectionReference mPersons;

    private NotificationHandler mNotificationHandler;

    private int gridNumber =1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);


        mFirestore = FirebaseFirestore.getInstance();
        mPersons = mFirestore.collection("Person");
        mRecyclervView = findViewById(R.id.recyclerView);
        mRecyclervView.setLayoutManager(new GridLayoutManager(this,gridNumber));

        mPersonList = new ArrayList<>();
        mAdapter = new PersonAdapter(this, mPersonList);
        mRecyclervView.setAdapter(mAdapter);



        mNotificationHandler = new NotificationHandler(this);

        person2=(Person) getIntent().getSerializableExtra("person");

        queryData();

    }

    private void queryData() {
        mPersonList.clear();

        mPersons.orderBy("birthDate").limit(1).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                Person person = document.toObject(Person.class);
                person.setId(document.getId());
                mPersonList.add(person);
            }
            if (mPersonList.size() == 0) {
                queryData();
            }
            mAdapter.notifyDataSetChanged();
        });
    }


    public void deletePerson(Person person){
        DocumentReference reference = mPersons.document(person.getId());

        reference.delete().addOnSuccessListener(success -> {
            Toast.makeText(this, "Személy törölve!", Toast.LENGTH_LONG).show();
        })
            .addOnFailureListener(fail -> {
                Toast.makeText(this, "Sikertelen törlés!", Toast.LENGTH_LONG).show();
            });
        queryData();
        mNotificationHandler.cancel();
    }

    public void updatePerson(Person person){
        mNotificationHandler.send(person.getName());
        Gson gson = new Gson();
        Intent intent = new Intent(this, personUpdateActivity.class);
        String actuallyPerson = gson.toJson(person);
        intent.putExtra("person",actuallyPerson);
        startActivity(intent);

        queryData();
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