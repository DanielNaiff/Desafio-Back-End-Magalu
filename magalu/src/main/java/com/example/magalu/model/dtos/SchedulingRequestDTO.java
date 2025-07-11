package com.example.magalu.model.dtos;

import com.example.magalu.model.enums.ComunicationType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record SchedulingRequestDTO(
        @NotNull(message = "The recipient is required.")
        String recipient,

        @NotNull(message = "The message is required.")
        String message,

        @NotNull(message = "The comunication type is required.")
        ComunicationType comunicationType,

        @NotNull(message = "The send date is required.")
        @Future(message = "The send date must be in the future.")
        LocalDateTime sentAt
) {

}
