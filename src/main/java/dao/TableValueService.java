package dao;

import models.TableValue;
import org.springframework.beans.ConfigurablePropertyAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component("tableValueService")
public class TableValueService {
    private TableValueImpl tableValue;
    @Autowired
    @Qualifier("tableValueImpl")
    public void setTableValue(TableValueImpl tableValue) {
        this.tableValue = tableValue;
    }

    public int[][] putValues(){ return  this.tableValue.putRandomValues();}
    public int[][] putOrderedValues(){return  this.tableValue.putOrderedValues();}
    public int[][] changeToCorrectArray(ArrayList<Integer> l1, ArrayList<Integer> l2,
                                        ArrayList<Integer> l3, ArrayList<Integer> l4){
      return   tableValue.changeListsToOneArray(l1,l2,l3,l4);
    }
    public int[][] moveRight(int[][] numbers){ return tableValue.moveRight(numbers);}


    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("testContext.xml");
        TableValueImpl bean = (TableValueImpl) context.getBean("tableValueImpl");
        int[][] ints = bean.putOrderedValues();
        int[][] ints1 = bean.moveRight(ints);
        ArrayList<ArrayList> lists = putValuesInCorrectPlace(ints1);
        int[][] ints2 = bean.changeListsToOneArray(lists.get(0), lists.get(1), lists.get(2), lists.get(3));

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.println(ints2[i][j]);
            }
        }


    }
    private static ArrayList<ArrayList> putValuesInCorrectPlace(int[][] values){
        ArrayList<Integer> first = new ArrayList<>();
        ArrayList<Integer> second = new ArrayList<>();
        ArrayList<Integer> third = new ArrayList<>();
        ArrayList<Integer> fourth = new ArrayList<>();

        ArrayList<ArrayList> lists = new ArrayList<>();

        for (int i = 0; i < 4; i++) {

            int s1 = values[0][i];
            if (s1!= 0){
                first.add(s1);
            }
            else first.add(0);
            int s2 = values[1][i];
            if (s2!=0) {
                second.add(s2);
            }
            else second.add(0);
            int s3 = values[2][i];
            if (s3!=0) {
                third.add(s3);
            }
            else third.add(0);
            int s4 = values[3][i];
            if (s4!=0) {
                fourth.add(s4);
            }
            else fourth.add(0);
        }

        lists.add(first);
        lists.add(second);
        lists.add(third);
        lists.add(fourth);

        return lists;
    }

}
