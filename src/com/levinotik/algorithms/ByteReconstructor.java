package com.levinotik.algorithms;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 *
 * Author: Levi Notik
 * Date: 9/1/11
 * Time: 8:23 PM
 *
 * This is my solution to an algorithm challenge which can be found @
 * http://venus.cs.qc.edu/~waxman/211/Challenge%20Problem%201%20-%20File%20Fragmentation.pdf
 */
public class ByteReconstructor {
    static int operations;
    static ArrayList<String> bytes;
    public static int targetLength;
    public static List<String> validSolutions;

    public static void solve(File tray) {
        bytes = new ArrayList<String>();
        sortStringsByLength(tray);
        targetLength = bytes.get(0).length() + bytes.get(bytes.size()-1).length();
        validSolutions = new ArrayList<String>();
        determineValidSolutions();
        System.out.println("There " + (validSolutions.size()>1? "are ":"is ") + (validSolutions.size()==1? "only ":" ") + String.valueOf(validSolutions.size()) + (validSolutions.size()>1? " solutions ":" solution ") + validSolutions);
        System.out.println(operations);

    }

    private static void determineValidSolutions() {

        for(int i=0; i<bytes.size(); i++) {
            operations++;
            for(int j=i+1; j<bytes.size(); j++) {
                operations++;
                String s = bytes.get(i);
                String a = bytes.get(j);
                int aLength = a.length();
                int sLength = s.length();
                if(s.length()+a.length()>targetLength) {
                    break;
                } else if (sLength + aLength == targetLength) {
                    boolean b = doesAgreesWithOthers(s.concat(a), i, j);
                    if(b)
                        validSolutions.add(s.concat(a));
                }
            }
        }
        HashSet set = new HashSet();
        set.addAll(validSolutions);
        validSolutions.clear();
        validSolutions.addAll(set);
    }

    private static boolean doesAgreesWithOthers(String possible, int indexA, int indexB) {



        ArrayList<String> tempArray = (ArrayList<String>) bytes.clone();
        tempArray.remove(indexA);
        tempArray.remove(indexB-1);
        int counter = 0;

        for(String s: tempArray) {
            operations++;
            for(String a: tempArray) {

                operations++;
                if((bytes.indexOf(a) != bytes.indexOf(s)) ) {
                    if(s.concat(a).length() > targetLength) {
                        break;
                    } else if(s.concat(a).length() == targetLength) {
                        if(s.concat(a).equals(possible)) {
                            counter++;
                        }
                    }
                }
            }
        }
        return(counter == ((bytes.size()/2)-2));
    }

    private static void sortStringsByLength(File tray) {
        try {
            FileInputStream fis = new FileInputStream(tray);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String line;
            while((line = br.readLine())!=null) {
                bytes.add(line);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        Collections.sort(bytes, new StringSorter());
        System.out.println("the bytes sorted by length: " + bytes);
    }

    private static class StringSorter implements Comparator {
        public int compare(Object o, Object o1) {
            Integer string1 = ((String)o).length();
            Integer string2 = ((String)o1).length();
            return string1.compareTo(string2);
        }
    }
}


