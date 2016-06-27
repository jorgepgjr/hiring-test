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
Return:

* **Code:** 200 OK
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
  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** 
```json
{"url":"http://localhost:8080/cep/wefwef","msg":"CEP informado é invalido","links":[]}
```

## Endereco Service
