package project_data_crawler;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;


import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.EventQueue;

/**
 * The GUI class is created to to display a Graphical User Interface for ease of use
 * @author User
 * It inherities the Jframe and implements the Action listerner that will override some of the operation
 */

public class GUI extends JFrame implements ActionListener{

	//variables
	private JLabel lL, l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12;
	private JButton b1, b2, b3, b4;
	private JTextField t1, t2, t3, t4, t5, t6;
	private JRadioButton r1, r2, r3, r4;
	static JFrame frame;

	private JPanel panelR, panelT, panelEx;
	private static final long serialVersionUID = 1L;
	

	/**
	 * This is the overloaded construtor for public class GUI which is used for creating the pie chart and line chart that is displayed
	 * @param i can be any number, just to tell the coder it is not the constructor used for UI
	 * @param Sen is the list for the data used in the charts
	 */
	
	public GUI(int i, ArrayList<Item> Sen) {	//for pie chart
		initUI(Sen);
	}
	/**
	 * This is the default construtor for public class GUI which first create the UI
	 */
	public GUI() {

    	//creating instance of button
      	b1=new JButton("Generate Graph");  
      	b1.setBounds(200,420,150,20); //x axis, y axis, width, height 
      	b2=new JButton("Search Reddit");  
      	b2.setBounds(100,340,150,20);
      	b3=new JButton("Search Twitter");  
      	b3.setBounds(470,340,150,20);
      	b4=new JButton("Generate Pie Chart");  
      	b4.setBounds(360,420,150,20);
      		
      	//set action listeners for buttons
        b1.addActionListener(this);    
        b2.addActionListener(this);    
        b3.addActionListener(this);    
        b4.addActionListener(this);    
        
        //creating instance of label
	    l1=new JLabel("  REDDIT");  
	    l1.setBounds(20,20,60,20);
	    l1.setForeground(Color.WHITE); l1.setBackground(Color.RED); l1.setOpaque(true);
	    l2=new JLabel(" TWITTER");  
	    l2.setBounds(370,20,65,20); 
	    l2.setForeground(Color.WHITE); l2.setBackground(Color.BLUE); l2.setOpaque(true);
	    l3=new JLabel("Search post:");  
	    l3.setBounds(20,60,100,20);
	    l4=new JLabel("Search tweet:");  
	    l4.setBounds(370,60,100,20);
	    l5=new JLabel("Search by date:");  
	    l5.setBounds(20,100,100,20);
	    l6=new JLabel("Search by date:");  
	    l6.setBounds(370,100,100,20);
	    l7=new JLabel("Sort Posts by:");  
	    l7.setBounds(20,200,100,20);
	    l8=new JLabel("Sort Tweets by:");  
	    l8.setBounds(370,200,100,20);
	    l9=new JLabel("View no. of Posts:");  
	    l9.setBounds(20,140,100,20);
	    l10=new JLabel("View no. of Tweets:");  
	    l10.setBounds(370,140,110,20);
	    l11=new JLabel("(YYYY-MM-DD)");  
	    l11.setBounds(20,113,100,20);
	    l12=new JLabel("(YYYY-MM-DD)");  
	    l12.setBounds(370,113,100,20);
	    
	    //creating instance of text field
	    t1=new JTextField();  
	    t1.setBounds(140,60,170,20);   
	    t2=new JTextField();  
	    t2.setBounds(510,60,170,20);
	    t3=new JTextField();  
	    t3.setBounds(140,100,170,20); 
	    t4=new JTextField();  
	    t4.setBounds(510,100,170,20); 
	    t5=new JTextField();  
	    t5.setBounds(140,140,170,20); 
	    t6=new JTextField();  
	    t6.setBounds(510,140,170,20); 
	    
	    //create instance of radio buttons
	    r1=new JRadioButton("Most Recent"); 
	    r1.setBounds(20,220,100,30); 
	    r1.setOpaque(false);
	    r2=new JRadioButton("Most Upvoted");    
	    r2.setBounds(20,250,120,30);   
	    r2.setOpaque(false);
	    
	    ButtonGroup bg1=new ButtonGroup();    
	    bg1.add(r1);bg1.add(r2); 
	    
	    r3=new JRadioButton("Most Recent"); 
	    r3.setBounds(370,220,100,30); 
	    r3.setOpaque(false);
	    r4=new JRadioButton("Most Retweeted");    
	    r4.setBounds(370,250,120,30);   
	    r4.setOpaque(false);
	    
	    ButtonGroup bg2=new ButtonGroup();    
	    bg2.add(r3);bg2.add(r4); 

	    //create panels to differentiate twitter and reddit sides
	    panelR=new JPanel();  
	    panelR.setBounds(10,50,345,350);    
	    panelR.setBackground(Color.PINK);   
	    panelT=new JPanel();  
	    panelT.setBounds(360,50,355,350);    
	    panelT.setBackground(Color.CYAN);
	    panelEx=new JPanel();  
	    panelEx.setBounds(360,50,355,350);    

	    //add elements to frame
	    add(b1);add(b2);add(b3);add(b4);
	    add(l1);add(l2);add(l3);add(l4);add(l5);add(l6);add(l7);add(l8);add(l9);add(l10);add(l11);add(l12);
	    add(t1);add(t2);add(t3);add(t4);add(t5);add(t6);
	    add(r1);add(r2);add(r3);add(r4);
	    add(panelR);add(panelT);add(panelEx);
 
    }
	
