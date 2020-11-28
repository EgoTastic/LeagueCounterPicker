# Arkkitehtuurikuvaus
  
## Rakenne  
  
Ohjelma koostuu kolmesta tasosta, joista _leaguecounterpicker.ui_ on vastuussa graafisesta käyttöliittymästä, _leaguecounterpicker.database_ sql-tietokannan käsittelystä ja _leaguecounterpicker.domain_ sisältää varsinaisen sovelluslogiikan.

<img src="https://raw.githubusercontent.com/EgoTastic/LeagueCounterPicker/main/Dokumentaatio/Kuvat/kuva1.png">

## Käyttöliittymä

Käyttöliittymä muodostuu yhdestä päänäkymästä, joka sisältää tarvittavan toiminnallisuuden. Käyttöliittymän luokka pitää yllä tietoa valintaboksien sisällöstä ja välittää ne sovelluslogiikalle käyttöliittymän nappi-elementtien kautta. Paluuarvojen mukaisesti käyttöliittymä esittää käyttäjälle status-ilmoituksia sekä sovelluslogiikan laskemia hahmovalinta ehdotuksia.

## Sovelluslogiikka

Sovelluksen varsinainen toiminnalisuus muodostuu luokkien DataHandler ja DatabaseIF toiminnalisuudesta.
<img src="https://raw.githubusercontent.com/EgoTastic/LeagueCounterPicker/main/Dokumentaatio/Kuvat/kuva2.png">
DataHandler vastaa varsinaisesta sovelluslogiikasta ja sisältää metodit:

* DataHandler()
	* Konstruktori luokka, luodessa kutsuu DatabaseID konstruktoria
* boolean connectionToDatabase()
	* Palauttaa boolean arvon, onko Picks.db tietokanta oikeassa paikassa
* boolean saveMatch(Boolean victoryStatus, ArrayList<String> enemyChampionList, String championPicked)
	* Suorittaa tietokantaan tallentamisen
	* Syötteenä tieto, päättyikö ottelu voittoon vai tappioon, lista vastapuolen hahmoista ja oma hahmovalinta
* string getPersonalRecommendation(ArrayList<String> enemyChampionList)
	* Laskee käyttäjän omien otteluiden tilastoista ehdotuksen hahmovalinnaksi
	* Mikäli tietoja ei ole tarpeeksi, palauttaa "No statistics"
	* Syötteenä lista vastapuolen hahmovalinnoista
* string getBaseRecommendation(ArrayList<String> enemyChampionList, String role)
	* Laskee yleisiin tilastoihin perustuvan ehdotuksen hahmovalinnaksi
	* Ottaa huomioon käyttäjän roolin, mikäli sellainen valitaan
	* Syötteenä lista vastapuolen hahmovalinnoista, sekä mahdollinen rooli
* string ArrayList<String> listChampions()
	* Palauttaa graafiselle käyttöliittymälle listan kaikista hahmoista, jotta käyttöliittymä voi koostaa choiceBox valintojen vaihtoehdot
* double countWinRate(ArrayList<Double> listOfWinRates)
	* Laskee keskiarvon voittomahdollisuuksista listan perusteella
	* Jos lista tyhjä, palauttaa -1
	* Syötteenä lista voittomahdollisuuksista double muodossa
* String championNameAndStatistic(int bestChampion, double bestWinRate)
	* Muodostaa palautettavan String merkkijonon, joka sisältää hahmoehdotuksen hahmonimen sekä voittotodennäköisyyden
	* Mikäli statistiikkaa ei ole löytynyt, palauttaa "No Statistics"
	* Syötteenä hahmon ID sekä voittoprosentti
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
* ArrayList<String> getChampionList()
	* Hakee tietokannasta kaikkien mahdollisten hahmojen nimet ja palauttaa ne listana
* String getMatchStatistic(String champion, String pick)
	* Hakee tietokannasta yksittäisen hahmon ottelutilaston toista hahmoa vastaan
	* Syötteenä hahmot, joiden perusteella haetaan
