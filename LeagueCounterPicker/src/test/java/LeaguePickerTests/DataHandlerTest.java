package LeaguePickerTests;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import leaguecounter.domain.DataHandler;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author miikakoskela
 */

public class DataHandlerTest{
    
    private DataHandler handler;
       
    @BeforeEach
    public void setUp(){
        handler = new DataHandler();
    }
    
    @Test
    public void BaseCounterZyra(){
        ArrayList<String> enemyChampionList = new ArrayList<>();
        enemyChampionList.add("Zyra");
        String retValue = handler.getBaseRecommendation(enemyChampionList, "Role");
        assertEquals("Alistar with 53,80%", retValue);
    }
    
    @Test
    public void BaseCounterWithEmptyList(){
        ArrayList<String> enemyChampionList = new ArrayList<>();
        String retValue = handler.getBaseRecommendation(enemyChampionList, "Role");
        assertEquals("No champs selected", retValue);
    }
    
    @Test
    public void listChampionsRightAmount(){
        ArrayList<String> champs = handler.listChampions();
        assertTrue(champs.size() == 153);
    }
    
    @Test
    public void persCounterAkali(){
        ArrayList<String> enemyChampionList = new ArrayList<>();
        enemyChampionList.add("Akali");
        String result = handler.getPersonalRecommendation(enemyChampionList);
        assertEquals("Blitzcrank with 100,00%", result);
    }
    
    @Test
    public void persCounterWithEmptyList(){
        ArrayList<String> enemyChampionList = new ArrayList<>();
        String retValue = handler.getPersonalRecommendation(enemyChampionList);
        assertEquals("No champs selected", retValue);        
    }
    
    @Test
    public void persCounterWithNoExistingStats(){
        ArrayList<String> enemyChampionList = new ArrayList<>();
        enemyChampionList.add("Ezreal");
        String result = handler.getPersonalRecommendation(enemyChampionList);
        assertEquals("No statistics", result);
    }
    
    @Test
    public void saveWithWrongPick(){
        ArrayList<String> champs = new ArrayList<>();
        champs.add("Akali");
        champs.add("Ahri");
        champs.add("Annie");
        champs.add("Ezreal");
        champs.add("Lux");       
        boolean result = handler.saveMatch(true, champs, "Champion");
        assertFalse(result);
    }
    
    @Test
    public void saveWithWrongList(){
        ArrayList<String> champs = new ArrayList<>();
        champs.add("Akali");
        champs.add("Ahri");
        champs.add("Annie");
        champs.add("Ezreal");
        champs.add("Champion");
        boolean result = handler.saveMatch(true, champs, "Aatrox");
        assertFalse(result);
    }
    
    @Test
    public void countingNewStatVictory(){
        String newStat = handler.countNewStatistic(true, "1,1");
        assertEquals("2,2", newStat);
    }
    
    @Test
    public void countingNewStatDefeat(){
        String newStat = handler.countNewStatistic(false, "1,1");
        assertEquals("1,2", newStat);
    }
    
    @Test
    public void correctTranformFromStringToDouble(){
        
        ArrayList<String> stringWin = new ArrayList<>();
        stringWin.add("2,4");
        ArrayList<Double> percents = handler.transformMatchStatisticsToWinRates(stringWin);
        
        assertTrue(0.5 == percents.get(0));
    }
    
    @Test
    public void transformReturnsEmptyList(){
        ArrayList<String> stringWin = new ArrayList<>();
        stringWin.add("0,0");
        ArrayList<Double> percents = handler.transformMatchStatisticsToWinRates(stringWin);
        
        assertTrue(percents.isEmpty());
    }
    
    @Test
    public void nameChampWithCorrectInput(){
        String comp = handler.championNameAndStatistic(1, 0.6);
        
        assertEquals("Aatrox with 60,00%", comp);
    }
    
    @Test
    public void nameChampWithIncorrectInput(){
        String comp = handler.championNameAndStatistic(0, 0.5);
        
        assertEquals("No statistics", comp);
    }
    
    @Test
    public void countWinRateWithCorrectInput(){
        ArrayList<Double> winRates = new ArrayList<>();
        winRates.add(0.0);
        winRates.add(1.0);
        double winRate = handler.countWinRate(winRates);
        assertTrue(0.5 == winRate);
    }
    
    @Test
    public void countWinRateWithIncorrectInput(){
        ArrayList<Double> winRates = new ArrayList<>();
        double winRate = handler.countWinRate(winRates);
        assertTrue(-1 == winRate);
    }
    
    @Test
    public void shortenTop(){
        assertEquals("top", handler.shortenRole("Top"));
    }
    
    @Test
    public void shortenJungle() {
        assertEquals("jgl", handler.shortenRole("Jungle"));
    }
    
    @Test
    public void shortenMiddle() {
        assertEquals("mid", handler.shortenRole("Middle"));
    }
    
    @Test
    public void shortenADC() {
        assertEquals("adc", handler.shortenRole("ADC"));
    }
    
    @Test
    public void shortenSupport() {
        assertEquals("sup", handler.shortenRole("Support"));
    }
    
    @Test
    public void shortenRolewithRole() {
        assertEquals("Role", handler.shortenRole("Role"));
    }
    
    
}
