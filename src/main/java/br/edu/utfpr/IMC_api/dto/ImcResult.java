package br.edu.utfpr.IMC_api.dto;

public class ImcResult {
    private Double imc;
    private String classification;

    public ImcResult() {
    }

    public ImcResult(Double imc, String classification) {
        this.imc = imc;
        this.classification = classification;
    }

    public Double getImc() {
        return imc;
    }

    public void setImc(Double imc) {
        this.imc = imc;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }
}
