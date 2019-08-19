/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author saura
 */
public class FileRenderer extends DefaultListCellRenderer {
FileSystemView fileSystemView;
    FileRenderer() {
        fileSystemView = FileSystemView.getFileSystemView();
    }

    @Override
    public Component getListCellRendererComponent(
        JList list,
        Object value,
        int index,
        boolean isSelected,
        boolean cellHasFocus) {

        Component c = super.getListCellRendererComponent(
            list,value,index,isSelected,cellHasFocus);
        JLabel l = (JLabel)c;
        File f = (File)value;
        l.setText(f.getName());
        Icon icon = FileSystemView.getFileSystemView().getSystemIcon(f);
        Image img = ((ImageIcon) icon).getImage().getScaledInstance(30,30,Image.SCALE_SMOOTH);
        l.setText(fileSystemView.getSystemDisplayName(f));
        l.setToolTipText(f.getPath());
   
        ImageIcon icon1 = new ImageIcon(img);

        l.setIcon(icon1);
        l.setPreferredSize(new Dimension(100,80));
        l.setHorizontalTextPosition(JLabel.CENTER);
        l.setVerticalTextPosition(JLabel.BOTTOM);
        l.setHorizontalAlignment(SwingConstants.CENTER);
        return l;
    }
}

