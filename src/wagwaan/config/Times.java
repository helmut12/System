/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wagwaan.config;

import java.util.Scanner;

/**
 *
 * @author Helmut
 */
public class Times {


public static void main(String [] args){
Scanner scan=new Scanner(System.in);
System.out.print("Enter the time in milliseconds:	");
long start=scan.nextLong();
long seconds=start/1000;
long current_sec=seconds%60;
long minutes=seconds/60;
long current_minutes=minutes%60;
long hour=minutes/60;
long current_hour=hour%60;
System.out.println(current_hour+" : "+current_minutes+" : "+current_sec);
}
}

