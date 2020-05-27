package android.example.fireapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*
 * This class is a thank you page for Mr. Davenport.
 *@date 27.05.2020
 *@author Group 3C
 */

public class EasterEggPage extends AppCompatActivity {
    Button btnSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easter_egg_page);
        btnSkip = (Button)findViewById(R.id.btnSkip);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(EasterEggPage.this, MainActivity.class));
                finish();

            }
        });

    }
}
