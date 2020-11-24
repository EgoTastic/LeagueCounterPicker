/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
            System.out.println("Error: " + e.getMessage());
        }               
    }
    
    public ArrayList<String> getMatchesOwn(ArrayList<String> champs, int pick) {
        ArrayList<String> matches = new ArrayList<>();
        String statement = "";
        boolean first = true;
        
        if (champs.isEmpty()) {
            return matches;
        }
        
        for (String champion:champs) {
            if (first) {
                statement = "SELECT " + "\"" + champion + "\"";
                first = false;
            } else {
                statement = statement + ", " + "\"" + champion + "\"";
            }
        }
        
        statement = statement + " FROM ownStatistics WHERE id = " + pick;
        try {
            PreparedStatement sqlstatement = this.database.prepareStatement(statement);
            ResultSet r = sqlstatement.executeQuery();
            int index = 1;
            while (r.next()) {
                matches.add(r.getString(index));
                index++;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return matches;
    }
    
    public ArrayList<Double> getPercentBase(ArrayList<String> champs, int pick) {
        ArrayList<Double> percents = new ArrayList<>();
        String statement = "";
        boolean first = true;
        
        if (champs.isEmpty()) {
            return percents;
        }
        
        for (String champion: champs) {
            if (first) {
                statement = "SELECT " + "\"" + champion + "\"";
                first = false;
            } else {
                statement = statement + ", " + "\"" + champion + "\"";
            }                       
        }
               
        statement = statement + " FROM baseStatistics WHERE id = " + pick;
        try {
            PreparedStatement sqlstatement = this.database.prepareStatement(statement); 
            ResultSet r = sqlstatement.executeQuery();
            int index = 1;
            while (r.next()) {
                percents.add(r.getDouble(index));
                index++;
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return percents;
    }
    
    public String getChampName(int championNumber) {
        try {
            PreparedStatement sqlstatement = this.database.prepareStatement("SELECT champion FROM baseStatistics where id = ?");
            sqlstatement.setInt(1, championNumber); 
            return sqlstatement.executeQuery().getString(1);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return "Error";
        }
        
    }
    
    public ArrayList<String> getChampList() {
        ArrayList<String> champlist = new ArrayList<>();
        try {
            PreparedStatement sqlstatement = this.database.prepareStatement("SELECT champion FROM baseStatistics");
            ResultSet r = sqlstatement.executeQuery();
            while (r.next()) {
                champlist.add(r.getString("champion"));
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return champlist;
    }
    
    public String getMatch(String champ, String pick) {
        String match = "";
        try {
            PreparedStatement sqlstatement = this.database.prepareStatement("SELECT \"" + champ + "\" FROM ownStatistics WHERE champion = '" + pick + "'");
            ResultSet r = sqlstatement.executeQuery();
            match = r.getString(1);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return match;
    }
    
    public void setMatch(String champ, String pick, String newStat) {
        try {
            PreparedStatement sqlstatement = this.database.prepareStatement("UPDATE ownStatistics SET \"" + champ + "\" = \"" + newStat + "\" WHERE champion = '" + pick + "'");
            sqlstatement.execute();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
}
