# WhatsApp Messaging and Notification

Esta aplicacion gestion la mensajeria entre usuario del sistema.

## Empecemos

Simplemente ejecutar el siguiente comando
```
mvn clean spring-boot:run
```

### Pre requisitos

Este proyecto utiliza lombok

1) Si ud es usuario de eclipse deberia bajarlo desde aqui [here](https://projectlombok.org/download) y seguir las instrucciones.

2) Si ud utiliza intellij vaya a preference -> plugins y luego instale el plugin de lombok.



# Endpoints

## Check for messages / Retorna los mensajes nuevos.

### Descripcion


Controller: MessageController

Method: GET

Este servicio entrega una lista mensajes que pertenece al usuario que lo consulta los cuales este ultimo no descargo.


### Params
```
@PathVariable Integer id
```


## Send a message / El sistema no retorna nada y envia un mensaje a un usuario destinatario.


### Description


Controller: MessageController

Method: POST

Este servicio persiste un mensaje enviado por un usuario originario del mensaje a un usuario destinatario de este.


### Params
```
@RequestBody InboxMessageDTO message
```



## Built With

* [Spring Boot](https://projects.spring.io/spring-boot/) - Framework
* [Maven](https://maven.apache.org/) - Dependency Management


## Authors

* **Guillermo Gorno**