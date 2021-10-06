package project_data_crawler;

/**
 * Model for Object-Oriented Programming
 * @author User
 */
public class Item {
	
	String Tweeter;
	String text;
	String date;
	int Retweetcount;
	
	String title;
	String link;
	String score;
	
	int sentimentindex;
	int sentimentvalue;
	
	/**
	 *  Get tweeter object
	 * @return tweeter in string
	 */
	public String getTweeter() {
		return Tweeter;
	}
	/**
	 * Set tweeter object
	 * @param tweeter
	 */
	public void setTweeter(String tweeter) {
		Tweeter = tweeter;
	}
	/**
	 * Get the text
	 * @return text in string
	 */
	public String getText() {
		return text;
	}
	/**
	 * Set the text
	 * @param text in string
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * Get the date
	 * @return Date  in string
	 */
	public String getDate() {
		return date;
	}
	/**
	 * Set the date
	 * @param date Set in string
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * Get the retweetcount 
	 * @return retweetcount in integer
	 */
	public int getRetweetcount() {
		return Retweetcount;
	}
	/**
	 * Set the retweetcount
	 * @param retweetcount in integer
	 */
	public void setRetweetcount(int retweetcount) {
		Retweetcount = retweetcount;
	}
	/**
	 * Get the title 
	 * @return Title in string
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * Set the title
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * Get the link 
	 * @return link in string
	 */
	public String getLink() {
		return link;
	}
	/**
	 * Set the link
	 * @param link in string
	 */
	public void setLink(String link) {
		this.link = link;
	}
	/**
	 * Get the score 
	 * @return score in string
	 */
	public String getScore() {
		return score;
	}
	/**
	 * Set the score
	 * @param score in string
	 */
	public void setScore(String score) {
		this.score = score;
	}
	/**
	 * Get the sentimentvalue
	 * @return sentimentvalue in string
	 */
	public int getSentimentvalue() {
		return sentimentvalue;
	}
	/**
	 * Set the sentimentvalue
	 * @param sentimentvalue in integer
	 */
	public void setSentimentvalue(int sentimentvalue) {
		this.sentimentvalue = sentimentvalue;
	}
	/**
	 * Get the sentimentindex
	 * @return sentimentindex in integer
	 */
	public int getSentimentindex() {
		return sentimentindex;
	}
	/**
	 * Set the sentimentindex
	 * @param sentimentindex in integer
	 */
	public void setSentimentindex(int sentimentindex) {
		this.sentimentindex = sentimentindex;
	}
	
	
	

}
