package br.com.lucasaraujo.schedulingapi.repository;

import br.com.lucasaraujo.schedulingapi.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    // Checks if there is already an appointment sheduled for that exact time.
    boolean existsByAppointmentDate(LocalDateTime appointmentDate);

    // Search the history of a specific customer using pagination.
    Page<Appointment> findByClientId(UUID clientId, Pageable pageable);
}
