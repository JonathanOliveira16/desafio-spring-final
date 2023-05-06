package com.jonathan.github.addressapi.rest.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class AddressDTO {

    @NotNull(message="Favor preencha o campo rua!")
    @NotBlank(message = "Favor preencha o campo rua!")
    @JsonProperty("rua")
    private String address;
    @NotNull(message="Favor preencha o campo numero!")
    @JsonProperty("numero")
    private Integer number;
    @NotNull(message="Favor preencha o campo codigo_postal!")
    @JsonProperty("codigo_postal")
    private Integer postalCode;
    @NotNull(message="Favor preencha o campo cidade!")
    @NotBlank(message = "Favor preencha o campo cidade!")
    @JsonProperty("cidade")
    private String city;
    @NotNull(message="Favor preencha o campo bairro!")
    @NotBlank(message = "Favor preencha o campo bairro!")
    @JsonProperty("bairro")
    private String neighborhood;

}
