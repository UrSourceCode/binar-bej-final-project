package com.binar.flyket.controller;

import com.binar.flyket.dto.model.AircraftDTO;
import com.binar.flyket.dto.request.AircraftRequest;
import com.binar.flyket.dto.response.Response;
import com.binar.flyket.dto.response.ResponseError;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.service.AircraftService;
import com.binar.flyket.utils.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Tag(name = "Aircraft")
@CrossOrigin(value = "*", maxAge = 3600L)
@RestController
@RequestMapping("/api/aircraft")
public class AircraftController {

    private AircraftService aircraftService;

    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addAircraft(@RequestBody AircraftRequest aircraftRequest) {
        try {
            aircraftService.addAircraft(airCraftRequestToDto(aircraftRequest));
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(),
                    Constants.SUCCESS_MSG, null));
        } catch (FlyketException.DuplicateEntityException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-all")
    public ResponseEntity<?> addAircraft(@RequestBody List<AircraftRequest> aircraftRequest) {
        try {
            List<AircraftDTO> aircraftDTOS = aircraftRequest.stream().map(this::airCraftRequestToDto).toList();
            aircraftService.addAircraft(aircraftDTOS);
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(),
                    Constants.SUCCESS_MSG, null));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
//    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAirCraft(@RequestParam("aircraftID") Integer airCraftID) {
        try {
            aircraftService.deleteAircraft(airCraftID);
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(),
                    Constants.SUCCESS_MSG, null));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAircraft() {
        return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(), Constants.SUCCESS_MSG, aircraftService.getAircraft()));
    }

    @GetMapping("/{aircraftID}")
    public ResponseEntity<?> getAircraftById(@PathVariable("aircraftID") Integer airCraftID) {
        try {
            return ResponseEntity.ok(new Response<>(HttpStatus.OK.value(), new Date(),
                    Constants.SUCCESS_MSG,  aircraftService.getAircraftById(airCraftID)));
        } catch (FlyketException.EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseError(e.getStatusCode().value(), new Date(), e.getMessage()), e.getStatusCode());
        }
    }

    private AircraftDTO airCraftRequestToDto(AircraftRequest request) {
        AircraftDTO aircraftDTO = new AircraftDTO();
        aircraftDTO.setId(request.getAircraftId());
        aircraftDTO.setType(request.getType());
        return aircraftDTO;
    }
}
