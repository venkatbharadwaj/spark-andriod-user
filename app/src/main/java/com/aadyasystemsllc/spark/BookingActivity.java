package com.aadyasystemsllc.spark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class BookingActivity extends AppCompatActivity {
EditText regsterd_vehicle,numberofhours;
TextView btnCheckout,titleTx,parkingname,totalsum;
ImageView back_but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        regsterd_vehicle=findViewById(R.id.regsterd_vehicle);
        parkingname=findViewById(R.id.parkingname);
        btnCheckout=findViewById(R.id.btnCheckout);
        titleTx=findViewById(R.id.title_tx);
        back_but=findViewById(R.id.back_but);
        back_but.setVisibility(View.VISIBLE);
        totalsum=findViewById(R.id.totalsum);
        numberofhours=findViewById(R.id.numberofhours);
        back_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent home=new Intent(BookingActivity.this,HomeActivity.class);
                startActivity(home);
            }
        });
        getIntentData();
        titleTx.setText("Booking");
        NumberPicker np = findViewById(R.id.numberPicker);
        np.setMinValue(1);
        np.setMaxValue(20);

        np.setOnValueChangedListener(onValueChangeListener);
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BookingActivity.this, "Will be Updated Soon.....", Toast.LENGTH_SHORT);
            }
        });
        TextWatcher listener = new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textChanged();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not used
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not used
            }
        };

        numberofhours.addTextChangedListener(listener);
    }

    NumberPicker.OnValueChangeListener onValueChangeListener =
            new     NumberPicker.OnValueChangeListener(){
                @Override
                public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                  //  Toast.makeText(BookingActivity.this, "selected number "+numberPicker.getValue(), Toast.LENGTH_SHORT);
                }
            };

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.car:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.truck:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }
    private void getIntentData() {
        Intent intent = getIntent();
        //  code = intent.getStringExtra("otp");
if (!intent.getStringExtra("parkingtitle").isEmpty()){
    parkingname.setText(intent.getStringExtra("parkingtitle"));
}else {
    parkingname.setText("Parking");
}


    }

    private void textChanged() {
        try {
            int a = Integer.parseInt(numberofhours.getText().toString());

            totalsum.setText(""+a*50);
          //  viewModel.updateValues(a, b, c);
        } catch (NumberFormatException e) {
           // viewModel.showError();
            e.printStackTrace();
        }
    }
}