    package android.example.fireapp;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.appcompat.app.AppCompatActivity;

    import android.app.ProgressDialog;
    import android.content.ContentResolver;
    import android.content.Intent;
    import android.net.Uri;
    import android.os.Bundle;
    import android.view.View;
    import android.view.WindowManager;
    import android.webkit.MimeTypeMap;
    import android.widget.Button;
    import android.widget.ImageView;
    import android.widget.Toast;

    import com.google.android.gms.tasks.OnFailureListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.storage.FirebaseStorage;
    import com.google.firebase.storage.OnProgressListener;
    import com.google.firebase.storage.StorageReference;
    import com.google.firebase.storage.UploadTask;
    import com.squareup.picasso.Picasso;

    /**
    *A class that enables users to upload seating plans to the system or modify the current seating plans.
    *@date 17.05.2020
    *@author Group 3C
     */
    public class UploadSeatingPlan extends AppCompatActivity {

        //Constants

        public static final int PICK_IMAGE_REQUEST = 1;

        //Variables

        Button choose_seating_plan, upload_seating_plan_pic;
        ImageView imageView, line;
        private Uri mImageUri;
        private StorageReference storageReference;
        private DatabaseReference databaseReference;
        private FirebaseUser user;
        private FirebaseAuth mAuth;

        //Program Code

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_upload_seating_plan);

            //initialization

            choose_seating_plan = findViewById(R.id.choose_seating_plan);
            upload_seating_plan_pic = findViewById(R.id.upload_seating_plan_pic);
            imageView = findViewById(R.id.imageView62);
            line = findViewById(R.id.imageView63);
            mAuth = FirebaseAuth.getInstance();
            user = mAuth.getCurrentUser();
            storageReference = FirebaseStorage.getInstance().getReference("uploads");
            databaseReference = FirebaseDatabase.getInstance().getReference("Restaurants");


            choose_seating_plan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    chooseImage();
                }
            });
            upload_seating_plan_pic.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    uploadImage();
                }
            });
        }

        //Methods

        /**
         * method that returning the chosen pic's url in string.
         * @param uri
         * @return String
         */
        public String getFileExtension(Uri uri){
            ContentResolver cR = getContentResolver();
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            return mimeTypeMap.getExtensionFromMimeType(cR.getType(uri));
        }

        /**
         * uploading image to the firebase.
         */
        private void uploadImage() {

            //creating progress dialog and show it in the onProgressListener
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("In progress...");
            progressDialog.show();

            if(mImageUri != null){
                StorageReference reference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
                reference.putFile(mImageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();
                                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!urlTask.isSuccessful());
                                Uri downloadUrl = urlTask.getResult();
                                //creating seatingPlan child to put the image under the specific place.
                                Upload upload = new Upload("seating plan",downloadUrl.toString());

                                String uploadId = databaseReference.push().getKey();
                                assert uploadId != null;
                                databaseReference.child(user.getUid()).child("Pictures").child("Seating Plan").child(uploadId).setValue(upload);
                                Toast.makeText(UploadSeatingPlan.this, "UPLOADED", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(UploadSeatingPlan.this,"failed" + e.getMessage() ,Toast.LENGTH_LONG)
                                        .show();

                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            // showing the percentage of the process.
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                progressDialog.setMessage("uploaded " + (int) progress  + " %");
                            }
                        });
            }
        }

        /**
         * Enables the user to choose an image from the listed images (the phone's gallery).
         */
        private void chooseImage(){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST); //start activity for result is a method that used for retrieving info from B to A.
        }

        @Override
        /**
         * Overrided method. This method is takes the chosen picture and display it in this activity.
         * @param requestCode
         * @param resultCode
         * @param data
         */
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
                mImageUri = data.getData();
                Picasso.with(this).load(mImageUri).into(imageView);
            }
        }
    }
