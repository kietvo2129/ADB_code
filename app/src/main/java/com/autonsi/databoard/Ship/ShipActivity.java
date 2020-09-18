package com.autonsi.databoard.Ship;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.autonsi.databoard.Ship.Confirm.ConfirmActivity;
import com.autonsi.databoard.Ship.ShippingScan.PickkingScanActivity;
import com.quickblox.sample.videochat.java.R;

public class ShipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ship);

        setTitle("Shipping");

        findViewById(R.id.rl_PickingM).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ShipActivity.this, PickingManualActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.rl_PickingS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ShipActivity.this, PickkingScanActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.rl_Confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ShipActivity.this, ConfirmActivity.class);
                startActivity(intent);
            }
        });
    }
}