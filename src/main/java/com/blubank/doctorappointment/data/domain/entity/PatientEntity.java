package com.blubank.doctorappointment.data.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "patient_tbl")
@EntityListeners(AuditingEntityListener.class)
public class PatientEntity implements Serializable {

    @Id
    @SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_PATIENT", allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
    private Long id;
    private String name;
    private String phone;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "patient")
    @Fetch(FetchMode.JOIN)
    private List<OrderEntity> order = new java.util.ArrayList<>();

    @CreatedDate
    @Column(name = "created_at")
    @JsonProperty("created_at")
    private String createdAt;
    @LastModifiedDate
    @Column(name = "update_at")
    @JsonProperty("update_at")
    private String updateAt;

    public PatientEntity() {
    }
    public PatientEntity(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public PatientEntity(Long id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }
}