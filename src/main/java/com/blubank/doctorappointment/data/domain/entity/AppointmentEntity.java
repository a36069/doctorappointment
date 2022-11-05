package com.blubank.doctorappointment.data.domain.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name = "appointment_tbl")
@EntityListeners(AuditingEntityListener.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppointmentEntity extends RepresentationModel<AppointmentEntity> implements Serializable {
    @Id
    @SequenceGenerator(name = "SEQ_GEN", sequenceName = "SEQ_APPOINTMENT", allocationSize = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN")
    private Long id;
    private boolean reserved;
    @Column(name = "date_start", nullable = false, unique = true)
    private String dateStart;
    @Column(name = "date_end", nullable = false, unique = true)
    private String dateEnd;
    @Column(name = "patient_id")
    private Long patientId;
    @CreatedDate
    @Column(name = "created_at")
    @JsonProperty("created_at")
    private String createdAt;
    @LastModifiedDate
    @Column(name = "update_at")
    @JsonProperty("update_at")
    private String updateAt;

    public AppointmentEntity(String dateStart, String dateEnd) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public AppointmentEntity(Long id,String dateStart, String dateEnd) {
        this.id = id;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }
}