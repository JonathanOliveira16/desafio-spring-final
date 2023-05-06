package com.jonathan.github.addressapi.rest.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class ResponseAddressDTO {

    @JsonProperty("endereco")
    private String address;
    @JsonProperty("latitude")
    private String lat;
    @JsonProperty("longitude")
    private String lng;
    @JsonProperty("distancia_entre_enderecos")
    private List<MapsApiResponseDTO.Root> distance;
    @JsonProperty("pares_de_enderecos")
    private PairOfAddressesLongestClosestDTO pairOfAddresses;
}
