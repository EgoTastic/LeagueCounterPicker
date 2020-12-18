/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LeaguePickerTests;

import java.util.ArrayList;
import leaguecounter.domain.DataAssistingTools;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

/**
 *
 * @author miikakoskela
 */
public class DataAssistingToolsTest {
    
    
    @Test
    public void countingNewStatVictory() {
        String newStat = DataAssistingTools.countNewStatistic(true, "1,1");
        assertEquals("2,2", newStat);
    }
    
    @Test
    public void countingNewStatDefeat() {
        String newStat = DataAssistingTools.countNewStatistic(false, "1,1");
        assertEquals("1,2", newStat);
    }
    
    @Test
    public void correctTranformFromStringToDouble() {
        
        ArrayList<String> stringWin = new ArrayList<>();
        stringWin.add("2,4");
        ArrayList<Double> percents = DataAssistingTools.transformMatchStatisticsToWinRates(stringWin);
        
        assertTrue(0.5 == percents.get(0));
    }
    
    @Test
    public void transformReturnsEmptyList() {
        ArrayList<String> stringWin = new ArrayList<>();
        stringWin.add("0,0");
        ArrayList<Double> percents = DataAssistingTools.transformMatchStatisticsToWinRates(stringWin);
        
        assertTrue(percents.isEmpty());
    }    
    
    @Test
    public void countWinRateWithCorrectInput() {
        ArrayList<Double> winRates = new ArrayList<>();
        winRates.add(0.0);
        winRates.add(1.0);
        double winRate = DataAssistingTools.countWinRate(winRates, false);
        assertTrue(0.5 == winRate);
    }
    
    @Test
    public void countWinRateWithIncorrectInput() {
        ArrayList<Double> winRates = new ArrayList<>();
        double winRate = DataAssistingTools.countWinRate(winRates, true);
        assertTrue(0.0 == winRate);
    }
    
    @Test
    public void shortenTop() {
        assertEquals("top", DataAssistingTools.shortenRole("Top"));
    }
    
    @Test
    public void shortenJungle() {
        assertEquals("jgl", DataAssistingTools.shortenRole("Jungle"));
    }
    
    @Test
    public void shortenMiddle() {
        assertEquals("mid", DataAssistingTools.shortenRole("Middle"));
    }
    
    @Test
    public void shortenADC() {
        assertEquals("adc", DataAssistingTools.shortenRole("ADC"));
    }
    
    @Test
    public void shortenSupport() {
        assertEquals("sup", DataAssistingTools.shortenRole("Support"));
    }
    
    @Test
    public void shortenRolewithRole() {
        assertEquals("Role", DataAssistingTools.shortenRole("Role"));
    }
    
    @Test
    public void checkEnemyListWithEmpty() {
        ArrayList<String> enemyChampions = new ArrayList<>();
        enemyChampions.add("Champion");
        enemyChampions.add("Champion");
        enemyChampions.add("Champion");
        enemyChampions.add("Champion");
        enemyChampions.add("Champion");
        String test = DataAssistingTools.checkEnemyList(enemyChampions);
        assertEquals("No champions selected!", test);
    }
    
    @Test
    public void checkEnemyListWithDuplicate() {
        ArrayList<String> enemyChampions = new ArrayList<>();
        enemyChampions.add("Champion");
        enemyChampions.add("Akali");
        enemyChampions.add("Brand");
        enemyChampions.add("Pyke");
        enemyChampions.add("Brand");
        String test = DataAssistingTools.checkEnemyList(enemyChampions);
        assertEquals("No duplicate champions!", test);
    }
    
    @Test
    public void checkEnemyListWithGoodList() {
        ArrayList<String> enemyChampions = new ArrayList();
        enemyChampions.add("Champion");
        enemyChampions.add("Brand");
        enemyChampions.add("Ahri");
        enemyChampions.add("Pyke");
        enemyChampions.add("Champion");
        String test = DataAssistingTools.checkEnemyList(enemyChampions);
        assertEquals("OK", test);
    }
    
}
