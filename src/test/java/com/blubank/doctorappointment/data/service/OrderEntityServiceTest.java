package com.blubank.doctorappointment.data.service;

import com.blubank.doctorappointment.data.domain.entity.AppointmentEntity;
import com.blubank.doctorappointment.data.domain.entity.OrderEntity;
import com.blubank.doctorappointment.data.domain.entity.PatientEntity;
import com.blubank.doctorappointment.data.repo.OrderEntityRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Transactional
class OrderEntityServiceTest {
    private static final Long APPOINTMENT_ID = 234434L;
    private static final Long PATIENT_ID = 234420L;

    private static final String dateStart = "2022/12/01 10:00:00";
    private static final String dateEnd = "2022/12/01 10:30:00";
    private static final String name = "ali";
    private static final String phone = "09118059415";
    @InjectMocks
    private OrderEntityService service;
    @Mock
    private OrderEntityRepository orderRepository;

    private static final PatientEntity patient = new PatientEntity(PATIENT_ID,name,phone);
    private static final AppointmentEntity appointment = new AppointmentEntity(APPOINTMENT_ID, dateStart, dateEnd);

    @Test
    void save() {
        ArgumentCaptor<OrderEntity> orderCaptor = ArgumentCaptor.forClass(OrderEntity.class);

        service.save(new OrderEntity(appointment,patient));

        verify(orderRepository).save(orderCaptor.capture());
        assertThat(orderCaptor.getValue().getAppointment(), is(appointment));
        assertThat(orderCaptor.getValue().getPatient(), is(patient));
    }


}