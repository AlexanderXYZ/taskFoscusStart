package com.example.task1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ConvertActivity extends AppCompatActivity {

    TextView tvResult;
    EditText etValue;
    Double value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert);


        TextView tvCharName = findViewById(R.id.textViewCodeConvert);
        TextView tvName = findViewById(R.id.textViewNameConvert);
        TextView tvValue = findViewById(R.id.textViewValueConvert);
        etValue = findViewById(R.id.editTextNumberDecimal);
        Button btConvert = findViewById(R.id.btConvert);
        tvResult = findViewById(R.id.textViewResult);

        Intent intent = getIntent();
        String charName = intent.getStringExtra("charCodeIntent");
        String name = intent.getStringExtra("nameIntent");
        value = intent.getDoubleExtra("valueIntent",1.1);

        tvCharName.setText(charName);
        tvName.setText(name);
        tvValue.setText(Double.toString(value));



    }

    public void btConvertClick(View view) {
        if((etValue.getText().toString()).matches("")) {
            Toast.makeText(this,"You did not enter a RUB",Toast.LENGTH_SHORT).show();
            return;
        }
        else tvResult.setText(Double.toString(Double.parseDouble(etValue.getText().toString())/value));
    }
}