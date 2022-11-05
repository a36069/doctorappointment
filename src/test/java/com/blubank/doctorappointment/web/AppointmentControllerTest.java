package com.blubank.doctorappointment.web;

import com.blubank.doctorappointment.data.domain.DateModel;
import com.blubank.doctorappointment.data.domain.ReserveModel;
import com.blubank.doctorappointment.data.domain.entity.AppointmentEntity;
import com.blubank.doctorappointment.data.domain.entity.OrderEntity;
import com.blubank.doctorappointment.data.domain.entity.PatientEntity;
import com.blubank.doctorappointment.data.service.AppointmentEntityService;
import com.blubank.doctorappointment.data.service.OrderEntityService;
import com.blubank.doctorappointment.data.service.PatientEntityService;
import com.blubank.doctorappointment.tools.DateHelper;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppointmentControllerTest {
    private static final String APPOINTMENT_URL = "/appointments";
    private static final Long APPOINTMENT_ID = 234434L;

    private static final String dateStart = "2022/12/01 10:00:00";
    private static final String dateEnd = "2022/12/01 10:30:00";
    private static final String name = "ali";
    private static final String phone = "09118059415";
    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private AppointmentEntityService service;
    @MockBean
    private OrderEntityService orderEntityService;
    @MockBean
    private PatientEntityService patientService;

    AppointmentEntity appointment = new AppointmentEntity(APPOINTMENT_ID, dateStart, dateEnd);


    @Test
    public void getIndexNormalize() {

        when(service.selectAll()).thenReturn(Arrays.asList(appointment, appointment, appointment));

        ResponseEntity<StatusDto<Iterable<AppointmentEntity>>> response = restTemplate.exchange(APPOINTMENT_URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(StreamSupport.stream(response.getBody().getData().spliterator(), false).toList().size(), is(3));
    }

    @Test
    public void insertNormalize() {
        restTemplate.postForEntity(APPOINTMENT_URL, new DateModel(dateStart, dateEnd), StatusDto.class);
        verify(this.service).insertAllDate(
                DateHelper.setIntervalsDate(dateStart, dateEnd,
                        30, Calendar.MINUTE));
    }

    @Test
    public void deleteNormalize() throws Exception {
        when(service.findById(APPOINTMENT_ID)).thenReturn(Optional.of(appointment));

        restTemplate.delete(APPOINTMENT_URL + "/" + APPOINTMENT_ID );

        verify(service).delete(appointment);
    }

    @Test
    public void reserveNormalize() {
        PatientEntity patient = new PatientEntity(name, phone);
        ReserveModel reserve = new ReserveModel(APPOINTMENT_ID,name, phone);

        when(service.findById(APPOINTMENT_ID)).thenReturn(Optional.of(appointment));
        when(patientService.saveIfNotExist(patient)).thenReturn(patient);
        when(orderEntityService.save(appointment, reserve)).thenReturn(new OrderEntity(appointment, patient));

        restTemplate.postForEntity(APPOINTMENT_URL + "/reserve"
                , reserve, StatusDto.class);

        verify(orderEntityService).save(appointment,reserve);
    }
}