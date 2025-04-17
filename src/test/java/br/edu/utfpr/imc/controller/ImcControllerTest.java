package br.edu.utfpr.imc.controller;

import br.edu.utfpr.imc.dto.ImcResult;
import br.edu.utfpr.imc.model.Classification;
import br.edu.utfpr.imc.model.Person;
import br.edu.utfpr.imc.service.ImcService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ImcController.class)
class ImcControllerTest {

    private static final double PERSON_WEIGHT = 70.0;
    private static final double PERSON_HEIGHT = 1.75;
    private static final double EXPECTED_IMC = 22.86;
    private static final Classification EXPECTED_CLASSIFICATION = Classification.NORMAL_WEIGHT;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ImcService imcService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCalculateImc() throws Exception {
        Person person = new Person(PERSON_WEIGHT, PERSON_HEIGHT);
        ImcResult imcResult = new ImcResult(EXPECTED_IMC, EXPECTED_CLASSIFICATION);

        when(imcService.calculateImc(any(Person.class))).thenReturn(imcResult);

        mockMvc.perform(post("/api/imc/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.imc").value(EXPECTED_IMC))
                .andExpect(jsonPath("$.classification").value(EXPECTED_CLASSIFICATION.toString()));
    }

    @Test
    void shouldReturnBadRequestWhenWeightIsNull() throws Exception {
        Person person = new Person(null, PERSON_HEIGHT);

        mockMvc.perform(post("/api/imc/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenHeightIsNull() throws Exception {
        Person person = new Person(PERSON_WEIGHT, null);

        mockMvc.perform(post("/api/imc/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isBadRequest());
    }
}