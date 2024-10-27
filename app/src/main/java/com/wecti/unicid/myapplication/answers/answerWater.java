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

public class answerWater extends AppCompatActivity {

    ImageView imageView;
    TextView txtTotal, txtResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_answer_water);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageView = findViewById(R.id.imgRabbit);

        Glide.with(this)
                .load(R.drawable.rabbit)
                .into(imageView);

        double total = getIntent().getDoubleExtra("consumo_total", 0);

        double consumoMaquina = getIntent().getDoubleExtra("consumo_maquina", 0);
        double consumoTanque = getIntent().getDoubleExtra("consumo_tanque", 0);
        double consumoDescarga = getIntent().getDoubleExtra("consumo_descarga", 0);
        double consumoPia = getIntent().getDoubleExtra("consumo_pia", 0);
        double consumoChuveiro = getIntent().getDoubleExtra("consumo_chuveiro", 0);
        double consumoCozinha = getIntent().getDoubleExtra("consumo_cozinha", 0);
        double consumoLavaLouca = getIntent().getDoubleExtra("consumo_lavaLouca", 0);

        txtTotal = findViewById(R.id.txtTotal);
        txtResultado = findViewById(R.id.txtResultado);

        // Exibe os valores de consumo individual nos TextView correspondentes
        ((TextView) findViewById(R.id.txtConsumoMaquina)).setText("Máquina de Lavar: " + String.format("%.2f", consumoMaquina) + "L");
        ((TextView) findViewById(R.id.txtConsumoTanque)).setText("Tanque:" + consumoTanque + "L");
        ((TextView) findViewById(R.id.txtConsumoDescarga)).setText("Descarga: " + consumoDescarga + "L");
        ((TextView) findViewById(R.id.txtConsumoBanheiro)).setText("Pia Banheiro: " + consumoPia + "L");
        ((TextView) findViewById(R.id.txtConsumoChuveiro)).setText("Chuveiro: " + consumoChuveiro + "L");
        ((TextView) findViewById(R.id.txtConsumoCozinha)).setText("Pia Cozinha: " + consumoCozinha + "L");
        ((TextView) findViewById(R.id.txtConsumoLavaLouca)).setText("Lava Louças: " + String.format("%.2f", consumoLavaLouca) + "L");

        txtTotal.setText("Consumo Total - " + String.format("%.2f", total) + "L");

        // Verifica se ultrapassou o limite e define a conclusão
        if (total > 110) {
            double excesso = total - 110;
            double porcentagem = (excesso / 110) * 100;
            txtResultado.setText("Você ultrapassou " + String.format("%.2f", excesso) +
                    "L do recomendado (" + String.format("%.2f", porcentagem) + "%)");
        } else {
            txtResultado.setText("Consumo dentro do recomendado");
        }
    }
}