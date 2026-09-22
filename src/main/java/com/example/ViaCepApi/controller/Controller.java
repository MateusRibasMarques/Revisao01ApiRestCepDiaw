// Define em qual pacote essa classe está
package com.example.ViaCepApi.controller;


// Importa a anotação para criar uma rota GET
import org.springframework.web.bind.annotation.GetMapping;

// Importa a anotação que pega um valor da URL
import org.springframework.web.bind.annotation.PathVariable;

// Diz que essa classe será um Controller de uma API REST
import org.springframework.web.bind.annotation.RestController;

// Importa nosso Service para podermos usá-lo
import com.example.ViaCepApi.service.Service;


// Diz ao Spring:
// "Essa classe recebe requisições da API"
@RestController
public class Controller {


    // Criamos um objeto da classe Service
    // Assim o Controller consegue chamar os métodos do Service
    Service service = new Service();


    // Cria o endpoint GET:
    //
    // GET /cep/{cep}
    //
    // Exemplo:
    // localhost:8080/cep/30421280
    //
    // Nesse caso:
    // {cep} = 30421280
    @GetMapping("/cep/{cep}")
    public String consultarCep(@PathVariable String cep) {


        // Chama o método consultarCep() que está no Service
        // e passa o CEP recebido pela URL.
        //
        // Exemplo:
        // service.consultarCep("30421280")
        //
        // O Service consulta o ViaCEP e devolve os dados.
        return service.consultarCep(cep);
    }
}