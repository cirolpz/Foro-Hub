
# API REST Foro-Hub con Spring Boot

## Video:
[![Video del Proyecto](https://img.youtube.com/vi/3LF_k-Gb7qM/maxresdefault.jpg)](https://youtu.be/3LF_k-Gb7qM)

## Descripción

Foro-Hub es una API REST que permite gestionar tópicos en un foro. Las principales funcionalidades incluyen crear, leer, actualizar y eliminar Tópicos, así como gestionar Usuarios, Cursos, Perfiles y Respuestas. La API está protegida con **Spring Security**, utilizando **HMAC256** como algoritmo de encriptación y **Auth0** para la generación y validación de tokens JWT.

El proyecto implementa **Docker**, migraciones con **Flyway**, y variables de entorno para facilitar la portabilidad y despliegue.

----------

## Tecnologías Utilizadas

-   **Java** (JDK 17)
-   **Spring Boot** (2.7.0 o superior)
-   **Maven**
-   **Jakarta Validation** (validaciones)
-   **H2 Database** (base de datos en memoria para desarrollo)
-   **Spring Data JPA**
-   **Spring Security**
-   **Flyway** (migraciones de base de datos)
-   **Docker**

----------

## Endpoints Disponibles

### Crear un Tópico

**POST /topicos**

Ejemplo de petición:

json

Copiar código

`{
    "title": "Mi primer tópico",
    "message": "Este es el mensaje del tópico",
    "autorId": 1,
    "cursoId": 1
}` 

**Validaciones:**

-   Todos los campos son obligatorios.
-   `title` y `message` no deben estar vacíos.

**Respuestas:**

-   **201 Created**: Tópico creado exitosamente.
-   **400 Bad Request**: Datos inválidos o campos faltantes.

----------

### Obtener Detalle de un Tópico

**GET /topicos/{id}**

Devuelve los detalles de un tópico específico.

**Respuestas:**

-   **200 OK**: Detalles del tópico.
-   **404 Not Found**: Tópico no encontrado.

----------

### Actualizar un Tópico

**PUT /topicos/{id}**

Ejemplo de petición:

json

Copiar código

`{
    "title": "Título actualizado",
    "message": "Mensaje actualizado",
    "autorId": 1,
    "cursoId": 1
}` 

**Validaciones:**

-   Todos los campos son obligatorios.

**Respuestas:**

-   **200 OK**: Tópico actualizado exitosamente.
-   **404 Not Found**: Tópico no encontrado.
-   **400 Bad Request**: Datos inválidos.

----------

### Eliminar un Tópico

**DELETE /topicos/{id}**

Elimina un tópico específico por su ID.

**Respuestas:**

-   **204 No Content**: Eliminación exitosa.
-   **404 Not Found**: Tópico no encontrado.

----------

## Validaciones Implementadas

-   **Anotaciones de Validación**:
    -   `@NotBlank` , `@NotNull` ,`@Email` ,`@Pattern` : Validan que los campos no estén vacíos o nulos.
-   **Manejo de Excepciones**:
    -   Uso de `ResponseStatusException` para retornar errores HTTP adecuados.
    -   Verificación de existencia mediante `Optional.isPresent()`.

----------

## Instalación y Configuración

### 1. Clonar el repositorio

bash

Copiar código

`git clone https://github.com/tu-usuario/nombre-del-repositorio.git` 

### 2. Configurar la base de datos

El proyecto utiliza H2 Database para desarrollo. Si deseas utilizar otra base de datos, modifica el archivo `application.properties`. Ejemplo de configuración para H2:

properties

Copiar código

`spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect` 

### 3. Ejecutar el proyecto

Con Maven:

bash

Copiar código

`mvn spring-boot:run` 

Con Docker:

bash

Copiar código

`docker build -t foro-hub .
docker run -p 8080:8080 foro-hub` 

----------

## Ejemplos de Uso con cURL

### Crear un Tópico

bash

Copiar código

`curl -X POST http://localhost:8080/topicos \
-H "Content-Type: application/json" \
-d '{
    "title": "Mi primer tópico",
    "message": "Este es el mensaje del tópico",
    "autorId": 1,
    "cursoId": 1
}'` 

### Obtener Detalle de un Tópico

bash

Copiar código

`curl -X GET http://localhost:8080/topicos/1` 

----------

## Mejoras Futuras

-   Implementar pruebas unitarias y de integración con **JUnit** y **Mockito**.
-   Añadir documentación interactiva utilizando **Swagger**.

----------

## Contribución

¡Tu colaboración es bienvenida! Sigue estos pasos para contribuir:

1.  Haz un fork del repositorio.
    
2.  Crea una nueva rama para tu funcionalidad o corrección:
    
    bash
    
    Copiar código
    
    `git checkout -b nombre-de-la-rama` 
    
3.  Realiza tus cambios y haz commit:
    
    bash
    
    Copiar código
    
    `git commit -m "Descripción de los cambios"` 
    
4.  Haz push de tu rama al repositorio remoto:
    
    bash
    
    Copiar código
    
    `git push origin nombre-de-la-rama` 
    
5.  Abre un Pull Request en GitHub.
    

----------

## Autor

Ciro López  
lopezciromartin@gmail.com  
[GitHub: Ciro](https://github.com/cirolpz)
