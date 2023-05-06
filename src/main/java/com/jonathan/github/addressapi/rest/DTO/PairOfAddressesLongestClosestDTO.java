package com.jonathan.github.addressapi.rest.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PairOfAddressesLongestClosestDTO {

    @JsonProperty("endereco_mais_distante_1")
    private String FirstAddressFar;
    @JsonProperty("km_mais_distante_1")
    private String FirstAddressFarDistance;
    @JsonProperty("endereco_mais_distante_2")
    private String SecondAddressFar;
    @JsonProperty("km_mais_distante_2")
    private String SecondaAddressFarDistance;
    @JsonProperty("endereco_mais_proximo_1")
    private String FirstAddressClosest;
    @JsonProperty("km_mais_proximo_1")
    private String FirstAddressClosestDistance;
    @JsonProperty("endereco_mais_proximo_2")
    private String SecondAddressClosest;
    @JsonProperty("km_mais_proximo_2")
    private String SecondAddressClosestDistance;

}
