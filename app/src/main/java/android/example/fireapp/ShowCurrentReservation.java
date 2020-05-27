package android.example.fireapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
/*
 *
 *@date 27.05.2020
 *@author Group 3C
 */
public class ShowCurrentReservation extends AppCompatActivity {
    //Properties
    TextView tvRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_show_current_reservation);

        tvRes = (TextView)findViewById(R.id.txtReservationShowCurrent);
        Intent i = getIntent();
        String reservTxt = i.getStringExtra("RESERVATION");
        tvRes.setText(reservTxt);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
