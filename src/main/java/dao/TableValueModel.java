package dao;

import java.util.ArrayList;

public interface TableValueModel {
     int[][] putRandomValues();
     int[][] putOrderedValues();
     int[][] changeListsToOneArray(ArrayList<Integer> l1, ArrayList<Integer> l2, ArrayList<Integer> l3, ArrayList<Integer> l4);
     int[][] moveRight(int[][] numbers);
    // int[][] cangeValues();
}
