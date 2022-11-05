package com.blubank.doctorappointment.web;

import com.blubank.doctorappointment.data.domain.entity.PatientEntity;
import com.blubank.doctorappointment.data.service.PatientEntityService;
import com.blubank.doctorappointment.web.dto.StatusDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;
import java.util.stream.StreamSupport;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PatientControllerTest {

    private static final String PATIENTS_URL = "/patients";
    private static final Long PATIENT_ID = 234434L;

    private static final String phone = "09118059415";
    private static final String name = "ali";
    private static final PatientEntity patient = new PatientEntity(PATIENT_ID, name, phone);

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private PatientEntityService patientService;

    @Test
    void indexNormalize() {
        when(patientService.findByPhone(phone)).thenReturn(Arrays.asList(patient, patient, patient));

        ResponseEntity<StatusDto<List<PatientEntity>>> response = restTemplate.exchange(PATIENTS_URL + "?phone=" + phone, HttpMethod.GET, null,
                new ParameterizedTypeReference<StatusDto<List<PatientEntity>>>() {
                });

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(StreamSupport.stream(response.getBody().getData().spliterator(), false).toList().size(), is(3));
    }

    @Test
    void showNormalize() {
        when(patientService.findById(PATIENT_ID)).thenReturn(Optional.of(patient));
        ResponseEntity<StatusDto<PatientEntity>> response = restTemplate.exchange(PATIENTS_URL + "/" + PATIENT_ID, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().getData().getId(), is(PATIENT_ID));
        assertThat(response.getBody().getData().getPhone(), is(phone));
        assertThat(response.getBody().getData().getName(), is(name));
    }
}