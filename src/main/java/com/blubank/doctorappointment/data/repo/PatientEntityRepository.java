package com.blubank.doctorappointment.data.repo;

import com.blubank.doctorappointment.data.domain.entity.PatientEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientEntityRepository extends PagingAndSortingRepository<PatientEntity, Long> {
    List<PatientEntity> findByPhone(String phone);
    Optional<PatientEntity> findByPhoneAndName(String phone,String name);
}