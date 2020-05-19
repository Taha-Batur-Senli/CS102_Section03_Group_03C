package android.example.fireapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ChooseUploadType extends AppCompatActivity {

    ImageView view,line;
    TextView textView;
    Button upload_gallery,upload_logo, upload_seating_plan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_upload_type);
        view = findViewById(R.id.imageView60);
        line = findViewById(R.id.imageView61);
        upload_gallery = findViewById(R.id.upload_gallery);
        upload_logo = findViewById(R.id.upload_logo);
        upload_seating_plan = findViewById(R.id.upload_seating_plan);
        textView = findViewById(R.id.textView17);

        upload_gallery.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseUploadType.this,UploadImage.class);
                startActivity(intent);
            }
        });

        upload_seating_plan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseUploadType.this,UploadSeatingPlan.class);
                startActivity(intent);
            }
        });
        upload_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseUploadType.this, UploadLogo.class);
                startActivity(intent);
            }
        });

    }
}
