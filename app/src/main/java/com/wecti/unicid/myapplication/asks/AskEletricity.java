package com.wecti.unicid.myapplication.asks;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.wecti.unicid.myapplication.R;
import com.wecti.unicid.myapplication.answers.AnswerEletricity;
import com.wecti.unicid.myapplication.calculator.ConsumoEletrico;

import java.util.ArrayList;

public class AskEletricity extends AppCompatActivity {

    ImageView imageView;
    ImageButton back;

    Spinner spinnerAparelhos;
    LinearLayout deviceList;
    Button btnAdicionar, btnCalcular;

    EditText edtTempo;

    // Listas para armazenar dispositivos e horas
    ArrayList<String> listaDispositivos = new ArrayList<>();
    ArrayList<Double> listaHoras = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ask_eletricity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageView = findViewById(R.id.imgEletricity);
        back = findViewById(R.id.btnVoltar);

        spinnerAparelhos = findViewById(R.id.spinnerAparelhos);
        deviceList = findViewById(R.id.deviceList);

        btnAdicionar = findViewById(R.id.btnAdicionar);
        btnCalcular = findViewById(R.id.btnCalcular);

        edtTempo = findViewById(R.id.edtTempo);

        Glide.with(this)
                .load(R.drawable.eletricidade)
                .into(imageView);

        back.setOnClickListener(view -> finish());

        btnAdicionar.setOnClickListener(view -> adicionarDispositivo());

        btnCalcular.setOnClickListener(view -> calcularConsumoTotal());
    }

    private void adicionarDispositivo() {
        String dispositivo = spinnerAparelhos.getSelectedItem().toString();
        String horas = edtTempo.getText().toString();

        if (dispositivo.equals("-- Selecione --")) {
            Toast.makeText(this, "Por favor, selecione um aparelho", Toast.LENGTH_SHORT).show();
            return;
        }

        if (horas.isEmpty()) {
            Toast.makeText(this, "Por favor, insira o número de horas", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verifica se o dispositivo já foi adicionado
        if (listaDispositivos.contains(dispositivo)) {
            Toast.makeText(this, "Dispositivo já adicionado!", Toast.LENGTH_SHORT).show();
            return;
        }

        double horasDouble = Double.parseDouble(horas);
        double consumo = ConsumoEletrico.calcularConsumo(dispositivo, horasDouble);

        // Adiciona o dispositivo e as horas às listas
        listaDispositivos.add(dispositivo);
        listaHoras.add(horasDouble);

        // Cria um TextView para o dispositivo com o consumo
        TextView novoDispositivo = new TextView(this);
        novoDispositivo.setText(dispositivo + " - Consumo: " + String.format("%.2f", consumo) + " kWh");
        novoDispositivo.setTextSize(16);
        novoDispositivo.setTextColor(getResources().getColor(R.color.black));
        novoDispositivo.setPadding(10, 10, 10, 10);

        deviceList.addView(novoDispositivo);
    }

    // Função para calcular o consumo total e abrir a próxima Activity
    private void calcularConsumoTotal() {
        double consumoTotal = 0;
        ArrayList<String> consumosIndividuais = new ArrayList<>();

        for (int i = 0; i < listaDispositivos.size(); i++) {
            String dispositivo = listaDispositivos.get(i);
            double horas = listaHoras.get(i);
            double consumo = ConsumoEletrico.calcularConsumo(dispositivo, horas);

            consumoTotal += consumo;
            consumosIndividuais.add(dispositivo + ": " + String.format("%.2f", consumo) + " kWh");
        }

        // Verificar se passou do limite
        double limiteRecomendado = 7;
        boolean passouDoLimite = consumoTotal > limiteRecomendado;
        double percentualExcedido = 0;
        if (passouDoLimite) {
            percentualExcedido = ((consumoTotal - limiteRecomendado) / limiteRecomendado) * 100;
        }

        // Enviar dados para a próxima tela
        Intent intent = new Intent(this, AnswerEletricity.class);
        intent.putExtra("consumoTotal", consumoTotal);
        intent.putExtra("consumosIndividuais", consumosIndividuais);
        intent.putExtra("passouDoLimite", passouDoLimite);
        intent.putExtra("percentualExcedido", percentualExcedido);
        startActivity(intent);
    }
}
