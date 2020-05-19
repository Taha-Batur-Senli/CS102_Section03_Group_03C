package android.example.fireapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
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

public class UploadImage extends AppCompatActivity {

    Button choose, upload, show_uploads;
    private EditText edit_name;
    public static final int PICK_IMAGE_REQUEST = 1;
    ImageView imageView;
    private Uri mImageUri;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    FirebaseUser user;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);
        choose = findViewById(R.id.choose);
        upload = findViewById(R.id.upload);
        show_uploads = findViewById(R.id.show_uploads);
        imageView = findViewById(R.id.image);
        edit_name = findViewById(R.id.give_file_name);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        databaseReference = FirebaseDatabase.getInstance().getReference("Restaurants");
        //adding function to choose button
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        show_uploads.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UploadImage.this,ImageRecycler.class);
                startActivity(intent);
            }
        });
    }

    public String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadImage() {
        //creating proggress dialog and show it in the onProgressListener
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
                            Upload upload = new Upload(edit_name.getText().toString().trim(),downloadUrl.toString());

                            String uploadId = databaseReference.push().getKey();
                            assert uploadId != null;
                            databaseReference.child(user.getUid()).child("Pictures").child(uploadId).setValue(upload);
                            Toast.makeText(UploadImage.this, "UPLOADED", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(UploadImage.this,"failed" + e.getMessage() ,Toast.LENGTH_LONG)
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

    private void chooseImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST); //start activity for result is a method that used for retrieving info from B to A.
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(imageView);
             /*
             try {
                 Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),mImageUri);
                 imageView.setImageBitmap(bitmap); // bitmap is a object that told the taken file is image to android.
                 //bitmap is as far as I get take the info of pixels and stored and passed to whatever you want. You can whether create
                 // your own image by BitMap factory or you can append the pixels into imageView.
             }
             catch (IOException e){
                 e.printStackTrace();
             }

              */
        }
    }
}
