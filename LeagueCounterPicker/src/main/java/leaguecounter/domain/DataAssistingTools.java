package leaguecounter.domain;

import java.util.ArrayList;

/**
 *
 * @author miikakoskela
 */

/**
 * Class includes assisting methods that are commonly used in DataHandler
 */
public class DataAssistingTools {
    
     /**
     * Counts the average win rates from the given list
     * Behaves differently depending on wether the list is from base or own statistics
     * 
     * @param listOfWinRates list of all win rates
     * @param baseStatistics status if the winrates are from base or own statistics
     * @return the average winrate from the list
     */
    public static double countWinRate(ArrayList<Double> listOfWinRates, boolean baseStatistics) {
        
        if (baseStatistics) {
            return listOfWinRates.stream().filter(d -> d > 0).mapToDouble(d -> d).average().orElse(0.0);
        } else {
            return listOfWinRates.stream().mapToDouble(d -> d).average().orElse(0.0);
        }
    }
      
    /**
     * Transforms a list of match statistics (wins, total) into percentages
     * 
     * @param listOfMatchStatistics List of the statististics
     * 
     * @return ArrayList that consists winrates for the given win statistics
     */
    public static ArrayList<Double> transformMatchStatisticsToWinRates(ArrayList<String> listOfMatchStatistics) {
        
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
    
    /**
     * Puts together the new statistic to save to the database
     * 
     * @param victoryStatus Boolean if the match was won or lost
     * @param oldStatistic The old statistic that this method modifies
     * 
     * @return String of the new statistic
     */
    public static String countNewStatistic(boolean victoryStatus, String oldStatistic) {
        
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
    
    /**
     * Shortens the role from the UI choiceBox to be used with SQL query
     * Returns "Role" if no role was picked
     * 
     * @param role The role that user picked (or didn't)
     * 
     * @return String of shortened version of picked role
     */
    public static String shortenRole(String role) {
        
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
    
    /**
     * Checks if chosen enemy champions contains duplicates
     * 
     * @param enemyChampions List of enemy champions
     * 
     * @return Errortext to show, if there is something wrong
     */
    public static String checkEnemyList(ArrayList<String> enemyChampions) {
        
        int emptyChampion = 0;
        for (String champion : enemyChampions) {
            
            if (champion.equals("Champion") && emptyChampion < 4) {
                emptyChampion++;
            } else if (champion.equals("Champion")) {
                return "No champions selected!";
            }
            
            int sameChamp = 0;
            for (String champion2 : enemyChampions) {
                if (champion.equals(champion2) && !champion.equals("Champion")) {
                    sameChamp++;
                } 
                
                if (sameChamp == 2) {
                    return "No duplicate champions!";
                }
            }
            
        }
        
        return "OK";
    }
    
}
