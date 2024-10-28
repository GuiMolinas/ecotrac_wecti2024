package com.wecti.unicid.myapplication.answers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.wecti.unicid.myapplication.R;
import com.wecti.unicid.myapplication.screens.Home;

import java.util.ArrayList;

public class AnswerEletricity extends AppCompatActivity {

    ImageView imageView;
    TextView txtTotalConsumo, txtConsumoIndividual, txtResultado;
    ImageButton btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_answer_eletricity);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageView = findViewById(R.id.imgTomada);
        txtTotalConsumo = findViewById(R.id.txtTotal);
        txtConsumoIndividual = findViewById(R.id.txtConsumos);
        txtResultado = findViewById(R.id.txtResultado);

        btnHome = findViewById(R.id.btnHome);

        Glide.with(this)
                .load(R.drawable.tomada)
                .into(imageView);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AnswerEletricity.this, Home.class);
                startActivity(intent);
                finish();
            }
        });

        // Obtendo os dados enviados pela AskEletricity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            double consumoTotal = extras.getDouble("consumoTotal", 0);
            ArrayList<String> consumosIndividuais = extras.getStringArrayList("consumosIndividuais");
            boolean passouDoLimite = extras.getBoolean("passouDoLimite", false);
            double percentualExcedido = extras.getDouble("percentualExcedido", 0);

            // Exibindo o consumo total
            txtTotalConsumo.setText("Consumo Total: " + String.format("%.2f", consumoTotal) + " kWh");

            // Exibindo o consumo individual de cada aparelho
            StringBuilder consumoIndividualText = new StringBuilder();
            if (consumosIndividuais != null) {
                for (String consumo : consumosIndividuais) {
                    consumoIndividualText.append(consumo).append("\n");
                }
            }
            txtConsumoIndividual.setText(consumoIndividualText.toString());

            // Exibindo o resultado se o consumo total passou do limite
            if (passouDoLimite) {
                txtResultado.setText("Você ultrapassou o limite recomendado em " + String.format("%.2f", percentualExcedido) + "%.");
            } else {
                txtResultado.setText("Seu consumo está dentro do limite recomendado.");
            }
        }
    }
}
