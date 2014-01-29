package baza;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import elementyBazy.Osobowosc;
import elementyBazy.Tematyka;
import elementyBazy.ZbiorOsobowosci;

public class Baza {
 
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:twitter.db";
 
    private Connection conn;
    private Statement stat;
 
    public Baza() {
        try {
            Class.forName(Baza.DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Brak sterownika JDBC");
            e.printStackTrace();
        }
 
        try {
            conn = DriverManager.getConnection(DB_URL);
            conn.setAutoCommit(false);
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem polaczenia");
            e.printStackTrace();
        }
 
    }
    
    public void commitBazy() {
    	try {
			this.conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
   
 
    public boolean insertOsobowosc(long id, String nick) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into Osobowosc values (?, ?, 0, 0, 0, NULL, 0);");
            prepStmt.setLong(1, id);
            prepStmt.setString(2, nick);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy wstawianiu osobowosci");
            return false;
        }
        return true;
    }
 
    public boolean insertTematyka(String tag) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into Tematyka values (?, 0);");
            prepStmt.setString(1, tag);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy wstawianiu tematyki");
            return false;
        }
        return true;
    }
 
    public boolean insertZbiorOsobowosci(String tag, long id) {
        try {
        	ResultSet result = stat.executeQuery("SELECT COUNT(*) FROM ZbiorOsobowosci WHERE tag =\"" + tag + "\" " +
        			"AND idOsobowosci = " + id);
        	short jest = result.getShort(1);
        	if (jest == 0) {
        		PreparedStatement prepStmt = conn.prepareStatement("insert into ZbiorOsobowosci values (?, ?);");
                prepStmt.setString(1, tag);
                prepStmt.setLong(2, id);
                prepStmt.execute();
        	} 
        	
            
        } catch (SQLException e) {
            System.err.println("Blad przy wstawianiu do zbioru osobowosci");
            return false;
        }
        return true;
    }
 
    
    public void updateOsobowosci(long id, short tweetyI, short tweetyII, short tweetyIII, Timestamp data) {
    	try {
			stat.executeUpdate("UPDATE Osobowosc SET tweetyI = " + tweetyI + " , tweetyII = " + tweetyII + " , tweetyIII = " + tweetyIII +
					" , dataWpisu = \"" + data.toString() + 
					"\" WHERE id = " + id);
		} catch (SQLException e) {
			System.err.println("Update Osobowosci nie powiodl sie.");
			e.getStackTrace();
		}
    }
    
    public List<Osobowosc> selectOsobowosc(String tag) {
        List<Osobowosc> osobowosci = new LinkedList<Osobowosc>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM Osobowosc WHERE id IN " +
            		"(SELECT idOsobowosci FROM ZbiorOsobowosci where tag = \"" + tag + "\")");
            long id;
        	String nick;
        	short tweetyI, tweetyII, tweetyIII, skutecznosc;
        	Timestamp dataWpisu;
            while(result.next()) {
                id = result.getLong("id");
                nick = result.getString("nick");
                tweetyI = result.getShort("tweetyI");
                tweetyII = result.getShort("tweetyII");
                tweetyIII = result.getShort("tweetyIII");
                dataWpisu = result.getTimestamp("dataWpisu");
                skutecznosc = result.getShort("skutecznosc");
                osobowosci.add(new Osobowosc(id, nick, tweetyI, tweetyII, tweetyIII, dataWpisu, skutecznosc));
            }
        } catch (SQLException e) {
            System.err.println("Select Osobowosci nie powiodl sie.");
            return null;
        }
        return osobowosci;
    }
    
    
    public Vector<Tematyka> selectTematyka() {
        Vector<Tematyka> tematyki = new Vector<Tematyka>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM Tematyka");
            String tag;
            short skutecznosc;
            while(result.next()) {
                tag = result.getString("tag");
                skutecznosc = result.getShort("skutecznosc");
                tematyki.add(new Tematyka(tag, skutecznosc));
            }
        } catch (SQLException e) {
        	System.err.println("Select Tematyki nie powiodl sie.");
            return null;
        }
        return tematyki;
    }
    
    
    public Vector<String> przekazTagi() {
        Vector<String> tematyki = new Vector<String>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM Tematyka");
            String tag;
            while(result.next()) {
                tag = result.getString("tag");
                tematyki.add(tag);
            }
        } catch (SQLException e) {
        	System.err.println("Select Tematyki nie powiodl sie.");
            return null;
        }
        return tematyki;
    }
    
    
    public void policzSkutecznoscOsobowosci(String tag) {
    	try {
            stat.executeUpdate("UPDATE Osobowosc " +
            		"SET skutecznosc = tweetyI + (tweetyII * 10) + (tweetyIII * 100) "  +
            		"WHERE id IN (SELECT idOsobowosci FROM ZbiorOsobowosci where tag = \"" + tag + "\")");
            conn.commit();
        } catch (SQLException e) {
        	System.err.println("Liczenie skutecznosci Osobowosci nie powiodlo sie.");
        }
    }
    
    
    public short policzSkutecznoscTagu(String tag) {
    	try {
    		ResultSet result = stat.executeQuery("SELECT AVG(Skutecznosc) FROM Osobowosc WHERE id IN " +
    				"(SELECT idOsobowosci FROM ZbiorOsobowosci where tag = \"" + tag + "\")");
    		short srednia = result.getShort(1);
    		stat.executeUpdate("UPDATE Tematyka SET Skutecznosc = " + srednia + " WHERE tag =\"" + tag + "\"");
    		conn.commit();
    		return srednia;
        } catch (SQLException e) {
        	System.err.println("Liczenie Skutecznosci tagu nie powiodlo sie.");
        }
    	return 0;
    }
    
    
    public void usunNieskuteczneOsobowosci(String tag) {
    	try {
			ResultSet result = stat.executeQuery("SELECT AVG(Skutecznosc) FROM Osobowosc WHERE id IN " +
				"(SELECT idOsobowosci FROM ZbiorOsobowosci where tag = \"" + tag + "\")");
			short srednia = (short) Math.max(7, result.getShort(1));
			stat.executeUpdate("DELETE FROM ZbiorOsobowosci " +
            		"WHERE tag = \"" + tag + "\" AND idOsobowosci IN (SELECT id FROM Osobowosc" +
            				" where skutecznosc < " + srednia + ")");
			stat.executeUpdate("DELETE FROM Osobowosc " +
            		"WHERE id NOT IN (SELECT idOsobowosci FROM ZbiorOsobowosci)");
            conn.commit();
		} catch (SQLException e) {
			System.err.println("Usuwanie nieskutecznych Osobowosci nie powiodlo sie.");
		}
    }
    
    public boolean jestTag(String tag) {
    	try {
			ResultSet result = stat.executeQuery("SELECT COUNT(*) FROM Tematyka where" +
					" tag = \"" + tag + "\"");
			if ( result.getShort(1) == 1) {
				return true;
			} else return false;
		} catch (SQLException e) {
			System.err.println("Select Tematyki nie powiodl sie.");
		}
    	return false;
    }

 
  
    public void closeConnection() {
        try {
        	conn.commit();
            conn.close();
        } catch (SQLException e) {
            System.err.println("Problem z zamknieciem polaczenia");
            e.printStackTrace();
        }
    }
}

