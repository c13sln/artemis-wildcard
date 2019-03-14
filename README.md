# artemis-wildcard

## Bakgrund 
### Topic Hierarchy
Apache ActiveMQ Artemis supports topic hierarchies. With a topic hierarchy you can register a subscriber with a wild-card and that     subscriber will receive any messages sent to an address that matches the wild card.

### Specifikation
Exempel på en arkitektur av PUBSUBs som filtrerar sin läsning med wildcard.

Teknik som kommer användas är Thorntail, ActiveMQ Artemis, Docker och JMS.

## Köra programmet

1. 
   Starta Artemis-brokern lokalt, genom **docker run -it  -e ARTEMIS_USERNAME=myuser -e ARTEMIS_PASSWORD=password --name activemq --rm   -    p 8161:8161   -p 61616:61616   vromero/activemq-artemis**. 
  1. 
     När brokern har tillåtits starta upp, kan man komma åt ett webbgränssnitt på *localhost:8161/console*. Här kan man övervaka händelser        och systemhälsa för brokern.
2. Bygg projektet: **mvn clean install**
3. Kör thorntail-servern: **mvn thorntail:run**

### Externa resurser

1. [Wildfly/Thorntail-dokumentation för project-defaults.yml](https://docs.thorntail.io/2.3.0.Final/)
