/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author saura
 */
public class TableView {
     JTable table;
    public FileTableModel fileTableModel;
    ListSelectionListener listSelectionListener;
    FileSystemView fileSystemView;
    boolean cellSizesSet = false;
    
    TableView()
    {
            table = new JTable();
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //selecting single row
            table.setSize(600, 430);
            table.setLocation(370, 120);
            
            fileSystemView = FileSystemView.getFileSystemView();
    
    }
    
    
    /** Update the table on the EDT
     * @param files */
    public void setTableData(final File[] files) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (fileTableModel==null) {
                    fileTableModel = new FileTableModel();
                    table.setModel(fileTableModel);
                }
                table.getSelectionModel().removeListSelectionListener(listSelectionListener);
                fileTableModel.setFiles(files);
                table.getSelectionModel().addListSelectionListener(listSelectionListener);
                    if (!cellSizesSet) {
                   Icon icon = fileSystemView.getSystemIcon(files[0]);

                    // size adjustment to better account for icons
                    table.setRowHeight( icon.getIconHeight() + 6);

                  cellSizesSet = true;
               }

            }
        });
    }
    
}
