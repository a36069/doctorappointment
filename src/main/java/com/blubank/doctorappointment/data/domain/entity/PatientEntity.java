package com.blubank.doctorappointment.data.domain.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "patient_tbl")
public class PatientEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid-system")
    @GenericGenerator(name = "uuid-system", strategy = "uuid")
    private String id;
    private String name;
    private String phone;
    @OneToMany(mappedBy = "patient")
    private List<OrderEntity> order;
    @CreatedDate
    @Column(name = "created_at")
    private String createdAt;

}