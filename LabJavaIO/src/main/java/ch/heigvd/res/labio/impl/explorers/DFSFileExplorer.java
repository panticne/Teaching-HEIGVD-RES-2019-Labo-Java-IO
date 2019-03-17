package ch.heigvd.res.labio.impl.explorers;

import ch.heigvd.res.labio.interfaces.IFileExplorer;
import ch.heigvd.res.labio.interfaces.IFileVisitor;

import java.io.File;
import java.util.Arrays;

/**
 * This implementation of the IFileExplorer interface performs a depth-first
 * exploration of the file system and invokes the visitor for every encountered
 * node (file and directory). When the explorer reaches a directory, it visits all
 * files in the directory and then moves into the subdirectories.
 * 
 * @author Olivier Liechti
 */
public class DFSFileExplorer implements IFileExplorer {

  @Override
  public void explore(File rootDirectory, IFileVisitor vistor) {

    File[] dfs = rootDirectory.listFiles();
    //Si l'arbo n'est pas null on trie
    if(dfs != null){
      Arrays.sort(dfs);
    }

    if(dfs == null){
      vistor.visit(rootDirectory);
    }else{
      //Sinon on parcourt chaque élément de l'arbo en appelant récursivement explore
      vistor.visit(rootDirectory);
      for (File file : dfs){

        if(file.isDirectory()){
          if(file.isDirectory()){
            explore(file,vistor);
          }
        }
      }
    }
  }



}
