package com.example.AppointmentService.Service;

import com.example.AppointmentService.Enums.AppointmentStatus;
import com.example.AppointmentService.Mapper.DoctorMapping;
import com.example.AppointmentService.Model.Appointment;
import com.example.AppointmentService.Model.Doctor;
import com.example.AppointmentService.Model.Slot;
import com.example.AppointmentService.Repository.AppointmentRepository;
import com.example.AppointmentService.Repository.SlotRepository;
import com.example.AppointmentService.Repository.SpecializationRepo;
import com.example.AppointmentService.Repository.DoctorRepository;
import com.example.AppointmentService.dto.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private SpecializationRepo specializationRepo;

    public doctorDto storeDoctorDetails(String loginId,String name, doctorDto doctordto) {
        UUID uuid = UUID.fromString(loginId);
        if(doctorRepository.existsByLoginId(uuid)) {
            throw new RuntimeException("Doctor already exists");
        }
        Doctor doctor= doctorRepository.save(DoctorMapping.DoctorDtoToDoctor(loginId,name,doctordto));
        System.out.println(specializationRepo.findByDoctorId(doctor.getId()));
        return DoctorMapping.DoctorToDoctorDto(doctor);
    }

    @Transactional
    public void deleteDoctor(String loginId) {
        UUID loginid = UUID.fromString(loginId);

        if(!doctorRepository.existsByLoginId(loginid)) {
            throw new RuntimeException("Doctor not found Error");
        }
        doctorRepository.deleteByloginId(loginid);
    }


    public List<DoctorNameResponse> getBySpec(String specialization) {

        List<DoctorNameResponse> specs = specializationRepo.findByname(specialization).stream()
                .map(s->{
                    DoctorNameResponse nameResponse = new DoctorNameResponse();

                    nameResponse.setName(s.getDoctor().getName());
                    return nameResponse;
                }).collect(Collectors.toList());

        return specs;
    }

    public List<DoctorSlotCreation> createSlots(String loginId, DoctorSlotCreation doctorSlotCreation) {
        UUID loginid = UUID.fromString(loginId);
        Doctor doctor = doctorRepository.findByLoginId(loginid);

        if(doctor == null) {
            throw new RuntimeException("Doctor did not exists" + loginId);
        }


        List<Slot> slots = new ArrayList<>();

        LocalDateTime from  = LocalDateTime.parse(doctorSlotCreation.getStartTime());
        LocalDateTime To = LocalDateTime.parse(doctorSlotCreation.getEndTime());


        while(from.isBefore(To)) {

            LocalDateTime startTime = from;
            LocalDateTime endTime = from.plusMinutes(30);

            List<Slot> checkOverlap = slotRepository.findOverlap(doctor,startTime,endTime);

            if(checkOverlap.isEmpty()) {
                Slot slot = new Slot();
                slot.setDoctor(doctor);
                slot.setBookingStatus(false);
                slot.setStartTime(startTime);
                slot.setEndTime(endTime);
                slots.add(slot);
            }
            from = from.plusMinutes(30);
        }
        //if there is no slot generation
        if(slots.isEmpty()) {
            throw new RuntimeException("All the provided time slots are already taken.");
        }

        //If already slot exist just update with existing slot
        List<Slot> existingSlot = doctor.getSlots();
        if(existingSlot == null) {
            existingSlot = new ArrayList<>();
        }
        existingSlot.addAll(slots);
        doctor.setSlots(existingSlot);

        doctorRepository.save(doctor);

        List<DoctorSlotCreation> allSlots = DoctorMapping.slotToDoctorSlotCreation(slots);

        return allSlots;
    }

    public List<AvailableSlots> returnAllSlots(String doctorName) {
        Doctor doctor = doctorRepository.findByname(doctorName);
        if(doctor == null) {
            throw new RuntimeException("Doctor did not exists");
        }

        return DoctorMapping.doctorToAvailableSlots(doctor);
    }

    @Transactional
    public AppointmentResponse bookAppointment(AvailableSlots availableSlots, String patientId) {

        UUID Doctoruuid = UUID.fromString(availableSlots.getDoctorId());
        Doctor doctor = doctorRepository.findById(Doctoruuid)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        UUID Slotuuid = UUID.fromString(availableSlots.getSlotId());
        Slot slot = slotRepository.findById(Slotuuid)
                .orElseThrow(() -> new RuntimeException("slot did not found"));

        UUID PatientUUID = UUID.fromString(patientId);

        PatientBookingConflict patientBookingConflict = appointmentRepository.findbypatientIdAndstatus(PatientUUID,AppointmentStatus.CONFIRMED,LocalDateTime.parse(availableSlots.getStartingTime()),LocalDateTime.parse(availableSlots.getEndingTime()));


        if(patientBookingConflict != null) {
            throw new RuntimeException("You already have appointment in this time " + patientBookingConflict.getStartTime() +
                    "to " + patientBookingConflict.getEndTime()+ " " + "has conflict");
        }


        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setSlot(slot);
        appointment.setPatientId(UUID.fromString(patientId));
        appointment.setStartingTime(LocalDateTime.parse(availableSlots.getStartingTime()));
        appointment.setEndingTime(LocalDateTime.parse(availableSlots.getEndingTime()));
        appointment.setStatus(AppointmentStatus.CONFIRMED);
        appointment.setBookingTime(LocalDateTime.now());

        slot.setBookingStatus(true);
        slotRepository.save(slot);

        Appointment saved = appointmentRepository.save(appointment);

        //Email and sms to patient

        return DoctorMapping.returnAppointment(saved);
    }

    public List<AppointmentBooked> GetAllBookedAppointmentByPatient(String patientId) {

        return List.of();
    }

    @Transactional
    public CancelAppointmentResponse CancelAppointment(String patientId) {

        return null;
    }


}