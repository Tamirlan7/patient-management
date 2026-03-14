package by.tami.patientservice.service;

import billing.BillingServiceGrpc;
import by.tami.patientservice.dto.PatientRequestDto;
import by.tami.patientservice.dto.PatientResponseDto;
import by.tami.patientservice.exception.BadRequestException;
import by.tami.patientservice.exception.NotFoundException;
import by.tami.patientservice.grpc.BillingServiceGrpcClient;
import by.tami.patientservice.kafka.KafkaProducer;
import by.tami.patientservice.mapper.PatientMapper;
import by.tami.patientservice.model.Patient;
import by.tami.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    public PatientService(
            PatientRepository patientRepository,
            BillingServiceGrpcClient billingServiceGrpcClient,
            KafkaProducer kafkaProducer) {
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
    }

    public List<PatientResponseDto> getPatients() {
        List<Patient> patients = patientRepository.findAll();
        return patients.stream().map(PatientMapper::toDto).toList();
    }

    public PatientResponseDto createPatient(PatientRequestDto patientDto) {
        if (patientRepository.existsByEmail(patientDto.getEmail())) {
            throw new BadRequestException("A patient with this email already exists " + patientDto.getEmail());
        }

        Patient patient = patientRepository.save(PatientMapper.toEntity(patientDto));
        billingServiceGrpcClient.createBillingAccount(patient.getId().toString(), patient.getName(), patient.getEmail());
        kafkaProducer.sendEvent(patient);
        return PatientMapper.toDto(patient);
    }

    public PatientResponseDto updatePatient(UUID id, PatientRequestDto patientDto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("patient with this id not found " + id));

        if (!Objects.isNull(patientDto.getName())) {
            patient.setName(patientDto.getName());
        }
        if (!Objects.isNull(patientDto.getEmail())) {
            if (patientRepository.existsByEmail(patientDto.getEmail())) {
                throw new BadRequestException("A patient with this email already exists " + patientDto.getEmail());
            }
            patient.setEmail(patientDto.getEmail());
        }
        if (!Objects.isNull(patientDto.getAddress())) {
            patient.setAddress(patientDto.getAddress());
        }
        if (!Objects.isNull(patientDto.getDateOfBirth())) {
            patient.setDateOfBirth(LocalDate.parse(patientDto.getDateOfBirth()));
        }

        patient = patientRepository.save(PatientMapper.toEntity(patientDto));
        return PatientMapper.toDto(patient);
    }

    public void deletePatient(UUID id) {
        patientRepository.deleteById(id);
    }
}
