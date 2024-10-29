package com.wecti.unicid.myapplication.screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.wecti.unicid.myapplication.R;
import com.wecti.unicid.myapplication.asks.AskCarbon;
import com.wecti.unicid.myapplication.asks.AskEletricity;
import com.wecti.unicid.myapplication.asks.AskWater;

public class Home extends AppCompatActivity {

    ImageButton btnExit, btnInfo;
    Button btnAgua, btnEletricidade, btnCarbono, btnHistorico;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnExit = findViewById(R.id.btnExit);
        btnInfo = findViewById(R.id.btnInfo);

        btnAgua = findViewById(R.id.btnWater);
        btnEletricidade = findViewById(R.id.btnEletricity);
        btnCarbono = findViewById(R.id.btnCarbon);
        btnHistorico = findViewById(R.id.btnHistorico);

        // Carregar nome do usuário
        SharedPreferences prefs = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String userName = prefs.getString("userName", "Usuário");

        // Exibir nome no TextView
        TextView txtWelcome = findViewById(R.id.txtWelcome);
        txtWelcome.setText(getString(R.string.welcome_message, userName));

        btnAgua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, AskWater.class);
                startActivity(intent);
            }
        });

        btnHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Historico.class);
                startActivity(intent);
            }
        });

        btnEletricidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, AskEletricity.class);
                startActivity(intent);
            }
        });

        btnCarbono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, AskCarbon.class);
                startActivity(intent);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Info.class);
                startActivity(intent);
            }
        });
    }
}