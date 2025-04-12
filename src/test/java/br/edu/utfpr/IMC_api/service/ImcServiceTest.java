package br.edu.utfpr.IMC_api.service;

import br.edu.utfpr.IMC_api.dto.ImcResult;
import br.edu.utfpr.IMC_api.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ImcServiceTest {

    private ImcService imcService;

    @BeforeEach
    void setUp() {
        imcService = new ImcService();
    }

    @Test
    void shouldCalculateImcCorrectly() {
        Person person = new Person(70.0, 1.75);

        ImcResult result = imcService.calculateImc(person);

        assertEquals(22.86, result.getImc());
        assertEquals("Peso normal", result.getClassification());
    }

    @Test
    void shouldThrowExceptionWhenPersonIsNull() {
        Person person = null;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> imcService.calculateImc(person));

        assertEquals("Pessoa, peso e altura não podem ser nulos", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenWeightIsNull() {
        Person person = new Person(null, 1.75);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> imcService.calculateImc(person));

        assertEquals("Pessoa, peso e altura não podem ser nulos", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenHeightIsNull() {
        Person person = new Person(70.0, null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> imcService.calculateImc(person));

        assertEquals("Pessoa, peso e altura não podem ser nulos", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "17.0, Abaixo do peso",
            "18.4, Abaixo do peso",
            "18.5, Peso normal",
            "24.9, Peso normal",
            "25.0, Sobrepeso",
            "29.9, Sobrepeso",
            "30.0, Obesidade Grau I",
            "34.9, Obesidade Grau I",
            "35.0, Obesidade Grau II",
            "39.9, Obesidade Grau II",
            "40.0, Obesidade Grau III",
            "45.0, Obesidade Grau III"
    })
    void shouldClassifyImcCorrectly(double imc, String expectedClassification) {
        String classification = imcService.classifyImc(imc);

        assertEquals(expectedClassification, classification);
    }
}