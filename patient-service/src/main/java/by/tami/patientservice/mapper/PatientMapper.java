package by.tami.patientservice.mapper;

import by.tami.patientservice.dto.PatientRequestDto;
import by.tami.patientservice.dto.PatientResponseDto;
import by.tami.patientservice.model.Patient;

import java.time.LocalDate;

public class PatientMapper {
    public static PatientResponseDto toDto(Patient patient) {
        PatientResponseDto dto = new PatientResponseDto();

        dto.setId(String.valueOf(patient.getId()));
        dto.setName(patient.getName());
        dto.setAddress(patient.getAddress());
        dto.setEmail(patient.getEmail());
        dto.setDateOfBirth(patient.getDateOfBirth().toString());

        return  dto;
    }

    public static Patient toEntity(PatientRequestDto dto) {
        var patient = new Patient();
        patient.setName(dto.getName());
        patient.setAddress(dto.getAddress());
        patient.setEmail(dto.getEmail());
        patient.setDateOfBirth(LocalDate.parse(dto.getDateOfBirth()));
        patient.setRegisteredDate(LocalDate.parse(dto.getRegisteredDate()));
        return patient;
    }
}