* void setMatchStatistic(String champion, String pick, String newStatistic)
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
	
<img src="https://raw.githubusercontent.com/EgoTastic/LeagueCounterPicker/main/Dokumentaatio/Kuvat/kuva3.png">


## Tietojen pysyväistallennus

_leaguecounter.database_ pitää huolen tietokannan yhteydestä. Luokka vastaa sieltä hakemisesta sekä kirjoittamisesta. Tietokantana toimii Picks.db SQL tietokanta, jota ohjelma käyttää SQLlite:llä
Tietokanta koostuu 4 taulusta, kaksi tauluista sisältää tilastot kahden hahmon keskinäisestä voittotodennäköisyydestä toinen sisältäen yleiseen dataan perustuvan ja toinen käyttäjän omaan dataan. Kaksi muuta taulua sisältävät roolimahdollisuuksia hahmoille. Samalla tavalla toinen perustuu yleiseen dataan ja toinen siihen missä rooleissa käyttäjä on pelannut mitäkin hahmoa.


### Tiedosto

Tiedostona toimii Paketissa mukana oleva Picks.db, johon on esikirjattu yleiseen dataan liittyvät tiedot. Tiedosto on tarpeellinen ohjelman käytön kannalta, eli sen joutuu siirtämään samaan kansioon .jar tiedoston kanssa paketoinnin jälkeen.

## Päätoiminnalisuudet

### Hahmoehdotuksen hakeminen yleiseen dataan perustuen 

"Recommend a pick" button elementin tapahtmakäsittelijä kutsuu _DataHandler_ luokan metodia _getBaseRecommendation_ antaen sille listan käyttäjän ilmoittamista vastajoukkueen hahmoista, sekä oman roolivalinnan (joka voi olla myös "tyhjä" Role). 
Metodi kutsuu oman luokan metodia _shortenRole_ josta saa takaisin SQL kyselyyn sopivan lyhennyksen roolista ja käy loopissa läpi kaikkien hahmojen ID:t läpi ja jokaisen kohdalla kutsuu omaa metodiaan _checkRoleBaseRoles_ joka tarkistaa, onko kyseisellä kierroksella läpikäytävä hahmo sovelias toivottuun rooliin, tämä metodi palauttaa true arvon mikäli sopii, tai jos käyttäjä ei ole ilmoittanut haluttua roolia. Jos rooli ei sovi, siirrytään seuraavaan hahmoon, jos sopii niin metodi kutsuu seuraavaksi _database_ luokan metodia _getWinRatesBaseStatistics_ antaen sille listan vastapuolesta, sekä kyseisellä kierroksella testattavan hahmon ID numeron.
Databasen metodi kutsuu oman luokan metodia _parseStatement_ joka muodostaa valmiin sql haun. _getWinRatesBaseStatistics_ käyttää tätä suorittamaan SQL-haun jonka jälkeen lisää palautettavaan listaan kaikki voittotilastot ja palauttaa sen takaisin _datahandler_ luokalle.
Nyt _getBaseRecommendation__ metodi kutsuu omaa metodiaan _countWinRate_ joka laskee keskiarvon saamansa listan voittotodennäköisyyksistä. Mikäli mahdollisuus on suurempi kuin tähän asti suurin löydetty, ottaa metodi talteen hahmon ID:n ja voittotodennäköisyyden. Kun kaikki hahmot on testattu, kutsutaan vielä omaa metodia _championNameAndStatistic_ joka muodostaa valmiin String muotoisen lauseen, joka sitten palautetaan käyttöliittymälle esitettäväksi. Merkkijono sisältää hahmon nimen ja sen voittotodennäköisyyden, jolla paras todennäköisyys voittaa ilmoitettuja hahmoja vastaan.

<img src="https://raw.githubusercontent.com/EgoTastic/LeagueCounterPicker/main/Dokumentaatio/Kuvat/kuva4.png">