package com.jonathan.github.addressapi.rest.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;

public class MapsApiResponseDTO {

    @Data
    public class Distance{
        @JsonProperty("distancia_em_milhas")
        public String text;
        @JsonIgnore
        public int value;
    }

    @Data
    public class Duration{
        @JsonProperty("distancia_em_tempo")
        public String text;
        @JsonIgnore
        public int value;
    }

    @Data
    public class Element{
        @JsonProperty("distancia")
        public Distance distance;
        @JsonProperty("duracao")
        public Duration duration;

        @JsonIgnore
        public String status;
    }

    @Data
    public class Row{
        @JsonProperty("elementos")
        public ArrayList<Element> elements;
    }

    @Data
    public class Root{
        @JsonProperty("destino")
        public ArrayList<String> destination_addresses;
        @JsonProperty("origem")
        public ArrayList<String> origin_addresses;
        @JsonProperty("dados")
        public ArrayList<Row> rows;
        @JsonIgnore
        public String status;
    }

}
