# Käyttöohje

Lataa tiedosto LeagueCounterPicker.jar sekä tietokantatiedosto Picks.db [Täältä](https://github.com/EgoTastic/LeagueCounterPicker/releases/viikko6)  
Muista pitää tiedostot samassa hakemistossa!

## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla 
```
java -jar LeagueCounterPicker-1.0.jar
```

Ohjelmassa näet valintaboksit hahmoille ja rooleille sekä toiminnalisuuden osalta tärkeät napit

<img src="https://raw.githubusercontent.com/EgoTastic/LeagueCounterPicker/main/Dokumentaatio/Kuvat/kayttokuva1.png">

## Tietokannan tarkistus

Mikäli tietokanta ei ole samassa hakemistossa ohjelma ilmoittaa asiasta, mikäli näet varoitukset niin tarkista onko tietokanta oikeassa paikassa!

<img src="https://raw.githubusercontent.com/EgoTastic/LeagueCounterPicker/main/Dokumentaatio/Kuvat/kayttokuva2.png">

## Hahmoehdotuksen hakeminen

Voit saada hahmoehdotuksen joka perustuu sekä yleisiin tilastoihin ja sinun omiin otteluihin painamalla _Recommend a pick_ nappulaa
Ohjelma laskee sinulle ehdotukset hahmoista näistä kategorioista. Voit myös _Role_ valintaboksilla spesifioida, mihin rooliin haluat ehdotuksen, mutta tämä ei ole pakollista.
Mikäli et valitse yhtään hahmoa, tai valitset saman hahmon useampaan kertaan, sovellus ilmoittaa virheestä ja kertoo mikä on ongelma.

<img src="https://raw.githubusercontent.com/EgoTastic/LeagueCounterPicker/main/Dokumentaatio/Kuvat/kayttokuva3.png">

<img src="https://raw.githubusercontent.com/EgoTastic/LeagueCounterPicker/main/Dokumentaatio/Kuvat/kayttokuva4.png">

<img src="https://raw.githubusercontent.com/EgoTastic/LeagueCounterPicker/main/Dokumentaatio/Kuvat/kayttokuva5.png">

## Ottelun lopputuloksen tallentaminen

_Victory_ ja _Defeat..._ nappulat tallentavat valitun tilanteet tietokantaan. Sinun tulee valita kaikki vastapuolen hahmovalinnat, oma hahmovalintasi sekä rooli jota pelasit!
Nappula määrittelee lasketaanko ottelu voitoksi vai tappioksi. Ohjelma ilmoittaa mikäli tallennus oli onnistunut.

<img src="https://raw.githubusercontent.com/EgoTastic/LeagueCounterPicker/main/Dokumentaatio/Kuvat/kayttokuva6.png">
