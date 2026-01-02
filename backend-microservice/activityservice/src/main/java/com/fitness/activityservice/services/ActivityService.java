package com.fitness.activityservice.services;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import jdk.jfr.Experimental;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.fitness.activityservice.models.Activity;
import com.fitness.activityservice.repositories.ActivityRepository;

import lombok.AllArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final userValidationService userValidationService;
    private final KafkaTemplate<String,Activity> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topicName;

    public ActivityResponse mapToResponse(Activity activity) {
        ActivityResponse activityResponse = new ActivityResponse();
        activityResponse.setId(activity.getId());
        activityResponse.setUserId(activity.getUserId());
        activityResponse.setActivityType(activity.getActivityType());
        activityResponse.setDuration(activity.getDuration());
        activityResponse.setCaloriesBurned(activity.getCaloriesBurned());
        activityResponse.setStarTime(activity.getStarTime());
        activityResponse.setAdditionalMeterics(activity.getAdditionalMeterics());
        activityResponse.setCreatedAt(activity.getCreatedAt());
        activityResponse.setUpdatedAt(activity.getUpdatedAt());

        return activityResponse;
    }

    public ActivityResponse trackActivity(ActivityRequest activityRequest) {
        boolean isValidUser=userValidationService.validateUser(activityRequest.getUserId());

        if(!isValidUser){
            throw new RuntimeException("Invalid User: "+ activityRequest.getUserId() );
        }

        Activity activity = new Activity().builder()
                .userId(activityRequest.getUserId())
                .activityType(activityRequest.getActivityType())
                .duration(activityRequest.getDuration())
                .caloriesBurned(activityRequest.getCaloriesBurned())
                .starTime(activityRequest.getStarTime())
                .additionalMeterics(activityRequest.getAdditionalMeterics()).build();

        Activity saveActivity = activityRepository.save(activity);
        try{
          kafkaTemplate.send(topicName,saveActivity.getUserId(),saveActivity);
        }catch(Exception e){

        }
        return mapToResponse(saveActivity);
    }

    public Activity getActivityById(String id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found with id: " + id));
    }

    public List<Activity> getActivitiesByUserId(String userId) {
        return activityRepository.findByUserId(userId);
    }

    public Activity updateActivity(String id, Activity activity) {
        Activity existingActivity = getActivityById(id);
        activity.setId(existingActivity.getId());
        return activityRepository.save(activity);
    }

    public void deleteActivity(String id) {
        activityRepository.deleteById(id);
    }
}
