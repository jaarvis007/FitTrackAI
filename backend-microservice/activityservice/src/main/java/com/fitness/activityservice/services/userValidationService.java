package com.fitness.activityservice.services;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

@Service
@RequiredArgsConstructor
@Slf4j
public class userValidationService {
    private final WebClient userServiceWebClient;

    public boolean validateUser(String userId){
        log.info("Calling User Service for {}",userId);
        try{
            return Boolean.TRUE.equals(userServiceWebClient.get().uri("/api/users/{userId}/validate", userId).retrieve().bodyToMono(Boolean.class).block());
        }catch(WebClientException e){
            e.printStackTrace();;
        }

        return false;

    }
}
