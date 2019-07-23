package controllers;

import dao.TableValueService;
import models.Values;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


@Controller
public class Main {
    private TableValueService tableValue;
    @Autowired
    @Qualifier("tableValueService")
    public void setTableValue(TableValueService tableValue) {
        this.tableValue = tableValue;
    }

    @RequestMapping(value = "/")
    public String main(){
        return "main";
    }

    @RequestMapping(value = "/gameBegin", method = RequestMethod.GET)
    public String gameBegin(Model model){

        int[][] values = tableValue.putOrderedValues();
        ArrayList<ArrayList> lists = putValuesInCorrectPlace(values);
        model.addAttribute("first", lists.get(0));
        model.addAttribute("second", lists.get(1));
        model.addAttribute("third", lists.get(2));
        model.addAttribute("fourth", lists.get(3));

       return "index";
    }

    @RequestMapping(value = "/moveRight", method = RequestMethod.GET)
    public String moveRight(Model model,
                            @ModelAttribute("first") ArrayList<Integer> first,
                            @ModelAttribute("second")ArrayList<Integer> second,
                            @ModelAttribute("third") ArrayList<Integer> third,
                            @ModelAttribute("fourth")ArrayList<Integer> fourth)
    {

        int[][] fromPage = tableValue.changeToCorrectArray(first, second, third, fourth);
        int[][] moveRight = tableValue.moveRight(fromPage);
        ArrayList<ArrayList> lists = putValuesInCorrectPlace(moveRight);
        //model.addAttribute("first", first);
        model.addAttribute("first", lists.get(0));
        model.addAttribute("second", lists.get(1));
        model.addAttribute("third", lists.get(2));
        model.addAttribute("fourth", lists.get(3));

        return "index";
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
