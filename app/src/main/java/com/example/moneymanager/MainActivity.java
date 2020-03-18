package com.example.moneymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.widget.*;
import android.os.Bundle;
import android.view.*;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {


    EditText date;
    TextView amount;
    EditText category;
    EditText history;
    EditText balanceAmount;
    public static final String SHARED_PREFS = "shared prefs";
    public static final String TEXT = "text";
    public static final String BALANCE = "balance";
    private String text;
    private String balance;
    final DecimalFormat df = new DecimalFormat("###.00");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        date = findViewById(R.id.date);
        amount = findViewById(R.id.amount);
        category = findViewById(R.id.category);
        history = findViewById(R.id.history);
        balanceAmount = findViewById(R.id.balanceAmount);
        Button add = findViewById(R.id.add);
        Button minus = findViewById(R.id.minus);
        Button clear = findViewById(R.id.clear);

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(),"Added", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();                if ((date.getText().length() > 0) && (amount.getText().length() > 0) && (category.getText().length() > 0)) {
                    history.append("Added $" + amount.getText().toString() + " on " + date.getText().toString() + " from " + category.getText().toString());
                    history.append("\n");
                    Double myBalance;
                    String finalBalance = "";
                    if (balanceAmount.getText().toString().length() != 0) {
                        myBalance = Double.parseDouble(balanceAmount.getText().toString());

                        Double myAmount = Double.parseDouble(amount.getText().toString());

                        myBalance += myAmount;
                    }

                    else {
                        myBalance = Double.parseDouble(amount.getText().toString());

                    }
                    balanceAmount.setText(df.format(myBalance));
                    date.setText("");
                    amount.setText("");
                    category.setText("");
                    saveData();
                }
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(),"Subtracted", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                if ((date.getText().length() > 0) && (amount.getText().length() > 0) && (category.getText().length() > 0)) {
                    history.append("Spent $" + amount.getText().toString() + " on " + date.getText().toString() + " for " + category.getText().toString());
                    history.append("\n");
                    Double myBalance;
                    if (balanceAmount.getText().toString().length() != 0) {
                        myBalance = Double.parseDouble(balanceAmount.getText().toString());

                        Double myAmount = Double.parseDouble(amount.getText().toString());

                        myBalance = myBalance - myAmount;
                    }

                    else {
                        myBalance = Double.parseDouble(amount.getText().toString());

                    }

                    balanceAmount.setText(df.format(myBalance));
                    date.setText("");
                    amount.setText("");
                    category.setText("");
                    saveData();
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                history.setText("");
                balanceAmount.setText("");
                saveData();
            }
        });
        loadData();
        updateViews();
    }




    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT, history.getText().toString());
        editor.putString(BALANCE, balanceAmount.getText().toString());

        editor.apply();

    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT, "");
        balance = sharedPreferences.getString(BALANCE, "");
    }

    public void updateViews() {
        history.setText(text);
        balanceAmount.setText(balance);
    }

}