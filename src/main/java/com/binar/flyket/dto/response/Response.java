package com.binar.flyket.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


/*
    Ini class untuk response.
    @Param <T> : bisa menerima tipe data, array, null.

    contoh output JSON:

    misal input : Response(200, new Date(), sebuah array)

  {
    "statusCode": 200,
    "timeStamp": "2022-09-23 00:00:00",
    "message": "success",
    "data": []
  }



 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response<T>{
    private Integer statusCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timeStamp;
    private String message;
    private T data;
}
