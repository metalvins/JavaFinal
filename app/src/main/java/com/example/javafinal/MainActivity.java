package com.example.javafinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button roll, rollTwice, addDice;
    TextView result;
    EditText newDiceSides;
    Spinner spinner;
    Die selectedDie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        roll = findViewById(R.id.roll);
        rollTwice = findViewById(R.id.rollTwice);
        addDice = findViewById(R.id.addDice);

        result = findViewById(R.id.result);
        newDiceSides = findViewById(R.id.newDiceSides);
        spinner = findViewById(R.id.spinner);

        List<Die> list = new ArrayList<Die>();
        //4,6,8,10,12,20
        list.add(new Die(4, 1));
        list.add(new Die(6, 1));
        list.add(new Die(8, 1));
        list.add(new Die(10, 0, "true d10"));
        list.add(new Die(12, 1));
        list.add(new Die(20, 1));
        list.add(new Die(10, 1, "special d10", 10));

        SharedPreferences savePref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String savedDice = savePref.getString("savedDice", "");
        List<Die> moreDice = makeDiceList(savedDice);
        if(moreDice.size() > 0) {
            list.addAll(moreDice);
        }

        ArrayAdapter<Die> adapter = new ArrayAdapter<Die>(this,
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDie = list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });

        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedDie.roll();
                result.setText(selectedDie.getSideUp());
            }
        });

        rollTwice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resStr;
                selectedDie.roll();
                resStr = selectedDie.getSideUp();
                selectedDie.roll();
                resStr = resStr + " , " + selectedDie.getSideUp();
                result.setText(resStr);
            }
        });

        addDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newDiceSides.getText().toString().equals("")) {
                    return;
                }
                Die newDie = new Die(Integer.parseInt(newDiceSides.getText().toString().trim()), 1);
                list.add(newDie);

                SharedPreferences savePref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                String savedDice = savePref.getString("savedDice", "");
                SharedPreferences.Editor editor = savePref.edit();
                editor.putString("savedDice", savedDice + " " + newDie.getNumSides());
                editor.apply();
            }
        });
    }

    private List<Die> makeDiceList(String savedDice) {
        List<String> tokens = Arrays.asList(savedDice.trim().split("\\s+"));
        List<Die> result = new ArrayList<Die>();
        for(String token: tokens) {
            if(!token.equals("")) {
                result.add(new Die(Integer.parseInt(token), 1));
            }
        }
        return result;
    }
}