package com.fitow2512.basketinfo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fitow2512.basketinfo.services.dtos.Articles;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/_service")
public class MaintenanceController {
    
    @GetMapping("/healthCheck")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Articles> healthCheck() {
    	log.info("healthCheck is OK");
        return new ResponseEntity<>( 
        		HttpStatus.OK); 
    }
}
