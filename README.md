## concurrenciaMonitores
son una collección de **ejemplos prácticos y Técnicas de Programacion Concurrente** implementados con **java threads**, sincronizando procesos y recursos compartido
atravez del uso de monitores

en cada ejemplo se busca **maximizar la Cocurrencia** y **mejorar el performance** de cada programa aprovenchando los multicore del procesador



### \#1 nRecursosCompartidos
Es un ejemplo donde **N Procesos Concurrentes Persona** se ejecutan como clientes esperando ser atendidos por un Banco.
en dicho Banco existen  **N Procesos Concurrentes Empleados** (recursos compartido por los clientes) quienes son encargados de procesar las solicitudes de los **clientes**,
cada empleado solo puede atender un cliente a la vez.

los clientes son atendidos segun el **Orden de llegada** al Banco, sincronizados por BancoMonitor y EscritorioMonitor

en el ejemplo para ser mas descriptivo son 10 clientes procesos persona y 3 procesos empleados ejecutados sobre un pool de treadhs de 6

##### Diagrama UML

![Screenshot](resources/nRecursosCompartidos/nRecursosCompartidos.png)

### \#1 PartidaPorTurnos
