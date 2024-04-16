# Verteilte Systeme

## Eingelogged
![image](https://hackmd.io/_uploads/BkWolB1JC.png)

## Klassen Diagram
![model.drawio](https://hackmd.io/_uploads/SJio2NJJR.png)

<div style="page-break-after: always;"></div>

## User Flow Diagram
![FlowDiagram.drawio](https://hackmd.io/_uploads/H1kEh41JC.png)

## Context Map / Bounded Context
![context-map.drawio (1)](https://hackmd.io/_uploads/S1e6fHJJA.png)

<div style="page-break-after: always;"></div>

## Erklärung für gewählten Bounded Context
Benutzerverwaltung:

Das System wird in Nutzerverwaltung und Produktmanagement geteilt.
Die Benutzerverwaltung ist vom Rest des Systems unabghänig, deswegen wird sie als eigener Bounded-Context abgebildet. Die zugehörigen Entitäten (Rolle und Nutzer) sind stark gekoppelt und bilden eine zusammenhängende Einheit.

Das Produktmangement, ist vom Rest des Systems unabhängig. Das Produktmangement setzt sich aus Product und Category zusammen, da diese beiden Enititäten fachlich eine Einheit bilden. 

## Aufgabe 2: Implementierung von Microservices

#### Git Commit: [Link](https://github.com/hka-iwi-vislab/hska-vis-legacy/commit/68d401a0cd75562cddfc900ad4c298716fd67f28)

### Implementierung mit Spring-Boot und Docker
- Verwendung von Spring-Boot für die Implementierung der Microservices.
- Verwendung von Docker für die Containerisierung der Microservices.

### Anforderungen an die Microservices:
- Jeder Microservice stellt, wenn nötig, eine REST-API bereit.
- Jeder Microservice besitzt eine eigene Datenbank.

### Integration in Docker Compose Konfiguration
- Integration der Microservice-Container in die Docker Compose Konfiguration aus Aufgabe 1.
- Verwendung des MySQL Containers aus Aufgabe 1 als einziges DBMS mit mehreren DBs.

## Neue UML-Diagramme 
### UML-Diagramm für Usermanagement
![usermanagement](https://hackmd.io/_uploads/rkzdW-jxA.png)

#### Beschreibung des Diagramms
Hier ist das UML-Diagramm für das Usermanagement. Die Klassen sind logisch getrennt, und es gibt entsprechende Verknüpfungen zwischen den relevanten Entitäten und Klassen. Die Rolle ist nun nur noch mit dem UserBuilder verknüpft, während der UserDTO abgekapselt ist. Die Klassen RoleRepository und UserController sind miteinander verknüpft, wobei der UserController das UserRepository verwendet. Klassen wie Category wurden aus diesem Kontext entfernt, da sie zum Produktmanagement gehören und nichts mit Benutzern und Rollen zu tun haben.

### UML-Diagramm für Produktmanagement
![productmanagement](https://hackmd.io/_uploads/S1nwZ-il0.png)

#### Beschreibung des Diagramms
Hier ist das UML-Diagramm für das Produktmanagement. Die Klassen sind logisch getrennt, und es gibt entsprechende Verknüpfungen zwischen den relevanten Entitäten und Klassen. Hier finden sich die abgekapselten Klassen UserCategoryDTO, ProductManagementApplication, CreateProductDTO und CreateCategoryDTO. Sie sind vollständig abgekapselt. Die zusammenhängenden Teile des Codes sind logisch miteinander verbunden, wo dies sinnvoll ist. Das Product hat beispielsweise einen ProductBuilder und eine Category, die den mit der Kategorie verknüpften CategoryBuilder verwendet, während die Kategorie den mit dem Produkt verbundenen ProductBuilder verwendet. Insgesamt ist der Code deutlich besser getrennt.
