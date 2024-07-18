package com.opzero.device.util;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;

public class GetCity {

    @Value("${geocoding.apiKey}")
    private static String apiKey;

    private static final String GEOCODING_RESOURCE = "https://geocode.maps.co/reverse";

    public static String reverseGeocode(String lat, String lon) throws IOException, InterruptedException {

        HttpClient httpClient = HttpClient.newHttpClient();
        String requestUri = GEOCODING_RESOURCE + "?lat=" + lat + "&lon=" + lon + "&api_key=667683985cd31759718558trj97858d";
        HttpRequest geocodingRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(requestUri))
                .timeout(Duration.ofMillis(2000)).build();

        HttpResponse geocodingResponse = httpClient.send(geocodingRequest, BodyHandlers.ofString());

        return geocodingResponse.body().toString();
    }
}