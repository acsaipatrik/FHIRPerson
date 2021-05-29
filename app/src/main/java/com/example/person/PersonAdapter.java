package com.example.person;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder>{


    private ArrayList<Person> mPersonDataAll;
    private Context mContext;
    private int lastPosition = -1;

    PersonAdapter(Context context, ArrayList<Person> personsData){

        this.mPersonDataAll = personsData;
        this.mContext=context;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(PersonAdapter.ViewHolder holder, int position) {
        Person currentPerson = mPersonDataAll.get(position);

        holder.bindTo(currentPerson);

        if (holder.getAbsoluteAdapterPosition() > lastPosition){
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in_row);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getBindingAdapterPosition();
        }
        

    }

    @Override
    public int getItemCount() {

        return mPersonDataAll.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mpersonNameTextView;
        private TextView mpersonTelecomTextView;
        private TextView mPatientAddressTextText;
        private TextView mPatientBirthDateEditText;
        private TextView mpersonGenderTextView;
        private TextView mpersonActiveTextView;
        private Button bUpdate;
        private Button bDelete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mpersonNameTextView = itemView.findViewById(R.id.personNameTextView);
            mpersonTelecomTextView = itemView.findViewById(R.id.personTelecomTextView);
            mPatientAddressTextText = itemView.findViewById(R.id.PatientAddressTextText);
            mPatientBirthDateEditText = itemView.findViewById(R.id.PatientBirthDateEditText);
            mpersonGenderTextView = itemView.findViewById(R.id.personGenderTextView);
            mpersonActiveTextView = itemView.findViewById(R.id.personActiveTextView);
            bUpdate = itemView.findViewById(R.id.updateButton);
            bDelete = itemView.findViewById(R.id.deleteButton);

        }

        public void bindTo(Person currentPerson) {

            mpersonNameTextView.setText(currentPerson.getName());
            mpersonTelecomTextView.setText(currentPerson.getTelecom());
            mPatientAddressTextText.setText(currentPerson.getAddress());
            mPatientBirthDateEditText.setText(currentPerson.getBirthDate());
            mpersonGenderTextView.setText(currentPerson.getGender());
            mpersonActiveTextView.setText(currentPerson.getActive());

            itemView.findViewById(R.id.updateButton).setOnClickListener(click -> ((PersonListActivity) mContext).updatePerson(currentPerson));
            itemView.findViewById(R.id.deleteButton).setOnClickListener(click2 -> ((PersonListActivity) mContext).deletePerson(currentPerson));

        }
    }



}
