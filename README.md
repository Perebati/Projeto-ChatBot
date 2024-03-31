## Execute o arquivo do docker
```
docker compose up -d
```

## Caso necessario, crie uma database de nome 'projetoGlobal'

## Execute o Spring Boot application
```
mvn spring-boot:run
```

## Realize os seguintes commandos SQL
```
INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');
```