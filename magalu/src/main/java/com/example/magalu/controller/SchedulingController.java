package com.example.magalu.controller;

import com.example.magalu.model.dtos.SchedulingRequestDTO;
import com.example.magalu.model.dtos.SchedulingResponseDTO;
import com.example.magalu.service.SchedulingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agendamentos")
@RequiredArgsConstructor
public class SchedulingController {
    private final SchedulingService service;

    @PostMapping
    public ResponseEntity<SchedulingResponseDTO> toSchedule(SchedulingRequestDTO requestDTO){
        SchedulingResponseDTO responseDTO = service.toSchedule(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchedulingResponseDTO> findById(@PathVariable Long id){
        SchedulingResponseDTO responseDTO = service.findById(id);

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SchedulingResponseDTO> delete(@PathVariable Long id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
