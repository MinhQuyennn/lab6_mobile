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
    DocumentFilter documentFilter;
    Button load, g5;
    ImageView imageView;
    Bitmap gb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        load = findViewById(R.id.load);
        g5 = findViewById(R.id.g5);
        imageView = findViewById(R.id.image);

        documentFilter = new DocumentFilter();

        // Set onClickListener for the shadow removal button
        g5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gb1 != null) {
                    documentFilter.getShadowRemoval(gb1, new DocumentFilter.CallBack<Bitmap>() {
                        @Override
                        public void onCompleted(Bitmap bitmap) {
                            imageView.setImageBitmap(bitmap);
                        }
                    });
                }
            }
        });

        // Set onClickListener for the load image button
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, IMAGE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                gb1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                imageView.setImageBitmap(gb1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
