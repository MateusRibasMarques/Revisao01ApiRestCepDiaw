1. CONTROLLER

O Controller é responsável principalmente por receber as requisições/rotas.

Exemplo:

@RestController
@RequestMapping("/alunos")
public class AlunoController {

}
@RestController

Diz:

Essa classe recebe requisições HTTP e normalmente devolve dados diretamente.

Muito usado em API REST.

@RequestMapping
@RequestMapping("/alunos")

Define a rota base.

Então:

@GetMapping("/listar")

dentro desse Controller representa:

GET /alunos/listar
2. SERVICE

O Service fica com a lógica da aplicação.

@Service
public class AlunoService {

    public String buscarAluno() {
        return "Mateus";
    }
}

O Controller pode usar esse Service:

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    @GetMapping
    public String buscar() {
        return service.buscarAluno();
    }
}

Fluxo:

GET /alunos
     ↓
AlunoController
     ↓
service.buscarAluno()
     ↓
AlunoService
     ↓
"Mateus"
Regra importante

Evita fazer toda a lógica no Controller:

@GetMapping
public String calcular() {
    // 50 linhas de lógica aqui ❌
}

Melhor:

@GetMapping
public String calcular() {
    return service.calcular();
}

E:

@Service
public class CalculoService {

    public String calcular() {
        // lógica
    }
}

Controller = recebe e encaminha.

Service = executa a lógica.

3. O QUE É API REST?

Uma API REST permite que sistemas conversem através de requisições HTTP.

Por exemplo, seu front pode chamar:

GET http://localhost:8080/alunos

E o Spring responder:

[
    {
        "id": 1,
        "nome": "Mateus"
    },
    {
        "id": 2,
        "nome": "João"
    }
]

O front não precisa saber como o Java funciona.

Ele só sabe:

eu mando uma requisição → recebo uma resposta
4. ENDPOINT / ROTA

Endpoint é basicamente um endereço da API que executa alguma operação.

@GetMapping("/usuarios")

Endpoint:

GET /usuarios

Outro:

@GetMapping("/usuarios/{id}")

Endpoint:

GET /usuarios/5
5. GET

GET é utilizado para buscar/consultar informações.

@GetMapping("/alunos")
public String listar() {
    return "Lista de alunos";
}

Requisição:

GET /alunos

Resposta:

Lista de alunos
GET com variável na URL

Aqui aparece MUITO:

@GetMapping("/alunos/{id}")
public String buscar(@PathVariable int id) {

    return "Aluno " + id;
}

Se acessar:

GET /alunos/7

Então:

id = 7;

O:

@PathVariable

pega um valor que está dentro da URL.

Outro exemplo:

@GetMapping("/usuarios/{nome}")
public String buscar(@PathVariable String nome) {
    return "Olá " + nome;
}

URL:

/usuarios/Mateus

Resposta:

Olá Mateus
6. GET com @RequestParam

Também podemos receber parâmetros assim:

@GetMapping("/soma")
public int somar(
        @RequestParam int a,
        @RequestParam int b) {

    return a + b;
}

URL:

/soma?a=10&b=5

Resultado:

15

Diferença:

@PathVariable

URL:

/alunos/5

Código:

@GetMapping("/alunos/{id}")
public String buscar(@PathVariable int id)

Já:

@RequestParam

URL:

/alunos?id=5

Código:

@GetMapping("/alunos")
public String buscar(@RequestParam int id)
7. POST

POST normalmente é usado para enviar/criar dados.

Exemplo:

@PostMapping("/alunos")
public String cadastrar(@RequestBody Aluno aluno) {

    return "Aluno cadastrado";
}

O cliente manda:

{
    "nome": "Mateus",
    "idade": 20
}

E:

@RequestBody Aluno aluno

transforma esse JSON em um objeto Java.

8. @RequestBody

Suponha:

public class Aluno {

    private String nome;
    private int idade;

    // getters e setters
}

Controller:

@PostMapping("/alunos")
public String cadastrar(@RequestBody Aluno aluno) {

    return aluno.getNome();
}

Mandamos:

{
    "nome": "Mateus",
    "idade": 20
}

O Spring faz aproximadamente:

Aluno aluno = new Aluno();

aluno.setNome("Mateus");
aluno.setIdade(20);

Automaticamente.

9. Controller + Service completo

Essa estrutura é importante você saber montar.

Service
@Service
public class CalculadoraService {

    public int somar(int a, int b) {
        return a + b;
    }
}
Controller
@RestController
@RequestMapping("/calculadora")
public class CalculadoraController {

    private final CalculadoraService service;

    public CalculadoraController(CalculadoraService service) {
        this.service = service;
    }

    @GetMapping("/somar")
    public int somar(
            @RequestParam int a,
            @RequestParam int b) {

        return service.somar(a, b);
    }
}

Chamamos:

GET /calculadora/somar?a=10&b=20

Acontece:

