package com.blubank.doctorappointment.data.service;

import com.blubank.doctorappointment.data.domain.entity.AppointmentEntity;
import com.blubank.doctorappointment.data.repo.AppointmentEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class AppointmentEntityService {
    private AppointmentEntityRepository entityRepository;

    @Autowired
    public AppointmentEntityService(AppointmentEntityRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public AppointmentEntity saveOrUpdate(AppointmentEntity appointment) {
        return entityRepository.save(appointment);
    }

    public Iterable<AppointmentEntity> insertAllDate(List<AppointmentEntity> appointments) {
        return entityRepository.saveAll(appointments);
    }

    public List<AppointmentEntity> selectAll() {
        return entityRepository.findAll();
    }

    public Optional<AppointmentEntity> findById(Long id) {
        return entityRepository.findById(id);
    }

    public List<AppointmentEntity> selectAll(boolean isReserved, String dateStart) {
        return entityRepository.findByReservedIsAndDateStartStartsWith(isReserved, dateStart);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void delete(AppointmentEntity appointment) {
        entityRepository.delete(appointment);
    }
}
