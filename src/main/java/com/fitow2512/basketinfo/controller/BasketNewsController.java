package com.fitow2512.basketinfo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fitow2512.basketinfo.services.PiratasBasketDataService;
import com.fitow2512.basketinfo.services.dtos.Articles;


@RestController
@RequestMapping("/news")
public class BasketNewsController {

    @GetMapping("/articles")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Articles> getArticles() {
        return new ResponseEntity<>(
        		PiratasBasketDataService.getNews(), 
        		HttpStatus.OK); 
    }
    
    @GetMapping("/transfers")
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Articles> getTransfers() {
        return new ResponseEntity<>(
        		PiratasBasketDataService.getTransfers(), 
        		HttpStatus.OK); 
    }
}
