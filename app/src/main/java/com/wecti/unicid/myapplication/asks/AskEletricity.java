package com.wecti.unicid.myapplication.asks;

import android.os.Bundle;
import android.view.View;
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
import com.wecti.unicid.myapplication.ConsumoEletrico;
import com.wecti.unicid.myapplication.R;

public class AskEletricity extends AppCompatActivity {

    ImageView imageView;
    ImageButton back;

    Spinner spinnerAparelhos;
    LinearLayout deviceList;
    Button btnAdicionar;
    
    EditText edtTempo;

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
        
        edtTempo = findViewById(R.id.edtTempo);

        Glide.with(this)
                .load(R.drawable.eletricidade)
                .into(imageView);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarDispositivo();
            }
        });
    }

    private void adicionarDispositivo() {
        String dispositivo = spinnerAparelhos.getSelectedItem().toString();
        String horas = edtTempo.getText().toString();

        if(dispositivo.equals("-- Selecione --")) {
            Toast.makeText(this, "Por favor, selecione um aparelho", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if(horas.isEmpty()) {
            Toast.makeText(this, "Por favor, insira o número de horas", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verifica se o dispositivo já foi adicionado
        boolean jaAdicionado = false;
        for (int i = 0; i < deviceList.getChildCount(); i++) {
            TextView textView = (TextView) deviceList.getChildAt(i);
            if (textView.getText().toString().equals(dispositivo)) {
                jaAdicionado = true;
                break;
            }
        }

        if(!jaAdicionado) {
            double horasDouble = Double.parseDouble(horas);
            double consumo = ConsumoEletrico.calcularConsumo(dispositivo, horasDouble);

            TextView novoDispositivo = new TextView(this);
            novoDispositivo.setText(dispositivo);
            novoDispositivo.setTextSize(16);
            novoDispositivo.setTextColor(getResources().getColor(R.color.black));
            novoDispositivo.setPadding(10, 10, 10, 10);

            deviceList.addView(novoDispositivo);
        }

        else {
            Toast.makeText(this, "Dispositivo já adicionado!", Toast.LENGTH_SHORT).show();
        }

    }
}