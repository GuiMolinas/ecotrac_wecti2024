package com.wecti.unicid.myapplication.first_contact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.wecti.unicid.myapplication.R;
import com.wecti.unicid.myapplication.asks.AskCarbon;
import com.wecti.unicid.myapplication.asks.AskEletricity;
import com.wecti.unicid.myapplication.asks.AskWater;

public class Introduction extends AppCompatActivity {

    ImageView image;
    Button btnWater, btnEletricity, btnCarbon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_introduction);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        image = findViewById(R.id.imgEarth);

        btnWater = findViewById(R.id.btnWater);
        btnEletricity = findViewById(R.id.btnEletricity);
        btnCarbon = findViewById(R.id.btnCarbon);

        Glide.with(this)
                .load(R.drawable.earth)
                .into(image);

        btnWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Introduction.this, AskWater.class);
                startActivity(intent);
            }
        });

        btnEletricity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Introduction.this, AskEletricity.class);
                startActivity(intent);
            }
        });

        btnCarbon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Introduction.this, AskCarbon.class);
                startActivity(intent);
            }
        });
    }
}