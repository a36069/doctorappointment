package com.blubank.doctorappointment.data.repo;

import com.blubank.doctorappointment.data.domain.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentEntityRepository extends JpaRepository<AppointmentEntity, Long> {

    List<AppointmentEntity> findByReservedIsAndDateStartStartsWith(boolean isReserved,String dateStart);

}