	/**
	 * This method is called whenever the user have clicked a button
	 */
	@Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        String strRDate = t3.getText(); //get textfield value
        String strTDate = t4.getText();
        String strRKey = t1.getText();
        String strTKey = t2.getText();
        String strRNum = t5.getText();
        String strTNum = t6.getText();
        
        int verify = 0;
        ArrayList<Item> ST = new ArrayList<>();
        
        //read REDDIT textfield inputs after pressing button
        if (action.equals("Search Reddit")) {
            
        	//check if search post field is filled
        	if (strRKey.matches("")) {
        		System.out.println("No keyword searched");
        	}else {
        		System.out.println(strRKey);
        		verify++;
        	}
        	
        	//check if view no. field is filled with integer
        	if (strRNum.matches("")) {
        		System.out.println("No number specified");
        	}else {
        		try {
        			int intRNum =Integer.parseInt(strRNum);
            		System.out.println(intRNum);
            		verify++;
                } catch (NumberFormatException e) {
                	JOptionPane.showMessageDialog (null, "Reddit: Error in View no. of Posts - Enter integer only");
                }
        	}
            
            //check if date field is filled and in correct format
            if (strRDate.matches("")) {
            	System.out.println("No date searched");
            }
            else if (strRDate.matches("\\d{4}-\\d{2}-\\d{2}")) 
            {
                        System.out.println(strRDate);
                        verify++;
            }else {
                JOptionPane.showMessageDialog (null, "Reddit: Error in Search by Date - Invalid Format");
            }
            
            //check which radio button is selected
            if (r1.isSelected() && verify == 3) {
            	System.out.println("sort by Most Recent Post selected");					//sort by Most Recent Post selected reddit
            	ST = SQLHelper.getDataR(strRKey, Integer.parseInt(strRNum));		//strRKey is keyword	//strRNum is number of post
            	int i = 1;
            	for (Item item : ST) {
        			
            		infoBox("Most Recent Post: "+ i + "\nTitle: " + item.getTitle() + "\nNumber of upvotes: " + 
            		item.getScore() + " \nSentimentvalue: " + item.getSentimentvalue()+"\n" + "Date of post: " + item.getDate(), "Most Recent Post");
            		i++;
                }
            	
            	ArrayList<Item> Sen = SQLHelper.getSentiment(2, strRKey, Integer.parseInt(strRNum));		//1 is for twitter 2 is for reddit		//.getSentiment is after filtering from db
	            EventQueue.invokeLater(() -> {

	                var ex = new GUI(1, Sen);	//1 for setting for other GUI interface
	                ex.setVisible(true);
	            });
            	
            	
            }
            else if (r2.isSelected() && verify == 3) {
            	System.out.println("sort by Most Upvoted Post selected");					//sort by Most Upvoted Post selected reddit	
            	ST = SQLHelper.getDataR(strRKey, strRDate, Integer.parseInt(strRNum));		//strRKey is keyword	//strRNum is number of post
            	int i=1;
            	for (Item item : ST) {
        			
            		infoBox("Most Upvoted Post: "+ i + "\nTitle " + item.getTitle() + "\nNumber of upvotes: " + item.getScore() +
            		" \nSentimentvalue: " + item.getSentimentvalue() + "\n" + "Date of post: " +  item.getDate(), "Most upvoted Post");
            		i++;
                }
            	
            	ArrayList<Item> Sen = SQLHelper.getSentiment(2, strRKey, strRDate, Integer.parseInt(strRNum));		//1 is for twitter 2 is for reddit		//.getSentiment is after filtering from db
	            EventQueue.invokeLater(() -> {

	                var ex = new GUI(1, Sen);	//1 for setting for other GUI interface
	                ex.setVisible(true);
	            });
            	
            	
            }
            else {
            	JOptionPane.showMessageDialog (null, "Reddit: Error in Sort by - Select one sorting method");
            }
        }
        																			//start of twitter
          //read TWITTER textfield inputs after pressing button
        int intTNum;
        int verify2 = 0;
        
