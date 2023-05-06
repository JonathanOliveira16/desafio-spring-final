package com.jonathan.github.addressapi.utils;

import com.jonathan.github.addressapi.rest.DTO.AddressDTO;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class StringMapsAddressBuilder {

    public static String getAddressBuilded(AddressDTO addressDTO){
        StringBuilder sb = new StringBuilder();
        sb.append(addressDTO.getAddress());
        sb.append(", " +addressDTO.getNumber() + " " + addressDTO.getNeighborhood());
        sb.append(", " + addressDTO.getCity() + ", " + addressDTO.getPostalCode());
        return sb.toString();
    }

    public static URI getUrlMapsDistance(String addressOne, String addressTwo, String key) throws URISyntaxException, UnsupportedEncodingException {
        String baseUrl = "https://maps.googleapis.com/maps/api/distancematrix/json?destinations="
                + URLEncoder.encode(addressTwo, StandardCharsets.UTF_8.toString()) + "&origins="
                + URLEncoder.encode(addressOne, StandardCharsets.UTF_8.toString())
                + "&units=imperial&key="+key;
        return new URI(baseUrl);
    }

}
