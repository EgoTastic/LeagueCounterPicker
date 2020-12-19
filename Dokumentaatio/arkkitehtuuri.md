# Arkkitehtuurikuvaus
  
## Rakenne  
  
Ohjelma koostuu kolmesta tasosta, joista _leaguecounterpicker.ui_ on vastuussa graafisesta käyttöliittymästä, _leaguecounterpicker.database_ sql-tietokannan käsittelystä ja _leaguecounterpicker.domain_ sisältää varsinaisen sovelluslogiikan.

<img src="https://raw.githubusercontent.com/EgoTastic/LeagueCounterPicker/main/Dokumentaatio/Kuvat/kuva1.png">

## Käyttöliittymä

Käyttöliittymä muodostuu yhdestä päänäkymästä, joka sisältää tarvittavan toiminnallisuuden. Käyttöliittymän luokka pitää yllä tietoa valintaboksien sisällöstä ja välittää ne sovelluslogiikalle käyttöliittymän nappi-elementtien kautta. Paluuarvojen mukaisesti käyttöliittymä esittää käyttäjälle status-ilmoituksia sekä sovelluslogiikan laskemia hahmovalinta ehdotuksia.

## Sovelluslogiikka

Sovelluksen varsinainen toiminnalisuus muodostuu luokkien DataHandler, DataAssistingTools ja DatabaseIF toiminnalisuudesta.
<img src="https://raw.githubusercontent.com/EgoTastic/LeagueCounterPicker/main/Dokumentaatio/Kuvat/kuva2.png">
DataHandler vastaa varsinaisesta sovelluslogiikasta ja sisältää metodit:

* DataHandler()
	* Konstruktori luokka, luodessa kutsuu DatabaseID konstruktoria
* boolean connectionToDatabase()
	* Palauttaa boolean arvon, onko Picks.db tietokanta oikeassa paikassa
* boolean saveMatch(Boolean victoryStatus, ArrayList<String> enemyChampionList, String championPicked, String role)
	* Suorittaa tietokantaan tallentamisen
	* Syötteenä tieto, päättyikö ottelu voittoon vai tappioon, lista vastapuolen hahmoista, oma hahmovalinta ja rooli missä pelasi
* String getPersonalRecommendation(ArrayList<String> enemyChampionList, String role)
	* Laskee käyttäjän omien otteluiden tilastoista ehdotuksen hahmovalinnaksi
	* Mikäli tietoja ei ole tarpeeksi, palauttaa "No statistics"
	* Syötteenä lista vastapuolen hahmovalinnoista sekä mahdollinen ehdotuksen roolirajaus
* String getBaseRecommendation(ArrayList<String> enemyChampionList, String role)
	* Laskee yleisiin tilastoihin perustuvan ehdotuksen hahmovalinnaksi
	* Ottaa huomioon käyttäjän roolin, mikäli sellainen valitaan
	* Syötteenä lista vastapuolen hahmovalinnoista, sekä mahdollinen ehdotuksen roolirajaus
* String ArrayList<String> listChampions()
	* Palauttaa graafiselle käyttöliittymälle listan kaikista hahmoista, jotta käyttöliittymä voi koostaa choiceBox valintojen vaihtoehdot
* String championNameAndStatistic(int bestChampion, double bestWinRate)
	* Muodostaa palautettavan String merkkijonon, joka sisältää hahmoehdotuksen hahmonimen sekä voittotodennäköisyyden
	* Mikäli statistiikkaa ei ole löytynyt, palauttaa "No Statistics"
	* Syötteenä hahmon ID sekä voittoprosentti
* boolean resetStats()
	* Tyhjentää kaikki henkilökohtaiset tilastot ja roolit
	* Palauttaa true/false riippuen onnistuiko tyhjennys
	
DataAssistingTools toimii DataHandler luokan apuluokkana, se sisältää metodeja, joilla vähennetään toistuvaa koodia ja jotka eivät itsessään kutsu DatabaseIF luokan metodeja

* double countWinRate(ArrayList<Double> listOfWinRates, boolean baseStatistics)
	* Laskee keskiarvon voittomahdollisuuksista listan perusteella
	* Syötteenä lista voittomahdollisuuksista double muodossa ja tieto suoritetaanko lasku base vai own Statistics taulun mukaan
	* Palauttaa keskiarvon voittomahdollisuuksista
* ArrayList<Double> transformMatchStatisticsToWinRates(ArrayList<String> listOfMatchStatistics)
	* Muuttaa saadun listan sisältämät "voitetut ottelut / kaikki ottelut" string muotoisen listaan, voitto prosenttilistaksi
	* Mikäli otteluita ei ole, ohittaa kyseisen tilaston
	* Syötteenä String lista ottelutilastoista
