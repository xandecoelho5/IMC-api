package br.edu.utfpr.imc.service;

import br.edu.utfpr.imc.dto.ImcResult;
import br.edu.utfpr.imc.model.Classification;
import br.edu.utfpr.imc.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
class ImcServiceTest {

    private static final double PERSON_WEIGHT = 70.0;
    private static final double PERSON_HEIGHT = 1.75;
    private static final double EXPECTED_IMC = 22.86;
    private static final Classification EXPECTED_CLASSIFICATION = Classification.NORMAL_WEIGHT;
    private static final String EXPECTED_ERROR_MESSAGE = "Pessoa, peso e altura não podem ser nulos";

    private ImcService imcService;

    @BeforeEach
    void setUp() {
        imcService = new ImcService();
    }

    @Test
    void shouldCalculateImcCorrectly() {
        Person person = new Person(PERSON_WEIGHT, PERSON_HEIGHT);

        ImcResult result = imcService.calculateImc(person);

        assertEquals(EXPECTED_IMC, result.imc());
        assertEquals(EXPECTED_CLASSIFICATION, result.classification());
    }

    @Test
    void shouldThrowExceptionWhenPersonIsNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> imcService.calculateImc(null));

        assertEquals(EXPECTED_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenWeightIsNull() {
        Person person = new Person(null, PERSON_HEIGHT);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> imcService.calculateImc(person));

        assertEquals(EXPECTED_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenHeightIsNull() {
        Person person = new Person(PERSON_WEIGHT, null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> imcService.calculateImc(person));

        assertEquals(EXPECTED_ERROR_MESSAGE, exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "17.0, UNDERWEIGHT",
            "18.4, UNDERWEIGHT",
            "18.5, NORMAL_WEIGHT",
            "24.9, NORMAL_WEIGHT",
            "25.0, OVERWEIGHT",
            "29.9, OVERWEIGHT",
            "30.0, OBESITY_GRADE_I",
            "34.9, OBESITY_GRADE_I",
            "35.0, OBESITY_GRADE_II",
            "39.9, OBESITY_GRADE_II",
            "40.0, OBESITY_GRADE_III",
            "45.0, OBESITY_GRADE_III"
    })
    void shouldClassifyImcCorrectly(double imc, Classification expectedClassification) {
        Classification classification = imcService.classifyImc(imc);

        assertEquals(expectedClassification, classification);
    }
}