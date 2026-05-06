package br.com.lucasaraujo.schedulingapi.dto;

import br.com.lucasaraujo.schedulingapi.model.Appointment;
import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentResponse(UUID id, String clientName, String serviceName, LocalDateTime appointmentDate, String status) {
    public static AppointmentResponse fromEntity(Appointment appointment) {
        return new AppointmentResponse(
                appointment.getId(),
                appointment.getClient().getName(),
                appointment.getServiceName(),
                appointment.getAppointmentDate(),
                appointment.getStatus().name()
        );
    }
}
