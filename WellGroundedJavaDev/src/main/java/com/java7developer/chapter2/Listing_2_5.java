package com.java7developer.chapter2;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Code for listing 2_5 - filter source files
 * 
 * TODO When build 130 is released, this entire example will be redundant
 * 
 * @author Ben Evans and Martijn Verburg
 */
public class Listing_2_5 {

  public static void main(String[] args) {

    try {
      Path dir = Paths.get("C:/workspace/java7developer/src/*.java");

      DirectoryStream<Path> stream =
              Files.newDirectoryStream(dir);

      for (Path entry : stream)
      {
        System.out.println(entry.getName(0));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