Controller recebe
        ↓
a = 10
b = 20
        ↓
service.somar(10, 20)
        ↓
Service calcula
        ↓
return 30
        ↓
Controller devolve 30
10. Exemplo com objeto

Classe:

public class Produto {

    private String nome;
    private double preco;

    // getters e setters
}

Service:

@Service
public class ProdutoService {

    public double calcularDesconto(Produto produto) {

        return produto.getPreco() * 0.9;
    }
}

Controller:

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @PostMapping("/desconto")
    public double desconto(@RequestBody Produto produto) {

        return service.calcularDesconto(produto);
    }
}

Mandamos:

{
    "nome": "Teclado",
    "preco": 100
}

Service recebe o objeto e calcula:

100 * 0.9

Resposta:

90.0
11. GET x POST x PUT x DELETE

Mesmo que a prova foque GET e POST, guarda isso:

Método	Normalmente
GET	Buscar
POST	Criar/enviar
PUT	Atualizar
DELETE	Excluir

Código:

@GetMapping
public String listar() {
}
@PostMapping
public String cadastrar() {
}
@PutMapping
public String atualizar() {
}
@DeleteMapping
public String excluir() {
}

É o famoso CRUD:

CREATE → POST
READ   → GET
UPDATE → PUT
DELETE → DELETE
12. Agora THYMELEAF

Aqui tem uma diferença MUITO importante.

Com API REST usamos normalmente:

@RestController

e retornamos dados:

@RestController
public class AlunoController {

    @GetMapping("/aluno")
    public String aluno() {
        return "Mateus";
    }
}

Resposta:

Mateus

Com Thymeleaf normalmente usamos:

@Controller

Porque queremos retornar uma página HTML.

@Controller
public class PaginaController {

    @GetMapping("/home")
    public String home() {

        return "home";
    }
}

Spring procura:

src
 └── main
      └── resources
           └── templates
                └── home.html
13. @Controller x @RestController

Essa diferença é MUITO importante.

REST
@RestController

Retorna dados:

@GetMapping("/nome")
public String nome() {
    return "Mateus";
}

Resultado:

Mateus
Thymeleaf
@Controller

Retorna uma página:

@GetMapping("/home")
public String home() {
    return "home";
}

O:

return "home";

significa:

procure home.html
14. Mandando Java → HTML com Thymeleaf

Agora entra o:

Model

Controller:

@Controller
public class PaginaController {

    @GetMapping("/home")
    public String home(Model model) {

        model.addAttribute("nome", "Mateus");

        return "home";
    }
}

O Java colocou:

nome = Mateus

no Model.

No HTML:

<h1 th:text="${nome}"></h1>

Resultado:

<h1>Mateus</h1>

Então:

model.addAttribute("nome", "Mateus");

manda a informação.

E:

${nome}

pega a informação.

15. Exemplo mais real com Thymeleaf

Controller:

@Controller
public class ProdutoController {

    @GetMapping("/produto")
    public String produto(Model model) {

        model.addAttribute("nome", "Notebook");
        model.addAttribute("preco", 4500);

        return "produto";
    }
}

produto.html:

<!DOCTYPE html>

<html>

<head>
    <title>Produto</title>
</head>

<body>

    <h1 th:text="${nome}"></h1>

    <p th:text="${preco}"></p>

</body>

</html>

Quando acessar:

localhost:8080/produto

Spring:

GET /produto

      ↓

ProdutoController

      ↓

model.addAttribute()

      ↓

produto.html

      ↓

Thymeleaf coloca os valores

      ↓

Página aparece no navegador
16. th:text

Substitui o texto.

<p th:text="${nome}"></p>

Se:

model.addAttribute("nome", "Mateus");

fica:

<p>Mateus</p>
17. th:if

Faz uma condição.

<p th:if="${idade >= 18}">
    Maior de idade
</p>

É praticamente um:

if (idade >= 18)

no HTML.

18. th:each

Usado para percorrer lista.

Java:

List<String> nomes = new ArrayList<>();

nomes.add("Mateus");
nomes.add("João");
nomes.add("Pedro");

model.addAttribute("nomes", nomes);

HTML:

<ul>

    <li th:each="nome : ${nomes}"
        th:text="${nome}">
    </li>

</ul>

É semelhante a:

for (String nome : nomes) {

}
19. Formulário com Thymeleaf

HTML:

<form action="/cadastrar" method="post">

    <input
        type="text"
        name="nome">

    <button type="submit">
        Cadastrar
    </button>

</form>

Ao clicar:

POST /cadastrar

Controller pode receber:

@PostMapping("/cadastrar")
public String cadastrar(
        @RequestParam String nome) {

    System.out.println(nome);

    return "sucesso";
}

Então o formulário:

HTML
 ↓
POST
 ↓
Controller
 ↓
pega nome
 ↓
processa
 ↓
return "sucesso"
 ↓
sucesso.html
