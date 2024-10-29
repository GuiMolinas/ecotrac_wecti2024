package com.wecti.unicid.myapplication.screens;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Typeface;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.content.ContextCompat;

import com.wecti.unicid.myapplication.R;
import com.wecti.unicid.myapplication.database.DatabaseHelper;

public class Historico extends AppCompatActivity {

    private ImageButton btnHome;
    private LinearLayout containerHistorico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_historico);

        // Ajuste de padding para as bordas do sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Configuração do botão Home
        btnHome = findViewById(R.id.btnHome);
        btnHome.setOnClickListener(view -> {
            Intent intent = new Intent(Historico.this, Home.class);
            startActivity(intent);
            finish();
        });

        // Referência ao container do histórico
        containerHistorico = findViewById(R.id.containerHistorico);

        // Carrega o histórico do banco de dados
        carregarHistorico();
    }

    // Método para carregar o histórico de água e carbono
    private void carregarHistorico() {
        carregarHistoricoAgua();
        carregarHistoricoCarbono();
    }

    // Método para carregar o histórico de consumo de água
    private void carregarHistoricoAgua() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.query(
                     DatabaseHelper.TABLE_WATER_HISTORY,
                     null, // Seleciona todas as colunas
                     null,
                     null,
                     null,
                     null,
                     DatabaseHelper.COLUMN_WATER_ID + " DESC" // Ordena para exibir os mais recentes primeiro
             )) {

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String data = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE_WATER));
                    double consumoAgua = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TOTAL_WATER_CONSUMPTION));
                    // Adiciona as informações do histórico de água ao layout
                    adicionarContainerHistoricoAgua(data, consumoAgua);
                } while (cursor.moveToNext());
            } else {
                adicionarMensagemNenhumRegistro("Nenhum histórico de água encontrado.");
            }
        }
    }

    // Método para carregar o histórico de emissões de carbono
    private void carregarHistoricoCarbono() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.query(
                     DatabaseHelper.TABLE_CARBON_HISTORY,
                     null, // Seleciona todas as colunas
                     null,
                     null,
                     null,
                     null,
                     DatabaseHelper.COLUMN_CARBON_ID + " DESC" // Ordena para exibir os mais recentes primeiro
             )) {

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String data = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE_CARBON));
                    double totalCarbono = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TOTAL_CARBON_EMISSION));
                    // Adiciona as informações do histórico de carbono ao layout
                    adicionarContainerHistoricoCarbono(data, totalCarbono);
                } while (cursor.moveToNext());
            } else {
                adicionarMensagemNenhumRegistro("Nenhum histórico de carbono encontrado.");
            }
        }
    }

    // Método para adicionar informações do histórico de água
    private void adicionarContainerHistoricoAgua(String data, double consumoAgua) {
        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setPadding(16, 16, 16, 16);
        container.setElevation(4);
        container.setBackgroundColor(ContextCompat.getColor(this, R.color.containerBackground)); // Define a cor de fundo

        TextView txtData = new TextView(this);
        txtData.setText("Data: " + data);
        txtData.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        txtData.setTextSize(16);
        txtData.setTypeface(null, Typeface.BOLD);
        container.addView(txtData);

        TextView txtConsumoAgua = new TextView(this);
        txtConsumoAgua.setText("Consumo de Água: " + String.format("%.2f", consumoAgua) + " L");
        txtConsumoAgua.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        txtConsumoAgua.setTextSize(14);
        container.addView(txtConsumoAgua);

        containerHistorico.addView(container);
    }

    // Método para adicionar informações do histórico de carbono
    private void adicionarContainerHistoricoCarbono(String data, double totalCarbono) {
        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setPadding(16, 16, 16, 16);
        container.setElevation(4);
        container.setBackgroundColor(ContextCompat.getColor(this, R.color.containerBackground)); // Define a cor de fundo

        TextView txtData = new TextView(this);
        txtData.setText("Data: " + data);
        txtData.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        txtData.setTextSize(16);
        txtData.setTypeface(null, Typeface.BOLD);
        container.addView(txtData);

        TextView txtTotalCarbono = new TextView(this);
        txtTotalCarbono.setText("Emissões de Carbono: " + String.format("%.2f", totalCarbono) + " kg CO₂");
        txtTotalCarbono.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        txtTotalCarbono.setTextSize(14);
        container.addView(txtTotalCarbono);

        containerHistorico.addView(container);
    }

    // Método para adicionar uma mensagem de "nenhum registro encontrado"
    private void adicionarMensagemNenhumRegistro(String mensagem) {
        TextView txtMensagem = new TextView(this);
        txtMensagem.setText(mensagem);
        txtMensagem.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        txtMensagem.setTextSize(16);
        txtMensagem.setPadding(16, 16, 16, 16);
        containerHistorico.addView(txtMensagem);
    }
}
