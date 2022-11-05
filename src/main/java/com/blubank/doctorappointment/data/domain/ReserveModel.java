package com.blubank.doctorappointment.data.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@SuperBuilder
@Jacksonized
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReserveModel {
    @JsonProperty("appointment_id")
    @NotEmpty(message = "appointment_id must not be empty")
    private Long appointmentId;
    @NotEmpty(message = "name must not be empty")
    private String name;
    @NotEmpty(message = "phone must not be empty")
    private String phone;
}
