package com.blubank.doctorappointment.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

import java.io.Serializable;

@SuperBuilder
@Jacksonized
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusDto<T>  implements Serializable {
    private int status;
    private String detail;
    @JsonProperty("detail_local")
    private String detailLocal;
    private T data;

    public StatusDto(int status, String detail, String detailLocal) {
        this.status = status;
        this.detail = detail;
        this.detailLocal = detailLocal;
    }
}

