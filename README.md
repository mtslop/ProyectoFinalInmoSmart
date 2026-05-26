S Proyecto Final Programación I - InmoSmart
Este es el repositorio de nuestro proyecto final para la materia Programación I de la carrera de Ingeniería de Sistemas y Computación en la Universidad del Quindío.  Desarrollamos un simulador para una plataforma inmobiliaria digital llamada InmoSmart.
La idea del sistema es conectar a personas que quieren vender propiedades con personas que quieren comprar o arrendar. Para que el código fuera ordenado y fácil de manejar, separamos todo usando la arquitectura Modelo-Vista-Controlador (MVC).

Qué hace el sistema

El proyecto cubre todo el ciclo de un negocio inmobiliario básico:

Registro de usuarios: El sistema deja registrar tanto a Compradores como a Vendedores con sus datos personales.

Publicación: Los vendedores pueden subir propiedades como Casas, Apartamentos, Locales comerciales o Terrenos. Cada uno tiene datos específicos como área, ciudad y precio.  

Búsqueda con filtros: Los compradores pueden buscar inmuebles filtrando por la ciudad, el tipo de propiedad, el rango de precio o el área mínima.

Ofertas y Negociación: Un comprador puede proponer un valor por un inmueble, y el vendedor tiene la opción de aceptar o rechazar esa oferta.

Transacciones: Si la oferta se acepta, el sistema registra la transacción (guarda quién compra, quién vende, el precio final y la fecha) y cambia el estado del inmueble a Vendido o Arrendado.

Sistema de Puntos y Rangos (Gamificación)Para motivar a los usuarios, agregamos una lógica donde acumulan puntos de reputación por hacer cosas en la aplicación:

Tabla de Puntos por AcciónAcciónPuntos obtenidosPublicar

inmueble+10
Realizar oferta+5 
Comprar inmueble+50
Completar transacción+100 

Rangos de UsuarioDependiendo de cuántos puntos tenga acumulados el usuario, el sistema lo clasifica automáticamente en uno de estos rangos: 

Principiante: De 0 a 100 puntos.
Inversionista: De 101 a 500 puntos.
Experto Inmobiliario: De 501 a 2000 puntos.
Magnate Inmobiliario: Más de 2000 puntos.

Funciones Especiales que IncluimosRecomendaciones: El programa revisa qué ha buscado el comprador y le recomienda cosas parecidas en la misma ciudad o rango de precio.  Alertas simuladas: El sistema avisa por consola (simulando Correo, SMS o WhatsApp) cuando aceptan una oferta o cuando un inmueble baja de precio.  Reportes: Se pueden sacar estadísticas básicas como cuáles son las ciudades más buscadas o qué vendedores tienen más propiedades.  

Adjunto queda el link de la documentacion del proyecto:

https://drive.google.com/file/d/1gYhCB8p5dpiW8SMPrErT8qxuMEZreg9k/view?usp=sharing
