package elementyBazy;

import java.sql.Timestamp;
//Czytaj wiêcej na: http://javastart.pl/zaawansowane-programowanie/bazy-danych-sqlite-w-javie/#ixzz2qN2hfD8C
public class Osobowosc {
	private long id;
	private String nick;
	private short tweetyI;
	private short tweetyII;
	private short tweetyIII;
	private Timestamp dataWpisu;
	private short skutecznosc;
 
    public Osobowosc(long id, String nick, short tweetyI, short tweetyII, short tweetyIII, Timestamp data, short skutecznosc) {
        this.id = id;
        this.nick = nick;
        this.tweetyI = tweetyI;
    	this.tweetyII = tweetyII;
    	this.tweetyIII = tweetyIII;
    	this.dataWpisu = data;
    	this.skutecznosc = skutecznosc;
        
    }
    
    
    public long getId() {
        return id;
    }
    public String getNick() {
        return nick;
    }
    public short getTweetyI() {
        return tweetyI;
    }
    public short getTweetyII() {
        return tweetyII;
    }
    public short getTweetyIII() {
        return tweetyIII;
    }
    public short getSkutecznosc() {
        return skutecznosc;
    }
    public Timestamp getDataWpisu() {
        return dataWpisu;
    }
    
    public void setTweetyI(short tweety) {
        this.tweetyI = tweety;
    }
    public void setTweetyII(short tweety) {
        this.tweetyII = tweety;
    }
    public void setTweetyIII(short tweety) {
        this.tweetyIII = tweety;
    }
    public void setSkutecznosc(short s) {
        this.skutecznosc = s;
    }
    public void setDataWpisu(Timestamp data) {
        this.dataWpisu = data;
    }


}
