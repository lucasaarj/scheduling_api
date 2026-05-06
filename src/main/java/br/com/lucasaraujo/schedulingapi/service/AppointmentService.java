package br.com.lucasaraujo.schedulingapi.service;

import br.com.lucasaraujo.schedulingapi.model.Appointment;
import br.com.lucasaraujo.schedulingapi.model.AppointmentStatus;
import br.com.lucasaraujo.schedulingapi.model.User;
import br.com.lucasaraujo.schedulingapi.repository.AppointmentRepository;
import br.com.lucasaraujo.schedulingapi.repository.UserRepository;
import br.com.lucasaraujo.schedulingapi.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    public Appointment scheduleAppointment(UUID clientId, String serviceName, LocalDateTime appointmentDate) {

        // 1. Business Rule: Check for scheduling conflicts.
        if (appointmentRepository.existsByAppointmentDate(appointmentDate)) {
            throw new IllegalArgumentException("This time slot is already booked.");
        }

        // 2. Check if the client exists
        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found."));

        // 3. Create and save the appointment
        Appointment appointment = new Appointment();
        appointment.setClient(client);
        appointment.setServiceName(serviceName);
        appointment.setAppointmentDate(appointmentDate);
        appointment.setStatus(AppointmentStatus.PENDING);

        return appointmentRepository.save(appointment);
    }

    public Page<Appointment> getClientHistory(UUID clientId, Pageable pageable) {
        return appointmentRepository.findByClientId(clientId, pageable);
    }

    public Page<Appointment> getClientAppointments(UUID clientId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return appointmentRepository.findByClientId(clientId, pageRequest);
    }

    public void cancelAppointment(UUID appointmentId) {
        // check if the id exists before trying
        if (!appointmentRepository.existsById(appointmentId)) {
            throw new IllegalArgumentException("Schedule not found for deletion!");
        }

        appointmentRepository.deleteById(appointmentId);
    }
}
