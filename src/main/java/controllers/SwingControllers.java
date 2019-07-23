package controllers;

import dao.TableValueService;
import models.SwingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SwingControllers {
    private SwingModel swingModel;
    @Autowired
    public void setSwingModel(SwingModel swingModel) {
        this.swingModel = swingModel;
    }
    private TableValueService tableValue;
    @Autowired
    @Qualifier("tableValueService")
    public void setTableValue(TableValueService tableValue) {
        this.tableValue = tableValue;
    }

    @RequestMapping(value = "gameBeginSWING")
    public String gameBeginSwing(){
        swingModel.setVisible(true);
        int[][] ints = tableValue.putOrderedValues();
        swingModel.setValues(ints);
        swingModel.repaintField();
        return "swing";

    }


}
