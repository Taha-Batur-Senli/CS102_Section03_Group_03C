package android.example.fireapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ShowCurrentReservation extends AppCompatActivity {
    //Properties
    TextView tvRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
