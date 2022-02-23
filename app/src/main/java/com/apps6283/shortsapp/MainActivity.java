package com.apps6283.shortsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.WindowManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

   private ArrayList<DataHandler>dataHandlers = new ArrayList<>();
    ViewPager2 viewPager2;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();

        DatabaseReference mRef = database.getReference("Videos");
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    for (DataSnapshot s: snapshot.getChildren()
                         ) {

                        Map map = (Map) s.getValue();

                        DataHandler data = new DataHandler(map.get("title").toString(),map.get("url").toString());
                        dataHandlers.add(data);

                        viewPager2=findViewById(R.id.viewPager);
                        PagerAdapter pagerAdapter = new PagerAdapter(dataHandlers,MainActivity.this);
                        viewPager2.setAdapter(pagerAdapter);

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }
}