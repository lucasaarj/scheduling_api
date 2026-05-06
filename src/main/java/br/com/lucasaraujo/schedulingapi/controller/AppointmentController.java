package br.com.lucasaraujo.schedulingapi.controller;

import br.com.lucasaraujo.schedulingapi.dto.AppointmentRequest;
import br.com.lucasaraujo.schedulingapi.dto.AppointmentResponse;
import br.com.lucasaraujo.schedulingapi.model.Appointment;
import br.com.lucasaraujo.schedulingapi.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.UUID;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentResponse> schedule(@RequestBody AppointmentRequest request) {
        Appointment appointment = appointmentService.scheduleAppointment(
                request.clientId(),
                request.serviceName(),
                request.appointmentDate()
            );
            return ResponseEntity.ok(AppointmentResponse.fromEntity(appointment));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<Page<AppointmentResponse>> getClientAppointments(
            @PathVariable UUID clientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        var appointments = appointmentService.getClientAppointments(clientId, page, size);


        Page<AppointmentResponse> response = appointments.map(AppointmentResponse::fromEntity);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancel(@PathVariable UUID id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.noContent().build();
    }
}
