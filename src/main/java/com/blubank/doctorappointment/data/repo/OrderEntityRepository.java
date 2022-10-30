package com.blubank.doctorappointment.data.repo;

import com.blubank.doctorappointment.data.domin.entity.OrderEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderEntityRepository extends PagingAndSortingRepository<OrderEntity, String> {
}