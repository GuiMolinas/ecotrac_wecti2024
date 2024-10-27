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
        potenciaAparelhos.put("Cafeteira", 0.8);
        potenciaAparelhos.put("Carregador de celular", 0.005);
        potenciaAparelhos.put("Chuveiro", 5.5);
        potenciaAparelhos.put("Computador", 0.2);
        potenciaAparelhos.put("Exaustor", 0.2);
        potenciaAparelhos.put("Ferro de passar roupa", 1.2);
        potenciaAparelhos.put("Freezer", 0.4);
        potenciaAparelhos.put("Geladeira", 0.15);
        potenciaAparelhos.put("Grill", 1.2);
        potenciaAparelhos.put("Lâmpada fluorescente", 0.015);
        potenciaAparelhos.put("Lâmpada incandescente", 0.06);
        potenciaAparelhos.put("Liquidificador", 0.4);
        potenciaAparelhos.put("Máquina de lavar", 0.5);
        potenciaAparelhos.put("Microondas", 1.2);
        potenciaAparelhos.put("Sanduicheira", 0.8);
        potenciaAparelhos.put("Secador de cabelo", 1.5);
        potenciaAparelhos.put("Televisor", 0.1);
        potenciaAparelhos.put("Telefone sem fio", 0.002);
        potenciaAparelhos.put("Torradeira", 0.8);
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

