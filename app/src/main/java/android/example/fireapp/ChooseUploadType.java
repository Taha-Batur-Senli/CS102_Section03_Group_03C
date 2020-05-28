    package android.example.fireapp;

    import androidx.appcompat.app.AppCompatActivity;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;
    import android.view.WindowManager;
    import android.widget.Button;
    import android.widget.ImageView;
    import android.widget.TextView;

    import org.w3c.dom.Text;

    /*
     * Activity that shows the upload type to the restaurant accounts
     *@date 23.05.2020
     *@author Group 3C
     */

    public class ChooseUploadType extends AppCompatActivity {

        //Properties

        ImageView view,line;
        Button upload_gallery,upload_logo, upload_seating_plan;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //making the activity full screen.
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_choose_upload_type);

            //initalization

            view = findViewById(R.id.imageView60);
            line = findViewById(R.id.imageView61);
            upload_gallery = findViewById(R.id.upload_gallery);
            upload_logo = findViewById(R.id.upload_logo);
            upload_seating_plan = findViewById(R.id.upload_seating_plan);

            /**
             * ClickListener to open the UploadImage to gallery activity.
             */
            upload_gallery.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ChooseUploadType.this,UploadImage.class);
                    startActivity(intent);
                }
            });

            /**
             * ClickListener to open UploadSeatingPlan activity.
             */
            upload_seating_plan.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ChooseUploadType.this,UploadSeatingPlan.class);
                    startActivity(intent);
                }
            });

            /**
             * ClickListener which go to the UploadLogo page.
             */
            upload_logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ChooseUploadType.this, UploadLogo.class);
                    startActivity(intent);
                }
            });

        }
    }
