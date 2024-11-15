
## REQUIREMENTS

``` 
 - Sistema operacional compatível com DOCKER Linux e Windows.
 - docker e docker-compose
    -> Windows: https://www.docker.com/products/docker-desktop/
    -> Linux: https://docs.docker.com/engine/install/ubuntu/
 - Git
 - Noções básicas de Docker, Git, Bash , Linux e Windows
 - Gerenciador de banco de dados  Navcat, DBeaver ou Workbench

```

---- 

## INSTRUÇÕES


#####  OBS: Todos os comando serão executados dentro do terminal Linux ou Windows

## 1 - CLONAR O REPOSITORIO

##### Estruture e Organize
* crie uma pasta para salvar os arquivos do repositorio e entre nela em seu sistema operacional

``` 
    mkdir servidorl2
    cd servidorl2
```  

* clone o repositorio
``` 
    git clone https://github.com/dyego-alves/sl2.git
```
      
## 2 - INICIALIZACAO DO SERVE COM DOCKER


#### Comando de incializacao do servidor com docker
Esse comando deve ser executado no terminal do servidor com o docker e docker-compose instalado e onde podemos encontrar o arquivo docker-compose.yml, ele cria os container e inicia o Loginserver, Gameserver  e mysql(database).

    docker-compose up --build -d

Caso precise subir separados os container, execute os comandos abaixo:

    docker-compose up --build -d db

    docker-compose up --build -d sl2

----

## 3 - COMANDO PRA CONTAINER DE BASE DE DADOS

#### Entre no container db com comando abaixo 
    docker-compose exec db bash

#### De permisao para arquivo
    chmod 600 /root/.my.cnf

#### Entrea no diretorio onde se encontra o arquivo .sql
    cd /docker-entrypoint-initdb.d

#### Executar o comando abaixo para realizar o restore da base de dados criada para projeto l2 ex: l2jteste
    mysql {NOME DO BANCO} < database.sql

#### Executar o comando abaixo para realizar o backup da base de dados criado para servidor de l2, ou nome definido na instalacao do server
    mysqldump {NOME DO BANCO} > database.sql


---


## 5 - LOGS DO SERVIDOR EM DOCKER CONTAINER 

* Dentro da pasta que encontra-se o docker-compose, execute o comando abaixo para saber alguns logs dos containers.


```
docker-compose logs -f 
```


--- 

#### Zip controle de versão do code server para envio ao desenvolvedor

zip -r servidor_interlude.zip * -x "sl2/game/log/*" "sl2/login/log/*" "mysql/*"