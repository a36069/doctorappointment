package com.blubank.doctorappointment.data.service;

import com.blubank.doctorappointment.data.domain.entity.AppointmentEntity;
import com.blubank.doctorappointment.data.domain.entity.PatientEntity;
import com.blubank.doctorappointment.data.repo.PatientEntityRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class PatientEntityServiceTest {
    private static final Long PATIENT_ID = 234420L;
    private static final String name = "ali";
    private static final String phone = "09118059415";
    private static final PatientEntity patient = new PatientEntity(PATIENT_ID,name,phone);
    @InjectMocks
    private PatientEntityService service;
    @Mock
    private PatientEntityRepository patientRepository;

    @Test
    void findById() {
        when(patientRepository.findById(PATIENT_ID)).thenReturn(Optional.of(patient));

        assertThat(service.findById(PATIENT_ID).get(), is(patient));
    }

    @Test
    void findByPhone() {
        when(patientRepository.findByPhone(phone)).thenReturn(Arrays.asList(patient,patient));

        assertThat(service.findByPhone(phone).get(0), is(patient));
    }

    @Test
    void saveIfNotExist() {
        ArgumentCaptor<PatientEntity> patientCaptor = ArgumentCaptor.forClass(PatientEntity.class);

        service.saveIfNotExist(patient);

        verify(patientRepository).save(patientCaptor.capture());
        assertThat(patientCaptor.getValue().getName(), is(name));
        assertThat(patientCaptor.getValue().getId(), is(PATIENT_ID));
    }
}