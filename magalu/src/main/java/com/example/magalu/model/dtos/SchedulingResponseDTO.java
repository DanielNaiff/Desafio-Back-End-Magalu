package com.example.magalu.model.dtos;

import com.example.magalu.model.enums.ComunicationType;
import com.example.magalu.model.enums.SchedulingStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record SchedulingResponseDTO(
        Long id,
        String recipient,
        String message,
        ComunicationType comunicationType,
        LocalDateTime sentAt,
        SchedulingStatus schedulingStatus,
        LocalDateTime createdAt
) {
}
