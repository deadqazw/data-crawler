package project_data_crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Implementation of inheritance
 * The reddit class is a subclass of crawler
 * The reddit scraper is then implemented, it involved invoking its constructor
 * @author user
 *
 */
public class RedditCrawler extends Crawler {

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
	
	
	/**
	 * Method to read received jSON strings
	 * @param url  in string
	 * @return json
	 * @throws IOException
	 * @throws JSONException
	 */
	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		URLConnection urlConnection = new URL(url).openConnection();
		urlConnection.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
		InputStream is = urlConnection.getInputStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		}
		finally {
			is.close();
		}
	}
	
	
	/**
	 * It is an abstract method from crawler class that is inherited
	 */
	public Item[] crawl(int num) { // weird shd not use tweets but other class

		String links = "";
		String postTime = "";
		String rScore = "";
		String title = "";
		String after = "";

		try {

			for (int i = 0; i < 10; i++) {
				// reddit highest limit for posts is 100, after = will display all posts after
				// designated value
				String url = "https://www.reddit.com/r/Coronavirus/new.json?limit=" + num + "&after=" + after;
				JSONObject json = readJsonFromUrl(url); // get json from reddit webpage
				// Parse json for title, links date and score
				JSONObject data = json.getJSONObject("data");
				JSONArray newTopics = data.getJSONArray("children");
				for (int j = 0; j < newTopics.length(); j++) {
					JSONObject topic = newTopics.getJSONObject(j).getJSONObject("data");
					after = topic.getString("name");
					links += topic.getString("permalink") + "\n";
					postTime += String.valueOf(topic.getInt("created_utc")) + "\n";
					rScore += String.valueOf(topic.getInt("score")) + "\n";
					title += topic.getString("title") + "\n";
				}
			}
			// System.out.println(title);
			String Titles[] = title.split("\n");
			String Dates[] = postTime.split("\n");
			String Score[] = rScore.split("\n");
			String Links[] = links.split("\n");
			// In json, date and time is stored as epoch time, so conversion is needed
			for (int i = 0; i < Dates.length; i++) {
				Date date = new Date(Long.parseLong(Dates[i]) * 1000L);
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd");	//yyyy-MM-dd HH:mm:ss.SSS
				format.setTimeZone(TimeZone.getTimeZone("UTC"));
				Dates[i] = format.format(date);
				System.out.println(Dates[i]);
			}

			SQLHelper.Maketable_Reddit(); // make new table with timestamp as title
			for (int i = 0; i < Titles.length; i++) {

				Item newPost = new Item();

				newPost.setTitle(Titles[i]);
				newPost.setDate(Dates[i]);
				newPost.setLink(Links[i]);
				newPost.setScore(Score[i]);
				newPost.setSentimentvalue(NLP.findSentiment(Titles[i]));

				SQLHelper.InsertPost(newPost);

			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}

		return new Item[0];

	}
}
