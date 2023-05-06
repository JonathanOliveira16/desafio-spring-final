package com.jonathan.github.addressapi.rest.resources;

import com.jonathan.github.addressapi.rest.DTO.AddressDTO;
import com.jonathan.github.addressapi.rest.DTO.ResponseAddressDTO;
import com.jonathan.github.addressapi.service.MapsService;
import com.jonathan.github.addressapi.utils.ValidatorBuilder;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api")
public class AddressResources {

    @Autowired
    private MapsService mapsService;

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PostMapping("/address")
    public List<ResponseAddressDTO> getAddressInformation(@RequestBody List< @Valid AddressDTO> addressDTO) throws IOException, URISyntaxException {
        if(ValidatorBuilder.isValidList(addressDTO)){
            List<ResponseAddressDTO> responseAddressDTOS = new ArrayList<>();
            responseAddressDTOS = mapsService.getResponseAddressApiInfo(addressDTO, responseAddressDTOS);
            responseAddressDTOS = mapsService.getDistanceInformations(responseAddressDTOS);
            return responseAddressDTOS;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Não é possívele enviar uma quantidade menor que três(3) endereços!");
    }
}
