package com.example.ViaCepApi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.ViaCepApi.service.Service;

@RestController
public class Controller {

    Service service = new Service();

    @GetMapping("/cep/{cep}")
    public String consultarCep(@PathVariable String cep){
        return service.consultarCep(cep);
    }
}