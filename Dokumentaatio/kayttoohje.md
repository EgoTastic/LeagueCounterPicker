# Käyttöohje

Lataa tiedosto LeagueCounterPicker.jar sekä tietokantatiedostot "Picks.db" ja "Picks_testeihin.db" [Täältä](https://github.com/EgoTastic/LeagueCounterPicker/releases/loppupalautus)  
Ohjelma tarvitsee yhden _picks.db_ nimisen tietokanta tiedoston samaan kansioon missä ohjelma ajetaan. Releasen mukana tuleva Picks.db on tyhjä, uudelle käyttäjälle tarkoitettu tietokanta missä ei ole mitään käyttäjäkohtaista dataa vielä. _Picks_testeihin.db_ taas sisältää etukäteen hieman laitettua dataa, käytä tätä tiedostoa maven testejä varten (muuten testattaisiin tyhjää tietokantaa). Testaamista varten muista niemtä "Picks_testeihin.db" uudestaan nimelle "Picks.db"

## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla 
```
java -jar LeagueCounterPicker-1.0.jar
```

Ohjelmassa näet valintaboksit hahmoille ja rooleille sekä toiminnalisuuden osalta tärkeät napit

### Käyttöliittymän näkymä
<img src="https://raw.githubusercontent.com/EgoTastic/LeagueCounterPicker/main/Dokumentaatio/Kuvat/kayttokuva1.png">

## Tietokannan tarkistus

Mikäli tietokanta ei ole samassa hakemistossa ohjelma ilmoittaa asiasta, mikäli näet varoitukset niin tarkista onko tietokanta oikeassa paikassa!

### Viesti virheellisestä tietokannasta
<img src="https://raw.githubusercontent.com/EgoTastic/LeagueCounterPicker/main/Dokumentaatio/Kuvat/kayttokuva2.png">

## Hahmoehdotuksen hakeminen

Voit saada hahmoehdotuksen joka perustuu sekä etukäteisdataan ja sinun omiin otteluihin painamalla _Recommend a pick_ nappulaa
Ohjelma laskee sinulle ehdotukset hahmoista näistä kategorioista. Voit myös _Role_ valintaboksilla spesifioida, mihin rooliin haluat ehdotuksen, mutta tämä ei ole pakollista.
Mikäli et valitse yhtään hahmoa, tai valitset saman hahmon useampaan kertaan, sovellus ilmoittaa virheestä ja kertoo mikä on ongelma.

### Hahmoehdotukset oikealla
<img src="https://raw.githubusercontent.com/EgoTastic/LeagueCounterPicker/main/Dokumentaatio/Kuvat/kayttokuva3.png">

### Ehdotukset kun vastapuolen hahmoja ei valittu
<img src="https://raw.githubusercontent.com/EgoTastic/LeagueCounterPicker/main/Dokumentaatio/Kuvat/kayttokuva4.png">

### Ehdotukset kun vastapuolen valinnoissa duplikaatteja
<img src="https://raw.githubusercontent.com/EgoTastic/LeagueCounterPicker/main/Dokumentaatio/Kuvat/kayttokuva5.png">

## Ottelun lopputuloksen tallentaminen

_Victory_ ja _Defeat..._ nappulat tallentavat valitun tilanteet tietokantaan. Sinun tulee valita kaikki vastapuolen hahmovalinnat, oma hahmovalintasi sekä rooli jota pelasit!
Nappula määrittelee lasketaanko ottelu voitoksi vai tappioksi. Ohjelma ilmoittaa mikäli tallennus oli onnistunut.

### Status kun peli tallennettu
<img src="https://raw.githubusercontent.com/EgoTastic/LeagueCounterPicker/main/Dokumentaatio/Kuvat/kayttokuva6.png">

## Tietojen tyhjentäminen

_Reset_ nappulaa painamalla voit tyhjentää omat tietosi tietokannasta ja aloittaa keräämään tietoja alusta. Tyhjennys ei tapahdu heti, vaan sovellus kysyy onko käyttäjä varmaa tyhjentämisestä ja vaatii nappulan painamisen toisen kerran.

### Tyhjentämisnappula
<img src="https://raw.githubusercontent.com/EgoTastic/LeagueCounterPicker/main/Dokumentaatio/Kuvat/kayttokuva7.png">

### Tyhjentämisnappula kun painaa ensimmäisen kerran
<img src="https://raw.githubusercontent.com/EgoTastic/LeagueCounterPicker/main/Dokumentaatio/Kuvat/kayttokuva8.png">

### Status kun painaa toisen kerran putkeen
<img src="https://raw.githubusercontent.com/EgoTastic/LeagueCounterPicker/main/Dokumentaatio/Kuvat/kayttokuva9.png">
