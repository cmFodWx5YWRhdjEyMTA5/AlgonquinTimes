package com.algonquintimes.algonquintimes.Email;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.algonquintimes.algonquintimes.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class SendTipActivity extends AppCompatActivity {

    Uri photoURI;
    private EditText editTextSubject;
    private EditText editTextMessage;
    private ImageView photoView;
    private String theImage;
    private Button buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_tip);

        FloatingActionButton photoButton = findViewById(R.id.cameraButton);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });

        editTextSubject = findViewById(R.id.editTitle);
        editTextMessage = findViewById(R.id.editReport);
        photoView = findViewById(R.id.photoView);
        buttonSend = findViewById(R.id.submitReport);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Send a Tip");

        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!editTextSubject.getText().toString().isEmpty() && !editTextMessage.getText().toString().isEmpty()) {
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                SendMail sender = new SendMail();
                                if (theImage != null) {
                                    sender.addAttachment(theImage);
                                }
                                sender.sendMail(editTextSubject.getText().toString(), "<div style=\"font-weight:bold;\">Message</div><br>" + editTextMessage.getText().toString());
                            } catch (Exception e) {
                                Log.i("Error: ", e.toString());
                            }
                        }
                    }).start();
                    Toast.makeText(getApplicationContext(), "Thank you for the tip!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    setResult(RESULT_CANCELED, intent);
                    finish();
                }
                if (editTextSubject.getText().toString().isEmpty()) {
                    editTextSubject.setError("Please enter a Title.");
                    editTextSubject.requestFocus();
                }
                if (editTextMessage.getText().toString().isEmpty()) {
                    editTextMessage.setError("What is this about?");
                    editTextMessage.requestFocus();
                }
            }
        });
    }

    private void choosePicture() {
        CharSequence colors[] = new CharSequence[]{"Take a picture", "Browse from gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Integer total = which;
                Log.i("CHOICE: ", total.toString());
                switch (which) {
                    case 0:
                        ActivityCompat.requestPermissions(SendTipActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                        if (ContextCompat.checkSelfPermission(SendTipActivity.this, Manifest.permission.CAMERA)
                                != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(getApplicationContext(), "Please allow camera permission!", Toast.LENGTH_LONG).show();
                            // Permission is not granted
                        }
                        else {
                            ActivityCompat.requestPermissions(SendTipActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    1);
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                File photoFile = null;
                                try {
                                    photoFile = createImageFile();

                                } catch (IOException ex) {
                                    Log.i("Error: ", ex.toString());
                                }
                                if (photoFile != null) {
                                    photoURI = FileProvider.getUriForFile(getApplicationContext(),
                                            "com.algonquintimes.algonquintimes.Email.SendTipActivity",
                                            photoFile);
                                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                                    startActivityForResult(takePictureIntent, 0);
                                }
                            }
                        }
                        break;
                    case 1:
                        ActivityCompat.requestPermissions(SendTipActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                        if (ContextCompat.checkSelfPermission(SendTipActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(getApplicationContext(), "Please allow the storage permission!", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickPhoto, 1);
                        }
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "Something went wrong...", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    photoView.setImageURI(photoURI);
                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    photoURI = imageReturnedIntent.getData();
                    photoView.setImageURI(photoURI);
                    theImage = getPath(getApplicationContext(), photoURI);
                }
                break;
        }
    }

    public static String getPath(Context context, Uri uri) {
        String result = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                result = cursor.getString(column_index);
            }
            cursor.close();
        }
        if (result == null) {
            result = "Not found";
        }
        return result;
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        theImage = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}


