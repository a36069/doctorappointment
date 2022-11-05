package com.blubank.doctorappointment.data.repo;

import com.blubank.doctorappointment.data.domain.entity.OrderEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderEntityRepository extends PagingAndSortingRepository<OrderEntity, Long> {
}