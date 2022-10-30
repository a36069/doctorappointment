package com.blubank.doctorappointment.data.repo;

import com.blubank.doctorappointment.data.domin.entity.PatientEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PatientEntityRepository extends PagingAndSortingRepository<PatientEntity, String> {
}