
package filemanager;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;


import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.swing.table.*;
import javax.swing.filechooser.FileSystemView;

import java.util.Date;
import java.util.List;

import java.io.*;


public class FileManager {

    FileSystemView fileSystemView;
    TableView tableview;
    ListView listview;
    TreeView treeview;    

    static int flag = 0;

    ListSelectionListener listSelectionListener_file;
    boolean cellSizesSet = false;
    int rowIconPadding = 6;

    JPanel gui;
    JTextField path;
    JButton table_button;
    JButton block_button;
    JButton go_button;
    JPanel panel_block;
    
    
    public JPanel getGui() {

            gui = new JPanel();
            gui.setLayout(null);
            gui.setSize(1000, 600);
            gui.setLocation(0, 0);
            
            tableview = new TableView();
            listview = new ListView();
            path = new JTextField(5);
            path.setSize(600, 30);
            path.setLocation(200, 75);
            path.setEditable(true);
            gui.add(path);
            
            ChooseView viewtype = new ChooseView(0);

            JPanel detailView = new JPanel();
            detailView.setSize(600, 430);
            detailView.setLayout(null);
            detailView.setLocation(370, 120);
            detailView.setBackground(Color.RED);

             tableview.table.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    int row = tableview.table.getSelectionModel().getLeadSelectionIndex();
                    File file = ((FileTableModel)tableview.table.getModel()).getFile(row);
                    if(!file.isFile()){
                    setFilePath(file);
                    File[] files = file.listFiles();
                    tableview.setTableData(files);
                    listview.setListData(files);}
                    System.out.println("DOuble clicked on the table");
                }}
            });   

            listview.filelist.addMouseListener(new MouseAdapter(){
                @Override
            public void mouseClicked(MouseEvent me) {
          
                if (me.getClickCount() == 2) {
              File file = (File)listview.filelist.getSelectedValue();
              if(!file.isFile()){
                  setFilePath(file);
                  File[] files = file.listFiles();
                   listview.setListData(files);
                   tableview.setTableData(files);
              }
                     //LoadPath(file.getPath()); 

                    System.out.println("DOuble clicked on the List");

                }
            }
            });

            JScrollPane tableScroll = new JScrollPane(tableview.table);
            tableScroll.setSize(600, 430);
            tableScroll.setLocation(0, 0);
            detailView.add(tableScroll);
            
            fileSystemView = FileSystemView.getFileSystemView();
            treeview = new TreeView();

            TreeSelectionListener treeSelectionListener = new TreeSelectionListener() {
                @Override
                public void valueChanged(TreeSelectionEvent tse){
                    DefaultMutableTreeNode node =
                        (DefaultMutableTreeNode)tse.getPath().getLastPathComponent();
                    treeview.showChildren(node, listview, tableview);
                    setFilePath((File)node.getUserObject());

                }
            };
         
             treeview.tree.addTreeSelectionListener(treeSelectionListener);

        JScrollPane treeScroll = new JScrollPane(treeview.tree);
        treeScroll.setSize(350, 430);
        treeScroll.setLocation(5, 120);
        //treeScroll.setBackground(Color.YELLOW);

        
         JLabel fname = new JLabel("Current Directory");
            fname.setSize(200, 50);
            fname.setLocation(10,60);
            fname.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
            gui.add(fname);


        panel_block = new JPanel();
        panel_block.setLayout(null);
        panel_block.setSize(600, 430);
        panel_block.setLocation(370, 120);
        //panel_block.setBackground(Color.GREEN);

        JScrollPane listscroll = new JScrollPane(listview.filelist);
        listscroll.setSize(600, 430);
        listscroll.setLocation(0, 0);
        panel_block.add(listscroll);


         table_button = new JButton("Table");
        table_button.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
        table_button.setSize(80, 40);
        table_button.setLocation(300,20);
        gui.add(table_button);

        block_button = new JButton("Block");
        block_button.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
        block_button.setSize(80, 40);
        block_button.setLocation(400,20);
        gui.add(block_button);


        table_button.addActionListener(new ActionListener()
              {
                      public void actionPerformed(ActionEvent e)
                      {
                          flag = 0;
                          //viewtype.chooseviewtype(flag, files);
                          System.out.println("table button pressed");
                          gui.remove(panel_block);
                          gui.add(detailView);
                          gui.revalidate();
                          gui.repaint();
                          table_button.setEnabled(false);
                          block_button.setEnabled(true);
                      }
              });

        block_button.addActionListener(new ActionListener()
              {
                      public void actionPerformed(ActionEvent e)
                      {
                          flag = 1;
                          //viewtype.chooseviewtype(flag, files);
                          System.out.println("block button pressed");
                          gui.add(panel_block);
                          gui.remove(detailView);
                          gui.revalidate();
                          gui.repaint();
                          table_button.setEnabled(true);
                          block_button.setEnabled(false);
                      }
              });


        go_button = new JButton("GO");
        go_button.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
        go_button.setSize(80, 40);
        go_button.setLocation(820,70);
        gui.add(go_button);
        
        
        go_button.addActionListener(new ActionListener()
              {
                      public void actionPerformed(ActionEvent e)
                      {
                          System.out.println("go button pressed");
                          if(new File(path.getText()).exists()){
                              LoadPath(path.getText());
                          }
                          else{
                           JOptionPane.showMessageDialog(gui, "Invalid Directory", "Error", JOptionPane.ERROR_MESSAGE);   
                          }
                      }
              });

            gui.add(treeScroll);
            gui.add(detailView);
            //gui.add

        return gui;
    }

    public void showRootFile() {
        // ensures that the main files are displayed
        treeview.tree.setSelectionInterval(0,0);
    }
   
    public void LoadPath(String dir) {
        File f = new File(dir);
        File[] s = f.listFiles();
        try {
              if (f.isFile()) {
                       System.out.println("This is a file cant open now");
                 } 
                  else {
                          setFilePath(f);
                            tableview.setTableData(s);
                            listview.setListData(s);
                        }
          
        } catch (Exception e) {}

    }

 
    private void setFilePath(File file) {
        path.setText(file.getPath());
        gui.repaint();
        System.out.println(file.getPath());
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch(Exception e) {
                }

                JFrame f = new JFrame("File Explorer");
                f.setSize(1000,600);
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                FileManager fileManager = new FileManager();
                
                f.add(fileManager.getGui());
                f.setVisible(true);
                f.setResizable(false);

                fileManager.showRootFile();

            }
        });
    }
}



