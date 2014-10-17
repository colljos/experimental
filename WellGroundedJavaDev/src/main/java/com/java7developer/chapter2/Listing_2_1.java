package com.java7developer.chapter2;

import java.io.IOException;
import java.nio.file.*;

/**
 * Code for listing 2_1 - Exploring the core java.nio.file classes
 * 
 * TODO When build 130 is released, this entire example will be redundant
 * 
 * This code listing is not of the highest quality. to see a slightly nicer 
 * example @see Listing_2_1_improved
 * 
 * @author Ben Evans and Martijn Verburg
 */
public class Listing_2_1 {

  public static void main(String[] args) throws IOException {
    
    try (FileSystem fileSystem = FileSystems.getDefault()) {
      
      Path timeSheetFile = fileSystem.getPath("C:/projects/timesheet.txt");
      Path backupDir = fileSystem.getPath("H:/projects/");
      Path backupFile = fileSystem.getPath("H:/projects/timesheet.txt");

      CopyOption copyOptions = StandardCopyOption.REPLACE_EXISTING;

      System.out.println(backupFile.toAbsolutePath());
    }
  }
}
