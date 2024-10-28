package com.wecti.unicid.myapplication.answers;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.wecti.unicid.myapplication.R;

public class AnswerCarbon extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_answer_carbon);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageView = findViewById(R.id.imgFootprint);

        Glide.with(this)
                .load(R.drawable.footprint)
                .into(imageView);

        // Recuperar dados
        double energiaFootprint = getIntent().getDoubleExtra("energiaFootprint", 0);
        double transporteFootprint = getIntent().getDoubleExtra("transporteFootprint", 0);
        double veiculoFootprint = getIntent().getDoubleExtra("veiculoFootprint", 0);
        double dietaFootprint = getIntent().getDoubleExtra("dietaFootprint", 0);
        double plasticoFootprint = getIntent().getDoubleExtra("plasticoFootprint", 0);
        double aviaoFootprint = getIntent().getDoubleExtra("aviaoFootprint", 0);
        double totalFootprint = getIntent().getDoubleExtra("totalFootprint", 0);

        // Média para comparação
        double mediaDiaria = 4.8;

        // Atualizar TextViews com os valores e cálculo do excesso
        String resultadoEnergia = formatResult("Energia", energiaFootprint, mediaDiaria);
        String resultadoTransporte = formatResult("Transporte", transporteFootprint, mediaDiaria);
        String resultadoVeiculo = formatResult("Veículo", veiculoFootprint, mediaDiaria);
        String resultadoDieta = formatResult("Dieta", dietaFootprint, mediaDiaria);
        String resultadoPlastico = formatResult("Plásticos", plasticoFootprint, mediaDiaria);
        String resultadoAviao = formatResult("Avião", aviaoFootprint, mediaDiaria);

        ((TextView) findViewById(R.id.txtConsumos)).setText(
                resultadoEnergia + "\n" + resultadoTransporte + "\n" + resultadoVeiculo + "\n" +
                        resultadoDieta + "\n" + resultadoPlastico + "\n" + resultadoAviao
        );

        ((TextView) findViewById(R.id.txtTotalFootprint)).setText(
                String.format("Pegada de Carbono Total: %.2f kg CO₂", totalFootprint)
        );

        if (totalFootprint > mediaDiaria) {
            double excesso = ((totalFootprint - mediaDiaria) / mediaDiaria) * 100;
            ((TextView) findViewById(R.id.txtResultado)).setText(
                    String.format("Excedeu o recomendado em %.2f%%", excesso)
            );
        } else {
            ((TextView) findViewById(R.id.txtResultado)).setText("Dentro da média recomendada.");
        }
    }

    // Método para formatar os resultados
    private String formatResult(String categoria, double valor, double media) {
        if (valor > media) {
            double excesso = ((valor - media) / media) * 100;
            return String.format("%s: %.2f kg CO₂ (%.2f%% acima da média)", categoria, valor, excesso);
        } else {
            return String.format("%s: %.2f kg CO₂ (Dentro da média)", categoria, valor);
        }
    }
}
