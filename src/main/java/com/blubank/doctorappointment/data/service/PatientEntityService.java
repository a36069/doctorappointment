package com.blubank.doctorappointment.data.service;

import com.blubank.doctorappointment.data.domain.entity.PatientEntity;
import com.blubank.doctorappointment.data.domain.entity.PatientEntity;
import com.blubank.doctorappointment.data.repo.PatientEntityRepository;
import com.blubank.doctorappointment.data.repo.PatientEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PatientEntityService {
    private PatientEntityRepository entityRepository;

    @Autowired
    public PatientEntityService(PatientEntityRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    public Optional<PatientEntity> findById(Long id) {
        return entityRepository.findById(id);
    }

    public List<PatientEntity> findByPhone(String phone) {
        return entityRepository.findByPhone(phone);
    }


    @Transactional
    public PatientEntity saveIfNotExist(PatientEntity patient) {
        return entityRepository.findByPhoneAndName(patient.getPhone(), patient.getName())
                .orElseGet(() -> entityRepository.save(patient));
    }

}
