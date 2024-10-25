package com.wecti.unicid.myapplication.first_contact;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.wecti.unicid.myapplication.R;

public class Welcome extends AppCompatActivity {

    private EditText edtName;
    private Button btnComecar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Verifica se a primeira vez do nosso user no app
        SharedPreferences prefs = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String userName = prefs.getString("userName", null);

        if(userName != null) {
            //se nome estiver salvo, vai para outra tela
            navigateToScreen();
            return;
        }

        edtName = findViewById(R.id.edtName);
        btnComecar = findViewById(R.id.btnStart);

        btnComecar.setOnClickListener(view -> {
            String name = edtName.getText().toString().trim();

            if(name.isEmpty()) {
                Toast.makeText(this, "Por favor, digite seu nome.", Toast.LENGTH_SHORT).show();
            }

            else {
                //Salva nome
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("userName", name);
                editor.putBoolean("isFirstTime", false);
                editor.apply();

                //Vai para outra tela
                navigateToScreen();
            }
        });
    }

    private void navigateToScreen() {
        Intent intent = new Intent(Welcome.this, Introduction.class);
        startActivity(intent);
        finish();
    }
}