* String countNewStatistic(boolean victoryStatus, String oldStatistic)
	* Muodostaa uuden ottelutilaston, vanhan tilaston perusteella
	* Riippuen oliko ottelu voitettu lisää otteluiden kokonaismäärää ja mahdollisesti sen lisäksi voitettujen ottelujen määrää
	* Syötteenä tieto oliko ottelu voitettu vai hävitty ja vanha ottelutilastoista
* String shortenRole(String role)
	* Lyhentää roolia samaan muotoon, mitä käytetään SQL tietokannassa jotta roolia voi käyttää SQL haussa
	* Syötteenä rooli
* String checkEnemyList(ArrayList<String> enemyChampions)
	* Tarkistaa onko käyttäjän valitsemat hahmot sopivia ehdotuksen hakemiseen
	* Mikäli vastapuolen hahmoja ei valittu tai jos sama hahmo toistuu useaan kertaan, hylätään lista, eikö sovellus etsi ehdotusta
	* Palauttaa virhe/ok viestin

DatabaseIF vastaa SQL tietokannan käsittelystä

* DatabaseIF()
	* Konstruktori, luo Connectionin tietokantaan
* boolean checkIfDatabaseExists()
	* Tarkistaa onko käytettävä tietokanta oikeaa tyyppiä (eli sisältääkö tarvittavat tiedot ohjelman käyttämiseen)
* ArrayList<String> getWinRatesOwnStatistics(ArrayList<String> enemyChampionList, int championToTest)
	* Palauttaa tietokannasta omiin tilastoihin perustuvan yksittäisen hahmon tilastot listana, syötteenä olevan listan mukaan
	* Syötteenä lista vastapuolen hahmovalinnoista, sekä nykyinen haettava hahmovalinta
