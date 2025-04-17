package br.edu.utfpr.IMC_api.controller;

import br.edu.utfpr.IMC_api.dto.ImcResult;
import br.edu.utfpr.IMC_api.model.Person;
import br.edu.utfpr.IMC_api.service.ImcService;
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

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ImcService imcService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCalculateImc() throws Exception {
        Person person = new Person(70.0, 1.75);
        ImcResult imcResult = new ImcResult(22.86, "Peso normal");

        when(imcService.calculateImc(any(Person.class))).thenReturn(imcResult);

        mockMvc.perform(post("/api/imc/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.imc").value(22.86))
                .andExpect(jsonPath("$.classification").value("Peso normal"));
    }

    @Test
    void shouldReturnBadRequestWhenWeightIsNull() throws Exception {
        Person person = new Person(null, 1.75);

        mockMvc.perform(post("/api/imc/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenHeightIsNull() throws Exception {
        Person person = new Person(70.0, null);

        mockMvc.perform(post("/api/imc/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isBadRequest());
    }
}