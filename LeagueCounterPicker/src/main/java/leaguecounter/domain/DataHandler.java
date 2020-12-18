package leaguecounter.domain;

import leaguecounter.database.DatabaseIF;
import leaguecounter.domain.DataAssistingTools;
import java.util.ArrayList;

/**
 *
 * @author miikakoskela
 */

/**
 * Class that handles main logic of the software and converges with database class
 * 
 * @author miikakoskela
 */
public class DataHandler {
    private final DatabaseIF database;
    
    /**
     * Class constructor
     */
    public DataHandler() {
        this.database = new DatabaseIF();
    }
    
    /**
     * Checks if the database is correct and not empty, created by the sqlite
     * 
     * @see leaguecounter.database.DatabaseIF#checkIfDatabaseExists()
     * 
     * @return  Returns true if everything is correct and false if incorrect
     */
    public boolean connectionToDatabase() {      
        return this.database.checkIfDatabaseExists();
    }
    
    /**
     * Method saves given match statistic (either victory or defeat) to the database.
     * Also saves that the user has played the picked champion at the picked role, and that information will be used in future recommendations.
     * 
     * @param victoryStatus Status if the match was won or lost
     * @param enemyChampionList List of all enemy champions that the player played against
     * @param championPicked The Champion, that the player played as
     * @param role The role that the player was playing
     * 
     * @see leaguecounter.database.DatabaseIF#setRolePlayed(String, String, String) 
     * @see leaguecounter.domain.DataAssistingTools#shortenRole(String)
     * @see leaguecounter.database.DatabaseIF#getMatchStatistic(String, String) 
     * @see leaguecounter.domain.DataAssistingTools#countNewStatistic(boolean, String)
     * @see leaguecounter.database.DatabaseIF#setMatchStatistic(String, String, String)
     * 
     * @return Returns true, if the save was succesfull, if the given information is incomplete, returns false
     */
    public boolean saveMatch(Boolean victoryStatus, ArrayList<String> enemyChampionList, String championPicked, String role) {
               
        for (String enemyChampion:enemyChampionList) {
            if ("Champion".equals(enemyChampion) || "Champion".equals(championPicked) || "Role".equals(role)) {
                return false;
            }
        }
        this.database.setRolePlayed(championPicked, DataAssistingTools.shortenRole(role), "1");
        
        for (String enemyChampion:enemyChampionList) {                      
            String newStatToSave = DataAssistingTools.countNewStatistic(victoryStatus, this.database.getMatchStatistic(enemyChampion, championPicked));            
            this.database.setMatchStatistic(enemyChampion, championPicked, newStatToSave);            
        }        
        
        return true;
    }
    
    /**
     * Method finds the most suitable pick to recommend to the user, based on users own statistics
     * Takes into consideration the wanted role, if given
     * 
     * @param enemyChampionList List of all enemy champions known so far
     * @param role The role that the player intends to play
     * 
     * @see leaguecounter.domain.DataAssistingTools#checkEnemyList(ArrayList) 
     * @see leaguecounter.domain.DataAssistingTools#shortenRole(String) 
     * @see leaguecounter.database.DatabaseIF#checkRoleOwnRoles(int, String) 
     * @see leaguecounter.database.DatabaseIF#getWinRatesOwnStatistics(ArrayList, int) 
     * @see leaguecounter.domain.DataAssistingTools#transformMatchStatisticsToWinRates(ArrayList) 
     * @see leaguecounter.domain.DataAssistingTools#countWinRate(ArrayList, boolean) 
     * @see leaguecounter.domain.DataHandler#championNameAndStatistic(int, double) 
     * 
     * @return Returns string to show to the user, that includes most suitable champion and its winrate
     */
    public String getPersonalRecommendation(ArrayList<String> enemyChampionList, String role) {
        String errorStatus = DataAssistingTools.checkEnemyList(enemyChampionList);
        if (!errorStatus.equals("OK")) {
            return errorStatus;
        } 
        
        double bestPercent = 0.0;
        double comparePercent;
        int bestChampion = 0;      
        
        for (int i = 1; i < 153; i++) {
            if (!this.database.checkRoleOwnRoles(i, DataAssistingTools.shortenRole(role))) {
                continue;
            }
            
            comparePercent = DataAssistingTools.countWinRate(DataAssistingTools.transformMatchStatisticsToWinRates(this.database.getWinRatesOwnStatistics(enemyChampionList, i)), false);
            

                            
            if (comparePercent > bestPercent) {
                bestPercent = comparePercent;
                bestChampion = i;
            }
            
        }       
        return championNameAndStatistic(bestChampion, bestPercent);
    }
     
