package com.blubank.doctorappointment.web;

import com.blubank.doctorappointment.data.domain.DateModel;
import com.blubank.doctorappointment.data.domain.ReserveModel;
import com.blubank.doctorappointment.data.domain.entity.AppointmentEntity;
import com.blubank.doctorappointment.data.domain.entity.OrderEntity;
import com.blubank.doctorappointment.data.domain.entity.PatientEntity;
import com.blubank.doctorappointment.data.service.AppointmentEntityService;
import com.blubank.doctorappointment.data.service.OrderEntityService;
import com.blubank.doctorappointment.data.service.PatientEntityService;
import com.blubank.doctorappointment.tools.DateHelper;
import com.blubank.doctorappointment.web.dto.StatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    private AppointmentEntityService appointmentService;
    private OrderEntityService orderEntityService;

    @Autowired
    public AppointmentController(AppointmentEntityService appointmentService, OrderEntityService orderEntityService) {
        this.appointmentService = appointmentService;
        this.orderEntityService = orderEntityService;
    }


    @PostMapping
    public ResponseEntity<StatusDto<Iterable<AppointmentEntity>>> insert(@RequestBody DateModel date) {
        Iterable<AppointmentEntity> resVal = appointmentService.insertAllDate(DateHelper.setIntervalsDate(date.getDateStart(), date.getDateEnd(), 30, Calendar.MINUTE));
        return new ResponseEntity<>(new StatusDto<>(HttpStatus.CREATED.value(), "created success", "", resVal), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<StatusDto<Iterable<AppointmentEntity>>> index(@RequestParam(required = false, name = "is_not_reserved") Boolean isNotReserved,
                                                                        @RequestParam(required = false, name = "date_start") String dateStart) throws Throwable {
        List<AppointmentEntity> resVal = null;
        if (isNotReserved != null && dateStart != null)
            resVal = appointmentService.selectAll(!isNotReserved, dateStart);
        else
            resVal = appointmentService.selectAll();

        for (final AppointmentEntity appointment : resVal) {
            if (appointment.getPatientId() != null) {
                Link patientLink = linkTo(methodOn(PatientController.class)
                        .show(appointment.getPatientId())).withRel("patient");
                appointment.add(patientLink);
            }
        }

        return new ResponseEntity<>(new StatusDto<>(HttpStatus.OK.value(), "request success", "", resVal), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<StatusDto<Object>> delete(@PathVariable Long id) throws Throwable {
        AppointmentEntity appointment = appointmentService.findById(id).orElseThrow((Supplier<Throwable>) () -> new NoSuchElementException("not found"));
        if (appointment.isReserved())
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();

        appointmentService.delete(appointment);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/reserve")
    public ResponseEntity<StatusDto<Object>> reserve(@RequestBody ReserveModel reserve) throws Throwable {
        AppointmentEntity appointment = appointmentService.findById(reserve.getAppointmentId()).orElseThrow((Supplier<Throwable>) () -> new NoSuchElementException("not found"));

        if (appointment.isReserved())
            return new ResponseEntity<>(new StatusDto<>(HttpStatus.NOT_ACCEPTABLE.value(),
                    "this appointment is reserved", ""),
                    HttpStatus.NOT_ACCEPTABLE);

        OrderEntity orderEntity = orderEntityService.save(appointment, reserve);

        return new ResponseEntity<>(
                new StatusDto<>(HttpStatus.CREATED.value(), "created success", "", orderEntity)
                , HttpStatus.CREATED);
    }

}
