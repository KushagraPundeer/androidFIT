package com.android.drexelfit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.*;


public class Rewards 
{
	private static final String[] days = new String[] {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
	private static final String[] meals = new String[] {"Breakfast","Lunch","Dinner"};
	private static int menuParsed;
	private static int goalChosen;
	
	// Multiplier for calculating calorie 'cost' of each reward (per Database 
	private static double costMultiplier;
	
	// Full list of all available menu items, all data
	private static List<List<String>> fullList;	
	
	// List of all available menu items, limited data
	private static List<List<String>> finalList;
	
	// List of menu items available as rewards, given caloric burn goals 
	private static List<List<String>> rewardsOffered;
	
	
	/*  Rewards() - class constructor  */
	public Rewards()
	{
		fullList = new ArrayList<List<String>>();
		finalList = new ArrayList<List<String>>();
		rewardsOffered = new ArrayList<List<String>>();
		menuParsed = -1;
		goalChosen = -1;
		costMultiplier = 1;	// Initial cost is straight caloric value
	}
	
	
	
	/*  setCostMultiplier - sets the multiplier used to calculate a given food's
	 *  caloric cost (per use database's Food.java object).
	 *  @param multiplier: a positive double between .5 and 2.0
	 */
	public void setCostMultiplier( double multiplier )
	{
		costMultiplier = multiplier;
	}
	
	
	
	/*  getCostMultiplier - returns the multiplier used to calculate a given food's
	 *  caloric cost (per use database's Food.java object).
	 */
	public double getCostMultiplier()
	{
		return costMultiplier;
	}
	
	
	
	/*  getRewards - return parsed list of all menu items 
	 *  @return: finalList, a 2 dimensional ArrayList of all menu items
	 */
	public List<List<String>> getRewards()
	{
		if( menuParsed > 0 )
			return finalList;
		else
			return null;
	}
	
	
	
	/*  getRewardsOffered - returns list of rewards available based on chosen goals.
	 *  @return: rewardsOffered, a 2 dimensional ArrayList of menu items available
	 *  given current calories burned per reward time period. 
	 */
	public List<List<String>> getRewardsOffered()
	{
		if( goalChosen > 1 )
			return rewardsOffered;
		else
			return null;
	}

	
	
	/*  connect - Creates an HttpURLConnection in order to connect to the
	 *  desired API.
	 *  @param urlKey: a website, configured with parameters as needed
	 *  @return: an InputStream object used by the calling method
	 */
	public static InputStream connect (String website)
	{
		URL url;
		HttpURLConnection urlRequest = null;
		InputStream inStream = null;
		try
		{
			url = new URL(website);
			urlRequest = (HttpURLConnection) url.openConnection();
			urlRequest.connect();
			inStream = urlRequest.getInputStream();
		}
		
		catch (MalformedURLException ex) 
		{
			ex.printStackTrace();
		} 
		
		catch (IOException ex) 
		{
			ex.printStackTrace();
		}
		
		return inStream;
	}	// End connect()
	
	
	
	/*  retMenu - retrieve menu items from Drexel University dining menu web site.
	 *  Sets 'fullList' class variable which contains all available nutrition and
	 *  information for each menu item.  Also sets meal type and day food item is offered.   
	 *  @param website: URL for weekly menu.
	 */
	public void retMenu( String website )
	{
		// Declarations
		URL url;
		InputStream inStream;
		String line = null;
		List<String> row = null;
		String day = null;
		String meal = null;
		String id = null;
		List<List<String>> foodDataList = new ArrayList<List<String>>();
		List<String> row2 = null;
		
		inStream = connect(website);
		
		/*  Retrieve menu, set 'fullList' variable  */
		try
		{
			BufferedReader read = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));
			
			while( (line = read.readLine()) != null )
			{				
				if( line.startsWith("aData["))
				{
					String[] foodArrays = line.replace("'", "").split(","); 
					row = new ArrayList<String>();
					fullList.add(row);		// Add row FIRST...
					
					for(int i = 0; i < foodArrays.length; i++)
					{
						row.add(foodArrays[i]);
					}
					
					row.set(0, row.get(0).substring(6, 22));
				}
				
				
				if( line.contains("id=\"S") )
				{
					String[] foodData = line.split("\\s");
					row2 = new ArrayList<String>();
					foodDataList.add(row2);
					
					for(int i = 0; i < foodData.length; i++)
					{
						row2.add(foodData[i]);
					}
				}
			}	// End while{}
			
			
			for(int i = 0; i < foodDataList.size(); i++)
			{
				for(int j = 0; j < foodDataList.get(i).size(); j++)
				{
					if(foodDataList.get(i).get(j).startsWith("id"))
					{		
						int dayPos =  Integer.parseInt(foodDataList.get(i).get(j).substring(5, 6));
						day = days[dayPos-1];
						
						String mealChar = foodDataList.get(i).get(j).substring(6, 7);
						
						if(mealChar.equals("B"))
							meal = meals[0];
						if(mealChar.equals("L"))
							meal = meals[1];
						if(mealChar.equals("S"))
							meal = meals[2];
						
						id = foodDataList.get(i).get(j).substring(13, 29);	// WATCH
					}
				}
				
				for(int k = 0; k < fullList.size(); k++)
				{
					if(fullList.get(k).get(0).equals(id))
					{
						fullList.get(k).add(meal);
						fullList.get(k).add(day);
					}
				}
			}
			
			inStream.close();
			
		}	// End try block
			
