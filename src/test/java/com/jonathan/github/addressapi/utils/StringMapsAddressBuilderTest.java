package com.jonathan.github.addressapi.utils;

import com.jonathan.github.addressapi.rest.DTO.AddressDTO;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class StringMapsAddressBuilderTest {

    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/distancematrix/json?destinations=addres2&origins=addres1&units=imperial&key=123";

    private static final String ADDRESS = "rua, 1 bairro, cidade, 123456788";

    @Test
    public void shouldReturnMapsUrlCorrectly() throws UnsupportedEncodingException, URISyntaxException {
        URI result = StringMapsAddressBuilder.getUrlMapsDistance("addres1", "addres2", "123");
        assertEquals(BASE_URL, result.toString());
    }

    @Test
    public void shouldReturnCorrectlyAddressFormatted(){
        AddressDTO addressDTO = new AddressDTO("rua",1,123456788, "cidade","bairro");
        String result = StringMapsAddressBuilder.getAddressBuilded(addressDTO);
        assertEquals(ADDRESS, result);
    }

}