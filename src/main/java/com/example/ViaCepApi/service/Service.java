package com.example.ViaCepApi.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Service {

    private static final String BASE_URL = "https://viacep.com.br/ws/";

    private String consultarURL(String apiUrl){

        String dados = "";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity(apiUrl, String.class);

        if(responseEntity.getStatusCode().is2xxSuccessful()){
            dados = responseEntity.getBody();
        }else{
            dados = "Falha ao obter dados, Código de status: "
                    + responseEntity.getStatusCode();
        }

        return dados;
    }

    public String consultarCep(String cep){
        return consultarURL(BASE_URL + cep + "/json/");
    }
}