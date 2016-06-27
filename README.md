# Hiring-test
[![Build Status](https://travis-ci.org/jorgepgjr/hiring-test.svg?branch=master)](https://travis-ci.org/jorgepgjr/hiring-test)
# Running
Make sure you have [maven](https://maven.apache.org/) installed and at the root folder of the project, run:

	mvn spring-boot:run

All Services are running by default at `http://localhost:8080` 
## CEP Service
	URI : GET /cep/{cep_number}
	Example:
	 /cep/11055341
	 
* **Sucess Response:**
* **Code:** 200 OK
* **Content:** 
```json 
{
	"cd": null,
	"cep": "11055341",
	"logradouro": "Rua Tocantins",
	"complemento": "de 67/68 ao fim",
	"bairro": "Gonzaga",
	"numero": null,
	"_links": {
		"self": {
			"href": "http://localhost:8080/cep/11055341"
		}
	},
	"cidade": "Santos",
	"estado": "SP"
}
```
* **Error Response:**
* * **URL:** /cep/wefwef
  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** 
```json
{
	"url":"http://localhost:8080/cep/wefwef",
	"msg":"CEP informado é invalido",
	"links":[]
}
```

## Endereco Service
	URI : GET /endereco
	Example:
	 /endereco
	Shows all Enderecos
	
	URI : GET /endereco/{id}
	Example:
	 /endereco/1
	Shows Enderecos with id={id}
	
	URI : POST /endereco
	Example:
	 /endereco
	 Body:
```json	 
	 {
		  "cep": "11044333",
		  "complemento": "de 67/68 ao fim",
		  "bairro": "Gonzaga",
		  "cidade": "Santos",
		  "estado": "SP",
		  "logradouro":"Rua x",
		  "numero":"7"
	}
```	
	URI : PUT /endereco/{id}
	Example:
	 /endereco/1
	Updates Endereco with id = 1
```json	 
	 {
		  "cep": "11044333",
		  "complemento": "de 67/68 ao fim",
		  "bairro": "Gonzaga",
		  "cidade": "Santos",
		  "estado": "SP",
		  "logradouro":"Rua x",
		  "numero":"8"
	}
```
	URI : DELETE /endereco/{id}
	Example:
	 /endereco/1
	Delete Endereco with id = 1
