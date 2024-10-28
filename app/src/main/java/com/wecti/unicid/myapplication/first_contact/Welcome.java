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
import com.wecti.unicid.myapplication.screens.Home;

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

        // Verifica se o nome já está salvo
        SharedPreferences prefs = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        String userName = prefs.getString("userName", null);

        if (userName != null) {
            // Se o nome estiver salvo, navega para a tela inicial (Home)
            navigateToHome();
            return;
        }

        edtName = findViewById(R.id.edtName);
        btnComecar = findViewById(R.id.btnStart);

        btnComecar.setOnClickListener(view -> {
            String name = edtName.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(this, "Por favor, digite seu nome.", Toast.LENGTH_SHORT).show();
            } else {
                // Salva o nome
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("userName", name);
                editor.apply();
            }
        });
    }

    private void navigateToHome() {
        Intent intent = new Intent(Welcome.this, Home.class);
        startActivity(intent);
        finish();
    }
}
