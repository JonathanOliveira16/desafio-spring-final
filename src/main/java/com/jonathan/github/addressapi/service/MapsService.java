package com.jonathan.github.addressapi.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.jonathan.github.addressapi.rest.DTO.AddressDTO;
import com.jonathan.github.addressapi.rest.DTO.MapsApiResponseDTO;
import com.jonathan.github.addressapi.rest.DTO.PairOfAddressesLongestClosestDTO;
import com.jonathan.github.addressapi.rest.DTO.ResponseAddressDTO;
import com.jonathan.github.addressapi.utils.StringMapsAddressBuilder;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MapsService {

    @Value("${google.maps.apikey.value}")
    private String apikey;

    private GeoApiContext context;

    private DecimalFormat df = new DecimalFormat("#.##");

    @PostConstruct
    public void init(){
        context = new GeoApiContext.Builder()
                .apiKey(apikey)
                .build();
    }

    public List<ResponseAddressDTO> getResponseAddressApiInfo(List<AddressDTO> addressDTO, List<ResponseAddressDTO> responseAddressDTOS){
        addressDTO.stream().forEach(x->{
            ResponseAddressDTO valuesToList = new ResponseAddressDTO();
            Map<String, String> longLatValues = new HashMap<>();

            String address = StringMapsAddressBuilder.getAddressBuilded(x);
            try {
                longLatValues = getLatLongValues(address);
            } catch (IOException | InterruptedException | ApiException e) {
                throw new RuntimeException(e);
            }
            valuesToList.setAddress(address);
            valuesToList.setLat(longLatValues.get("lat"));
            valuesToList.setLng(longLatValues.get("lng"));
            valuesToList.setDistance(null);
            responseAddressDTOS.add(valuesToList);
        });
        return  responseAddressDTOS;
    }

    public List<ResponseAddressDTO> getDistanceInformations(List<ResponseAddressDTO> responseAddressDTOS) throws UnsupportedEncodingException, URISyntaxException {
        for(ResponseAddressDTO r: responseAddressDTOS){
            List<MapsApiResponseDTO.Root> listOfDistances = new ArrayList<>();
            for(int i = 0; i< responseAddressDTOS.size(); i++){
                if(!responseAddressDTOS.get(i).getAddress().equals(r.getAddress())){
                    MapsApiResponseDTO.Root jsonResponse = getDistanceBetweenDestinations(r.getAddress(), responseAddressDTOS.get(i).getAddress());
                    listOfDistances.add(jsonResponse);
                    r.setDistance(listOfDistances);
                }
            }
            PairOfAddressesLongestClosestDTO pairOfAddressesLongestClosestDTO = getDistanceFarAndClosest(r.getDistance());
            r.setPairOfAddresses(pairOfAddressesLongestClosestDTO);
        }
        return responseAddressDTOS;
    }

    private Map<String, String> getLatLongValues(String address) throws IOException, InterruptedException, ApiException {
        Map<String, String> mapValues = new HashMap<>();
        GeocodingResult[] results =  GeocodingApi.geocode(context,
                address).await();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        mapValues.put("lat", gson.toJson(results[0].geometry.location.lat));
        mapValues.put("lng", gson.toJson(results[0].geometry.location.lng));
        return mapValues;
    }

    private MapsApiResponseDTO.Root getDistanceBetweenDestinations(String addressOne, String addressTwo) throws URISyntaxException, UnsupportedEncodingException {
        RestTemplate restTemplate = new RestTemplate();
        URI mapsURL = StringMapsAddressBuilder.getUrlMapsDistance(addressOne, addressTwo,apikey);
        ResponseEntity<String> response = restTemplate.getForEntity(mapsURL, String.class);
        MapsApiResponseDTO.Root distance = new Gson().fromJson(response.getBody(), MapsApiResponseDTO.Root.class);

        return distance;
    }

    private PairOfAddressesLongestClosestDTO getDistanceFarAndClosest(List<MapsApiResponseDTO.Root> listOfAddresses){
        PairOfAddressesLongestClosestDTO obj = new PairOfAddressesLongestClosestDTO();
        obj = getFarAddresses(listOfAddresses, obj);
        obj = getClosestAddresses(listOfAddresses, obj);
        return obj;
    }

    private PairOfAddressesLongestClosestDTO getFarAddresses(List<MapsApiResponseDTO.Root> listOfAddresses, PairOfAddressesLongestClosestDTO pairOfAddressesLongestClosestDTO){
        double maxNum1 = 0;
        double maxNum2 = 0;
        String previousAddress = "";
        for(MapsApiResponseDTO.Root d : listOfAddresses){
            double num = Double.parseDouble(d.getRows().get(0).getElements().get(0).getDistance().getText().replaceAll("[^\\.0123456789]",""));
            if(num > maxNum1){
                if(maxNum2 != 0 && maxNum1 > maxNum2){
                    maxNum2 = maxNum1;
                    pairOfAddressesLongestClosestDTO.setSecondAddressFar(previousAddress);
                    pairOfAddressesLongestClosestDTO.setSecondaAddressFarDistance(df.format(maxNum1*1.60934) + " km");
                }
                maxNum1 = num;
                pairOfAddressesLongestClosestDTO.setFirstAddressFar(d.getDestination_addresses().get(0));
                pairOfAddressesLongestClosestDTO.setFirstAddressFarDistance(df.format(maxNum1*1.60934) + " km");
                previousAddress = d.getDestination_addresses().get(0);
            }else if(num > maxNum2){
                maxNum2 = num;
                pairOfAddressesLongestClosestDTO.setSecondAddressFar(d.getDestination_addresses().get(0));
                pairOfAddressesLongestClosestDTO.setSecondaAddressFarDistance(df.format(maxNum2*1.60934) + " km");
            }
        }
        return pairOfAddressesLongestClosestDTO;
    }

    private PairOfAddressesLongestClosestDTO getClosestAddresses(List<MapsApiResponseDTO.Root> listOfAddresses, PairOfAddressesLongestClosestDTO pairOfAddressesLongestClosestDTO){
        double minNum1 = 0;
        double minNum2 = 0;
        String previousAddress = "";
        for(MapsApiResponseDTO.Root d : listOfAddresses){
            double num = Double.parseDouble(d.getRows().get(0).getElements().get(0).getDistance().getText().replaceAll("[^\\.0123456789]",""));
            if(minNum1 == 0){
                previousAddress = d.getDestination_addresses().get(0);
                minNum1 = num;
                pairOfAddressesLongestClosestDTO.setFirstAddressClosest(d.getDestination_addresses().get(0));
                pairOfAddressesLongestClosestDTO.setFirstAddressClosestDistance(df.format(minNum1*1.60934) + " km");
            }else if(minNum1 > num){
                if(minNum1 != 0){
                    minNum2 = minNum1;
                    pairOfAddressesLongestClosestDTO.setSecondAddressClosest(previousAddress);
                    pairOfAddressesLongestClosestDTO.setSecondAddressClosestDistance(df.format(minNum2*1.60934) + " km");
                }
                previousAddress = d.getDestination_addresses().get(0);
                minNum1 = num;
                pairOfAddressesLongestClosestDTO.setFirstAddressClosest(d.getDestination_addresses().get(0));
                pairOfAddressesLongestClosestDTO.setFirstAddressClosestDistance(df.format(minNum1*1.60934) + " km");
            }else if(minNum2 > num){
                minNum2 = num;
                pairOfAddressesLongestClosestDTO.setSecondAddressClosest(d.getDestination_addresses().get(0));
                pairOfAddressesLongestClosestDTO.setSecondAddressClosestDistance(df.format(minNum2*1.60934) + " km");
            }
        }
        return pairOfAddressesLongestClosestDTO;
    }

}
