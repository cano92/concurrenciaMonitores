## concurrenciaMonitores
son una collección de **ejemplos prácticos y Técnicas de Programacion Concurrente** implementados con **java threads**, sincronizando procesos y recursos compartido
atravez del uso de monitores

en cada ejemplo se busca **maximizar la Cocurrencia** y **mejorar el performance** de cada programa aprovenchando los multicore del procesador


### \#1 PassingContion

##### N procesos Persona - Un Recurso Compartido

ejemplo en el que se usa la tecnica de **programacion concurrente passing the condition**, 

el ejemplo consiste en simular el acceso a un recurso compartido por **N procesos** Concurrentes Persona, el recurso compartido representa el uso de un **Cajero Automatico** que solo puede ser usado por una persona a la vez. El acceso al Recurso compartido es **Administrado y sincronizado** por una implementacion de la interface CajeroMonitor

las distintas implementaciones del monitor **cambian las reglas de acceso** al recurso

##### Diagrama UML

![passingCondition](resources/passingCondition/passingCondition.png)

##### CajeroMonitorSimple
es la implementacion mas sencilla de un monitor. todos los **procesos concurrentes Persona** acceden al recurso compartido.. segun el **orden de llegada** al mismo, el monitor permite el paso inmediatamente si ningun proceso esta ocupando el recurso compartido (cajero). si el recurso se encuentra ocupado el monitor demora al proceso en una fila de espera, para que cuando el recurso sea liberado inmediatamente pueda ser accedido por el siguiente proceso Persona de la fila

##### CajeroMonitorPrioridad
al igual que el monitor simple el acceso al recurso compartido es en **orden de llegada** con la diferencia que, si llega una persona **mayor a 60 años de edad** es tomado como prioridad y este es el proximo en acceder al recurso sin importar la cantidad de Personas de la fila de espera

##### CajeroMonitorEdad
esta implementacion permite acceder al recurso compartido al **proceso concurrente persona** de mayor edad que se encuentre en la fila de espera, y en busca de mantener el mejor rendimiento posible se usa una estructura de Arbol Ordenado **TreeSet** que garantiza **el tiempo de Log(n)** para las operaciones 

### \#2 nRecursosCompartidos

##### N procesos Persona - N Recurso Compartido

Es un ejemplo donde **N Procesos Concurrentes Persona** se ejecutan como clientes esperando ser atendidos por un Banco.
en dicho Banco existen  **N Procesos Concurrentes Empleados** (recursos compartido por los clientes) quienes son encargados de procesar las solicitudes de los **clientes**,
cada empleado solo puede atender un cliente a la vez.

los clientes son atendidos segun el **Orden de llegada** al Banco, sincronizados por BancoMonitor y EscritorioMonitor

en la implementacion para ser mas descriptivo son 10 clientes procesos persona y 3 procesos empleados ejecutados sobre un pool de treadhs de 6

##### Diagrama UML

![Screenshot](resources/nRecursosCompartidos/nRecursosCompartidos.png)

**BancoMonitor** se encarga de recibir clientes y mantenerlos en la fila( **orden de llegada** ) hasta que algun Empleado este libre y pueda atenderlo

**EscritorioMonitor** se encarga de sincronizar la interacción del cliente y empleado asignado por el banco para ser atendiddo. hasta concluir la atención y el empleado pueda atender otro

### \#3 PartidaPorTurnos

este ejemplo representa una **platilla Base** para algun juego Generico en el que **N Procesos concurrentes Jugador** comparten **Un Proceso Concurrente Juego** administrado por dos monitores

el proceso NombreJuego es demorado hasta que todos los procesos Jugador se encuentren listos y activos para iniciar la partida. una vez iniciada la partida los jugadores tendran un turno para realizar una accion. una vez concluido el turno el jugador pasa a la lista de procesos demorados esperando un turno. y se libera al proximo jugador correspondiente al turno

el proceso Concurrente **NombreJuego** se encuentra activo en todo momento, y es quien da inicio al juego y tambien conoce las reglas para finalizarlo  

##### Diagrama UML

![Screenshot](resources/partidaPorTurnos/partidaPorTurnos.png)

##### TurnosMonitor
es encargado de administrar y sincronizar a los jugadores a lo largo de la partida, iniciando la partida eligiendo al proximo jugador como tambien finalizando la partida

##### CanchaMonitor
es encargado de la sincronizacion y comunicacion del Jugador con el juego, ejemplo enviar la jugada del jugador actual con NombreJuego. para guardar las jugadas de cada jugador, elegir al ganador o revisar que sea una jugada valida

en la implementacion de ejemplo se encuentra un juego de 3 jugadores que deben adivinar un nro elegido por turnos

### \#2 tiempoSincronizado

es un ejemplo donde **N procesos Concurrente** comparten un mismo tiempo para realizar sus acciones..y una vez concluido el tiempo todos los Procesos concurrentes se demoran.. el tiempo debe ser exactemente igual para cada proceso Concurrente y una vez concluido el tiempo ningun proceso debe poder realizar ninguna accion

el ejemplo simula un partido de futbol donde 22 procesos Jugador inician una partida, en la cual todos comparten un mismo tiempo.
la partida no puede iniciar si no se encuentran los 22 procesos juagador listos para empezar.y una vez finalizada la partida ningun proceso jugador puede realizar ningun movimiento

##### Diagrama UML
