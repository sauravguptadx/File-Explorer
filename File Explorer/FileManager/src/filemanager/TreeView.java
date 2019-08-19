/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import java.io.File;
import java.util.List;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author saura
 */
public class TreeView {
    FileSystemView fileSystemView;
    DefaultTreeModel treeModel;
    JTree tree;
    //ListView listview;
   // TableView tableview;
    
    TreeView()
    {
        //listview = new ListView();
        //tableview = new TableView();
       // file_tree();
        
        fileSystemView = FileSystemView.getFileSystemView();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
            treeModel = new DefaultTreeModel(root);
            
            File[] roots = fileSystemView.getRoots();
            for (File fileSystemRoot : roots) {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(fileSystemRoot);
                root.add( node );

                File[] files = fileSystemView.getFiles(fileSystemRoot, true);
                for (File file : files) {
                    if (file.isDirectory()) {
                        node.add(new DefaultMutableTreeNode(file));

                    }
                }

            }
        
        //treegui();
            tree = new JTree(treeModel);
            tree.setRootVisible(false);
            //tree.addTreeSelectionListener(treeSelectionListener);
            tree.setCellRenderer(new FileTreeCellRenderer());
            tree.expandRow(0);
            tree.setVisibleRowCount(15);
    }
    
    /** Add the files that are contained within the directory of this node.
    Thanks to Hovercraft Full Of Eels. */
    public void showChildren(final DefaultMutableTreeNode node, ListView listview, TableView tableview) {
        tree.setEnabled(false);

        SwingWorker<Void, File> worker = new SwingWorker<Void, File>() {
            @Override
            public Void doInBackground() {
                File file = (File) node.getUserObject();
              //if (file.isDirectory()) {
                    File[] files = fileSystemView.getFiles(file, true); //!!
                    //if (node.isLeaf()) {
                        for (File child : files) {
                           // if (child.isDirectory()) {
                                publish(child);
                           // }
                        }
                    //}
                    listview.setListData(files);
                   tableview.setTableData(files);
                //}
                return null;
            }

            @Override
            protected void process(List<File> chunks) {
                for (File child : chunks) {
                    node.add(new DefaultMutableTreeNode(child));
                }
            }


            @Override
            protected void done() {

                tree.setEnabled(true);
            }
        };
        worker.execute();
    }

}
