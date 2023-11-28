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
8. `DB_CONNECTION_HIBERNATE_SHOW_SQL=TRUE`: TRUE, se muestran las sentencias SQL en consola antes de ejecutarlas. FALSE, no se muestra nada.
9. `LISTADO_INCIDENTES_URL=https://localhost:8082/incidentes`: URL de la aplicación externa donde buscamos el listado de incidentes.
10. `SERVICIO_CALCULO_GRADO_CONFIANZA_URL=http://localhost:8080`: URL de la aplicación externa para calcular el grado de confianza.
11. `SERVICIO_FUSION_URL=http://localhost:8081`: URL de la aplicación externa para fusionar organizaciones o comunidades.
12. `WORST_PASSWORDS_FILE=main-project/src/main/resources/public/validacion/10000WorstPasswords.txt`: ruta del archivo que contiene los peores passwords para el sistema de validación.
13. ~~`NOTIFICACION_PENDIENTES_MINUTOS=30`: cada cuantos minutos se envían las notificaciones pendientes.~~ **Deprecado**
14. ~~`NOTIFICACION_AL_MOMENTO_MINUTOS=5`: cada cuantos minutos se envían las notificaciones al momento.~~ **Deprecado**
15. ~~`GENERAR_RANKING_DIA=MONDAY`: día en el que se hace la generación de ranking.~~ **Deprecado**
16. ~~`GENERAR_RANKING_HORA=00:00`: hora en el que se hace la generación del ranking.~~ **Deprecado**
17. ~~`CALCULAR_CONFIANZA_DIA=SUNDAY`: día en el que se calcula el grado de confianza.~~ **Deprecado**
18. ~~`CALCULAR_CONFIANZA_HORA=13:00`: horario en el que se calcula el grado de confianza~~. **Deprecado**
19. `CRON_TASK_PERIODO_REFRESCO_MIN=1`: cada cuantos minutos se revisa la DB para refrescar los parámetros de las cron tasks.
20. `EVALUADOR_SOL_REVISION_RANGO_CERCANIA_METROS=100`: el rango de metros con el que se considera que un usuario está cerca de un incidente. 100 indica que si el usuario está a 100 metros o menos de un incidente, entonces está cerca de él y puede ser notificado.
* *Los valores mostrados son sólo de ejemplo y deben ser readaptados según el entorno de ejecución (local, docker, nube, etc).*