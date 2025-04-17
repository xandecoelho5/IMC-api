package br.edu.utfpr.imc.model;

public enum Classification {
    UNDERWEIGHT("Abaixo do peso", 18.5),
    NORMAL_WEIGHT("Peso normal", 25.0),
    OVERWEIGHT("Sobrepeso", 30.0),
    OBESITY_GRADE_I("Obesidade Grau I", 35.0),
    OBESITY_GRADE_II("Obesidade Grau II", 40.0),
    OBESITY_GRADE_III("Obesidade Grau III", Double.MAX_VALUE);

    private final String description;
    private final double minValue;

    Classification(String description, double minValue) {
        this.description = description;
        this.minValue = minValue;
    }

    public String getDescription() {
        return description;
    }

    public double getMinValue() {
        return minValue;
    }
}
