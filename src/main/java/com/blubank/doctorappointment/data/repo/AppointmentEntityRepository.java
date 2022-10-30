package com.blubank.doctorappointment.data.repo;

import com.blubank.doctorappointment.data.domin.entity.AppointmentEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AppointmentEntityRepository extends PagingAndSortingRepository<AppointmentEntity, String> {
}