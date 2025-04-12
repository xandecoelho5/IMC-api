package br.edu.utfpr.IMC_api.service;

import br.edu.utfpr.IMC_api.dto.ImcResult;
import br.edu.utfpr.IMC_api.model.Person;
import org.springframework.stereotype.Service;

@Service
public class ImcService {
    public ImcResult calculateImc(Person person) {
        if (person == null || person.getWeight() == null || person.getHeight() == null) {
            throw new IllegalArgumentException("Pessoa, peso e altura não podem ser nulos");
        }

        double imc = calculateImcValue(person.getWeight(), person.getHeight());
        String classification = classifyImc(imc);

        return new ImcResult(imc, classification);
    }

    protected double calculateImcValue(double weight, double height) {
        return Math.round((weight / (height * height)) * 100.0) / 100.0;
    }

    protected String classifyImc(double imc) {
        if (imc < 18.5) {
            return "Abaixo do peso";
        } else if (imc < 25.0) {
            return "Peso normal";
        } else if (imc < 30.0) {
            return "Sobrepeso";
        } else if (imc < 35.0) {
            return "Obesidade Grau I";
        } else if (imc < 40.0) {
            return "Obesidade Grau II";
        } else {
            return "Obesidade Grau III";
        }
    }
}