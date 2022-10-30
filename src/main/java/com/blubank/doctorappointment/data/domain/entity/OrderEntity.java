package com.blubank.doctorappointment.data.domin.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "order_tbl")
public class OrderEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid-system")
    @GenericGenerator(name = "uuid-system", strategy = "uuid")
    private String id;
    @NotNull
    @OneToOne
    @JoinColumn(name = "appointment_id")
    private AppointmentEntity appointment;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;
    @CreatedDate
    @Column(name = "created_at")
    private String createdAt;

}