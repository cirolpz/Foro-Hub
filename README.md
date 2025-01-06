
# API REST Foro-Hub con Spring Boot

## Descripción

Esta API REST permite gestionar tópicos de un foro, incluyendo funcionalidades para crear, leer, actualizar y eliminar tópicos. Además, incluye validaciones para asegurar la consistencia de los datos enviados por el cliente.

----------

## Tecnologías Utilizadas

-   **Java** (JDK 17)
    
-   **Spring Boot** (versión 2.7.0 o superior)
    
-   **Maven**
    
-   **Jakarta Validation** para validaciones
    
-   **H2 Database** (en memoria para desarrollo)
    
-   **Spring Data JPA**
    
    

----------

## Endpoints Disponibles

### Crear un Tópico

**POST /topicos**

```
{
    "title": "Mi primer tópico",
    "message": "Este es el mensaje del tópico",
    "autorId": 1,
    "cursoId": 1
}
```

**Validaciones:**

-   Todos los campos son obligatorios.
    
-   `title` y `message` no deben estar vacíos.
    

**Respuesta:**

-   **201 Created**: Tópico creado exitosamente.
    
-   **400 Bad Request**: Si faltan campos o los datos son inválidos.
    

----------

### Obtener Detalle de un Tópico

**GET /topicos/{id}**

-   Devuelve los detalles de un tópico específico.
    

**Respuesta:**

-   **200 OK**: Si el tópico existe.
    
-   **404 Not Found**: Si el tópico no existe.
    

----------

### Actualizar un Tópico

**PUT /topicos/{id}**

```
{
    "title": "Título actualizado",
    "message": "Mensaje actualizado",
    "autorId": 1,
    "cursoId": 1
}
```

**Validaciones:**

-   Todos los campos son obligatorios.
    

**Respuesta:**

-   **200 OK**: Si el tópico se actualizó exitosamente.
    
-   **404 Not Found**: Si el tópico no existe.
    
-   **400 Bad Request**: Si los datos son inválidos.
    

----------

### Eliminar un Tópico

**DELETE /topicos/{id}**

-   Elimina un tópico específico por su ID.
    

**Respuesta:**

-   **204 No Content**: Si el tópico fue eliminado exitosamente.
    
-   **404 Not Found**: Si el tópico no existe.
    

----------

## Validaciones Implementadas

-   `**@NotBlank**` **y** `**@NotNull**`: Para asegurar que los campos no estén vacíos ni nulos.
    
-   **Manejo de Excepciones**:
    
    -   `**ResponseStatusException**` para retornar errores HTTP adecuados.
        
    -   Verificación de existencia con `Optional.isPresent()` en consultas y eliminaciones.
        

----------

## Instalación y Configuración

1.  Clonar este repositorio:
    
    ```
    git clone https://github.com/tu-usuario/nombre-del-repositorio.git
    ```
    
2.  Configurar el archivo `application.properties` si deseas usar otra base de datos.
    
    ```
    spring.datasource.url=jdbc:h2:mem:testdb
    spring.datasource.driverClassName=org.h2.Driver
    spring.datasource.username=sa
    spring.datasource.password=
    spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
    ```
    
3.  Ejecutar la aplicación:
    
    ```
    mvn spring-boot:run
    ```
    

----------

## Ejecución de Pruebas

Puedes usar herramientas como **Postman** o **cURL** para probar los endpoints. Ejemplo con cURL:

### Crear un Tópico:

```
curl -X POST http://localhost:8080/topicos \
-H "Content-Type: application/json" \
-d '{
    "title": "Mi primer tópico",
    "message": "Este es el mensaje del tópico",
    "autorId": 1,
    "cursoId": 1
}'
```

----------

## Mejoras Futuras

-   Implementar seguridad con **Spring Security**.
    
-   Agregar paginación y filtros en los listados.
    
-   Crear pruebas unitarias con **JUnit** y **Mockito**.
    
-   Documentación interactiva con **Swagger**.
    

----------

## Contribución

1.  Haz un fork del repositorio.
    
2.  Crea una rama para tu funcionalidad o corrección:
    
    ```
    git checkout -b nombre-de-la-rama
    ```
    
3.  Haz commit de tus cambios:
    
    ```
    git commit -m "Descripción de los cambios"
    ```
    
4.  Haz push a tu rama:
    
    ```
    git push origin nombre-de-la-rama
    ```
    
5.  Abre un pull request en GitHub.
    

----------

## Autor

[Ciro Lopez]  
[lopezciromartin@gmail.com]  
[Ciro](https://github.com/cirolpz)