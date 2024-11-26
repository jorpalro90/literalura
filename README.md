
# Literalura

**Literalura** es una aplicación desarrollada en Java con Spring Boot que permite gestionar una biblioteca de libros y autores, proporcionando funcionalidades como la búsqueda, el registro, la visualización de estadísticas y la gestión de descargas de libros. La aplicación hace uso de la API de **Gutendex** (https://gutendex.com/) para obtener los datos de los libros disponibles en el dominio público, y también se conecta a una base de datos **PostgreSQL** para almacenar los registros de libros y autores.

## Características

- **Gestión de Libros y Autores**: Registro de libros y autores en la base de datos.
- **Filtrado por Idioma**: Permite buscar libros en diferentes idiomas.
- **Filtrado por Género**: Los libros pueden ser filtrados por géneros registrados.
- **Estadísticas**: Obtén estadísticas como el género más frecuente, el número total de libros y autores registrados.
- **Top 10 Libros Más Descargados**: Muestra los 10 libros más descargados.

## Tecnologías Utilizadas

- **Java 17** (o superior)
- **Spring Boot**: Para crear la aplicación web y gestionar las funcionalidades.
- **Spring Data JPA**: Para interactuar con la base de datos.
- **H2 Database** (en memoria para desarrollo) o **PostgreSQL** (para producción).
- **Maven**: Para la gestión de dependencias.

## Requisitos Previos

Antes de ejecutar el proyecto, asegúrate de tener lo siguiente instalado en tu máquina:

- **Java 17** o superior.
- **Maven**: Para gestionar dependencias y construir el proyecto.
- **IDE** como IntelliJ IDEA, Eclipse o Visual Studio Code.
- **PostgreSQL**: Si deseas utilizar PostgreSQL como base de datos de producción.

## Instalación

1. **Clona el repositorio**:
   ```bash
   git clone https://github.com/tu_usuario/literalura.git
   ```

2. **Navega a la carpeta del proyecto**:
   ```bash
   cd literalura
   ```

3. **Configura la base de datos PostgreSQL**:
   Asegúrate de tener una instancia de PostgreSQL corriendo y crea una base de datos para la aplicación. Luego, actualiza el archivo `application.properties` con los datos de conexión de tu base de datos.

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/nombre_de_tu_base_de_datos
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña
   spring.datasource.driver-class-name=org.postgresql.Driver
   spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
   spring.jpa.hibernate.ddl-auto=update
   spring.datasource.initialization-mode=always
   ```

4. **Compila el proyecto** utilizando Maven:
   ```bash
   mvn clean install
   ```

5. **Ejecuta la aplicación**:
   ```bash
   mvn spring-boot:run
   ```

6. La aplicación estará disponible en [http://localhost:8080](http://localhost:8080).

## API Gutendex

Este proyecto obtiene los datos de libros y autores desde la API **[Gutendex](https://gutendex.com/)**, una fuente que proporciona acceso libre a libros en el dominio público. Los usuarios pueden realizar búsquedas de libros por título, autor, idioma y más, utilizando la API para acceder a una vasta biblioteca de textos.

## Endpoints

La API proporciona varios endpoints para interactuar con los datos:

- **GET /libros**: Devuelve todos los libros registrados.
- **GET /libros/{id}**: Devuelve un libro por su ID.
- **GET /libros/por-idioma/{idioma}**: Devuelve los libros filtrados por idioma.
- **GET /libros/por-genero/{genero}**: Devuelve los libros filtrados por género.
- **GET /autores**: Devuelve todos los autores registrados.
- **GET /autores/vivos/{ano}**: Devuelve los autores vivos en un determinado año.
- **GET /top10-descargados**: Devuelve los 10 libros más descargados.
- **GET /generos**: Devuelve una lista de géneros registrados.

## Funcionamiento del Menú

El menú principal proporciona diversas opciones para interactuar con los libros y autores registrados en la base de datos, así como obtener estadísticas relevantes. A continuación, se explica cada opción del menú:

1. **Buscar libro por título**: Permite buscar un libro en la base de datos utilizando su título. Si el libro está registrado, se mostrará la información relacionada.
   
2. **Listar libros registrados**: Muestra una lista de todos los libros que han sido registrados en la base de datos.

3. **Listar autores registrados**: Muestra una lista de todos los autores que han sido registrados en la base de datos.

4. **Listar autores vivos en un determinado año**: Permite listar los autores que estaban vivos en un año específico. Esto es útil para obtener una visión de los autores activos en un período de tiempo determinado.

5. **Listar libros por idioma**: Muestra los libros registrados en un idioma específico. Se asegura de que el idioma solicitado esté disponible en la base de datos.

6. **Listar libros por género**: Permite listar los libros de un género específico. Los géneros son extraídos de los libros registrados.

7. **Listar géneros de libros**: Muestra todos los géneros registrados en la base de datos, sin mostrar los libros. Es útil para explorar los géneros disponibles.

8. **Top 10 libros más descargados**: Muestra los 10 libros más descargados, basándose en la cantidad de descargas registradas en la base de datos.

9. **Estadísticas**: Proporciona estadísticas sobre los libros y autores, como el número total de libros registrados, el total de autores registrados y el género más frecuente entre los libros.

0. **Salir**: Cierra el menú y termina la ejecución de la aplicación.

### Ejemplo de Interacción

```text
**********************************************
1. Buscar libro por título
2. Listar libros registrados
3. Listar autores registrados
4. Listar autores vivos en un determinado año
5. Listar libros por idioma
6. Listar libros por género
7. Listar géneros de libros
8. Top 10 libros más descargados
9. Estadísticas

0. Salir
**********************************************

Elige una opción: 8

*********************************
Top 10 Libros más Descargados:
1. Frankenstein; Or, The Modern Prometheus - Descargas: 174761
2. History of Tom Jones, a Foundling - Descargas: 40581
3. My Life — Volume 1 - Descargas: 39844
...
```

## Contribuciones

¡Las contribuciones son bienvenidas! Si tienes alguna sugerencia o encuentras un error, no dudes en crear un **issue** o enviar un **pull request**.

### Pasos para contribuir:

1. Haz un **fork** del proyecto.
2. Crea una rama con un nombre descriptivo para tu cambio.
3. Realiza tus modificaciones y asegúrate de que el código funcione correctamente.
4. Envía un **pull request** con una descripción detallada de lo que has cambiado.

## Licencia

Este proyecto está bajo la **Licencia MIT**. Consulta el archivo [LICENSE](LICENSE) para más detalles.

## Autores

- **Jorge Palacio** (Desarrollador principal)
  
Si tienes alguna pregunta o sugerencia, no dudes en ponerte en contacto conmigo.

---

¡Gracias por usar **Literalura**!
