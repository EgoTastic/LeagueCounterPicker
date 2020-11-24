# Ohjelmistotekniikka Syksy 2020 harjoitustyö

## League Counter Picker

Sovelluksen avulla käyttäjän on mahdollista pitää kirjaa omasta suoriutumisesta "League of Legends" pelissä. Sovelluksella käyttäjä voi saada vastapuolen joukkueen hahmovalintoihin perustuvan ehdotuksen omalle hahmovalinnalle. Ehdotus perustuu sekä yleiseen globaaleihin tilastoihin, sekä käyttäjän itse tuottamaan dataan, joka kertyy ajan myötä. Pelin jälkeen käyttäjä voi tallentaa vastapuolen joukkueen hahmot, oman hahmovallinnan sekä pelin lopputuloksen, jonka myötä omaan dataan perustuvaa tietokantaa muokataan kehittäen sovelluksen antamaa personaalista ehdotusta käyttäjälle.

### Dokumentaatio 
  
  [Vaatimusmäärittely](https://github.com/EgoTastic/LeagueCounterPicker/blob/main/Dokumentaatio/vaatimusmäärittely.md)  
  [Työaikakirjanpito](https://github.com/EgoTastic/LeagueCounterPicker/blob/main/Dokumentaatio/työaikakirjanpito.md)  
  [Arkkitehtuuri](https://github.com/EgoTastic/LeagueCounterPicker/blob/main/Dokumentaatio/arkkitehtuuri.md)  

## Releaset

### Viikko 4

* Edellisen viikon tavoitteista toteutunut:
  * omaan pelaamiseen perustuvan ehdotuksen toteuttaminen
  * pelien tallennus
* Toiminnalisuus nyt "valmiissa" muodossa, mahdollisia virhetilanteita on olemassa vielä
* Tietokantaa korjattu
* Testikattavuus lähempänä 80%
* Checkstyle luotu, virheitä 5 ja kaikki ovat liian pitkistä metodeista
* Jar tiedoston paketoiminen onnistuu
* Seuraavat tavoitteet:
  * Virhetilanteiden etsimistä ja paikkaamista
  * Koodin parantelua ja tiivistämistä
  * Metodien jakamista osiin
  * Globaalin datan lisääminen tietokantaan
  * JavaDOC aloitus
  * .db tiedostojen paketointi jar paketoinnin mukana?

### Viikko 3

* Ensimmäinen toiminnallinen versio luotu
* JavaFX puolen työ hyvässä mallissa (toiminnalisuuden osalta, kauniimmaksi voi vielä tehdä)
* Tietokanta luotu, ei varsinaisesti sisällä mitään merkityksellistä, mutta mahdollistaa testaamisen
* Toiminnalisuudesta toteutettu globaaliin dataan perustuva hahmoehdotus (toiminnallisuus olemassa, oikea data puuttuu tietokannasta)
* Testejä 1 kpl
* Seuraava tavoitteet: 
  * globaalin datan lisääminen tietokantaan
  * omaan pelaamiseen perustuvan ehdotuksen toteuttaminen
  * pelien tallennus?


### Viikko 2

Alustava vaatimusmäärittely luotu


## Komentorivitoiminnot

### Ohjelman suorittaminen

Ohjelman voi ajaa suoraan paketoimatta mavenilla komennolla

´´´
mvn compile exec:java -Dexec.mainClass=leaguecounter.ui.Main
´´´

### Testaus

Testit suoritetaan komennolla

´´´
mvn test
´´´

Testikattavuusraportti luodaan komennolla

´´´
mvn jacoco:report
´´´

Kattavuusraporttiin pääsee avaamalla tiedosto _target/site/jacoco/index.html_

### .jar tiedoston luonti

Komento

´´´
mvn package
´´´

Tiedoston löytää target/

### Checkstyle

Checkstylen suorittaminen toimii komennolla 

´´´
mvn jxr:jxr checkstyle:checkstyle
´´´

Checkstyle-raporttiin pääsee avaamalla tiedosto _target/site/checkstyle.html_
