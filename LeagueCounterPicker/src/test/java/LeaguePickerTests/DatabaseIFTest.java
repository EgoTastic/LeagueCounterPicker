package LeaguePickerTests;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import leaguecounter.database.DatabaseIF;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author miikakoskela
 */

public class DatabaseIFTest {
    
    private DatabaseIF database;
    
    @BeforeEach
    public void setUp() {
        database = new DatabaseIF();
    }
    
    @Test
    public void parseStatementCorrectly(){
        ArrayList<String> champs = new ArrayList<>();
        champs.add("Aatrox");
        champs.add("Blitzcrank");
        champs.add("Lux");
        
        String statement = database.parseStatement(champs, 19, "ownStatistics");
        
        assertEquals("SELECT \"Aatrox\", \"Blitzcrank\", \"Lux\" FROM ownStatistics WHERE id = 19", statement);
    }
    
    @Test
    public void parseStatementWithWrongInput(){
        ArrayList<String> champs = new ArrayList<>();
        champs.add("Champion");
        
        String statement = database.parseStatement(champs, 18, "ownStatistics");
        
        assertEquals("Error", statement);
    }
    
    @Test
    public void getMatchesOwnEmptyList() {
        ArrayList<String> champs = new ArrayList<>();
        ArrayList<String> test = database.getWinRatesOwnStatistics(champs, 0);
        assertTrue(test.isEmpty());
    }
    
    @Test
    public void getMatchesPersEmptyList() {
        ArrayList<String> champs = new ArrayList<>();
        ArrayList<Double> test = database.getWinRatesBaseStatistics(champs, 0);
        assertTrue(test.isEmpty());
    }
    
    @Test
    public void checkBaseRoleCorrect() {
        assertTrue(database.checkRoleBaseRoles(1, "top"));
    }
    
    @Test
    public void checkBaseRoleIncorrect() {
        assertFalse(database.checkRoleBaseRoles(1, "adc"));
    }
    
    @Test
    public void checkBaseRoleNotExistingChamp() {
        assertFalse(database.checkRoleBaseRoles(190, "top"));
    }
    
    @Test
    public void getChampNameCorrect(){
        String name = database.getChampionName(1);
        assertTrue("Aatrox".equals(name));
    }
    
    @Test
    public void getChampNameIncorrect0(){
        String name = database.getChampionName(0);
        assertTrue("Error".equals(name));
    }
    
    @Test
    public void getCHampNameIncorrect1000(){
        String name = database.getChampionName(1000);
        assertTrue("Error".equals(name));
    }
    
    @Test
    public void getMatchStatisticsCorrect(){
        String statistic = database.getMatchStatistic("Aatrox", "Blitzcrank");
        assertTrue("1,1".equals(statistic));
    }
    
    @Test
    public void getMatchStatisticIncorrect(){
        String statistic = database.getMatchStatistic("Test", "Test2");
        assertTrue("".equals(statistic));
    }
}
