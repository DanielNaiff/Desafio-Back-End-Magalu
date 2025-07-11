package com.example.magalu.service;

import com.example.magalu.model.Scheduling;
import com.example.magalu.model.dtos.SchedulingRequestDTO;
import com.example.magalu.model.dtos.SchedulingResponseDTO;
import com.example.magalu.model.enums.SchedulingStatus;
import com.example.magalu.repository.SchedulingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SchedulingService {
    private final SchedulingRepository repository;

    public SchedulingResponseDTO toSchedule(SchedulingRequestDTO schedulingRequestDTO){
        Scheduling scheduling = Scheduling.builder().
                recipient(schedulingRequestDTO.recipient()).
                message(schedulingRequestDTO.message()).
                comunicationType(schedulingRequestDTO.comunicationType()).
                sentAt(schedulingRequestDTO.sentAt()).
                status(SchedulingStatus.SCHEDULED).
                createAt(LocalDateTime.now()).build();

        return toResponse(repository.save(scheduling));
    }

    public SchedulingResponseDTO findById(Long id){
        Scheduling scheduling = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Scheduling not found"));

        return toResponse(scheduling);
    }

    public void delete(Long id){
       if (!repository.existsById(id)){
           throw new EntityNotFoundException("Scheduling not found");
       }

        repository.deleteById(id);
    }

    public SchedulingResponseDTO toResponse(Scheduling scheduling){
        return new SchedulingResponseDTO(scheduling.getId(),
                scheduling.getRecipient(),
                scheduling.getMessage(),
                scheduling.getComunicationType(),
                scheduling.getSentAt(),
                scheduling.getStatus(),
                scheduling.getCreateAt());
    }
}
