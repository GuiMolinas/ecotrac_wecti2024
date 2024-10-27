package com.wecti.unicid.myapplication;

import java.util.HashMap;
import java.util.Map;

public class ConsumoEletrico {

    private static final Map<String, Double> potenciaAparelhos;

    static {
        potenciaAparelhos = new HashMap<>();
        potenciaAparelhos.put("Ar-condicionado", 1.5);
        potenciaAparelhos.put("Aparelho de som", 0.1);
        potenciaAparelhos.put("Aspirador", 1.2);
        potenciaAparelhos.put("Batedeira", 0.3);
        // Adicione as potências para cada aparelho...
        potenciaAparelhos.put("Ventilador", 0.1);
        potenciaAparelhos.put("Videogame", 0.15);
    }

    public static double calcularConsumo(String dispositivo, double horas) {
        Double potencia = potenciaAparelhos.get(dispositivo);
        if (potencia == null) {
            return 0; // Se o dispositivo não estiver na lista, retorna 0 kWh
        }
        return potencia * horas;
    }
}

