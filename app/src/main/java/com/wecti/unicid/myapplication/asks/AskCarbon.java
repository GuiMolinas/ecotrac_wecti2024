package com.wecti.unicid.myapplication.asks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.wecti.unicid.myapplication.R;
import com.wecti.unicid.myapplication.answers.AnswerCarbon;
import com.wecti.unicid.myapplication.calculator.CarbonFootprintCalculator;
import com.wecti.unicid.myapplication.screens.Home;

public class AskCarbon extends AppCompatActivity {

    ImageView imageView;
    ImageButton back;
    Button btnEnviar;
    EditText edtEnergia, edtTransporte, edtVeiculo, edtPlastico, edtAviao;
    Spinner spinnerDieta;
    TextView txtResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ask_carbon);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageView = findViewById(R.id.imgCarbon);
        back = findViewById(R.id.btnVoltar);
        edtEnergia = findViewById(R.id.edtEnergia);
        edtTransporte = findViewById(R.id.edtTransporte);
        edtVeiculo = findViewById(R.id.edtVeiculo);
        edtPlastico = findViewById(R.id.edtPlastico);
        edtAviao = findViewById(R.id.edtAviao);
        spinnerDieta = findViewById(R.id.spinnerDieta);
        btnEnviar = findViewById(R.id.btnEnviarCarbono);

        Glide.with(this)
                .load(R.drawable.carbon)
                .into(imageView);

       back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(AskCarbon.this, Home.class);
               startActivity(intent);
               finish();
           }
       });

        btnEnviar.setOnClickListener(view -> calcularPegadaDeCarbono());
    }

    private void calcularPegadaDeCarbono() {
        double energia = getDoubleFromEditText(edtEnergia);
        double transporte = getDoubleFromEditText(edtTransporte);
        double veiculo = getDoubleFromEditText(edtVeiculo);
        int plastico = (int) getDoubleFromEditText(edtPlastico);
        int aviao = (int) getDoubleFromEditText(edtAviao);

        // Obtém o tipo de dieta selecionado no spinner
        String dieta = spinnerDieta.getSelectedItem().toString();


        // Calcule a pegada de carbono individual para cada categoria
        double energiaFootprint = CarbonFootprintCalculator.calculateEnergyFootprint(energia); // Consumo mensal
        double transporteFootprint = CarbonFootprintCalculator.calculatePublicTransportFootprint(transporte); // Consumo mensal
        double veiculoFootprint = CarbonFootprintCalculator.calculateCarFootprint(veiculo); // Consumo mensal
        double dietaFootprint = CarbonFootprintCalculator.getDietFootprint(dieta);
        double plasticoFootprint = CarbonFootprintCalculator.calculatePlasticFootprint(plastico); // Consumo mensal
        double aviaoFootprint = CarbonFootprintCalculator.calculateFlightFootprint(aviao); // Número de voos anuais

        // Calcula a pegada de carbono total
        double totalFootprint = energiaFootprint + transporteFootprint + veiculoFootprint
                + dietaFootprint + plasticoFootprint + aviaoFootprint;

        // Inicia AnswerCarbon com os resultados
        Intent intent = new Intent(this, AnswerCarbon.class);
        intent.putExtra("energiaFootprint", energiaFootprint);
        intent.putExtra("transporteFootprint", transporteFootprint);
        intent.putExtra("veiculoFootprint", veiculoFootprint);
        intent.putExtra("dietaFootprint", dietaFootprint);
        intent.putExtra("plasticoFootprint", plasticoFootprint);
        intent.putExtra("aviaoFootprint", aviaoFootprint);
        intent.putExtra("totalFootprint", totalFootprint);
        startActivity(intent);
    }

    // Método auxiliar para converter valor de EditText para double (ou zero se vazio)
    private double getDoubleFromEditText(EditText editText) {
        String text = editText.getText().toString();
        return text.isEmpty() ? 0 : Double.parseDouble(text);
    }
}
