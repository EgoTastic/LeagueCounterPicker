package LeaguePickerTests;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import leaguecounter.database.DatabaseIF;
import org.junit.jupiter.api.BeforeAll;

/**
 *
 * @author miikakoskela
 */

public class DatabaseIFTest {
    
    private static DatabaseIF database;
    
    @BeforeAll
    public static void setUp() {
        database = new DatabaseIF();
    }
    
    @Test
    public void parseStatementCorrectly() {
        ArrayList<String> champs = new ArrayList<>();
        champs.add("Aatrox");
        champs.add("Blitzcrank");
        champs.add("Lux");
        
        String statement = database.parseStatement(champs, 19, "ownStatistics");
        
        assertEquals("SELECT \"Aatrox\", \"Blitzcrank\", \"Lux\" FROM ownStatistics WHERE id = 19", statement);
    }
    
    @Test
    public void parseStatementWithWrongInput() {
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
    public void checkBaseRolesWithRole() {
        assertTrue(database.checkRoleBaseRoles(9, "Role"));
    }
    
    @Test
    public void getChampNameCorrect() {
        String name = database.getChampionName(1);
        assertTrue("Aatrox".equals(name));
    }
    
    @Test
    public void getChampNameIncorrect0() {
        String name = database.getChampionName(0);
        assertTrue("Error".equals(name));
    }
    
    @Test
    public void getChampNameIncorrect1000() {
        String name = database.getChampionName(1000);
        assertTrue("Error".equals(name));
    }
    
    @Test
    public void getMatchStatisticsCorrect() {
        String statistic = database.getMatchStatistic("Aatrox", 13);
        assertTrue("1,1".equals(statistic));
    }
    
    @Test
    public void getMatchStatisticIncorrect() {
        String statistic = database.getMatchStatistic("Test", 0);
        assertTrue("".equals(statistic));
    }
    
    @Test
    public void databaseExists() {
        assertTrue(database.checkIfDatabaseExists());
    }
    
    @Test
    public void checkOwnRoleCorrect() {
        assertTrue(database.checkRoleOwnRoles(13, "sup"));
    }
    
    @Test
    public void checkOwnRoleIncorret() {
        assertFalse(database.checkRoleOwnRoles(13, "adc"));
    }
    
    @Test
    public void checkOwnRoleNotExistingChamp() {
        assertFalse(database.checkRoleOwnRoles(190, "top"));
    }
    
    @Test
    public void checkOwnRoleWithRole() {
        assertTrue(database.checkRoleOwnRoles(8, "Role"));
    }
    
    @Test
    public void saveNewStatistic() {
        String oldStat = database.getMatchStatistic("Vladimir", 83);
        assertTrue(database.setMatchStatistic("Vladimir", 83, "50,80"));
        assertEquals("50,80", database.getMatchStatistic("Vladimir", 83));
        assertTrue(database.setMatchStatistic("Vladimir", 83, oldStat));
        
    }
    
    @Test
    public void saveRole() {
        assertTrue(database.setRolePlayed(79, "sup", "1"));
        assertTrue(database.checkRoleOwnRoles(79, "sup"));
        database.setRolePlayed(79, "sup", "null");
        
    }
    
    @Test
    public void getChampionIdCorrectName() {
        int id = database.getChampionId("Nami");
        assertTrue(id == 77);
    }
    
    @Test
    public void getChampionIdWrongName() {
        int id = database.getChampionId("Matti");
        assertTrue(id == 0);
    }
}
