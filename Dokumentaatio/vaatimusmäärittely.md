# Vaatimusmäärittely  
  
## Sovelluksen tarkoitus  
  
Sovelluksen avulla käyttäjä saa tukea hahmon valintaan League of Legends -pelissä.  
Sovellukseen talletetaan toteutuneiden pelien tuloksia ja niiden perusteella ohjelma suosittelee tuleviin peleihin hahmovalintoja.  
Sovellus voi ehdottaa vaihtoehtoja myös etukäteen luotujen tietojen mukaan, jotka eivät riipu käyttäjän syötteestä.
  
## Toiminnallisuus  
  
Käyttäjä voi:
* Syöttää ottelussaan käyttämänsä hahmon ja vastustajan joukkueen hahmot ja tallentaa pelin 
* Ennen ottelua syöttää vastajoukkueen jo valitut hahmot ja niiden perusteella saada ehdotus omalle valinnalle
* Hakiessa ehdotusta määrittää, mitä roolia on pelaamassa ja saada valittuun rooliin sopivimman hahmon
* Ehdotusta hakiessa saada kaksi ehdotusta, toinen perustuu käyttäjän omaan dataan mikä kertyy pelejä tallettaessa, ja etukäteen olemassa olevaan dataan.
* Tyhjentää oman datansa, jolloin kaikki omat tilastot ja roolivalinnat tyhjennetään (etukäteis tilastot pysyvät ennallaan)

## Jatkokehitysideoita
* Useamman ehdotuksen esittäminen tai "next" nappula jolla saa seuraavaksi parhaimman ehdotuksen
* Automaattinen haku etukäteis datalle joko jonkin sivuston tai Riot-pelifirman omasta APIsta, tai sitten haku raa'asti html sivuilta
