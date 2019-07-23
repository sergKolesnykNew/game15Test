package models;

import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class TableValue {

    private int[][] table = new int[4][4];

    public int[][] getTable() {
        return table;
    }

    public void setTable(int[][] table) {
        this.table = table;
    }

    @Override
    public String toString() {
        return "tableEntity{" +
                "table=" + Arrays.toString(table) +
                '}';
    }
}