		/*  Process exceptions  */
		catch (java.net.MalformedURLException ex)
		{
			System.out.println("Invalid URL");
		}
		
		catch (java.io.IOException ex)
		{
			System.out.println("I/O Error!");
		}
	}
	
	
	
	/*  parseMenu - parses 'fullList', populating the 'finalList' array with the following values:
	 *  Food item
	 *  Calories
	 *  Day available (Monday - Sunday)
	 *  Meal (Breakfast, Lunch, Dinner)
	 *  (Note: All values are as offered though the Drexel Food Service web site) 
	 */
	public void parseMenu()
	{
		// Declarations
		List<String> row = null;
		
		/*  Print the result  */
		for(int i = 0; i < fullList.size(); i++)
		{
			row = new ArrayList<String>();
			finalList.add(row);
			
			// Add food item
			row.add(fullList.get(i).get(22));

			// Add calories
			row.add(fullList.get(i).get(1));	
			
			// Add day available
			row.add(fullList.get(i).get(fullList.get(i).size()-1));
			
			// Add meal type
			row.add(fullList.get(i).get(fullList.get(i).size()-2));
		}
		
		menuParsed = 1;
	}
	
	
	
	/*  caloriesPerStep - calculates a user's calories burned per step.
	 *  @param weight: the user's weight as obtained from the FitBit API.
	 *  @param strideLength: the user's stride length as obtained from the FitBit API.
	 *  @return: CPS: the number of calories a user burns per step.
	 *  Note: calculated per method obtained from: 
	 *  http://www.livestrong.com/article/238020-how-to-convert-pedometer-steps-to-calories/
	 */
	public static double caloriesPerStep ( double weight, double strideLength )
	{
		/*  Calculate calories burned per mile  */
		double CPM = (weight * .57);
		
		/*  Calculate steps per mile  */
		double SPM = (5280 / strideLength);
		
		/*  Calculate calories per step  */
		double CPS = (CPM / SPM);
		
		return CPS;
	}
	
	
	
	/*  calculateFoodCost - finds the calorie 'cost' for given food item per use 
	 *  database's Food.java object.
	 *  @param calories: calories for a given food item
	 *  @return: caloric cost, which is calories * costMultiplier
	 */
	public int calculateFoodCost( int calories )
	{
		int foodCost = (int) (calories * costMultiplier);
		
		return foodCost;	
	}
	
	
	
	/*  findCurrentRewards - populates 'rewardsOffered' ArrayList with list of items
	 *  that are currently available for user to choose as as reward.
	 *  @param caloriesBurned: the total number of calories (per step) that a user
	 *  has burned over the course of a given activity. 
	 */
	
	// TODO: This should use data from a GOAL object (from the database). This object
	// 		 should contain the current calories burned for the given time period
	public void findCurrentRewards( int caloriesBurned )
	{
		// Declarations
		List<String> row = null;
		
		rewardsOffered.clear();
		
		for(int i = 0; i < finalList.size(); i++)
		{
			row = new ArrayList<String>();
			rewardsOffered.add(row);
			
			int cost = calculateFoodCost( Integer.parseInt(finalList.get(i).get(1)) );
			
			//if ( caloriesBurned >= Double.parseDouble(finalList.get(i).get(1)) )
			if ( caloriesBurned >= cost )
			{
				row.addAll(finalList.get(i));	// WATCH, may have to iterate
			}
		}
		
		goalChosen = 1;
	}
	
	
}	// End class





