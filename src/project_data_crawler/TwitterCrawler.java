package project_data_crawler;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

//twitter4j imported class
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.Status;
//2nd function
import twitter4j.PagableResponseList;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;

/**
 * Implementation of inheritance
 * The twitter class is a subclass of crawler
 * The twitter API is then implemented, it involved invoking its constructor
 * @author user
 *
 */
public class TwitterCrawler extends Crawler {
	
	/**
	 * There is final variables being set to determine the confuguration for twitter api
	 */
	public Item[] crawl(int num) {

		final String consumerKey = "xxxx";
		final String consumerSecret = "xxxx";
		final String accessToken = "xxxx-xxxx";
		final String accessTokenSecret = "xxxx";

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey).setOAuthConsumerSecret(consumerSecret)
				.setOAuthAccessToken(accessToken).setOAuthAccessTokenSecret(accessTokenSecret);

		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();

		SQLHelper.MakeTable_tweet();

		String userlis[] = { "BogochIsaac", "SueDHellmann", "DrNancyM_CDC", "DrTedros", "T_Inglesby", "DrTomFrieden" };
		String keyword[] = { "covid", "covid19", "COVID19", "covid-19", "corona", "coronavirus", "virus", "hcov",
				"sars-cov", "mers-cov", "sars-cov-2", "pandemic" };

		lookupUsers(twitter, userlis, keyword, num);
		return new Item[0];
	}
	/**
	 * The function is created to get list of all twitter followers name or id
	 * @param twitter from Twitter class
	 */
	public static void getFollowerName(Twitter twitter) {
		try {
			String twitterScreenName = twitter.getScreenName();
			PagableResponseList<User> statuse = twitter.getFollowersList(twitterScreenName, -1);
			for (User follower : statuse) {
				System.out.println(follower.getName());
			}
		} catch (TwitterException te) {
			System.out.print(te.getMessage());
		}

	}
	/**
	 * Date header
	 * @param date in Date class
	 * @return date in desired format
	 */
	public static String convertDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String format = formatter.format(date);

		return format;
	}
	/**
	 * Method to get more than 1 tweet from user and derive sentiment value from it
	 * @param twitter from Twitter class
	 * @param usersList  in string array
	 * @param keyword  in string array
	 * @param num  in integer
	 */
	public static void lookupUsers(Twitter twitter, String[] usersList, String[] keyword, int num) 
	{
		try {
			ResponseList<User> users = twitter.lookupUsers(usersList);
			Paging paging = new Paging(1, num); // limit the amount of information being retrived from twitter
			List<Status> statuses;
			List<Long> listid = new ArrayList<Long>();// make sure the data inserted is unique

			SQLHelper.Deletetweet(); // delete exisiting data in table first before adding new data

			for (User user : users) {
				statuses = twitter.getUserTimeline(user.getScreenName(), paging);

				System.out.println("\nUser: @" + user.getScreenName());
				for (Status s : statuses) {
					String strTweet = s.getText().toLowerCase();

					// loop through array of keywords
					for (int i = 0; i < keyword.length; i++) // if tweet contains a keyword
					{

						if (strTweet.contains(keyword[i]) == true) // it starts to get the most recent post and search
																	// for the keyword specfied in the array list
						{
							if (listid.contains(s.getId())) { // if it is duplicate do nothing

							} else { // if id the same means that they are duplicate cannot add to data base

								System.out.println(
										"@" + s.getUser().getScreenName() + " - " + s.getText() + "\nRetwitt count: "
												+ s.getRetweetCount() + " Date: " + convertDate(s.getCreatedAt()));
								listid.add(s.getId()); // after adding to database add to list //usually there is
														// duplicate if user has retweeted so need this function
									//higher sentiment value means more positive 
								//value from 0 to 5

								Item tweets = new Item();
								tweets.setTweeter(s.getUser().getScreenName());
								tweets.setDate(convertDate(s.getCreatedAt()));
								tweets.setText(s.getText());
								tweets.setRetweetcount(s.getRetweetCount());
								tweets.setSentimentvalue(NLP.findSentiment(s.getText()));		//higher sentiment value means more positive 
								
								SQLHelper.InsertTweet(tweets);
							}
							// break;
						}

					}
				}

			}
		} catch (TwitterException te) {
			System.out.print(te.getMessage());
			// e.printStackTrace();
		}
	}

	public static void SearchbyHashtag(Twitter twitter, int numberOfTweets) {
		Query query = new Query("#covid19");
		// int numberOfTweets = 100;
		long lastID = Long.MAX_VALUE;
		ArrayList<Status> tweets = new ArrayList<Status>();
		while (tweets.size() < numberOfTweets) {
			if (numberOfTweets - tweets.size() > 100) {
				query.setCount(100);
			} else {
				query.setCount(numberOfTweets - tweets.size());
			}

			try {
				QueryResult result = twitter.search(query);
				tweets.addAll(result.getTweets());
				System.out.println("Gathered " + tweets.size() + " tweets" + "\n");
				for (Status t : tweets) {
					if (t.getId() < lastID) {
						lastID = t.getId();
					}
				}
			}

			catch (TwitterException te) {
				System.out.println("Couldn't connect: " + te);
			}

			query.setMaxId(lastID - 1);
		}

		for (int i = 0; i < tweets.size(); i++) {
			Status t = (Status) tweets.get(i);

			// GeoLocation loc = t.getGeoLocation();

			String user = t.getUser().getScreenName();
			String msg = t.getText();
			// String time = "";
			// if (loc!=null) {
			// Double lat = t.getGeoLocation().getLatitude();
			// Double lon = t.getGeoLocation().getLongitude();*/
			System.out.println(i + " USER: " + user + " wrote: " + msg + "\n");
		}
	}

	public static void getHomeUserTimeline(Twitter twitter) throws TwitterException {
		List<Status> statuses = twitter.getHomeTimeline();

		System.out.println("Showing home timeline.");
		for (Status status : statuses) {
			System.out.println(status.getUser().getName() + "------------" + status.getText()
					+ "\nNumber of retwit count: " + status.getRetweetCount() + " Fav count: "
					+ status.getFavoriteCount() + " Date of Creation: " + status.getCreatedAt());
		}
	}

}
