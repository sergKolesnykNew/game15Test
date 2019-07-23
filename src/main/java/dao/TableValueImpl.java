package dao;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;
@Component("tableValueImpl")
public class TableValueImpl implements TableValueModel {
    private static int[][]  table = new int[4][4];

    @Override
    public int[][] putRandomValues() {


        Random generator = new Random();

        //Проходимося по всім комбінація чисел в масиві масивів
        //Варіантів в циклі на один менше, відповідно, одне значення буде пустим
        for (int i = 1; i < 16; i++) {
            int k, l;
            do {
                k = generator.nextInt(4);
                l = generator.nextInt(4);
            }
            while (table[k][l] != 0);
            table[k][l] = i;
        }
        return table;
    }

    @Override
    public int[][] putOrderedValues() {
        int[][] tab = new int[4][4];
        tab[0][0] = 1;
        tab[0][1] = 2;
        tab[0][2] = 3;
        tab[0][3] = 4;
        tab[1][0] = 5;
        tab[1][1] = 6;
        tab[1][2] = 7;
        tab[1][3] = 8;
        tab[2][0] = 9;
        tab[2][1] = 10;
        tab[2][2] = 11;
        tab[2][3] = 12;
        tab[3][0] = 13;
        tab[3][1] = 14;
        tab[3][2] = 15;
        tab[3][3] = 0;

        return tab;
    }

    @Override
    public int[][] changeListsToOneArray(ArrayList<Integer> l1, ArrayList<Integer> l2, ArrayList<Integer> l3, ArrayList<Integer> l4) {
        int[][] correctArray = new int[4][4];

            for (int i = 0; i < 4; i++) {
                correctArray[0][i] = l1.get(i);
            }


            for (int i = 0; i < 4; i++) {
                correctArray[1][i] = l2.get(i);
            }

        for (int i = 0; i < 4; i++) {
            correctArray[2][i] = l3.get(i);
        }

            for (int i = 0; i < 4; i++) {
                correctArray[3][i] = l4.get(i);
            }

        return correctArray;
    }

    @Override
    public int[][] moveRight(int[][] numbers) {
        int zeroX = 0;
        int zeroY = 0;
        int reverse;
       // int[][] afterMoveRight = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (numbers[i][j]==0 ){
                    zeroX = i;
                    zeroY = j;
                }
            }
        }
        if (zeroY > 0) {
            //listOfRandomShufle.add("DOWN");
            reverse = numbers[zeroX ][zeroY-1];
            numbers[zeroX][zeroY] = reverse;
            numbers[zeroX][zeroY-1] = 0;
        }
        return numbers;
    }


}