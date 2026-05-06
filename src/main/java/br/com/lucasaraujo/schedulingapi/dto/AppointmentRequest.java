package br.com.lucasaraujo.schedulingapi.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentRequest(UUID clientId, String serviceName, LocalDateTime appointmentDate) {
}
