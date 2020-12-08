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
        enemyChampionList.add("Champion");
        enemyChampionList.add("Champion");
        enemyChampionList.add("Champion");
        enemyChampionList.add("Champion");
        String retValue = handler.getBaseRecommendation(enemyChampionList, "Role");
        assertEquals("Alistar with 53.80%", retValue);
    }
    
    @Test
    public void BaseCounterWithEmptyList(){
        ArrayList<String> enemyChampionList = new ArrayList<>();
        enemyChampionList.add("Champion");
        enemyChampionList.add("Champion");
        enemyChampionList.add("Champion");
        enemyChampionList.add("Champion");
        enemyChampionList.add("Champion");
        String retValue = handler.getBaseRecommendation(enemyChampionList, "Role");
        assertEquals("No champions selected!", retValue);
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
        enemyChampionList.add("Champion");
        enemyChampionList.add("Champion");
        enemyChampionList.add("Champion");
        enemyChampionList.add("Champion");
        String result = handler.getPersonalRecommendation(enemyChampionList, "Top");
        assertEquals("Fiora with 100.00%", result);
    }
    
    @Test
    public void persCounterWithEmptyList(){
        ArrayList<String> enemyChampionList = new ArrayList<>();
        enemyChampionList.add("Champion");
        enemyChampionList.add("Champion");
        enemyChampionList.add("Champion");
        enemyChampionList.add("Champion");
        enemyChampionList.add("Champion");
        String retValue = handler.getPersonalRecommendation(enemyChampionList, "ADC");
        assertEquals("No champions selected!", retValue);        
    }
    
    @Test
    public void perCounterWithDuplicates(){
        ArrayList<String> enemyChampionList = new ArrayList<>();
        enemyChampionList.add("Champion");
        enemyChampionList.add("Champion");
        enemyChampionList.add("Champion");
        enemyChampionList.add("Akali");
        enemyChampionList.add("Akali");
        String retValue = handler.getPersonalRecommendation(enemyChampionList, "ADC");
        assertEquals("No duplicate champions!", retValue);
    }
    
    @Test
    public void baseCounterWithDuplicates(){
        ArrayList<String> enemyChampionList = new ArrayList<>();
        enemyChampionList.add("Champion");
        enemyChampionList.add("Champion");
        enemyChampionList.add("Champion");
        enemyChampionList.add("Akali");
        enemyChampionList.add("Akali");
        String retValue = handler.getBaseRecommendation(enemyChampionList, "ADC");
        assertEquals("No duplicate champions!", retValue);
    }
    
    @Test
    public void persCounterWithNoExistingStats(){
        ArrayList<String> enemyChampionList = new ArrayList<>();
        enemyChampionList.add("Ezreal");
        enemyChampionList.add("Champion");
        enemyChampionList.add("Champion");
        enemyChampionList.add("Champion");
        enemyChampionList.add("Champion");
        String result = handler.getPersonalRecommendation(enemyChampionList, "Middle");
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
        boolean result = handler.saveMatch(true, champs, "Champion", "Middle");
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
        boolean result = handler.saveMatch(true, champs, "Aatrox", "Middle");
        assertFalse(result);
    }
    
    @Test
    public void nameChampWithCorrectInput(){
        String comp = handler.championNameAndStatistic(1, 0.6);
        
        assertEquals("Aatrox with 60.00%", comp);
    }
    
    @Test
    public void nameChampWithIncorrectInput(){
        String comp = handler.championNameAndStatistic(0, 0.5);
        
        assertEquals("No statistics", comp);
    }
    
    @Test
    public void databaseExists(){
        assertTrue(handler.connectionToDatabase());
    }
    
}
