package com.example.magalu;

import com.example.magalu.controller.SchedulingController;
import com.example.magalu.model.dtos.SchedulingRequestDTO;
import com.example.magalu.model.dtos.SchedulingResponseDTO;
import com.example.magalu.model.enums.ComunicationType;
import com.example.magalu.model.enums.SchedulingStatus;
import com.example.magalu.service.SchedulingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SchedulingController.class)
public class SchedulingControllerTest {
    //Permite simular requisicoes http
    @Autowired
    private MockMvc mockMvc;

    // Object Mapper -> Converte objetos em JSON e vice-versa
    @Autowired
    private ObjectMapper objectMapper;

    //Criar um mock do service usado dentro do controller
    //evitar chamadas reais ao banco de dados ou regra de negocio
    //
    //Esse mock vai ser injetado no controller pelo spring, simulando o comportamento esperado\
    //para testes
    @MockitoBean
    private SchedulingService service;

    @Test
    void mustSchedullingComunication() throws Exception{
        SchedulingRequestDTO requestDTO = new SchedulingRequestDTO(
                "client@gmail.com",
                "mensagem de teste",
                ComunicationType.EMAIL,
                LocalDateTime.now().plusMinutes(1));

        SchedulingResponseDTO responseDTO = new SchedulingResponseDTO(1L,
                requestDTO.recipient(),
                requestDTO.message(),
                requestDTO.comunicationType(),
                requestDTO.sentAt(),
                SchedulingStatus.SCHEDULED,
                LocalDateTime.now()
                );

        when(service.toSchedule(any())).thenReturn(responseDTO);

        mockMvc.perform(post("/api/agendamentos")
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.recipient").value("client@gmail.com"));
    }

    @Test
    void mustSearchSchedulleById() throws Exception{
        Long id = 1L;
        SchedulingResponseDTO responseDTO = new SchedulingResponseDTO(1L,
                "client@gmail.com",
                "mensagem de teste",
                ComunicationType.SMS,
                LocalDateTime.now().plusMinutes(1),
                SchedulingStatus.SCHEDULED,
                LocalDateTime.now()
        );

        when(service.findById(id)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/agendamentos/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.comunicationType").value("SMS"));
    }

    @Test
    void mustRemoveSchedule() throws Exception {
        Long id = 1L;

        doNothing().when(service).delete(id);

        mockMvc.perform(delete("/api/agendamentos/{id}", id))
                .andExpect(status().isNoContent());
    }

}
