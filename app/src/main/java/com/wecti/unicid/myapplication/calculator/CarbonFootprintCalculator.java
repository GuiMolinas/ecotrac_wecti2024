package com.wecti.unicid.myapplication.calculator;

public class CarbonFootprintCalculator {

    // Fatores de emissão (em kg CO₂) - valores aproximados
    public static final double ENERGY_EMISSION_FACTOR = 0.233; // kg CO₂ por kWh
    public static final double PUBLIC_TRANSPORT_EMISSION_FACTOR = 0.1; // kg CO₂ por km
    public static final double CAR_EMISSION_FACTOR = 0.21; // kg CO₂ por km
    public static final double LONG_FLIGHT_EMISSION_FACTOR = 4400; // kg CO₂ por viagem

    // Valores aproximados de pegada diária baseados no tipo de dieta
    private static final double DIET_VEGETARIAN = 2.74; // kg CO₂ por dia
    private static final double DIET_VEGAN = 1.37; // kg CO₂ por dia
    private static final double DIET_LOW_MEAT = 4.93; // kg CO₂ por dia
    private static final double DIET_HIGH_MEAT = 6.85; // kg CO₂ por dia

    // Plásticos de uso único - aproximado em kg CO₂ por produto
    public static final double PLASTIC_EMISSION_FACTOR = 0.5; // kg CO₂ por item de plástico de uso único

    // Métodos para calcular cada pegada de carbono individualmente
    public static double calculateEnergyFootprint(double monthlyEnergyConsumption) {
        double dailyEnergyConsumption = monthlyEnergyConsumption / 30; // Média diária
        return dailyEnergyConsumption * ENERGY_EMISSION_FACTOR; // Cálculo para consumo diário
    }

    public static double calculatePublicTransportFootprint(double monthlyPublicTransportKm) {
        double dailyPublicTransportKm = monthlyPublicTransportKm / 30; // Média diária
        return dailyPublicTransportKm * PUBLIC_TRANSPORT_EMISSION_FACTOR; // Cálculo para consumo diário
    }

    public static double calculateCarFootprint(double monthlyCarKm) {
        double dailyCarKm = monthlyCarKm / 30; // Média diária
        return dailyCarKm * CAR_EMISSION_FACTOR; // Cálculo para consumo diário
    }

    public static double getDietFootprint(String dietType) {
        switch (dietType) {
            case "Vegetariana":
                return DIET_VEGETARIAN;
            case "Vegana":
                return DIET_VEGAN;
            case "Onívora com baixo consumo de carne":
                return DIET_LOW_MEAT;
            case "Onívora com alto consumo de carne":
                return DIET_HIGH_MEAT;
            default:
                return 0;
        }
    }

    public static double calculatePlasticFootprint(int monthlyPlasticItems) {
        double dailyPlasticItems = monthlyPlasticItems / 30.0; // Média diária
        return dailyPlasticItems * PLASTIC_EMISSION_FACTOR; // Cálculo para consumo diário
    }

    public static double calculateFlightFootprint(int annualFlights) {
        // Calcula a pegada de carbono de voos anuais
        return (annualFlights * LONG_FLIGHT_EMISSION_FACTOR) / 365; // Média diária
    }

    // Método principal para calcular a pegada de carbono total
    public static double calculateTotalFootprint(double monthlyEnergyConsumption,
                                                 double monthlyPublicTransportKm,
                                                 double monthlyCarKm,
                                                 String dietType,
                                                 int monthlyPlasticItems,
                                                 int annualFlights) {

        double energyFootprint = calculateEnergyFootprint(monthlyEnergyConsumption);
        double publicTransportFootprint = calculatePublicTransportFootprint(monthlyPublicTransportKm);
        double carFootprint = calculateCarFootprint(monthlyCarKm);
        double dietFootprint = getDietFootprint(dietType);
        double plasticFootprint = calculatePlasticFootprint(monthlyPlasticItems);
        double flightFootprint = calculateFlightFootprint(annualFlights);

        // Somatório da pegada de carbono total
        return energyFootprint + publicTransportFootprint + carFootprint + dietFootprint + plasticFootprint + flightFootprint;
    }
}
