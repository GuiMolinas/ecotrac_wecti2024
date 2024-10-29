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

    private ImageButton btnHome, btnLixeira;
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
        btnLixeira = findViewById(R.id.btnLixeira);

        btnHome.setOnClickListener(view -> {
            Intent intent = new Intent(Historico.this, Home.class);
            startActivity(intent);
            finish();
        });

        btnLixeira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper dbHelper = new DatabaseHelper(Historico.this);
                dbHelper.clearAllHistory();  // Limpa todo o histórico
                containerHistorico.removeAllViews();  // Remove todas as visualizações do histórico
                adicionarMensagemNenhumRegistro("Histórico limpo.");  // Adiciona uma mensagem informando que o histórico foi limpo
            }
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
        carregarHistoricoEletricidade();
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
                    adicionarContainerHistorico("Consumo de Água", data, consumoAgua, "L");
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
                    adicionarContainerHistorico("Emissões de Carbono", data, totalCarbono, "kg CO₂");
                } while (cursor.moveToNext());
            } else {
                adicionarMensagemNenhumRegistro("Nenhum histórico de carbono encontrado.");
            }
        }
    }

    // Método para carregar o histórico de consumo de eletricidade
    private void carregarHistoricoEletricidade() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.query(
                     DatabaseHelper.TABLE_ELECTRICITY_HISTORY,
                     null, // Seleciona todas as colunas
                     null,
                     null,
                     null,
                     null,
                     DatabaseHelper.COLUMN_ELECTRICITY_ID + " DESC" // Ordena para exibir os mais recentes primeiro
             )) {

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    String data = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE_ELECTRICITY));
                    double consumoEletricidade = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TOTAL_ELECTRICITY_CONSUMPTION));
                    // Adiciona as informações do histórico de eletricidade ao layout
                    adicionarContainerHistorico("Consumo de Eletricidade", data, consumoEletricidade, "kWh");
                } while (cursor.moveToNext());
            } else {
                adicionarMensagemNenhumRegistro("Nenhum histórico de eletricidade encontrado.");
            }
        }
    }

    // Método genérico para adicionar informações do histórico
    private void adicionarContainerHistorico(String tipo, String data, double valor, String unidade) {
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

        TextView txtValor = new TextView(this);
        txtValor.setText(tipo + ": " + String.format("%.2f", valor) + " " + unidade);
        txtValor.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        txtValor.setTextSize(14);
        container.addView(txtValor);

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
