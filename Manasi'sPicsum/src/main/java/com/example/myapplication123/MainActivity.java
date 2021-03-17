package com.example.myapplication123;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

     private List<ImagesResponse> imagesResponseList=new ArrayList<>();
     GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView=findViewById(R.id.gridView);

        getAllImages();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                startActivity(new Intent(MainActivity.this,ClickedActivity.class).putExtra("data",imagesResponseList.get(i)));
            }
        });
    }
    public void getAllImages()
    {
        Call<List<ImagesResponse>> imageResponse=ApiClient.getInterface().getAllImages();
        imageResponse.enqueue(new Callback<java.util.List<ImagesResponse>>() {
            @Override
            public void onResponse(Call<java.util.List<ImagesResponse>> call, Response<java.util.List<ImagesResponse>> response) {
                if(response.isSuccessful())
                {
                    String message="Request successful..";
                    Toast.makeText(MainActivity.this, message,Toast.LENGTH_LONG).show();
                    imagesResponseList=response.body();
                    CustomAdapter customAdapter=new CustomAdapter(imagesResponseList,MainActivity.this);
                    gridView.setAdapter(customAdapter);
                }
                else{
                    String message="An error occurred try again later..";
                    Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<java.util.List<ImagesResponse>> call, Throwable t) {
                String message = t.getLocalizedMessage();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();

            }
        });
    }
    public class CustomAdapter extends BaseAdapter
    {
        private List<ImagesResponse> imageResponseList;
        private Context context;
        private LayoutInflater layoutInflater;
        public CustomAdapter(List<ImagesResponse> imageResponseList,Context context)
        {
            this.imageResponseList= imageResponseList;
            this.context=context;
            this.layoutInflater=(LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }
        public int getCount()
        {
            return imageResponseList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }
        public long getItemId(int i)
        {
            return 0;
        }

        public View getView(int i, View view, ViewGroup viewGroup)
        {
            if(view==null)
            {
                view=layoutInflater.inflate(R.layout.row_grid_items,viewGroup,false);
            }
            ImageView imageView=view.findViewById(R.id.ImageView);
            TextView textView=view.findViewById(R.id.textView);
            textView.setText(imageResponseList.get(i).getAuthor());
            GlideApp.with(context).load(imageResponseList.get(i).getPost_url()).into(imageView);
            return view;
        }
    }
}