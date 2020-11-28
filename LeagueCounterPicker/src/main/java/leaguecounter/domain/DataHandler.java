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
    
    public boolean connectionToDatabase() {      
        return this.database.checkIfDatabaseExists();
    }
    
    // Save function for both victory and defeat cases
    public boolean saveMatch(Boolean victoryStatus, ArrayList<String> enemyChampionList, String championPicked) {
               
        for (String enemyChampion:enemyChampionList) {
            if ("Champion".equals(enemyChampion) || "Champion".equals(championPicked)) {
                return false;
            }
        }
        
        for (String enemyChampion:enemyChampionList) {                      
            String newStatToSave = countNewStatistic(victoryStatus, this.database.getMatchStatistic(enemyChampion, championPicked));            
            this.database.setMatchStatistic(enemyChampion, championPicked, newStatToSave);            
        }        
        
        return true;
    }
    
    // Get function for recommendation based on personalized pick
    public String getPersonalRecommendation(ArrayList<String> enemyChampionList) {
        if (enemyChampionList.isEmpty()) {
            return "No champs selected";
        }
        
        double bestPercent = 0.0;
        double comparePercent = 0.0;
        int bestChampion = 0;      
        
        for (int i = 1; i < 153; i++) {
            
            comparePercent = countWinRate(transformMatchStatisticsToWinRates(this.database.getWinRatesOwnStatistics(enemyChampionList, i)));
            
            if (comparePercent == -1) {
                continue;
            } else {
                            
                if (comparePercent >= bestPercent) {
                    bestPercent = comparePercent;
                    bestChampion = i;
                }
            }
        }       
        return championNameAndStatistic(bestChampion, bestPercent);
    }
     
    // Get function for recommandion based on global statistics
    public String getBaseRecommendation(ArrayList<String> enemyChampionList, String role) {
        if (enemyChampionList.isEmpty()) {
            return "No champs selected";
        }
        
        double bestPercent = 0.0;
        int bestChamp = 0;
        double comparePercent;
        for (int i = 1; i < 153; i++) {
            if (!this.database.checkRoleBaseRoles(i, shortenRole(role))) {
                continue;
            }
            
            comparePercent = countWinRate(this.database.getWinRatesBaseStatistics(enemyChampionList, i));
            
            if (comparePercent >= bestPercent) {
                bestPercent = comparePercent;
                bestChamp = i;
            }              
        }
        
        return championNameAndStatistic(bestChamp, bestPercent);

    }
    
    // Return list of all possible champion choices (used for choiceBox creation)
    public ArrayList<String> listChampions() {
        
        ArrayList<String> championList = new ArrayList<>();
        championList.add("Champion");
        ArrayList<String> temporaryList = this.database.getChampionList();
        for (String champion:temporaryList) {
            championList.add(champion);
        }
        
        return championList;
    } 
    
    // Counts the average winrate from list of doubles
    public double countWinRate(ArrayList<Double> listOfWinRates) {
        
        if (listOfWinRates.isEmpty()) {
            return -1;
        }
        
        double totalWinRate = 0.0;
        int amountOfWinRates = 0;
        
        for (double winRate:listOfWinRates) {
            totalWinRate += winRate;
            amountOfWinRates++;
        }
        return totalWinRate / amountOfWinRates;
    }
    
    // Parses the name of the champ and rest of the string to show to user
    public String championNameAndStatistic(int bestChampion, double bestWinRate) {
        
        String championName = this.database.getChampionName(bestChampion);
        if (championName.equals("Error")) {
            return "No statistics";
        } else {
            String nameAndStatistic = championName + " with " + String.format("%.2f", bestWinRate * 100) + "%";
            return nameAndStatistic.replace(",", ".");
        }
    }
      
    // Transforms a list of String statistics into double percent win rates
    public ArrayList<Double> transformMatchStatisticsToWinRates(ArrayList<String> listOfMatchStatistics) {
        
        ArrayList<Double> winRates = new ArrayList<>();
        
        for (String matchStatistic: listOfMatchStatistics) {
            
            int wins = Integer.parseInt(matchStatistic.split(",")[0]);
            int total = Integer.parseInt(matchStatistic.split(",")[1]);
            
            if (total == 0) {
                continue;
            } else {           
                double winRate = (double) wins / total;
                winRates.add(winRate);
            }
        }
        
        return winRates;
    }
    
    // Parses the newStat to save to database
    public String countNewStatistic(boolean victoryStatus, String oldStatistic) {
        
        int wins = Integer.parseInt(oldStatistic.split(",")[0]);
        int total = Integer.parseInt(oldStatistic.split(",")[1]);
        
        if (victoryStatus) {
            wins++;
            total++;
        } else {
            total++;
        }
        
        return wins + "," + total;
    }
    
    // Shortens the string role to be used with database
    public String shortenRole(String role) {
        
        if (role.equals("Top")) {
            return "top";
        }        
        if (role.equals("Jungle")) {
            return "jgl";
        }
        if (role.equals("Middle")) {
            return "mid";
        }
        if (role.equals("ADC")) {
            return "adc";
        }
        if (role.equals("Support")) {
            return "sup";
        }
        
        return role;
    }
}
