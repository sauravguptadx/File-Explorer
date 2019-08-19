/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import java.io.File;
import javax.swing.JList;
import javax.swing.SwingUtilities;

/**
 *
 * @author saura
 */
public class ListView {
    
 JList filelist; 
 
 ListView()
 {
     filelist = new JList();
 }
 
    
    public void setListData(final File[] files) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                filelist.setListData(files);
                filelist.setCellRenderer(new FileRenderer());
                filelist.setFixedCellHeight(50);
                filelist.setFixedCellWidth(100);
                filelist.setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
                filelist.setVisibleRowCount(-1);

            }
        });
    }
}
