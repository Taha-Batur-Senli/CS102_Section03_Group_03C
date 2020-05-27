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
/*
 *A class that helps restaurant owners to upload their restaurant's logo to the system and edit it.
 *@date 27.05.2020
 *@author Group 3C
 */
public class UploadLogo extends AppCompatActivity {

    //Constants
    public static final int PICK_IMAGE_REQUEST = 1;

    //Variables
    Button choose_logo, arrange_logo;
    ImageView imageView;
    private Uri uri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    //Program Code
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_upload_logo);
        imageView = findViewById(R.id.image_logo);
        choose_logo = findViewById(R.id.choose_logo);
        arrange_logo = findViewById(R.id.arrange_logo);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        databaseReference = FirebaseDatabase.getInstance().getReference("Restaurants");

        choose_logo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                chooseLogo();
            }
        });
        arrange_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadLogo();
            }
        });
    }

    private void chooseLogo(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST); //start activity for result is a method that used for retrieving info from B to A.
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            uri = data.getData();
            Picasso.with(this).load(uri).into(imageView);
        }
    }

    public String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadLogo() {
        //creating progress dialog and show it in the onProgressListener
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("In progress...");
        progressDialog.show();

        if(uri != null){
            StorageReference reference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
            reference.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                        /* UploadTask.TaskSnapshot has a method named getMetadata() which returns a StorageMetadata object.
                            This StorageMetadata object contains a method named getReference() which returns a StorageReference object.
                            That StorageReference object contains the getDownloadUrl() method, which now returns a Task
                            object instead of an Uri object.
                         */
                        /*
                            Upload upload = new Upload(edit_name.getText().toString().trim()
                                    ,taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                            String uploadID = databaseReference.push().getKey();
                            databaseReference.child(uploadID).setValue(upload);

                         */
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful());
                            Uri downloadUrl = urlTask.getResult();

                            //Log.d(TAG, "onSuccess: firebase download url: " + downloadUrl.toString()); //use if testing...don't need this line.
                            Upload upload = new Upload("logo",downloadUrl.toString());

                            String uploadId = databaseReference.push().getKey();
                            assert uploadId != null;
                            databaseReference.child(user.getUid()).child("Pictures").child("Logo").child(uploadId).setValue(upload);
                            Toast.makeText(UploadLogo.this, "ARRANGED", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(UploadLogo.this,"failed" + e.getMessage() ,Toast.LENGTH_LONG)
                                    .show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("uploaded " + (int) progress  + " %");
                        }
                    });
        }
    }

}
