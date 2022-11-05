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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;

@RestController
@RequestMapping("/patients")
public class PatientController {
    private PatientEntityService patientService;

    @Autowired
    public PatientController(PatientEntityService patientService) {
        this.patientService=patientService;
    }


    @GetMapping
    public ResponseEntity<StatusDto<List<PatientEntity>>> index(@RequestParam String phone) {
        List<PatientEntity> resVal = patientService.findByPhone(phone);

        return new ResponseEntity<>(new StatusDto<>(HttpStatus.OK.value(), "request success", "", resVal), HttpStatus.OK);
    }


    @GetMapping(path = "/{id}")
    @Cacheable(value = "patients",key = "#id")
    public StatusDto<PatientEntity> show(@PathVariable Long id) throws Throwable {
        Optional<PatientEntity> resVal = patientService.findById(id);

        return new StatusDto<>(HttpStatus.OK.value(),
                "request success", "",
                resVal.orElseThrow((Supplier<Throwable>) () -> new NoSuchElementException("not found")));

    }


}
