package com.wecti.unicid.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

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
    }
}