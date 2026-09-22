// Define o pacote onde essa classe está
package com.example.ViaCepApi.service;


// ResponseEntity representa a resposta HTTP completa
// Permite acessar:
// - corpo da resposta
// - status HTTP
import org.springframework.http.ResponseEntity;


// RestTemplate é usado para fazer requisições HTTP
// para outras APIs
import org.springframework.web.client.RestTemplate;


public class Service {


    // URL base da API ViaCEP
    //
    // static final = constante
    //
    // Depois vamos completar essa URL com:
    // CEP + "/json/"
    private static final String BASE_URL =
            "https://viacep.com.br/ws/";


    // Método auxiliar responsável por consultar uma URL
    //
    // Recebe uma URL completa.
    //
    // Exemplo:
    // https://viacep.com.br/ws/30421280/json/
    private String consultarURL(String apiUrl) {


        // Variável que vai guardar os dados recebidos
        // da API
        String dados = "";


        // Criamos o RestTemplate.
        //
        // É ele que vai fazer a requisição HTTP
        // para a API externa.
        RestTemplate restTemplate = new RestTemplate();


        // Faz uma requisição GET para a URL recebida.
        //
        // apiUrl = endereço que queremos consultar
        //
        // String.class = queremos receber a resposta
        // como String
        //
        // ResponseEntity guarda:
        // - status HTTP
        // - corpo da resposta
        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity(apiUrl, String.class);


        // Verifica se a requisição funcionou.
        //
        // 2xx significa sucesso:
        // 200, 201, 204 etc.
        if (responseEntity.getStatusCode().is2xxSuccessful()) {


            // Pega o CORPO da resposta.
            //
            // Nesse caso, será o JSON retornado pelo ViaCEP.
            dados = responseEntity.getBody();


        } else {


            // Se a requisição não tiver sucesso,
            // guardamos uma mensagem de erro.
            dados = "Falha ao obter dados, Código de status: "
                    + responseEntity.getStatusCode();
        }


        // Retorna os dados recebidos
        return dados;
    }


    // Esse é o método que o Controller chama.
    //
    // Ele recebe somente o CEP.
    //
    // Exemplo:
    // cep = "30421280"
    public String consultarCep(String cep) {


        // Monta a URL completa:
        //
        // BASE_URL
        // +
        // cep
        // +
        // "/json/"
        //
        // Resultado:
        // https://viacep.com.br/ws/30421280/json/
        //
        // Depois chama consultarURL() para fazer
        // a requisição GET.
        return consultarURL(BASE_URL + cep + "/json/");
    }
}