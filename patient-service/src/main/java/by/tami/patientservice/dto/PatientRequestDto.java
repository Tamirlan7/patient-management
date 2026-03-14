package by.tami.patientservice.dto;

import by.tami.patientservice.dto.validators.CreatePatientValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PatientRequestDto {

    @Size(max = 100, message = "Name cannot exceed 100 characters")
    @Size(min = 1, message = "Name cannot be less than 1 character")
    @NotBlank(message = "Name is required", groups = CreatePatientValidationGroup.class)
    private String name;

    @Email(message = "email should be valid")
    @NotBlank(message = "email is required", groups = CreatePatientValidationGroup.class)
    private String email;

    @NotBlank(message = "address is required", groups = CreatePatientValidationGroup.class)
    private String address;

    @NotBlank(message = "dateOfBirth is required", groups = CreatePatientValidationGroup.class)
    private String dateOfBirth;

    @NotBlank(message = "registeredDate is required", groups = CreatePatientValidationGroup.class)
    private String registeredDate;


    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
