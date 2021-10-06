package project_data_crawler;

import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;

import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentAnnotatedTree;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

/**
 * Class called NLP which run sentiment analysis, it uses an external JAR library
 * @author junji
 *
 */
public class NLP {
	static StanfordCoreNLP pipeline;
	
	/**
	 * Pre-set the properties of the sentiment analysis library
	 */
	public static void init() {
		Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
		
		pipeline = new StanfordCoreNLP(props);
	}
	
	/**
	 * Method that run sentiment analysis on a single tweet or post for both twitter and reddit
	 * @param tweet It can be any string string
	 * @return mainSentiment value in integer after the string is determined
	 */
	public static int findSentiment(String tweet) {
		
		Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
		
		pipeline = new StanfordCoreNLP(props);
		

		int mainSentiment = 0;
		if (tweet != null && tweet.length() > 0) {	
			int longest = 0;
			Annotation annotation = pipeline.process(tweet);

            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence.get(SentimentAnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                String partText = sentence.toString();
                if (partText.length() > longest) {
                    mainSentiment = sentiment;
                    longest = partText.length();
                }

            }
			
		}
		return mainSentiment;
	}

}
