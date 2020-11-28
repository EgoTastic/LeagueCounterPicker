package leaguecounter.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author miikakoskela
 */

public class DatabaseIF {
    private Connection database;
    
    public DatabaseIF() {
        try {
            this.database = DriverManager.getConnection("jdbc:sqlite:Picks.db");
        } catch (SQLException e) {
            System.out.println("Error DatabaseIF: " + e.getMessage());
        }               
    }
    
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
    
    // Returns list of statistics for 1 champ against 5 champs from the list from users own table
    public ArrayList<String> getWinRatesOwnStatistics(ArrayList<String> enemyChampionList, int championToTest) {
        ArrayList<String> winRates = new ArrayList<>();
        
        String statement = parseStatement(enemyChampionList, championToTest, "ownStatistics");
        
        if (enemyChampionList.isEmpty() || statement.equals("Error")) {
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
    
    // Returns list of statistics for 1 champ against 5 champs from the list from global table
    public ArrayList<Double> getWinRatesBaseStatistics(ArrayList<String> enemyChampionList, int championToTest) {
         
        ArrayList<Double> winRates = new ArrayList<>();
        
        String statement = parseStatement(enemyChampionList, championToTest, "baseStatistics");
        
        if (enemyChampionList.isEmpty() || statement.equals("Error")) {
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
    
    // Returns champion name based on given id number
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
    
    // Returns list of all champions
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
    
    // Returns champion-on-champion stats based on user statistics
    public String getMatchStatistic(String champ, String pick) {
        
        String matchStatistic = "";
        try {
            PreparedStatement sqlstatement = this.database.prepareStatement("SELECT \"" + champ + "\" FROM ownStatistics WHERE champion = '" + pick + "'");
            ResultSet r = sqlstatement.executeQuery();
            if (r.next()) {
                matchStatistic = r.getString(1);
            }
            
        } catch (SQLException error) {
            System.out.println("Error getMatchStatistic: " + error.getMessage());
        }
        return matchStatistic;
    }
      
    // Updates champion-on-champion stats with new statistics
    public void setMatchStatistic(String champ, String pick, String newStat) {
        
        try {
            PreparedStatement sqlstatement = this.database.prepareStatement("UPDATE ownStatistics SET \"" + champ + "\" = \"" + newStat + "\" WHERE champion = '" + pick + "'");
            sqlstatement.execute();
        } catch (SQLException error) {
            System.out.println("Error setMatchStatistic: " + error.getMessage());
        }
    }
    
    // Creates the SQL statement for finding statistics
    public String parseStatement(ArrayList<String> champs, int pick, String table) {
        
        String sqlstatement = "";
        boolean first = true;
        
        for (String champion:champs) {
            
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
    
    // Checks if the champion is suitable for picked role from base roles
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
      
}
