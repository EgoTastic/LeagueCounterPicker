# Arkkitehtuurikuvaus
  
## Rakenne  
  
Ohjelma koostuu kolmesta tasosta, joista yksi on vastuussa graafisesta käyttöliittymästä, toinen sql-tietokannan käsittelystä ja kolmas sisältää varsinaisen sovelluslogiikan.

<img src="https://raw.githubusercontent.com/EgoTastic/OhTe2020/Dokumentaatio/Kuvat/kuva1.png">

## Sovelluslogiikka

Sovelluksen varsinainen toiminnalisuus muodostuu luokkien DataHandler ja DatabaseIF toiminnalisuudesta.
<img src="https://raw.githubusercontent.com/EgoTastic/OhTe2020/Dokumentaatio/Kuvat/kuva2.png">
DataHandler vastaa varsinaisesta sovelluslogiikasta ja sisältää metodit:

* boolean save(Boolean victory, ArrayList<String> champs, String pick)
	* Suorittaa tietokantaan tallentamisen
	* Syötteenä tieto, päättyikö ottelu voittoon vai tappioon, lista vastapuolen hahmoista ja oma hahmovalinta
* string getPersPick(ArrayList<String> champs)
	* Laskee käyttäjän omien otteluiden tilastoista ehdotuksen hahmovalinnaksi
	* Mikäli tietoja ei ole tarpeeksi, palauttaa "No statistics"
	* Syötteenä lista vastapuolen hahmovalinnoista
* string getNormPick(ArrayList<String> champs)
	* Laskee yleisiin tilastoihin perustuvan ehdotuksen hahmovalinnaksi
	* Syötteenä lista vastapuolen hahmovalinnoista
* string ArrayList<String> listChampions()
	* Palauttaa graafiselle käyttöliittymälle listan kaikista hahmoista, jotta käyttöliittymä voi koostaa choiceBox valintojen vaihtoehdot
	
DatabaseIF vastaa SQL tietokannan käsittelystä

* ArrayList<String> getMatchesOwn(ArrayList<String> champs, int pick)
	* Palauttaa tietokannasta omiin tilastoihin perustuvan yksittäisen hahmon tilastot listana, syötteenä olevan listan mukaan
	* Syötteenä lista vastapuolen hahmovalinnoista, sekä nykyinen haettava hahmovalinta
* ArrayList<double> getPercentBase((ArrayList<String> champs, int pick)
	* Palauttaa tietokannasta yleiseen dataan perustuvan yksittäisen hahmon tilastot listana, syötteenä olevan listan mukaan
	* Syötteenä lista vastapuolen hahmovalinnoista, sekä nykyinen haettava hahmovalinta
* String getChampName(int championNumber)
	* Hakee tietokannasta id numeron perusteella hahmon nimen
	* Syötteenä haettavan hahmon id numero
*ArrayList<String> getChampList()
	* Hakee tietokannasta kaikkien mahdollisten hahmojen nimet ja palauttaa ne listana
* String getMatch(String champ, String pick)
	* Hakee tietokannasta yksittäisen hahmon ottelutilaston toista hahmoa vastaan
	* Syötteenä hahmot, joiden perusteella haetaan
* void setMatch(String champ, String pick, String newStat)
	* Kirjaa tietokantaan uuden tilaston yksittäisen hahmon ottelutilastosta toista hahmoa vastaan
	* Syötteenä hahmot, joiden perusteella kirjataan uusi tilasto newStat
	
<img src="https://raw.githubusercontent.com/EgoTastic/OhTe2020/Dokumentaatio/Kuvat/kuva3.png">