package leaguecounter.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author miikakoskela
 */

/**
 * Class that handles SQL database
 */
public class DatabaseIF {
    private Connection database;
    
    
    /**
     * Class constructor
     */
    public DatabaseIF() {
        try {
            this.database = DriverManager.getConnection("jdbc:sqlite:Picks.db");
        } catch (SQLException e) {
            System.out.println("Error DatabaseIF: " + e.getMessage());
        }               
    }
    
    /**
     * Checks if the database that the program is connected to, is suitable for the program
     * 
     * @return boolean of wether the program can continue or not
     */
    public boolean checkIfDatabaseExists() {
        
        boolean dbExists = false;
        
        try {
            ResultSet result = this.database.getMetaData().getTables(null, null, null, null);
            while (result.next()) {
                dbExists = true;
            }
        } catch (SQLException error) {
            System.out.println("Error checkIfDatabaseExistsAndConnect: " + error.getMessage());
        }
        
        return dbExists;
        
    }
    
    /**
     * Fetches the winrates for given champion against all the enemy champions from users own statistics
     * 
     * @param enemyChampionList List of all enemy champions
     * @param championToTest The champion ID that is being fetched from database
     * 
     * @see leaguecounter.database.DatabaseIF#parseStatement(ArrayList, int, String) 
     * 
     * @return ArrayList of all the win statistics given by the query
     */
    public ArrayList<String> getWinRatesOwnStatistics(ArrayList<String> enemyChampionList, int championToTest) {
        ArrayList<String> winRates = new ArrayList<>();
        
        String statement = parseStatement(enemyChampionList, championToTest, "ownStatistics");
        
        if (statement.equals("Error")) {
            return winRates;
        }
        
        
        try {
            PreparedStatement sqlstatement = this.database.prepareStatement(statement);
            ResultSet result = sqlstatement.executeQuery();
            
            for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
                winRates.add(result.getString(i));
            }
            
        } catch (SQLException error) {
            System.out.println("Error getWinRatesOwnStatistics: " + error.getMessage());
        }
        
