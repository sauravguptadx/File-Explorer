/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import java.io.File;

/**
 *
 * @author saura
 */
public class ChooseView {
    static int flagg;
    static ChooseView viewtype;
    
    ChooseView(int i) {
        flagg =  i;
    }
    
    static ChooseView getObject(){
        if(viewtype == null) 
          viewtype = new ChooseView(0);
        return viewtype;
    }
    
    public void chooseviewtype(int flagg, File[] files)
    {
        if(flagg == 1)
        {
            ListView listvw = new ListView();
            listvw.setListData(files);
        }
        
        else if(flagg == 0)
        {
            TableView tablevw = new TableView();
            tablevw.setTableData(files);
        }
        
    }
}

