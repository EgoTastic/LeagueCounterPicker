# Testausdokumentti

Ohjelman testaus perustuu yksikkötestauksiin sekä integraatiotesteihin jotka suoritetaan JUnitilla.

## JUnit testaus

### Testiluokat

Jokaiselle luokalle on luotu oma vastaava testiluokka, _DataHandlerTest_, _DataAssistingToolsTest_ ja _DatabaseIFTest_. Kukin keskittyy oman luokan testaamiseen.  
_DataHandler_ testaa samalla koko sovelluksen toimivuutta, sillä kaikki sen metodit käyttävät muotakin metodeja esim DataBaseIF luokassa

### leaguecounter.ui // Main ja UI

Nämä luokat eivät ole mukana testaamisessa, sillä koostuvat vain ohjelman käynnistämisestä ja käyttöliittymän rakentamisesta. Ainoa rakentamisen ulkopuolella oleva toiminnallisuus on muutaman _Label_ elementin sisältämän tekstin vaihtaminen, siihen mitä varsinainen sovelluslogiikka palauttaa.  
  
### leaguecounter.domain // DataHandler ja DataAssistingTools

Domain paketin luokat ovat mukana testauksessa.  
_DataAssistingTools_ luokasta kaikki metodit on testattu yksikkötesteillä.  
_DataHandler_ luokan osalta testaamatta on jäänyt tallentamisen metodi _saveMatch_ suurimmalti osin SQL-komentojen peruuttamiseen liittyvien epäselvyyksien takia. Samoiten _resetStats_ metodi on testaamatta. Muutoin kaikki metodit on testattu integraatiotestein.

### leaguecounter.database // DatabaseIF

_DatabaseIF_ luokan testauksista ulkopuolelle jää ainoastaan _resetAllPersonalStatistics_ sen perumiseen liittyvien epäselvyyksien takia sekä SQL-virhetilanteet, varsinaiset syötteen mukana tulevat virheet kierretään, eikä normaali käyttö nosta SQL-virhettä koskaan. Tästä johtuen catch SQLexception sisältää vain ja ainoastaan kriittisiä virheitä jotka saattavat johtua esimerkiksi korruptoituneesta tietokannasta tai vastaavaa.

## Testauskattavuus

Varsinaisen testikattavuus on rivien osalta 77% ja haaraumien osalta 86%.

<img src="https://raw.githubusercontent.com/EgoTastic/LeagueCounterPicker/main/Dokumentaatio/Kuvat/testaus1.png">

## Järjestelmätestaus

Sovellus on testattu macOS ja Windows ympäristöissä. Cubbli-linux opiskelijatunnuksilla ei anna sqliten kirjoittaa tietokantaan mitään, tunnettu ongelma telegrammista päätellen.

