
package com.weathertech.weatheranalyser;
//Imported classes
import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.time.format.DateTimeFormatter;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.lang.Exception;


public class WeatherDataAnalyser 
{
    //instance variables
    LocalDate date = LocalDate.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("E dd-MM-yyyy");
    ArrayList<String[]> dates = new ArrayList<>();
    ArrayList<Double[]> temps = new ArrayList<>();
    ArrayList<String> wCdates = new ArrayList<>();
    int weekOption; 
    final String celsius=(char)176+"C";
    Scanner kb = new Scanner(System.in);
    
//Method for displaying program logo with nested for loop.
public void logo()
{
    for(int i=0; i<5; i++)
    {
        System.out.println();
        for(int j=0;j<50;j++)
        {
            if (i==1 && j==18)
            {
                System.out.print(" WEATHERTECH ");
                j+=12;
            }
            else if(i==3 && j==13)
            {
                System.out.print(" WeatherDataAnalyser"+celsius+" ");
                j+=22;
            }

            else {System.out.print("/");}
        }
        }
    }

    //Method for displaying menu options and then taking user
    // input with scanner object and passing to menuOptions()
    public void mainMenu()
    {
    int userOp=0;
    boolean valid=false;
    logo();
    while(!valid)
    {
        try {
            System.out.println("\n|1| Enter Tempratures\n|2| Average Temperature\n" +
                    "|3| Highest Temperature\n|4| Lowest Temperature\n|0| Quit");
            System.out.println("/////////////////////////////////////////////////");
            String strUserOp=kb.nextLine();
            userOp = Integer.parseInt(strUserOp);
            
            if(userOp>4||userOp<0) 
            {
            throw new InputMismatchException();
            }
            valid=true;
            }
            catch (InputMismatchException|NumberFormatException e) 
            {
            System.out.println("Error: Invalid selection");
            kb.nextLine();
            }
           
            
            
    }
    menuOptions(userOp);
    }

//Method to take user WC date and temps and assign a date incremented each time and put into appropriate array.
//Will only accept temps with a realistic range
public void collectData()throws DateTimeException
{
    boolean valid=false;
    String[] imputWeek = new String[7];
    Double[] weeksTemps = new Double[7];
    while(!valid)
    {   
        try { 
    
    System.out.println("Please enter the week commencing date  |0| Cancel");
    System.out.println("(Monday)");
    System.out.println("Format: DD/MM/YYYY");
    String dateStr = kb.nextLine();
    if(dateStr.length()<10){
    if (Integer.parseInt(dateStr)==0){
        cancel();
     } 
    }
    if (!validDate(dateStr)){
        throw new InputMismatchException();
    }
    String regex = "[\\/\\.\\s]";
    String[] dateArr = dateStr.split(regex);
    String day = dateArr[0];
    String month = dateArr[1];
    String year = dateArr[2];
    int dayInt = Integer.parseInt(day);
    int monthInt = Integer.parseInt(month);
    int yearInt = Integer.parseInt(year);
    date=date.of(yearInt, monthInt, dayInt);
    
    valid=true;
}
    catch(ArrayIndexOutOfBoundsException|InputMismatchException|NumberFormatException|DateTimeException e){
        System.out.println("Error: Invalid imput");
    }
    
    }
    for (int i=0;i<7;i++)
    {
        boolean validTemp=false;
        while(!validTemp)
          try
          {
          System.out.println("--------------------------------------------------");
          System.out.println(date.plusDays(i).format(myFormatObj)+"| Enter temp:             |0| Cancel");
          double temp=Double.parseDouble(kb.nextLine());
          if(temp==0){cancel();}
          else if(temp>80||temp<-100)
          {
          throw new InputMismatchException();
          }
          if(temp > 60.0||temp < -50.0||temp>0&&temp<1) 
          {
            System.out.println("/////////////////////////////////////////////////");
            System.out.println("Alert: " +temp+" entererd\nPlease reenter to confirm");
            System.out.println("/////////////////////////////////////////////////");
            temp=Double.parseDouble(kb.nextLine());
          }
          validTemp=true;
          weeksTemps[i]=temp;
          imputWeek[i]=date.plusDays(i).format(myFormatObj);
          }
          catch (InputMismatchException|NumberFormatException e) {
          System.out.println("Error: Invalid imput");
    }
    }
    wCdates.add(date.format(myFormatObj));
    dates.add(imputWeek);
    temps.add(weeksTemps);
    next(1);
}
//checks date entry is of valid length
public boolean validDate(String str)
{
    boolean valid=false;
    if(str.length()!=10)
    {
        return valid;
    }
    return !valid;
}
//Prompts user to select a week to perform calculation on. Weeks are displayed by WC date.
public String weekSelector()
{
   if(wCdates.size()==0)
   {
   System.out.println("No weeks have been recorded");
   mainMenu();
   }
   //Scanner kb = new Scanner(System.in);
    String selectedWeek;
    Boolean valid=false;
    System.out.println("/////////////////////////////////////////////////");
    while(!valid)
    {
    try {
        
        for(int i=0;i<wCdates.size();i++)
        {    
        //int numList=i;    
        System.out.println("|"+(i+1)+"|    "+wCdates.get(i));
        }
        System.out.println("Please select week:                   |0| Cancel");
        weekOption= Integer.parseInt(kb.nextLine());
        if(weekOption==0){cancel();}
        if(weekOption-1>wCdates.size()||weekOption<0) 
        {
        throw new InputMismatchException("Error: Invalid selection");
        }
        
        valid=true;
        
        }
        catch (InputMismatchException|NumberFormatException e) 
        {
        System.out.println("Error: "+e.getMessage());
        }
        
}
selectedWeek = wCdates.get(weekOption-1);
return selectedWeek;
}
//Uses nested loop to iterate through 2d array. The outer being an arraylist and the inner being an array.
//Displays all temperatures of that week with date.
public void calculateAverageTemperature(String selectedWeek)
{
    for(int i=0;i<dates.size();i++)
    {
        for(int j=0;j<dates.get(i).length;j++)
        {
         if(dates.get(i)[j].equals(selectedWeek))                                                     
         {                                                                                             
            System.out.println("Average temprature for week commencing:\n"+selectedWeek);
            System.out.println("-------------------------------------------------");    
            double total=0;                                                                           
         for(int t=0;t<temps.get(i).length;t++)                                                       
         {
            System.out.println(dates.get(i)[t]+"  |  "+temps.get(i)[t]+(char)176+"C");
         total+=temps.get(i)[t];
         }
         System.out.println("-------------------------------------------------"); 
         System.out.println((total/temps.get(i).length+celsius));
         }
        }
    }
    next(2);
}
//Calculates and displays the highest temperature through a nested for loop in same way as calculateAverageTemperature()
//Displays all temperatures of that week with date.
public void findHighestTemperature(String selectedWeek)
{
    for(int i=0;i<dates.size();i++)
    {
        for(int j=0;j<dates.get(i).length;j++)
        {
         if(dates.get(i)[j].equals(selectedWeek))                                                      
         {                                                                                             
            System.out.println("Highest temprature for week commencing:\n"+selectedWeek);
            System.out.println("-------------------------------------------------");    
            double highestTemp=temps.get(i)[0];                                                                          
         for(int t=0;t<temps.get(i).length;t++)                                                        
         {
            {
                highestTemp=Math.max(highestTemp,temps.get(i)[t]);
            }
        }
    for(int x=0;x<temps.get(i).length;x++)
        if(highestTemp==temps.get(i)[x])
        {
        System.out.println(dates.get(i)[x]+"  |  "+temps.get(i)[x]+celsius+"    Highest");
        }
        else
        {
        System.out.println(dates.get(i)[x]+"  |  "+temps.get(i)[x]+celsius);
        }
        }
        }
    }
    next(3);
}
//Calculates and displays the lowest temperature through a nested for loop in same way as findHighestTemperature()
//Displays all temperatures of that week with date.
public void findLowestTemperature(String selectedWeek)
{
    for(int i=0;i<dates.size();i++)
    {
        for(int j=0;j<dates.get(i).length;j++)
        {
         if(dates.get(i)[j].equals(selectedWeek))                                                      
         {                                                                                             
            System.out.println("Lowest temprature for week commencing:\n"+selectedWeek+":");
            System.out.println("-------------------------------------------------");  
            double lowestTemp=temps.get(i)[0];                                                                           
         for(int t=0;t<temps.get(i).length;t++)                                                       
        {
            lowestTemp=Math.min(lowestTemp,temps.get(i)[t]);
        }
    for(int x=0;x<temps.get(i).length;x++)
        if(lowestTemp==temps.get(i)[x])
        {
        System.out.println(dates.get(i)[x]+"  |  "+temps.get(i)[x]+celsius+"    Lowest");
        }
        else
        {
        System.out.println(dates.get(i)[x]+"  |  "+temps.get(i)[x]+celsius);
        }
        }
        }
    }
    next(4);
}
//Uses switch statement and user input to either return to menu or repeat previous operation.
public void next(int repeatedOption)
{
    boolean valid=false;
    while(!valid)
    {
    try
    {
    System.out.println("/////////////////////////////////////////////////");
    System.out.println("Next:\n|0| Menu\n|1| Repeat");
    int userOption=Integer.parseInt(kb.nextLine());
    if(userOption<0||userOption>1)
    {
        throw new InputMismatchException();
    }
    switch (userOption) {
        case 1:
            switch (repeatedOption) {
                case 1:
                    menuOptions(1);
                case 2:
                    menuOptions(2);
                case 3:
                    menuOptions(3);
                case 4:
                    menuOptions(4);
            }
        case 0:
            mainMenu();
    }
    valid=true;
    } catch (InputMismatchException|NumberFormatException e) {
        System.out.println("Error: Invalid imput");
    }
}
}
//Takes user input passed to it and calls corresponding method.
public void menuOptions(int userOp)
{
    switch (userOp) 
    {
        case 1:
            collectData();
        case 2:
            calculateAverageTemperature(weekSelector());
        case 3:
            findHighestTemperature(weekSelector());
        case 4:
            findLowestTemperature(weekSelector());
        case 0:
        System.out.println("Program shutting down...");
        System.exit(0);
        break;
    } 
}
//Returns user to main menu
public void cancel()
{
    mainMenu();
}



//Main menu with initialized object and mainMenu called on it.
public static void main(String[] args) throws Exception 
    {
        WeatherDataAnalyser analyser = new WeatherDataAnalyser();
        analyser.mainMenu();
    }
}
