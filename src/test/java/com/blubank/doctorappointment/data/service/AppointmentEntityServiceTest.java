package com.blubank.doctorappointment.data.service;

import com.blubank.doctorappointment.data.domain.entity.AppointmentEntity;
import com.blubank.doctorappointment.data.repo.AppointmentEntityRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
class AppointmentEntityServiceTest {
    private static final Long APPOINTMENT_ID = 234434L;

    private static final String dateStart = "2022/12/01 10:00:00";
    private static final String dateEnd = "2022/12/01 10:30:00";

    @InjectMocks
    private AppointmentEntityService service;
    @Mock
    private AppointmentEntityRepository appointmentRepository;


    AppointmentEntity appointment = new AppointmentEntity(APPOINTMENT_ID, dateStart, dateEnd);

    @Test
    void saveOrUpdate() {
        ArgumentCaptor<AppointmentEntity> appointmentCaptor = ArgumentCaptor.forClass(AppointmentEntity.class);

        service.saveOrUpdate(appointment);

        verify(appointmentRepository).save(appointmentCaptor.capture());
        assertThat(appointmentCaptor.getValue().getDateStart(), is(dateStart));
        assertThat(appointmentCaptor.getValue().getId(), is(APPOINTMENT_ID));
    }

    @Test
    void selectAll() {
        when(appointmentRepository.findAll()).thenReturn(Arrays.asList(appointment,appointment));

        assertThat(service.selectAll().get(0), is(appointment));
    }

    @Test
    void findById() {
        when(appointmentRepository.findById(APPOINTMENT_ID)).thenReturn(Optional.of(appointment));

        assertThat(service.findById(APPOINTMENT_ID).get(), is(appointment));
    }

    @Test
    void selectAllIsNotReserved() {
        when(appointmentRepository.findByReservedIsAndDateStartStartsWith(false,dateStart)).thenReturn(Arrays.asList(appointment,appointment));

        assertThat(service.selectAll(false,dateStart).get(0), is(appointment));
    }

    @Test
    void delete() {
        service.delete(appointment);

        verify(appointmentRepository).delete(any(AppointmentEntity.class));
    }
}