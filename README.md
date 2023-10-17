# linktic-java-app

Aplicacion de prueba para realizar un proceso batch y un ecommerce basico solo con los servicios de backend.
 ## Tecnologias
  - Spring Boot
  - Spring Webflux
  - H2 DB
  - R2DBC
  - Projec Reactor
  - Gradle
  - Lombok
  - MapStruct
  
## Arquitectura
Una aplicación molitica basada en Clean Architecture y Hexagonal para separar las responsabilidades de cada unas de las dependencias y capas que interactuan entre si. 
Promoviendo a que la logica de negocio siempre este presente en los casos de usos.

## Compilación
Utilice la linea de comandos para ejecutar el comando gradle

`gradlew clean build`

  ### Servicio de Checkout o Compra
  `
  curl --request POST \
  --url http://localhost:8080/cart/add \
  --header 'Content-Type: application/json' \
  --data '{
	"cartId": 1,
	"productId": 17,
	"quantity": 1
}'
`

  ### Servicio de Vacia Carrito de compra
  
  `curl --request DELETE \
  --url http://localhost:8080/cart/1`
  
  
  ### Servicio de Obtener productos del carro de compras
  
  `curl --request GET \
  --url 'http://localhost:8080/cart?cartId=1'`
  
  ### Servicio para agregar productos al carrito de compras
  
  `curl --request POST \
  --url http://localhost:8080/cart/add \
  --header 'Content-Type: application/json' \
  --data '{
	"cartId": 1,
	"productId": 17,
	"quantity": 1
}'`
