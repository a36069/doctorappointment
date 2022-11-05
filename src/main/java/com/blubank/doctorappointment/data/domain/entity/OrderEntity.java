package com.blubank.doctorappointment.data.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "order_tbl")
@EntityListeners(AuditingEntityListener.class)
public class OrderEntity implements Serializable {
    @Id
    @SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_ORDER", allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
    private Long id;
    @NotNull
    @OneToOne
    @JoinColumn(name = "appointment_id")
    private AppointmentEntity appointment;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonIgnore
    private PatientEntity patient;
    @CreatedDate
    @Column(name = "created_at")
    @JsonProperty("created_at")
    private String createdAt;


    public OrderEntity() {
    }

    public OrderEntity(AppointmentEntity appointment, PatientEntity patient) {
        this.appointment = appointment;
        this.patient = patient;
    }
}