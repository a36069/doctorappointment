package com.blubank.doctorappointment.data.domain.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "appointment_tbl")
public class AppointmentEntity implements Serializable {
	@Id
	@GeneratedValue(generator = "uuid-system")
	@GenericGenerator(name = "uuid-system", strategy = "uuid")
	private String id;
	private boolean isReserved;
	private String dateStart;
	private String dateEnd;
	@OneToOne
	@JoinColumn(name = "patient_id")
	private PatientEntity patient;

	@CreatedDate
	@Column(name = "created_at")
	private String createdAt;

}