        if (action.equals("Search Twitter")) {
            //check if search tweet field is filled
            if (strTKey.matches("")) {
            	System.out.println("No keyword searched");
            }else {
            		System.out.println(strTKey);
            		verify2++;
            }
            	
            //check if view no. field is filled with integer
            if (strTNum.matches("")) {
            	System.out.println("No number specified");
            }else {
            	try {
            		intTNum=Integer.parseInt(strTNum);
                	System.out.println(intTNum);
                	verify2++;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog (null, "Twitter: Error in View no. of Posts - Enter integer only");
                }
            }
                
            //check if date field is filled and in correct format
            if (strTDate.matches("")) {
                System.out.println("No date searched");
            }
            else if (strTDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                System.out.println(strTDate);
                verify2++;
            }else {
                JOptionPane.showMessageDialog (null, "Twitter: Error in Search by Date - Invalid Format");
            } 
            
          //check which radio button is selected
            if (r3.isSelected() && verify2 == 3) 		//varify makes sure it must satisfy the three condition for search
            {
            	System.out.println("sort by Most Recent Tweet selected");							//sort by Most Recent Tweet selected    twitter
            	
            	ST = SQLHelper.getDataT(strTKey, Integer.parseInt(strTNum));		//strTKey is keyword	//strTDate is date	//strTNum is number of post
            	int i = 1;
            	for (Item item : ST) {
        			
            		infoBox("Most Recent Tweets: "+ i + "\nTweeter: " + item.getTweeter() + " said\n" + item.getText() + 
            		"\nNumber of Retweets: " + 	item.getRetweetcount() + " \nSentimentvalue: " + item.getSentimentvalue()+"\n" +
            		"Date of post: " + item.getDate(), "Most Recent Tweets");
            		i++;
                }
            	
            	ArrayList<Item> Sen = SQLHelper.getSentiment(1, strTKey, Integer.parseInt(strTNum));		//1 is for twitter 2 is for reddit		//.getSentiment is after filtering from db
	            EventQueue.invokeLater(() -> {

	                var ex = new GUI(1, Sen);	//1 for setting for other GUI interface
	                ex.setVisible(true);
	            });
            	
            	
            }
            else if (r4.isSelected()) 
            {
            	System.out.println("sort by Most Retweeted Tweet selected");						//sort by Most Retweeted Tweet selected    twitter
            	
            	ST = SQLHelper.getDataT(strTKey, strTDate, Integer.parseInt(strTNum));		//strTKey is keyword	//strTDate is date	//strTNum is number of post
            	int i = 1;
            	for (Item item : ST) {
        			
            		infoBox("Most Retweeted Tweets: "+ i + "\nTweeter: " + item.getTweeter() + " said\n" + item.getText() + 
            		"\nNumber of Retweets: " + 	item.getRetweetcount() + " \nSentimentvalue: " + item.getSentimentvalue()+"\n" +
            		"Date of post: " + item.getDate(), "Most Retweeted Tweets");
            		i++;
                }
            	ArrayList<Item> Sen = SQLHelper.getSentiment(1, strTKey, strTDate, Integer.parseInt(strTNum));		//1 is for twitter 2 is for reddit		//.getSentiment is after filtering from db
	            EventQueue.invokeLater(() -> {

	                var ex = new GUI(1, Sen);	//1 for setting for other GUI interface
	                ex.setVisible(true);
	            });
            	
            	
            }else {
            	JOptionPane.showMessageDialog (null, "Twitter: Error in Sort by - Select one sorting method");
            }
            
        }