    /**
     * Method finds the most suitable pick to recommend to the user, based on users own statistics
     * Takes into consideration the wanted role, if given
     * 
     * @param enemyChampionList List of all enemy champions known so far
     * @param role The role that the player intends to play
     * 
     * @see leaguecounter.domain.DataAssistingTools#checkEnemyList(ArrayList) 
     * @see leaguecounter.domain.DataAssistingTools#shortenRole(String) 
     * @see leaguecounter.database.DatabaseIF#checkRoleOwnRoles(int, String) 
     * @see leaguecounter.database.DatabaseIF#getWinRatesOwnStatistics(ArrayList, int) 
     * @see leaguecounter.domain.DataAssistingTools#countWinRate(ArrayList, boolean) 
     * @see leaguecounter.domain.DataHandler#championNameAndStatistic(int, double) 
     * 
     * @return Returns string to show to the user, that includes most suitable champion and its winrate
     */
    public String getBaseRecommendation(ArrayList<String> enemyChampionList, String role) {
        String errorStatus = DataAssistingTools.checkEnemyList(enemyChampionList);
        if (!errorStatus.equals("OK")) {
            return errorStatus;
        }
        
        double bestPercent = 0.0;
        int bestChamp = 0;
        double comparePercent;
        for (int i = 1; i < 153; i++) {
            if (!this.database.checkRoleBaseRoles(i, DataAssistingTools.shortenRole(role))) {
                continue;
            }
            
            comparePercent = DataAssistingTools.countWinRate(this.database.getWinRatesBaseStatistics(enemyChampionList, i), true);
            
            if (comparePercent >= bestPercent) {
                bestPercent = comparePercent;
                bestChamp = i;
            }              
        }
        
        return championNameAndStatistic(bestChamp, bestPercent);

    }
    
    /**
     * Fetches list of all possible champions
     * 
     * @see leaguecounter.database.DatabaseIF#getChampionList() 
     * 
     * @return List of all champion names
     */
    public ArrayList<String> listChampions() {
        
        ArrayList<String> championList = new ArrayList<>();
        championList.add("Champion");
        ArrayList<String> temporaryList = this.database.getChampionList();
        for (String champion:temporaryList) {
            championList.add(champion);
        }
        
        return championList;
    } 
    
    /**
     * Puts together string to show to the user
     * 
     * @see leaguecounter.database.DatabaseIF#getChampionName(int) 
     * 
     * @param bestChampion The id of the champion with the best winrate
     * @param bestWinRate The winrate of the most suitable champion
     * 
     * @return Returns string that the UI shows to the user
     */
    public String championNameAndStatistic(int bestChampion, double bestWinRate) {
        
        String championName = this.database.getChampionName(bestChampion);
        if (championName.equals("Error")) {
            return "No statistics";
        } else {
            String nameAndStatistic = championName + " with " + String.format("%.2f", bestWinRate * 100) + "%";
            return nameAndStatistic.replace(",", ".");
        }
    }
    
    /**
     * Resets all personal statistics in the database
     * 
     * @see leaguecounter.database.DatabaseIF#resetAllPersonalStatistics()
     */
    public void resetStats() {       
        database.resetAllPersonalStatistics();
    }
    

}
