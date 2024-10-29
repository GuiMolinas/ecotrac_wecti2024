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
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Historico.this, Home.class);
                startActivity(intent);
                finish();
            }
        });

        // Referência ao container do histórico
        containerHistorico = findViewById(R.id.containerHistorico);

        // Carrega o histórico do banco de dados
        carregarHistorico();
    }

    // Método para carregar o histórico
    private void carregarHistorico() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Realiza a consulta para obter todos os registros do histórico
        Cursor cursor = db.query(
                DatabaseHelper.TABLE_WATER_HISTORY,
                null, // Seleciona todas as colunas
                null,
                null,
                null,
                null,
                DatabaseHelper.COLUMN_ID + " DESC" // Ordena para exibir os mais recentes primeiro
        );

        if (cursor.moveToFirst()) {
            do {
                String data = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE));
                double consumoAgua = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TOTAL_CONSUMPTION));

                // Adiciona as informações do histórico ao layout
                adicionarContainerHistorico(data, consumoAgua);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }

    // Método para adicionar informações ao histórico
    private void adicionarContainerHistorico(String data, double consumoAgua) {
        // Cria um novo LinearLayout para o container do histórico
        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setPadding(16, 16, 16, 16);
        container.setElevation(4);

        // Data do Histórico
        TextView txtData = new TextView(this);
        txtData.setText("Data: " + data);
        txtData.setTextColor(getResources().getColor(android.R.color.white));
        txtData.setTextSize(16);
        txtData.setTypeface(null, Typeface.BOLD);
        container.addView(txtData);

        // Consumo de Água
        TextView txtConsumoAgua = new TextView(this);
        txtConsumoAgua.setText("Consumo de Água: " + String.format("%.2f", consumoAgua) + " L");
        txtConsumoAgua.setTextColor(getResources().getColor(android.R.color.white));
        txtConsumoAgua.setTextSize(14);
        container.addView(txtConsumoAgua);

        // Adiciona o container ao layout principal
        containerHistorico.addView(container);
    }
}
