package project_data_crawler;

/**
 * Any class inheriting from Crawler must implement the following method 
 * which is abstract item[].crawl()  
 * @author User
 *
 */
public abstract class Crawler {

	/**
	 * Any class inheriting from Crawler must implement the following method 
	 * which is abstract item[].crawl()  
	 * @param num it determines how much information is crawled from social media
	 * @return null
	 */
	
	public abstract Item[] crawl(int num);
	
	//shd i use abstract to get sentiment value from both twitter and reddit
}
