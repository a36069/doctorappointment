package com.blubank.doctorappointment.data.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;

@SuperBuilder
@Jacksonized
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateModel {
    @JsonProperty("date_start")
    private String dateStart;
    @JsonProperty("date_end")
    private String dateEnd;
}
