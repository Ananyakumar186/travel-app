package com.example.travelapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class currencyActivity extends AppCompatActivity {

    EditText fromInput, toOutput;
    Button convertButton;
    Spinner fromDropdown, toDropdown;
    String fromCurrency, toCurrency;
    ImageView backButton2;
    private boolean findViewById;

    void setupListeners() {
        fromDropdown.setOnItemSelectedListener(new FromDropdown());
        toDropdown.setOnItemSelectedListener(new ToDropdown());
        convertButton.setOnClickListener(v -> {
            if (!isNetworkConnected()) {
                Toast.makeText(currencyActivity.this, "Host unreachable, check your internet connection and try again", Toast.LENGTH_SHORT).show();
            }
            if (fromCurrency == toCurrency) {
                Toast.makeText(currencyActivity.this, "from and to values are same.", Toast.LENGTH_SHORT).show();
            } else if (fromInput.getText().toString().isEmpty()) {
                Toast.makeText(currencyActivity.this, "Please enter a value to convert.", Toast.LENGTH_SHORT).show();
            } else {
                JSONFetch obj = new JSONFetch();
                obj.execute(fromInput.getText().toString());
            }
        });
    }

    void showToast(String message) {
        Toast.makeText(currencyActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    public boolean isNetworkConnected() {
        // TODO: Check if connected to internet
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        fromInput = findViewById(R.id.fromCurrency);
        toOutput = findViewById(R.id.toCurrency);
        convertButton = findViewById(R.id.convertButton);
        fromDropdown = findViewById(R.id.fromDropdown);
        toDropdown = findViewById(R.id.toDropdown);
        backButton2 = findViewById(R.id.backButton2);

                setupListeners();
    }
    public void back(View view){
        Intent backintent = new Intent(this, MainActivity.class);
        startActivity(backintent);
    }

    class FromDropdown implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            fromCurrency = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }


    class ToDropdown implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            toCurrency = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    class JSONFetch extends AsyncTask<String, Void, Void> {
        ProgressDialog dialog = new ProgressDialog(currencyActivity.this);
        String error = "", apiResponse = "";

        @Override
        protected Void doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("https://frankfurter.app/latest?amount=" + params[0] + "&from=" + fromCurrency + "&to=" + toCurrency).build();
            try {
                Response response = client.newCall(request).execute();
                apiResponse = response.body().string();
            } catch (Exception e) {
                error = e.toString();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Converting...");
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void v) {
            dialog.dismiss();
            if (!error.isEmpty()) {
                showToast("Something went wrong " + error);
            }
            try {
                toOutput.setText(new JSONObject(apiResponse).getJSONObject("rates").getString(toCurrency));
            } catch (Exception e) {
                showToast("Something went wrong " + e.toString());
            }

            super.onPostExecute(v);
        }
    }
}
