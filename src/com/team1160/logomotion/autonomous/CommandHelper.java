/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.autonomous;

import com.team1160.logomotion.teleopManager.RobotCommand;
import edu.wpi.first.wpilibj.util.SortedVector;
import java.util.Hashtable;

/**
 *
 * @author nttoole
 */
public class CommandHelper {

    Hashtable map;
    SortedVector timeSet;

    int lastLeftIndex;


    public CommandHelper()
    {
        map = new Hashtable();
        timeSet = new SortedVector(new DoubleComparator());

        this.lastLeftIndex = 0;
    }

    public void clear()
    {
        this.map.clear();
        this.timeSet.removeAllElements();
    }

    public void addCommand(double time, RobotCommand cmd)
    {
        //create object
        Double bigD = new Double(time);

        //add to sorted lists
        timeSet.addElement(bigD);

        //add command to dictionary
        map.put(bigD, cmd);
        
    }

    public RobotCommand getCommand(double time)
    {
        RobotCommand cmd = null;
        
        int index = this.getIndexFor(time);
        //System.out.println("Index for time: " + time + " = " + index);
        

        if (index != -1)
        {
            double recTime = this.getValueForIndex(index);
            cmd = getCommandForTimeKey(recTime);
          //  System.out.println("Found a command to return for the time: " + time);
        }

        return cmd;
    }

    protected RobotCommand getCommandForTimeKey(double time)
    {
        RobotCommand cmd = null;

        Double key = new Double(time);
        cmd = (RobotCommand) this.map.get(key);

        return cmd;
    }

//int binarySearch(int item)
//{
//    int length = timeSet.size();
//    int first = 0;                  // Start with the first element.
//    int middle = 0;                 // Middle to set middle element.
//    int last = (length - 1);        // Last element,(length - 1) because arrays starts at 0.
//    int position = 0;               // Element position found to be returned from search.
//    boolean found = false;             // Changes to true when found, CANNOT enter while loop.
//
//    while ( (!found) && (first <= last) )
//    {
//        middle = (first + last) / 2;     // Set middle AFTER the start of while loop.
//
//
//        if (timeSet.elementAt(middle) == item)     // If value was found.
//      {
//         found = true;                 // Flag found as true so no more looping.
//         position = middle;            // and set position to the element location.
//      }
//      else if (item < numbers[middle]) // Search the left half of the sorted array.
//         last = (middle - 1);
//      else if (item > numbers[middle]) // Search the right half of the sorted array.
//         first = (middle + 1);
//   }
//   return position;                     // If position is NOT found, returned position is 0.
//}

    //TO do - this is a dumb linear search. Change to
    //binary search
    public int getIndexFor(double time)
    {
        int index = -1;

         int size = this.timeSet.size();

         if (size > 0)
         {
             //System.out.println("Searching for time "+time);
             for (int i = this.lastLeftIndex; i < size-1; ++i)
             {
                 double leftVal  = getValueForIndex(i);
                 double rightVal = getValueForIndex(i+1);

               //  System.out.println("Left = "+leftVal+" and Right = "+rightVal);

                 if (leftVal <= time && time < rightVal){
                     this.lastLeftIndex = i;
                     index = i;
                 }
             }

             if (index == -1)
                 index = size - 1;
         }

         return index;
     }

     public double getValueForIndex(int index)
     {
         double val = 0.0;

         if (index >= 0 && index < this.timeSet.size())
         {
             val = ((Double) this.timeSet.elementAt(index)).doubleValue();
         }

         return val;

     }

     public double getEarliest()
     {
         return this.getValueForIndex(0);
     }

     public double getLatest()
     {
        // System.out.println("The Lastest Time in index: " + this.getValueForIndex(this.timeSet.size() - 1));
         //System.out.println("The Earlist Time in index: " + this.getValueForIndex(0));
         return this.getValueForIndex(this.timeSet.size() - 1);
     }

     class DoubleComparator implements SortedVector.Comparator
     {
        public int compare(Object object1,
                           Object object2)
        {
            double d1 = ((Double) object1).doubleValue();
            double d2 = ((Double) object2).doubleValue();

            if (d1 == d2)
                return 0;
            else if (d1 < d2)
                return 1;
            else
                return -1;
        }
    }
}