	        //check if generate graph or pie chart button was pressed
	        if (action.equals("Generate Graph")) {
	        	//if the button was pressed, perform this function
	            System.out.println("Generate Graph button pressed!");				//Generate Graph button pressed		
	            
	            EventQueue.invokeLater(() -> {
	                GUI linechart = new GUI("Number of Post");  
	                linechart.setSize(800, 400);  
	                linechart.setLocationRelativeTo(null);  
	                linechart.setVisible(true);  
	            });
	            
	            
	            
	        }else if (action.equals("Generate Pie Chart")) {
	            System.out.println("Generate Pie Chart button pressed!");			//Generate Pie Chart button pressed		//ADD REDDIT DATA ALSO
	            //get data first
	            ArrayList<Item> Sen = SQLHelper.getSentiment_T();

	            
	            
	            EventQueue.invokeLater(() -> {

	                var ex = new GUI(1, Sen);
	                ex.setVisible(true);
	            });
	            
	        }
    }
	public static String convertDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String format = formatter.format(date);

		return format;
	}
	/**
	 * converting to localdate for easier methods of getting the day,month and year and date.get method is depricated
	 * @param date1 it is the date that needs to be converted
	 * @return Return the date that is in the correct format required
	 */
	
	public static LocalDate convertString(String date1) {

		try {

			LocalDate d1 = LocalDate.parse(date1);
			
			return d1;
		}
		catch (Exception e) {
			System.out.print(e.getMessage());
		}
		return null;

		
		
	}
	
	/**
	 * This is called when it want to create a piechart
	 * @param Sen is the array list which the data is generated by SQLHelper
	 */
    private void initUI(ArrayList<Item> Sen) {
    	//ArrayList<Item> Sen = new ArrayList<>();
    	//Sen = SQLHelper.getSentiment(1);		//world.twittertweet or world.redditpost   //1 is for twitter 2 is for reddit
		
    	
    	final PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("{0} = {1}");
    	
    	
        DefaultPieDataset dataset = createDataset(Sen);

        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel);
        PiePlot plot1 = (PiePlot) chart.getPlot();
        plot1.setLabelGenerator(labelGenerator);
        plot1.setExplodePercent("3", 0.25);
        pack();
        setTitle("Pie chart");
        setLocationRelativeTo(null);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);			//close the program when the graph generated is close
    }
    
    /**
     * This is filtering some data into the more readable format
     * @param Sen is the array list 
     * @return Return the dataset from it is converted into the more readable format
     */
    private DefaultPieDataset createDataset(ArrayList<Item> Sen) {

        var dataset = new DefaultPieDataset();
        String PosNeg="";
        String pre_sen="";
        int pre_val=0;
        
		for (Item item : Sen) {
			/*
            //System.out.print(customer.getSentimentindex());						
            Lname.add(String.valueOf(item.getSentimentindex()));	//convert to string
            total += item.getSentimentvalue();
            //System.out.print(customer.getSentimentvalue());
            Lval.add(String.valueOf(item.getSentimentvalue()));	//convert to string
            */
			//dataset.setValue("Apache", 52);
	        //dataset.setValue("Nginx", 31);
	        switch(item.getSentimentindex()) 
	        { 
	        	case 0: 
	            	PosNeg = "Very Negative";
	            	//pre_sen="Undefined";
	            	//pre_val=item.getSentimentvalue();
	                break; 
	            case 1: 
	                PosNeg = "Negative";
	                //pre_sen="Negative";
	                //pre_val=item.getSentimentvalue();
	                break; 
	            case 2: 
	            	PosNeg = "Neutral";
	            	//pre_sen="Neutral";
	            	//pre_val=item.getSentimentvalue();
	                break; 
	            case 3: 
	            	PosNeg = "Positive";
	            	//pre_sen="Positive";
	            	//pre_val=item.getSentimentvalue();
	                break;
	            case 4: 
	            	PosNeg = "Very Positive";
	            	//pre_sen="Very Positive";
	            	//pre_val=item.getSentimentvalue();
	                break; 
	            case 5: 
	            	PosNeg = "Undefined";
	            	//pre_sen="Undefined";
	            	//pre_val=item.getSentimentvalue();
	                break; 
	            default: 
	                System.out.println("no match"); 
	        } 
			if (pre_sen == PosNeg) {
				dataset.setValue(PosNeg, item.getSentimentvalue() + pre_val);
				
				
				pre_val = 0;
			}
			else {
				pre_sen= PosNeg;
				pre_val=item.getSentimentvalue();
				dataset.setValue(PosNeg, item.getSentimentvalue());		//if same key will get replaced instead of adding
			}
        }
        
        return dataset;
    }

    private JFreeChart createChart(DefaultPieDataset dataset) {

        JFreeChart pieChart = ChartFactory.createPieChart(
                "Overall Sentiment",
                dataset,
                false, true, false);

        return pieChart;
    }
	/**
	 * Creates the UI for the user to enter keyword , date and number of post or tweets to show
	 */
    private static void createAndShowGUI() {
    	 
    	//Create and set up the window.
    	frame = new GUI();
    	//Display the window.
    	frame.setSize(740,500);
    	frame.setVisible(true);
    	frame.setLayout(null);
    	frame.setTitle("Reddit and Twitter Crawler");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
    }
    
    public GUI(String title) {  
        super(title);  
        // Create dataset and pass in 2 data set in sql already done then use for loop
        
        
        
        XYDataset dataset = createDataset();  
        // Create chart  
        JFreeChart chart = ChartFactory.createTimeSeriesChart(  
            "Time Series Chart", // Chart  
            "Date", // X-Axis Label  
            "Number", // Y-Axis Label  
            dataset);  
      
        //Changes background color  
        XYPlot plot = (XYPlot)chart.getPlot();  
        plot.setBackgroundPaint(new Color(255,228,196));  
          
        ChartPanel panel = new ChartPanel(chart);  
        setContentPane(panel);  
      }  
    
    private XYDataset createDataset() {  
        TimeSeriesCollection dataset = new TimeSeriesCollection();  
        ArrayList<Item> num_tweet = SQLHelper.getPost_T(1);		//1 is tweeter		//2 is reddit
        ArrayList<Item> num_post= SQLHelper.getPost_T(2);
        
        TimeSeries series1 = new TimeSeries("Twitter");  
        for (Item item : num_tweet) {
        	LocalDate d1 = convertString(item.getDate());		// * 10
        	int x = d1.getYear();
        	int xx = d1.getMonthValue();
        	int xxx = d1.getDayOfMonth();
        	
        	series1.add(new Day( xxx,xx,x ), item.getSentimentvalue() * 5);  //why i put the number 5		//	https://www.statista.com/statistics/1015131/impact-of-social-media-on-daily-life-worldwide/
        }
        //series1.add(new Day(1, 1, 2017), 50);  
      //  series1.add(new Day(2, 1, 2017), 40);  
      //  series1.add(new Day(3, 1, 2017), 45);  

        dataset.addSeries(series1);  
      
        TimeSeries series2 = new TimeSeries("Reddit");  
        for (Item item : num_post) {
        	LocalDate d1 = convertString(item.getDate());
        	int x = d1.getYear();
        	int xx = d1.getMonthValue();
        	int xxx = d1.getDayOfMonth();
        	
        	series2.add(new Day( xxx,xx,x ), item.getSentimentvalue());
        }

        dataset.addSeries(series2);  
          
      
        return dataset;  
      }  

    /**
     * This is the method called once the post or tweets needs to be shown
     * @param infoMessage this is the information in the textbox
     * @param titleBar 	This is the title bar of the messagebox
     */

    public static void infoBox(String infoMessage, String titleBar)		//message box
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
	/**
	 * method of the crawl
	 * @param crawler specifices which crawler to crawl data from either reddit or twitter
	 * @param num It specifies the amount of information to get from the specific social media
	 */
	public void crawl(Crawler crawler,int num) {
		crawler.crawl(num);
	}
	/**
	 * Main method of GUI 
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
    	
		//First i get the data then i display the required information!!
		GUI crawlerprogram = new GUI();

		
		
		
		Crawler tweeterCrawler = new TwitterCrawler();
		Crawler redditCrawler = new RedditCrawler();

		//crawlerprogram.crawl(tweeterCrawler, 10);	//5 is the amount of information
		//crawlerprogram.crawl(redditCrawler, 100);	//100 is the value of information that reddit crawl by pages
		
		
		//Schedule a job for the event-dispatching thread:
    	//creating and showing this application's GUI.
    	javax.swing.SwingUtilities.invokeLater(new Runnable() {
    		public void run() {
    			createAndShowGUI(); 
    		}
    	});
		

	}

}
