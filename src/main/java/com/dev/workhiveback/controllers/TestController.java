package com.dev.workhiveback.controllers;

import com.dev.workhiveback.entities.UserEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @RequestMapping(value = "/requestTest",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> requestTest() throws JsonProcessingException, InterruptedException {

        JSONObject response = new JSONObject();
        response.put("result", "requestTest");

        UserEntity user = new UserEntity();
        user.setName("test");


        response.put("user", user);

        HttpStatus status = HttpStatus.OK;
        return ResponseEntity.status(status).body(response);
    }

}
