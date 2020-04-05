package com.Group6.checkup.Utils;

import java.util.HashMap;
import java.util.List;

public class Sort{

    final static public int DESCENDING = 0;
    final static public int ASCENDING = 1;

    //insertion sort algorithm to sort the doctors data list by their distances
    public static void byKeyValue(List<HashMap<String,String>> a, String sortKey, Class valueType,int sortOrder) {

        for(int i = 1; i < a.size(); i++){

            // grab next comparison value
            HashMap<String,String> nextInsertValue = a.get(i);

            // place value into final its position
            insertIntoIndex(nextInsertValue, a, 0, (i-1), sortKey, valueType,sortOrder);

        }

    }
    //insertion sort helper method
    private static void insertIntoIndex(HashMap<String,String> nextToInsert, List<HashMap<String,String>> a,int sortStart,int sortEnd,String sortKey,Class valueType,int sortOrder) {

        int index = sortEnd;

        while (index >= sortStart && compareTo(a,index,nextToInsert,sortKey,valueType,sortOrder)) {

            a.remove(index+1);
            a.add(index + 1,a.get(index));
            index--;
        }

        a.remove(index+1);
        a.add(index + 1,nextToInsert);

    }

    private static boolean compareTo(List<HashMap<String,String>> a, int index, HashMap<String,String> nextToInsert, String sortKey, Class valueType,int sortOrder){
        if(valueType == float.class){
            if(sortOrder == Sort.ASCENDING){
                return Float.parseFloat(a.get(index).get(sortKey)) > Float.parseFloat(nextToInsert.get(sortKey));
            } else if(sortOrder == Sort.DESCENDING) {
                return Float.parseFloat(a.get(index).get(sortKey)) < Float.parseFloat(nextToInsert.get(sortKey));
            }
        } else if(valueType ==long.class){
            if(sortOrder == Sort.ASCENDING) {
                return Long.parseLong(a.get(index).get(sortKey)) > Long.parseLong(nextToInsert.get(sortKey));
            } else if(sortOrder == Sort.DESCENDING) {
                return Long.parseLong(a.get(index).get(sortKey)) < Long.parseLong(nextToInsert.get(sortKey));
            }
        }
        return false;
    }

}