        return winRates;
    }
    
    /**
     * Fetches the winrates for given champion against all the enemy chamoions from global statistics
     * 
     * @param enemyChampionList List of all enemy champions
     * @param championToTest The champion ID that is being fetched from database
     * 
     * @see leaguecounter.database.DatabaseIF#parseStatement(ArrayList, int, String) 
     * 
     * @return ArrayList of all the win statistics given by the query
     */
    public ArrayList<Double> getWinRatesBaseStatistics(ArrayList<String> enemyChampionList, int championToTest) {
         
        ArrayList<Double> winRates = new ArrayList<>();
        
        String statement = parseStatement(enemyChampionList, championToTest, "baseStatistics");
        
        if (statement.equals("Error")) {
            return winRates;
        }
        
        try {
            PreparedStatement sqlstatement = this.database.prepareStatement(statement); 
            ResultSet result = sqlstatement.executeQuery();
            
            for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
                winRates.add(result.getDouble(i));
            }
            
        } catch (SQLException error) {
            System.out.println("Error getWinRatesBaseStatistics: " + error.getMessage());
        }
        return winRates;
    }
    
    /**
     * Returns the champion name the given ID corresponds to.
     * If the champion is 0, returns "Error"
     * 
     * @param championNumber ID of the champion
     * @return Name of the champion
     */
    public String getChampionName(int championNumber) {
        
        if (championNumber == 0) {
            return "Error";
        }
        
        try {
            PreparedStatement sqlstatement = this.database.prepareStatement("SELECT champion FROM baseStatistics where id = " + championNumber);
            ResultSet result = sqlstatement.executeQuery();
            if (result.next()) {
                return result.getString(1);
            } 
            
        } catch (SQLException error) {
            System.out.println("Error getChampionName: " + error.getMessage());
        }
        
        return "Error";
        
    }
    
    /**
     * Fetches the list of all champions from the database
     * 
     * @return ArrayList of all champion names
     */
    public ArrayList<String> getChampionList() {
        
        ArrayList<String> championList = new ArrayList<>();
        try {
            PreparedStatement sqlstatement = this.database.prepareStatement("SELECT champion FROM baseStatistics");
            ResultSet result = sqlstatement.executeQuery();
            while (result.next()) {
                championList.add(result.getString("champion"));
            }
        } catch (SQLException error) {
            System.out.println("Error getChampionList: " + error.getMessage());
        }
        return championList;
    }
    
    /**
     * 
     * Fetches the statistic from the database for the given champion matchup
     * 
     * @param enemyChampion Enemy champion to check
     * @param pick Champion to check enemy champion against
     * 
     * @return Match statistics for the query in string form "won,total"
     */
    public String getMatchStatistic(String enemyChampion, String pick) {
        
        String matchStatistic = "";
        try {
            PreparedStatement sqlstatement = this.database.prepareStatement("SELECT \"" + enemyChampion + "\" FROM ownStatistics WHERE champion = '" + pick + "'");
            ResultSet r = sqlstatement.executeQuery();
            if (r.next()) {
                matchStatistic = r.getString(1);
            }
            
        } catch (SQLException error) {
            System.out.println("Error getMatchStatistic: " + error.getMessage());
        }
        return matchStatistic;
    }
      
    /**
     * 
     * Sets new statistic to the database for the given champion matchup
     * 
     * @param champ Enemy champion to check
     * @param pick Champion to check enemy champion against
     * @param newStat new statistic to write to the database
     */
    public void setMatchStatistic(String champ, String pick, String newStat) {
        
        try {
            PreparedStatement sqlstatement = this.database.prepareStatement("UPDATE ownStatistics SET \"" + champ + "\" = \"" + newStat + "\" WHERE champion = '" + pick + "'");
            sqlstatement.execute();
        } catch (SQLException error) {
            System.out.println("Error setMatchStatistic: " + error.getMessage());
        }
    }
    
    /**
     * Sets that the champion has been played by the user in the given role
     * 
     * @param champion Champion that the user played as
     * @param role Role that the user played as
     * @param stat The status to set
     */
    public void setRolePlayed(String champion, String role, String stat) {
        try {
            PreparedStatement sqlstatement = this.database.prepareStatement("UPDATE ownRoles SET " + role + " = " + stat + " WHERE champion = \"" + champion + "\"");
            sqlstatement.execute();
        } catch (SQLException error) {
            System.out.println("Error setRolePlayed: " + error.getMessage());
        }
    }
    
    /**
     * Creates the SQL statement to use for SQL query
     * 
     * @param enemyChampions champions which columns to fetch
     * @param pick row of the champion to check
     * @param table information on which table to check from
     * 
     * @return String of the SQL statement
     */
    public String parseStatement(ArrayList<String> enemyChampions, int pick, String table) {
        
        String sqlstatement = "";
        boolean first = true;
        
        for (String champion:enemyChampions) {
            
            if (champion.equals("Champion")) {
                continue;
            }
            
            if (first) {
                sqlstatement = "SELECT " + "\"" + champion + "\"";
                first = false;
            } else {
                sqlstatement = sqlstatement + ", " + "\"" + champion + "\"";
            }
        }
        
        if (first) {
            return "Error";
        }
        
        return sqlstatement + " FROM " + table + " WHERE id = " + pick;
        
    }
    
    /**
     * Checks if the champion is suitable for the given role from the base roles
     * 
     * @param championID Champion to check
     * @param role Role to check
     * 
     * @return boolean wether the role fits the champion
     */
    public boolean checkRoleBaseRoles(int championID, String role) {
        
        if (role.equals("Role")) {
            return true;
        }
       
        try {
            PreparedStatement statement = this.database.prepareStatement("SELECT " + role + " FROM baseRoles WHERE id = " + championID);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                if (result.getInt(1) == 1) {
                    return true;
                } 
            }
            
        } catch (SQLException error) {
            System.out.println("Error checkRoleBaseRoles: " + error.getMessage());
        }
        
        return false;
        
    }
    
    /**
     * Checks if the champion suitable for the given role from the own roles
     * 
     * @param championID Champion to check
     * @param role Role to check
     * 
     * @return boolean wether the role fits the champion
     */
    public boolean checkRoleOwnRoles(int championID, String role) {
        if (role.equals("Role")) {
            return true;
        }
        
        try {
            PreparedStatement sqlstatement = this.database.prepareStatement("SELECT " + role + " FROM ownRoles WHERE id = " + championID);
            ResultSet result = sqlstatement.executeQuery();
            if (result.next()) {
                if (result.getInt(1) == 1) {
                    return true;
                }
            }
        } catch (SQLException error) {
            System.out.println("Error checkRoleBaseRoles: " + error.getMessage());
        }
        
        return false;
    }
    
    /**
     * Resets all personal statistics in the database
     */
    public void resetAllPersonalStatistics() {
        ArrayList<String> championList = getChampionList();
        
        try {
            PreparedStatement sqlstatement;
            for (String champion : championList) {    
                sqlstatement = this.database.prepareStatement("UPDATE ownStatistics SET \"" + champion + "\" = \"0,0\"");
                sqlstatement.execute();
            }
            sqlstatement = this.database.prepareStatement("UPDATE ownRoles SET top = 0, jgl = 0, mid = 0, adc = 0, sup = 0");
            sqlstatement.execute();
        } catch (SQLException error) {
            System.out.println("Error resetAllPersonalStatistics: " + error.getMessage());
        }
    }
    
      
}
