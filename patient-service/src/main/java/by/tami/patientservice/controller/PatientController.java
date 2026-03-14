package by.tami.patientservice.controller;

import by.tami.patientservice.dto.PatientRequestDto;
import by.tami.patientservice.dto.PatientResponseDto;
import by.tami.patientservice.dto.validators.CreatePatientValidationGroup;
import by.tami.patientservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/patients")
@Tag(
        name = "Patient",
        description = "API for managing patients."
)
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    @Operation(summary = "GET patients")
    public ResponseEntity<List<PatientResponseDto>> getPatients() {
        return ResponseEntity.ok(patientService.getPatients());
    }

    @PostMapping
    @Operation(summary = "CREATE patient")
    public ResponseEntity<PatientResponseDto> createPatient(
            @Validated({
                    CreatePatientValidationGroup.class,
                    Default.class
            }) @RequestBody PatientRequestDto patient) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(patientService.createPatient(patient));
    }

    @PutMapping("/{id}")
    @Operation(summary = "UPDATE patient")
    public ResponseEntity<PatientResponseDto> updatePatient(
            @PathVariable UUID id,
            @Validated({Default.class}) @RequestBody PatientRequestDto patient
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(patientService.updatePatient(id, patient));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "DELETE patient")
    public ResponseEntity<Void> deletePatient(@PathVariable UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
