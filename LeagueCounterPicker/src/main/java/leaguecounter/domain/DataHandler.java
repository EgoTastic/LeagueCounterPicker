package leaguecounter.domain;
import leaguecounter.database.DatabaseIF;
import java.util.ArrayList;

/**
 *
 * @author miikakoskela
 */
public class DataHandler {
    private DatabaseIF database;
    
    public DataHandler() {
        this.database = new DatabaseIF();
    }
    
    public boolean save(Boolean victory, ArrayList<String> champs, String pick) {
        
        
        for (String champ:champs) {
            if (champ == "Champion") {
                return false;
            }
        }
        if (pick == "Champion") {
            return false;
        }
        
        for (String champ:champs) {
            
            String matchResult = this.database.getMatch(champ, pick);
            
            String[] parts = matchResult.split(",");
            int won = Integer.parseInt(parts[0]);
            int total = Integer.parseInt(parts[1]);
            
            if (victory) {
                won += 1;
                total += 1;
            } else {
                total += 1;
            }
            
            String newStat = won + "," + total;
            
            this.database.setMatch(champ, pick, newStat);
            
        }

        
        return true;
    }
    
    public String getPersPick(ArrayList<String> champs) {
        if (champs.isEmpty()) {
            return "No champs selected";
        }
        double bestPercent = 0.0;
        double compPercent = 0.0;
        int bestChamp = 0;
        ArrayList<String> matchResults = null;        
        
        for (int i = 1; i < 153; i++) {
            
            matchResults =  this.database.getMatchesOwn(champs, i);
            compPercent = 0;
            int a = 0;
            
            for (String stat:matchResults) {
                String[] parts = stat.split(",");
                int victory = Integer.parseInt(parts[0]);
                int total = Integer.parseInt(parts[1]);
                if (total == 0) {
                    continue;
                }
                compPercent += (double) victory / total;
                a++;
            }
            if (a == 0) {
                continue;
            } else {
                
            
                if (compPercent >= bestPercent) {
                    bestPercent = compPercent;
                    bestChamp = i;
                }
            }
        }
        
        String nameChamp = this.database.getChampName(bestChamp);
        if (nameChamp.equals("Error")) {
            return "No statistics";
        } else {
            return nameChamp + " with " + bestPercent * 100 + "%";
        }
    }
    
    public String getNormPick(ArrayList<String> champs) {
        if (champs.isEmpty()) {
            return "No champs selected";
        }
        double bestPercent = 0.0;
        int bestChamp = 0;
        double compPercent = 0;
        for (int i = 1; i < 153; i++) {
            ArrayList<Double> percents = this.database.getPercentBase(champs, i);
            compPercent = 0;
            int a = 0;
            
            for (double percent:percents) {
                compPercent += percent;
                a++;
            }
            
            compPercent = compPercent / a;
            if (compPercent >= bestPercent) {
                bestPercent = compPercent;
                bestChamp = i;
            }   
            
            
        }
        
        String nameChamp = this.database.getChampName(bestChamp);
        if (nameChamp.equals("Error")) {
            return "Error";
        } else {
            return nameChamp + " with " + bestPercent * 100 + "%";
        }

    }
    
    public ArrayList<String> listChampions() {
        ArrayList<String> champlist = new ArrayList<>();
        champlist.add("Champion");
        ArrayList<String> list = this.database.getChampList();
        for (String champ:list) {
            champlist.add(champ);
        }
        
        return champlist;
    }
}
