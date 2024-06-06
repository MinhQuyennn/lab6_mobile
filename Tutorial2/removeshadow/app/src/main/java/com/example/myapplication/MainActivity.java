package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final int IMAGE_REQUEST = 1;
    private DocumentFilter documentFilter;
    private Button loadButton, applyFilterButton;
    private ImageView imageView;
    private Bitmap originalBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        loadButton = findViewById(R.id.load);
        applyFilterButton = findViewById(R.id.g5);
        imageView = findViewById(R.id.image);

        // Initialize document filter
        documentFilter = new DocumentFilter();

        // Set up button click listeners
        setupLoadButton();
        setupApplyFilterButton();
    }

    // Method to set up the load button click listener
    private void setupLoadButton() {
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage();
            }
        });
    }

    // Method to set up the apply filter button click listener
    private void setupApplyFilterButton() {
        applyFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyShadowRemovalFilter();
            }
        });
    }

    // Method to start an activity for image selection
    private void loadImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    // Method to apply the shadow removal filter
    private void applyShadowRemovalFilter() {
        if (originalBitmap != null) {
            documentFilter.getShadowRemoval(originalBitmap, new DocumentFilter.CallBack<Bitmap>() {
                @Override
                public void onCompleted(Bitmap filteredBitmap) {
                    if (filteredBitmap != null) {
                        imageView.setImageBitmap(filteredBitmap);
                    }
                }
            });
        }
    }

    // Handle the result of the image selection activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            loadBitmapFromUri(uri);
        }
    }

    // Method to load a bitmap from a given URI
    private void loadBitmapFromUri(Uri uri) {
        try {
            originalBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            imageView.setImageURI(uri); // Display the selected image
        } catch (IOException e) {
            e.printStackTrace();
            // Display an error message or toast to the user
        }
    }
}
