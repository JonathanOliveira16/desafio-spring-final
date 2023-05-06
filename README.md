# Address Api

Api responsável por retornar dados de endereços.

## Usage

Necessário realizar uma chamada POST com o objeto para pesquisa dos endereços, é necessário ao menos 3 endereço não menos que isso

```bash
http://localhost:8083/api/address
```

## Body Example

```json
[{
        "rua":"Americo Rodrigues",
        "numero":20,
        "codigo_postal":23013670,
        "cidade":"Rio de janeiro",
        "bairro":"Senador vasconcelos"
},
{
     "rua":"Estr. de Paciência",
        "numero":655,
        "codigo_postal":23066271,
        "cidade":"Rio de janeiro",
        "bairro":"Campo Grande"
},{
     "rua":"Av. Rio Branco",
        "numero":156,
        "codigo_postal":20040905,
        "cidade":"Rio de janeiro",
        "bairro":"Centro"
}]
```

## Informations

A aplicação contém validações de campos not null and not blank, além é claro da quanitdade de endereços recebidos e também outros erros conhecidos na aplicação.
## License

[Git Jonathan](https://github.com/JonathanOliveira16)