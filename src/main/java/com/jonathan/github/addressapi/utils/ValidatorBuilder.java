package com.jonathan.github.addressapi.utils;

import com.jonathan.github.addressapi.rest.DTO.AddressDTO;

import java.util.List;

public class ValidatorBuilder {

    public static boolean isValidList(List<AddressDTO> addressDTOList){
        if(addressDTOList.size() < 3){
            return false;
        }
        return true;
    }

}
