package com.wecti.unicid.myapplication.asks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.wecti.unicid.myapplication.R;
import com.wecti.unicid.myapplication.answers.answerWater;

public class askWater extends AppCompatActivity {

    ImageView imageView;
    ImageButton back;

    EditText edtMaquina, edtTanque, edtDescarga, edtPia, edtChuveiro,
            edtCozinha, edtLavaLouca;

    Button btnEnviarDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ask_water);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageView = findViewById(R.id.imgWater);
        back = findViewById(R.id.btnVoltar);

        edtMaquina = findViewById(R.id.edtMaquina);
        edtTanque = findViewById(R.id.edtTanque);
        edtDescarga = findViewById(R.id.edtDescarga);
        edtPia = findViewById(R.id.edtPia);
        edtChuveiro = findViewById(R.id.edtChuveiro);
        edtCozinha = findViewById(R.id.edtCozinha);
        edtLavaLouca = findViewById(R.id.edtLavaLouca);

        btnEnviarDados = findViewById(R.id.btnEnviarDados);

        Glide.with(this)
                .load(R.drawable.water)
                .into(imageView);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnEnviarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double consumoMaquina = obterValor(edtMaquina) * 19;
                double consumoTanque = obterValor(edtTanque) * 15;
                double consumoDescarga = obterValor(edtDescarga) * 6;
                double consumoPia = obterValor(edtPia) * 15;
                double consumoChuveiro = obterValor(edtChuveiro) * 12;
                double consumoCozinha = obterValor(edtCozinha) * 15;
                double consumoLavaLouca = obterValor(edtLavaLouca) * 2;

                double total = consumoMaquina + consumoTanque + consumoDescarga + consumoPia
                        + consumoChuveiro + consumoCozinha + consumoLavaLouca;

                Intent intent = new Intent(askWater.this, answerWater.class);
                intent.putExtra("consumo_total", total);

                intent.putExtra("consumo_maquina", consumoMaquina);
                intent.putExtra("consumo_tanque", consumoTanque);
                intent.putExtra("consumo_descarga", consumoDescarga);
                intent.putExtra("consumo_pia", consumoPia);
                intent.putExtra("consumo_chuveiro", consumoChuveiro);
                intent.putExtra("consumo_cozinha", consumoCozinha);
                intent.putExtra("consumo_lavaLouca", consumoLavaLouca);
                startActivity(intent);

                //Teste
                //Toast.makeText(askWater.this, "Consumo diário: " + total, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private double obterValor(EditText edtText) {
        String valor = edtText.getText().toString();
        return valor.isEmpty() ? 0 : Double.parseDouble(valor);
    }

}