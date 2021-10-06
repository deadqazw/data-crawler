package project_data_crawler;

/**
 * import from SQL connector an external JAR file
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Connects the program to MySQL for data insertion
 * @author User
 *
 */
public class SQLHelper {
	/**
	 * These are the default configuration of the SQLhelper to connect to the database
	 */
	private static String JDBCURL = "jdbc:mysql://localhost:3306/world";
	private static String username = "root";
	private static String password = "123456";
	private static Connection conn = null;
	private static Date date = new Date();
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static String timeStamp = format.format(date);

	/**
	 * Method for inserting tweets into database
	 * @param tweets tweets from Item class
	 * @return true
	 */
	public static boolean InsertTweet(Item tweets) {

		try {
			conn = DriverManager.getConnection(JDBCURL, username, password);

			// System.out.println("Connection is successful."); //testing if connection to
			// database is successful

			String sql = "INSERT INTO world.twittertweet"
					+ "(Tweeter, Texts, Dates, Retweetcount, DateInserted, Sentiment)" + "VALUES "
					+ "(?, ?, ?, ?, ?, ?);";

			PreparedStatement Statement = conn.prepareStatement(sql);
			Statement.setString(1, tweets.getTweeter());
			Statement.setString(2, tweets.getText());
			Statement.setString(3, tweets.getDate());
			Statement.setInt(4, tweets.getRetweetcount());
			Statement.setString(5, timeStamp);
			Statement.setInt(6, tweets.getSentimentvalue());

			Statement.executeUpdate();

			conn.close();

		} catch (Exception e) {

			e.printStackTrace();
		}

		return true;
	}

	
	/**
	 * Method to insert reddit post into database
	 * @param newPost newPost from Item class
	 * @return true
	 */
	public static boolean InsertPost(Item newPost) {

		try {
			conn = DriverManager.getConnection(JDBCURL, username, password);
			// System.out.println("Connection is successful.");

			String sql = "INSERT INTO redditpost" + "(Title, Dates, Links, Score, Sentiment)" + "VALUES "
					+ "(?, ?, ?, ?, ?);";
			PreparedStatement Statement = conn.prepareStatement(sql);
			Statement.setString(1, newPost.getTitle());
			Statement.setString(2, newPost.getDate());
			Statement.setString(3, newPost.getLink());
			Statement.setString(4, newPost.getScore());
			Statement.setInt(5, newPost.getSentimentvalue());

			Statement.executeUpdate();

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

		return true;
	}
	
	
	/**
	 * Method to delete tweet from database
	 */
	public static void Deletetweet() {

		try {

			conn = DriverManager.getConnection(JDBCURL, username, password);

			Statement stmt = conn.createStatement();
			String query = "TRUNCATE TABLE world.twittertweet"; // delete existing data in the table
			stmt.executeUpdate(query);

			conn.close();
		} catch (Exception e) {

			e.printStackTrace();

		}
	}
	/**
	 * Method to delete reddit post from database
	 */
	public static void Deletepost() {

		try {

			conn = DriverManager.getConnection(JDBCURL, username, password);

			Statement stmt = conn.createStatement();
			String query = "TRUNCATE TABLE world.redditpost"; // delete existing data in the table
			stmt.executeUpdate(query);

			conn.close();
		} catch (Exception e) {

			e.printStackTrace();

		}
	}
	
	
	/**
	 * Method to create a reddit table in database
	 * @return true
	 */
	public static boolean Maketable_Reddit() {

		try {
			conn = DriverManager.getConnection(JDBCURL, username, password);
			Statement stmt = conn.createStatement();
			// Make new table with timestamp as title
			String maketable = "CREATE TABLE redditpost" + " (No INT NOT NULL AUTO_INCREMENT," + " Title VARCHAR(255),"
					+ " Dates VARCHAR(255), " + " Links VARCHAR(255), " + " Sentiment VARCHAR(255), "
					+ " Score VARCHAR(255),  PRIMARY KEY (No)) ";
			stmt.executeUpdate(maketable);
			conn.close();

		} catch (Exception e) {
			System.out.print("Table is already Created!");
		}

		return true;
	}
	
	/**
	 * Method to create a tweet table in database
	 * @return true
	 */
	public static boolean MakeTable_tweet() {

		try {
			conn = DriverManager.getConnection(JDBCURL, username, password);
			Statement stmt = conn.createStatement();
			String maketable = "CREATE TABLE twittertweet " + "(No INT NOT NULL AUTO_INCREMENT,"
					+ " Tweeter VARCHAR(100), " + " Texts LONGTEXT," + " Dates VARCHAR(30)," + " Retweetcount  INT, "
					+ " Sentiment VARCHAR(255), " + " DateInserted VARCHAR(30),  PRIMARY KEY (No))";

			stmt.executeUpdate(maketable);
			conn.close();

		} catch (Exception e) {
			System.out.print("Table is already Created!");
			// e.printStackTrace();

		}

		return true;
	}
	
	/**
	 * This method is used for getting data from reddit, which is the score is the highest
	 * @param searchkey It is the keyword enterred by the user in the UI
	 * @param date It is the date enterred by the user in the UI
	 * @param num It is the num enterred by the user in the UI
	 * @return Data in format of arraylist item
	 */
	
	public static ArrayList<Item> getDataR(String searchkey, String date, int num) {			//currently is reddit
		
		ArrayList<Item> Data = new ArrayList<>();
		
		try {
			conn = DriverManager.getConnection(JDBCURL, username, password);

			String sql = "SELECT Title,Dates,Links,CAST(Score as unsigned) as score,Sentiment FROM world.redditpost " +
			"where DATE(Dates) = DATE('" + date +"') AND Title LIKE'%" + searchkey + "%'" + 
			"Order by score DESC LIMIT " + num;

			Statement stmt = conn.createStatement();
			ResultSet rst;
			rst = stmt.executeQuery(sql);

			

			while (rst.next()) {
				Item item = new Item();
				item.setTitle(rst.getString("Title"));//rst.getInt("score")
				item.setDate(rst.getString("Dates"));
				item.setScore(rst.getString("score"));
				item.setSentimentvalue(rst.getInt("Sentiment"));
				//System.out.print("I " + item.getSentimentindex());
				//System.out.print("V " + item.getSentimentvalue() + "\n");
				Data.add(item);
			}
		}
		catch (Exception e) {
			System.out.print(e.getMessage());
		}
				
		return Data;
	}
	/**
	 * This method is used for getting data from reddit, which is the most recent date
	 * @param searchkey It is the keyword enterred by the user in the UI
	 * @param num It is the num enterred by the user in the UI
	 * @return Data in format of arraylist item
	 */
	public static ArrayList<Item> getDataR(String searchkey, int num) {	    //overloading	//do for tweet and reddit	//currently is reddit
		
		ArrayList<Item> Data = new ArrayList<>();
		
		try {
			conn = DriverManager.getConnection(JDBCURL, username, password);

			String sql = "SELECT  Title,Dates,Sentiment,CAST(Score as unsigned) as score " + 
			"FROM world.redditpost where Title LIKE'%" + searchkey + "%'" + " Group by Title " +
			"ORDER BY Dates DESC " + "LIMIT " + num;

			Statement stmt = conn.createStatement();
			ResultSet rst;
			rst = stmt.executeQuery(sql);

			

			while (rst.next()) {
				Item item = new Item();
				item.setTitle(rst.getString("Title"));
				item.setDate(rst.getString("Dates"));
				item.setScore(rst.getString("score"));
				item.setSentimentvalue(rst.getInt("Sentiment"));
				Data.add(item);
			}
		}
		catch (Exception e) {
			System.out.print(e.getMessage());
		}
		
		return Data;
	}
	
	
	/**
	 * This method is used for getting data from twitter, which is the score is the highest
	 * @param searchkey It is the keyword enterred by the user in the UI
	 * @param date It is the date enterred by the user in the UI
	 * @param num It is the num enterred by the user in the UI
	 * @return Data in format of arraylist item
	 */
	public static ArrayList<Item> getDataT(String searchkey, String date, int num) {			//currently is tweeter most re-tweeted
		
		ArrayList<Item> Data = new ArrayList<>();
		
		try {
			conn = DriverManager.getConnection(JDBCURL, username, password);

			String sql = "SELECT Tweeter,Texts,Dates,Sentiment, CAST(Retweetcount as unsigned) as Tcount FROM world.twittertweet " + 
			"where DATE(Dates) = DATE('" + date + "') AND Texts LIKE'%" + searchkey + "%' " + " Order by Tcount DESC LIMIT " + num + ";";

			Statement stmt = conn.createStatement();
			ResultSet rst;
			rst = stmt.executeQuery(sql);

			

			while (rst.next()) {
				Item item = new Item();
				item.setTweeter(rst.getString("Tweeter"));
				item.setText(rst.getString("Texts"));
				item.setDate(rst.getString("Dates"));
				item.setSentimentvalue(rst.getInt("Sentiment"));
				item.setRetweetcount(rst.getInt("Tcount"));

				Data.add(item);
			}
		}
		catch (Exception e) {
			System.out.print(e.getMessage());
		}
				
		return Data;
	}
	
	/**
	 * This method is used for getting data from twitter, which is the most recent
	 * @param searchkey  It is the keyword enterred by the user in the UI
	 * @param num It is the num enterred by the user in the UI
	 * @return Data in format of arraylist item
	 */
	public static ArrayList<Item> getDataT(String searchkey, int num) {			//overloading		//currently is tweeter most recent tweet
		
		ArrayList<Item> Data = new ArrayList<>();
		
		try {
			conn = DriverManager.getConnection(JDBCURL, username, password);

			String sql = "SELECT  Tweeter,Texts,Dates,Sentiment, CAST(Retweetcount as unsigned) as Tcount " + 
			"FROM world.twittertweet  where Texts LIKE '%" + searchkey + "%' " + " ORDER BY Dates DESC " + " LIMIT " + num;

			Statement stmt = conn.createStatement();
			ResultSet rst;
			rst = stmt.executeQuery(sql);

			

			while (rst.next()) {
				Item item = new Item();
				item.setTweeter(rst.getString("Tweeter"));
				item.setText(rst.getString("Texts"));
				item.setDate(rst.getString("Dates"));
				item.setSentimentvalue(rst.getInt("Sentiment"));
				item.setRetweetcount(rst.getInt("Tcount"));

				Data.add(item);
			}
		}
		catch (Exception e) {
			System.out.print(e.getMessage());
		}
				
		return Data;
	}

	/**
	 * This method get the total of senitment value and group then into the index
	 * @return Data in format of arraylist item
	 */
	public static ArrayList<Item> getSentiment_T() {
		
		ArrayList<Item> sentimentlist = new ArrayList<>(); 	//create the sentiment list first
		try {
			conn = DriverManager.getConnection(JDBCURL, username, password);

			String sql = "select  Sentiment, COUNT( (Sentiment)) as NUM from world.twittertweet " 
					+ " group by Sentiment UNION ALL" + " select  Sentiment, COUNT( (Sentiment)) as NUM from world.redditpost " +
					" group by Sentiment Order by Sentiment ASC ";

			Statement stmt = conn.createStatement();
			ResultSet rst;
			rst = stmt.executeQuery(sql);

			

			while (rst.next()) {
				Item item = new Item();
				item.setSentimentindex(rst.getInt("Sentiment"));
				item.setSentimentvalue(rst.getInt("NUM"));
				//System.out.print("I " + item.getSentimentindex());
				//System.out.print("V " + item.getSentimentvalue() + "\n");
				sentimentlist.add(item);
			}

		} catch (Exception e) {

		}

		return sentimentlist;
	}
	
	
	/**
	 * This get sentiment for each invidual graph after the user searchs (Most upvoted or Most retweeted)
	 * @param db 1 will serach for tweeter and 2 will search for reddit
	 * @param searchkey It is the keyword enterred by the user in the UI
	 * @param date It is the date enterred by the user in the UI
	 * @param num It is the num enterred by the user in the UI
	 * @return Data in format of arraylist item
	 */
	public static ArrayList<Item> getSentiment(int db, String searchkey, String date, int num) {
		
		ArrayList<Item> sentimentlist = new ArrayList<>(); 	//create the sentiment list first
		String Db = "";
		String Comparer ="";
		if (db == 1) {
			Db = "world.twittertweet";
			Comparer = "Texts";
		}
		else if (db == 2){
			Db = "world.redditpost";
			Comparer = "Title";
		}
		else {
			
		}
		try {
			conn = DriverManager.getConnection(JDBCURL, username, password);

			String sql = "select   Sentiment, COUNT( (Sentiment)) as NUM FROM " + Db
					+ " where DATE(Dates) = DATE('" + date + "') and " + Comparer + " LIKE '%" + searchkey + "%'" + 
					" group by Sentiment Order by Sentiment ASC LIMIT " + num ;

			Statement stmt = conn.createStatement();
			ResultSet rst;
			rst = stmt.executeQuery(sql);

			

			while (rst.next()) {
				Item item = new Item();
				item.setSentimentindex(rst.getInt("Sentiment"));
				item.setSentimentvalue(rst.getInt("NUM"));
				//System.out.print("I " + item.getSentimentindex());
				//System.out.print("V " + item.getSentimentvalue() + "\n");
				sentimentlist.add(item);
			}

		} catch (Exception e) {

		}

		return sentimentlist;
	}
	
	
	/**
	 * This is the overload for getting sentiment after the user searchs (Most recent)
	 * @param db 1 will serach for tweeter and 2 will search for reddit
	 * @param searchkey It is the keyword enterred by the user in the UI
	 * @param num It is the num enterred by the user in the UI
	 * @return Data in format of arraylist item
	 */
	public static ArrayList<Item> getSentiment(int db, String searchkey, int num) {
		
		ArrayList<Item> sentimentlist = new ArrayList<>(); 	//create the sentiment list first
		String Db = "";
		String Comparer = "";
		if (db == 1) {
			Db = "world.twittertweet";
			Comparer = "Texts";
		}
		else {
			Db = "world.redditpost";
			Comparer = "Title";
		}
		try {
			conn = DriverManager.getConnection(JDBCURL, username, password);

			String sql = "select   Sentiment, COUNT( (Sentiment)) as NUM FROM " + Db
					+ " WHERE " + Comparer + " LIKE '%" + searchkey + "%'" + 
					" group by Sentiment Order by Sentiment ASC LIMIT " + num ;
			
			Statement stmt = conn.createStatement();
			ResultSet rst;
			rst = stmt.executeQuery(sql);

			

			while (rst.next()) {
				Item item = new Item();
				item.setSentimentindex(rst.getInt("Sentiment"));
				item.setSentimentvalue(rst.getInt("NUM"));
				//System.out.print("I " + item.getSentimentindex());
				//System.out.print("V " + item.getSentimentvalue() + "\n");
				sentimentlist.add(item);
			}

		} catch (Exception e) {

		}

		return sentimentlist;
	}
	
	
	/**
	 * Gets the total number of post for tweeter to generate the line graph
	 * @param db 1 will serach for tweeter and 2 will search for reddit
	 * @return Data in format of arraylist item
	 */
	public static ArrayList<Item> getPost_T(int db) {
		
		ArrayList<Item> sentimentlist = new ArrayList<>(); 	//create the sentiment list first
		String Db = "";
		String Comparer = "";
		if (db == 1) {
			Db = "world.twittertweet";
			Comparer = "Texts";
		}
		else {
			Db = "world.redditpost";
			Comparer = "Title";
		}
		try {
			conn = DriverManager.getConnection(JDBCURL, username, password);

			String sql = "select dates,COUNT(" + Comparer + ") as num from " + Db + " group by dates order by dates ASC";

			Statement stmt = conn.createStatement();
			ResultSet rst;
			rst = stmt.executeQuery(sql);

			

			while (rst.next()) {
				Item item = new Item();
				item.setDate(rst.getString("dates"));
				item.setSentimentvalue(rst.getInt("num"));
				//System.out.print("I " + item.getSentimentindex());
				//System.out.print("V " + item.getSentimentvalue() + "\n");
				sentimentlist.add(item);
			}

		} catch (Exception e) {

		}

		return sentimentlist;
	}
	
	
	
	
}
