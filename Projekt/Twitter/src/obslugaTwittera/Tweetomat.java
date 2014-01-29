package obslugaTwittera;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.swing.Timer;



import twitter4j.*;
import twitter4j.api.FavoritesResources;
import twitter4j.auth.AccessToken;
import elementyBazy.Osobowosc;
import baza.Baza;



public class Tweetomat {
	public Baza baza = new Baza();
	private Twitter twitter;
	private final static String CONSUMER_KEY = "9d1PktUWTr1qAnODfGmNA";
	private final static String CONSUMER_KEY_SECRET = "ddxRdhmbPV1INQjNWYPSbjY19pcOGGFXAmQMRt7eg";
	private final static String accessToken = "2233340924-3jhWOeOR3mFF68MCoeMhGU9nXdeTxB4OOh6IDUj";
	private final static String  accessTokenSecret = "fxkLJgtlo2dQQBM8HijhAeU5zDv2Sd5nSPLrrsd4rTzx1";
	
	
	

	
	public Tweetomat() {
		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);
		AccessToken oathAccessToken = new AccessToken(accessToken, accessTokenSecret);
		twitter.setOAuthAccessToken(oathAccessToken);
	}
	
	public void limit(String endpoint) {
		RateLimitStatus status;
		RateLimitStatus statusLimit;
		String family = endpoint.split("/", 3)[1];  
		int sekundy;
		  try {
			statusLimit = twitter.getRateLimitStatus("application").get("/application/rate_limit_status");
			if (statusLimit.getRemaining() < 3) {
				sekundy = statusLimit.getSecondsUntilReset();
				
				System.out.println("Czekam na dostep do rate_limit_status " +
						sekundy + " sekund");
				try {
					Thread.sleep(sekundy * 1000 + 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Budze sie.");
			}
			status = twitter.getRateLimitStatus(family).get(endpoint);
			int liczbaDostepow = status.getRemaining();
			if (liczbaDostepow < 1) {
				sekundy = status.getSecondsUntilReset();
				System.out.println("Czekam na dostep " + endpoint +
					" " + sekundy + " sekund ");
				try {
					Thread.sleep(sekundy * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Budze sie.");
				
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}
	
	//zbiera popularne tweety
	public void wyszukaj(String tag)  {
		limit("/users/search");
		baza.insertTematyka(tag);
        Query query = new Query(tag);
        query.setResultType(Query.POPULAR);
        QueryResult result;
		try {
			result = twitter.search(query);
			if (result == null) {
			}
			for (Status tweet : result.getTweets()) {			
				User u = tweet.getUser();
				long id = u.getId();
				String nick = u.getName();
				this.baza.insertOsobowosc(id, nick);
				this.baza.insertZbiorOsobowosci(tag, id);
        	}
			this.baza.commitBazy();
		} catch (TwitterException e) {
			System.err.println("Wyszukiwanie tagu nie powiodlo sie.");
		}
            
	}
	
	
	public void aktualizujOsobowosci(String tag) {
		List<Osobowosc> osobowosci = new LinkedList<Osobowosc>();
		osobowosci = this.baza.selectOsobowosc(tag);
		ListIterator<Osobowosc> iter = osobowosci.listIterator();
	      while(iter.hasNext()) {
	    	 Osobowosc o = iter.next();
	         long id = o.getId();
	         short tweetyI = o.getTweetyI();
	         short tweetyII = o.getTweetyII();
	         short tweetyIII = o.getTweetyIII();
	         Timestamp data = o.getDataWpisu();
	         Timestamp dataTweeta = new Timestamp(System.currentTimeMillis());
	         try {
	        	 limit("/statuses/user_timeline");
	        	 ResponseList<Status> statuses = twitter.getUserTimeline(id);
	        	 for (Status status : statuses) {
	        		 if (data == null || (data != null && dataTweeta.after(data))) {
	        			 long retweets = status.getRetweetCount(); 
	        		     if (retweets >= 500) {
	        			     if (retweets < 1000) {
	        				     ++tweetyI;
	        			     } else {
	        				     if (retweets < 5000) {
	        					     ++tweetyII;
	        				     } else {
	        					     ++tweetyIII;
	        				     }
	        			     }	        			 
	        		     }
	        		 } 
	        	 }
	     
	        this.baza.updateOsobowosci(id, tweetyI, tweetyII, tweetyIII, dataTweeta);
			} catch (TwitterException e) {
				System.err.println("Aktualizowanie Osobowosci nie powiodlo sie.");
				e.getStackTrace();
			}
	      }
	      this.baza.commitBazy();
	}
	
	
	public void usunNieskutecznych(String tag) {
		this.baza.policzSkutecznoscOsobowosci(tag);
		this.baza.usunNieskuteczneOsobowosci(tag);
		this.baza.commitBazy();
	}
	
	
	public void szukajPobocznych(String tag) {
		List<Osobowosc> osobowosci = new LinkedList<Osobowosc>();
		osobowosci = this.baza.selectOsobowosc(tag);
		ListIterator<Osobowosc> iter = osobowosci.listIterator();
	while(iter.hasNext()) {
			long id = (iter.next()).getId();
	    	try {
	    		limit("/favorites/list");
	    		FavoritesResources favorites = twitter.favorites();
	    		ResponseList<Status> statuses = favorites.getFavorites(id);
				for (Status status : statuses) {
					User user = status.getUser();
					this.baza.insertOsobowosc(user.getId(), user.getName());
					this.baza.insertZbiorOsobowosci(tag, user.getId());
				}
				this.baza.commitBazy();
			} catch (TwitterException e) {
				System.err.println("Szukanie pobocznych nie powiodlo sie.");
				e.printStackTrace();
			}
	    }
	}
	
	
	
	public void retweetuj(String tag) {
		List<Osobowosc> osobowosci = new LinkedList<Osobowosc>();
		osobowosci = this.baza.selectOsobowosc(tag);
		ListIterator<Osobowosc> iter = osobowosci.listIterator();
		while(iter.hasNext()) {
			long id = (iter.next()).getId();
	    	try {
	    		limit("/statuses/user_timeline");
	    		ResponseList<Status> statuses = twitter.getUserTimeline(id);
				for (Status status : statuses) {
					if (!status.isRetweetedByMe()) {
						limit("/statuses/retweets/:id");
						limit("/statuses/retweeters/ids");
						
						this.twitter.retweetStatus(status.getId());
					}
				}
			} catch (TwitterException e) {
				System.err.println("Reetwentowanie nie powiodlo sie.");
				e.printStackTrace();
			}
		}
	}
	
	
	public void zamknij() {
		this.baza.commitBazy();
		this.baza.closeConnection();
	}
	

}
