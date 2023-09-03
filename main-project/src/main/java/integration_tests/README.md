# Integration Tests

## Objetivo
Este package contiene pequeñas aplicaciones de consola que permitan probar la integración de nuestras clases con el entorno real.

**Por ejemplo**: `TestServicioGeoRef`

Esta es una clase con un método main que invoca funciones que van a interactuar de verdad con una REST API desplegada. En este caso esa Rest API es GeoRef.

## Observaciones

1. Estas clases **NO SON UNIT TESTS**.
2. Los unit tests van por separado en el package de test.
3. Esto es sólo para probar que se codificó correctamente una implementación contra una REST API, SDK, o Web Service concreto.
4. Idealmente este código debería estar excluido del *Code Coverage*.
5. Este código es parecido al que se usaría en un entorno real.

## Inventario

1. `TestServicioGeoRef`: Prueba la ejecución de métodos de ServicioGeoRef.