package LeaguePickerTests;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import leaguecounter.domain.DataHandler;
import leaguecounter.database.DatabaseIF;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author miikakoskela
 */
public class DataHandlerTest{
    
    private DataHandler handler;
    private DatabaseIF database;
       
    @BeforeEach
    public void setUp(){
        handler = new DataHandler();
        database = new DatabaseIF();
    }
    
    @Test
    public void normCounterAkali(){
        ArrayList<String> champs = new ArrayList<>();
        champs.add("Akali");
        String retValue = handler.getNormPick(champs);
        assertEquals("Zyra with 50.0%", retValue);
    }
    
    @Test
    public void normCounterWithEmptyList(){
        ArrayList<String> champs = new ArrayList<>();
        String retValue = handler.getNormPick(champs);
        assertEquals("No champs selected", retValue);
    }
    
    @Test
    public void listChampionsRightAmount(){
        ArrayList<String> champs = handler.listChampions();
        assertTrue(champs.size() == 153);
    }
    
    @Test
    public void getChampNameCorrect(){
        String name = database.getChampName(1);
        assertTrue("Aatrox".equals(name));
    }
    
    @Test
    public void getChampNameIncorrect(){
        String name = database.getChampName(0);
        assertTrue("Error".equals(name));
    }
    
    @Test
    public void persCounterAkali(){
        ArrayList<String> champs = new ArrayList<>();
        champs.add("Akali");
        String result = handler.getPersPick(champs);
        assertEquals("Aatrox with 50.0%", result);
    }
    
    @Test
    public void persCounterWithEmptyList(){
        ArrayList<String> champs = new ArrayList<>();
        String retValue = handler.getPersPick(champs);
        assertEquals("No champs selected", retValue);        
    }
    
    
    @Test
    public void persCounterWithNoExistingStats(){
        ArrayList<String> champs = new ArrayList<>();
        champs.add("Ezreal");
        String result = handler.getPersPick(champs);
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
        boolean result = handler.save(true, champs, "Champion");
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
        boolean result = handler.save(true, champs, "Aatrox");
        assertFalse(result);
    }
}