* ArrayList<double> getWinRatesBaseStatistics((ArrayList<String> enemyChampionList, int championToTest)
	* Palauttaa tietokannasta yleiseen dataan perustuvan yksittäisen hahmon tilastot listana, syötteenä olevan listan mukaan
	* Syötteenä lista vastapuolen hahmovalinnoista, sekä nykyinen haettava hahmovalinta
* String getChampionName(int championNumber)
	* Hakee tietokannasta id numeron perusteella hahmon nimen
	* Syötteenä haettavan hahmon id numero
* int getChampionId(String championName)
	* Hakee tietokannasta numeron perusteella hahmo id numeron
	* Syötteenä haettavan hahmon nimi
* ArrayList<String> getChampionList()
	* Hakee tietokannasta kaikkien mahdollisten hahmojen nimet ja palauttaa ne listana
* String getMatchStatistic(String champion, int championID)
	* Hakee tietokannasta yksittäisen hahmon ottelutilaston toista hahmoa vastaan
	* Syötteenä hahmot, joiden perusteella haetaan
* void setMatchStatistic(String champion, int championID, String newStatistic)
	* Kirjaa tietokantaan uuden tilaston yksittäisen hahmon ottelutilastosta toista hahmoa vastaan
	* Syötteenä hahmot, joiden perusteella kirjataan uusi tilasto newStatistic
* String parseStatement(ArrayList<String> enemyChampionList, int pick, String table)
	* Luo sql kyselyä varten kyselyn
	* Mikäli lista tyhjä tai ei sisällä valintoja, palauttaa "Error"
	* Syötteenä lista hahmoista joiden perusteella valitaan kolumnit, hahmoID jonka perusteella valitaan rivi ja tieto mistä taulusta tieto haetaan
* boolean checkRoleBaseRoles(int championID, String role)
	* Tarkistaa, onko hahmo tarkoitettu annettuun rooliin
	* Palauttaa true, mikäli ehto toteutuu, tai roolia ei ole valittu
	* Syötteenä testattava hahmo sekä rooli
* boolean checkRoleOwnRoles(int championID, String role)
	* Tarkistaa, onko käyttäjä pelannut hahmoa roolissa
	* Palauttaa true, mikäli ehto toteutuu, tai roolia ei ole valittu
	* Syötteenä testattava hahmo sekä rooli
* void setRolePlayed(String champion, String role, String stat)
	* Asettaa roolin pelatuksi annetulla hahmolla henkilökohtaisiin tilastoihin
	* Syötteenä hahmo jonka kohdalle merkataan, rooli mikä merkataan pelatuksi ja merkki pelatusta (olemassa vain sitä varten että tämä voidaan perua testeissä fiksusti)
* boolean resetAllPersonalStatistics()
	* Tyhjentää kaikki henkilökohtaiset tilastot sekä roolit
	* Palauttaa true/false riippuen onnistuuko tyhjentäminen

<img src="https://raw.githubusercontent.com/EgoTastic/LeagueCounterPicker/main/Dokumentaatio/Kuvat/kuva3.png">


## Tietojen pysyväistallennus

_leaguecounter.database_ pitää huolen tietokannan yhteydestä. Luokka vastaa sieltä hakemisesta sekä kirjoittamisesta. Tietokantana toimii Picks.db SQL tietokanta, jota ohjelma käyttää SQLlite:llä
Tietokanta koostuu 4 taulusta, kaksi tauluista sisältää tilastot kahden hahmon keskinäisestä voittotodennäköisyydestä toinen sisältäen yleiseen dataan perustuvan ja toinen käyttäjän omaan dataan. Kaksi muuta taulua sisältävät roolimahdollisuuksia hahmoille. Samalla tavalla toinen perustuu yleiseen dataan ja toinen siihen missä rooleissa käyttäjä on pelannut mitäkin hahmoa.


### Tiedosto

Tiedostona toimii Paketissa mukana oleva Picks.db, johon on esikirjattu yleiseen dataan liittyvät tiedot. Tiedosto on tarpeellinen ohjelman käytön kannalta, eli sen joutuu siirtämään samaan kansioon .jar tiedoston kanssa paketoinnin jälkeen.

## Päätoiminnalisuudet

### Hahmoehdotuksen hakeminen yleiseen dataan perustuen 

"Recommend a pick" button elementin tapahtumakäsittelijä kutsuu _DataHandler_ luokan metodia _getBaseRecommendation_ antaen sille listan käyttäjän ilmoittamista vastajoukkueen hahmoista, sekä oman roolivalinnan (joka voi olla myös "tyhjä" Role). Metodi kutsuu _DataAssistingTools_ luokan metodia _checkEnemyList_ joka tarkistaa onko käyttäjän valinnat fiksuja toiminnalisuuden kannalta ja palauttaa joko virheviestin tai OK.  
  
Metodi kutsuu _DataAssistingTools_ luokan metodia _shortenRole_ josta saa takaisin SQL kyselyyn sopivan lyhennyksen roolista ja käy loopissa läpi kaikkien hahmojen ID:t läpi ja jokaisen kohdalla kutsuu omaa metodiaan _checkRoleBaseRoles_ joka tarkistaa, onko kyseisellä kierroksella läpikäytävä hahmo sovelias toivottuun rooliin, tämä metodi palauttaa true arvon mikäli sopii, tai jos käyttäjä ei ole ilmoittanut haluttua roolia. Jos rooli ei sovi, siirrytään seuraavaan hahmoon, jos sopii niin metodi kutsuu seuraavaksi _database_ luokan metodia _getWinRatesBaseStatistics_ antaen sille listan vastapuolesta, sekä kyseisellä kierroksella testattavan hahmon ID numeron.  
  
Databasen metodi kutsuu oman luokan metodia _parseStatement_ joka muodostaa valmiin sql haun. _getWinRatesBaseStatistics_ käyttää tätä suorittamaan SQL-haun jonka jälkeen lisää palautettavaan listaan kaikki voittotilastot ja palauttaa sen takaisin _datahandler_ luokalle.  
  
Nyt _getBaseRecommendation_ metodi kutsuu _DataAssistingTools_ luokan metodia _countWinRate_ joka laskee keskiarvon saamansa listan voittotodennäköisyyksistä. Mikäli mahdollisuus on suurempi kuin tähän asti suurin löydetty, ottaa metodi talteen hahmon ID:n ja voittotodennäköisyyden. Kun kaikki hahmot on testattu, kutsutaan vielä omaa metodia _championNameAndStatistic_ joka muodostaa valmiin String muotoisen lauseen, joka sitten palautetaan käyttöliittymälle esitettäväksi. Merkkijono sisältää hahmon nimen ja sen voittotodennäköisyyden, jolla paras todennäköisyys voittaa ilmoitettuja hahmoja vastaan.

<img src="https://raw.githubusercontent.com/EgoTastic/LeagueCounterPicker/main/Dokumentaatio/Kuvat/kuva4.png">

### Hahmoehdotuksen hakeminen omaan dataan perustuen

"Recommend a pick" button elementin tapahtumakäsittelijä kutsuu _DataHandler_ luokan metodia _getPersonalRecommendation_ antaen sille listan käyttäjän ilmoittamista vastajoukkueen hahmoista, sekä oman roolivalinnan (joka voi olla myös "tyhjä" Role). Metodi kutsuu _DataAssistingTools_ luokan metodia _checkEnemyList_ joka tarkistaa onko käyttäjän valinnat fiksuja toiminnalisuuden kannalta ja palauttaa joko virheviestin tai OK.  
  
Metodi kutsuu _DataAssistingTools_ luokan metodia _shortenRole_ josta saa takaisin SQL kyselyyn sopivan lyhennyksen roolista ja käy loopissa läpi kaikkien hahmojen ID:t läpi ja jokaisen kohdalla kutsuu omaa metodiaan _checkRoleOwnRoles_ joka tarkistaa, onko kyseisellä kierroksella läpikäytävä hahmo sellainen, jota käyttäjä itse on pelannut kyseisessä roolissa, tämä metodi palauttaa true arvon mikäli sopii, tai jos käyttäjä ei ole ilmoittanut haluttua roolia. Jos rooli ei sovi, siirrytään seuraavaan hahmoon, jos sopii niin metodi kutsuu seuraavaksi _database_ luokan metodia _getWinRatesOwnStatistics_ antaen sille listan vastapuolesta, sekä kyseisellä kierroksella testattavan hahmon ID numeron.  
  
Databasen metodi kutsuu oman luokan metodia _parseStatement_ joka muodostaa valmiin sql haun. _getWinRatesOwnStatistics_ käyttää tätä suorittamaan SQL-haun jonka jälkeen lisää palautettavaan listaan kaikki voittotilastot ja palauttaa sen takaisin _datahandler_ luokalle.  
  
Nyt _getBaseRecommendation_ metodi kutsuu _DataAssistingTools_ luokan metodia _transformMatchStatisticsToWinRates_ jolle annetaan lista voittotilastoista jotka ovat String muodossa "4,9" (4 voittoa, yhdeksän ottelua yhteensä) ja muodostaa vastaavan listan joka koostuu voittoprosenteista, tämän jälkeen kutsutaan metodia _countWinRate_ joka laskee keskiarvon saamansa listan voittotodennäköisyyksistä. Mikäli mahdollisuus on suurempi kuin tähän asti suurin löydetty, ottaa metodi talteen hahmon ID:n ja voittotodennäköisyyden. Kun kaikki hahmot on testattu, kutsutaan vielä omaa metodia _championNameAndStatistic_ joka muodostaa valmiin String muotoisen lauseen, joka sitten palautetaan käyttöliittymälle esitettäväksi. Merkkijono sisältää hahmon nimen ja sen voittotodennäköisyyden, jolla paras todennäköisyys voittaa ilmoitettuja hahmoja vastaan.

<img src="https://raw.githubusercontent.com/EgoTastic/LeagueCounterPicker/main/Dokumentaatio/Kuvat/kuva5.png">

### Ottelun tallentaminen

"Victory" ja "Defeat" button elementtien tapahtumakäsittelijät kutsuvat _DataHandler_ luokan metodia _saveMatch_ antaen sille listan vastapuolen joukkueesta, käyttäjän oman hahmovalinnan sekä roolin missä pelasi.  
  
Metodi kutsuu _DatabaseIF_ luokan metodia getChampionID_ joka puolestaan palauttaa hahmon ID numeron. Tämän jälkeen _DataHandler_ kutsuu _DataAssistingTools_ luokan _shortenRole_ metodia joka lyhentää roolin SQL käskyille sopivaan muotoon. Tämän jälkeen _DataHandler_ kutsuu luokan _DatabaseIF_ metodia _setRolePlayed_ joka kutsuu ensin oman luokan metodia _getChampionId_ joka palauttaa hahmon nimeä vastaavan id numeron ja sitten  asettaa käyttäjän omiin tilastoihin, että hän on pelannu hahmovalintaansa kyseisessä roolissa, jotta tätä tietoa voidaan käyttää tulevaisuudessa hahmoehdotuksiin.  
  
Nyt käydään koko vastustajien hahmolista läpi ja kutsutaan _DatabaseIF_ luokan _getMatchStatistic_ metodia, joka toteuttaa SQL haun, missä haetaan hahmo yhdistelmän vanha ottelutilasto. Tämän jälkeen _DataHandler_ kutsuu _DataAssistingTools_ luokan _countNewSatistic_ metodia jolle annetaan vanha tilasto ja tieto, oliko ottelu voitettu vai hävitty. Metodi palauttaa uuden tallennettavan tilaston.  
  
Lopuksi kutsutaan _DatabaseIF_ metodia _setMatchStatistic_ joka suorittaa tallennuksen tietokantaan uudella tilastolla. Metodi palauttaa _UI_ luokalle true tai false sen mukaan, oliko asennus onnistunut.

<img src="https://raw.githubusercontent.com/EgoTastic/LeagueCounterPicker/main/Dokumentaatio/Kuvat/kuva6.png">

### Tilastojen tyhjentäminen

"Reset" button elementin tapahtumakäsittelijä kutsuu _DataHandler_ luokan metodia _resetStats_ joka puolestaan kutsuu _DatabaseIF_ luokan _resetAllStatistics_ metodia. Tämä metodi nollaa kaikki tilastot ja roolit käyttäjän omista tilastoista. UIlle palautetaan tieto onnistuiko nollaus.

<img src="https://raw.githubusercontent.com/EgoTastic/LeagueCounterPicker/main/Dokumentaatio/Kuvat/kuva7.png">
