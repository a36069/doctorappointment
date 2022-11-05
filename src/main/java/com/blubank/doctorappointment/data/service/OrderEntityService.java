package com.blubank.doctorappointment.data.service;

import com.blubank.doctorappointment.data.domain.ReserveModel;
import com.blubank.doctorappointment.data.domain.entity.AppointmentEntity;
import com.blubank.doctorappointment.data.domain.entity.OrderEntity;
import com.blubank.doctorappointment.data.domain.entity.PatientEntity;
import com.blubank.doctorappointment.data.repo.AppointmentEntityRepository;
import com.blubank.doctorappointment.data.repo.OrderEntityRepository;
import com.blubank.doctorappointment.data.repo.PatientEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class OrderEntityService {
    private OrderEntityRepository entityRepository;
    private PatientEntityService patientService;
    private AppointmentEntityService appointmentService;

    @Autowired
    public OrderEntityService(OrderEntityRepository entityRepository, PatientEntityService patientService, AppointmentEntityService appointmentService) {
        this.entityRepository = entityRepository;
        this.patientService = patientService;
        this.appointmentService = appointmentService;
    }


    @Transactional(isolation = Isolation.SERIALIZABLE)
    public OrderEntity save(OrderEntity orderEntity) {
        return entityRepository.save(orderEntity);
    }


    public OrderEntity save(AppointmentEntity appointment, ReserveModel reserve) {
        PatientEntity patient = patientService.saveIfNotExist(new PatientEntity(reserve.getName(), reserve.getPhone()));
        OrderEntity order = save(new OrderEntity(appointment, patient));

        appointment.setReserved(true);
        appointment.setPatientId(patient.getId());
        appointmentService.saveOrUpdate(appointment);


        return order;
    }
}
