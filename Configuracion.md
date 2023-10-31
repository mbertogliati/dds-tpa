# Configuración

## Variables de entorno

A continuación se especifican las variables de entorno utilizadas en la aplicación.
Estas variables pueden ser especificadas en la IDE utilizada para trabajar en este proyecto.
También se pueden especificar por terminal antes de ejecutar la aplicación.

1. `APP_MAIN_PORT=8080`: puerto utilizado por el servidor principal javalin.
2. `DB_CONNECTION_DRIVER_CLASS=org.postgresql.Driver`: driver utilizado al conectarse a la DB.
3. `DB_CONNECTION_HIBERNATE_DIALECT=org.hibernate.dialect.PostgreSQLDialect`: dialecto utilizado para conectarse a la DB.
4. `DB_CONNECTION_HIBERNATE_HBM2DDL_AUTO=update`: modo con el que nos conectamos a la DB. 
   * `create` reconstruye el esquema en la DB.
   * `update` actualiza el contenido sin alterar el esquema de la DB.
5. `DB_CONNECTION_HOST=jdbc:postgresql://localhost:5432/pruebas`: dirección del servidor de la DB.
6. `DB_CONNECTION_PASSWORD=password`: password de la DB.
7. `DB_CONNECTION_USERNAME=usuario`: usuario de la DB.
8. `LISTADO_INCIDENTES_URL=https://localhost:8082/incidentes`: URL de la aplicación externa donde buscamos el listado de incidentes.
9. `SERVICIO_CALCULO_GRADO_CONFIANZA_URL=http://localhost:8080`: URL de la aplicación externa para calcular el grado de confianza.
10. `SERVICIO_FUSION_URL=http://localhost:8081`: URL de la aplicación externa para fusionar organizaciones o comunidades.
11. `WORST_PASSWORDS_FILE=main-project/src/main/resources/public/validacion/10000WorstPasswords.txt`: ruta del archivo que contiene los peores passwords para el sistema de validación.

* *Los valores mostrados son sólo de ejemplo y deben ser readaptados según el entorno de ejecución (local, docker, nube, etc).*