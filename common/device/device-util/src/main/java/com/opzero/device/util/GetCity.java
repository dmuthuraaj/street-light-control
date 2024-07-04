package com.opzero.device.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GetCity {

    private static final String GEOCODING_RESOURCE = "https://geocode.maps.co/reverse";

    public static void main(String[] args) throws IOException, InterruptedException {

        ObjectMapper mapper = new ObjectMapper();

        String response = reverseGeocode("40.7558017", "-73.9787414");
        JsonNode responseJsonNode = mapper.readTree(response);

        JsonNode address = responseJsonNode.get("address");
        String city = address.get("city").asText();
        String country = address.get("country").asText();
        System.out.println("Reverse geocoding returned: " + city + ", " + country);
    }

    public static String reverseGeocode(String lat, String lon) throws IOException, InterruptedException {

        HttpClient httpClient = HttpClient.newHttpClient();
        String requestUri = GEOCODING_RESOURCE + "?lat=" + lat + "&lon=" + lon;
        HttpRequest geocodingRequest = HttpRequest.newBuilder()
                                                  .GET()
                                                  .uri(URI.create(requestUri))
                                                  .timeout(Duration.ofMillis(2000)).build();

        HttpResponse geocodingResponse = httpClient.send(geocodingRequest, BodyHandlers.ofString());

        return geocodingResponse.body().toString();
    }
}