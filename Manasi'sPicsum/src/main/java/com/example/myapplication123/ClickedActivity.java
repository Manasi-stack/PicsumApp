package com.example.myapplication123;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ClickedActivity extends AppCompatActivity {
    ImagesResponse imagesResponse;
    TextView tvAuthor;
    ImageView imageView;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked);
        tvAuthor=findViewById(R.id.SelectedAuthor);
        imageView=findViewById(R.id.selectedImage);
        Intent intent=getIntent();
        if(intent.getExtras()!=null)
        {
            imagesResponse=(ImagesResponse)intent.getSerializableExtra("data");
           tvAuthor.setText(imagesResponse.getAuthor());
           GlideApp.with(this).load(imagesResponse.getPost_url()).into(imageView);

        }
    }


}
