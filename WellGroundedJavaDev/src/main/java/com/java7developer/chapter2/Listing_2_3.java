package com.java7developer.chapter2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Code for listing 2_3 - Symbolic Links
 * 
 * TODO When build 130 is released, this entire example will be redundant
 * 
 * @author Ben Evans and Martijn Verburg
 */
public class Listing_2_3 {

  public static void main(String[] args) {
    Path file = Paths.get("/opt/platform/java");

    try {
      BasicFileAttributes attrs =
              Files.readAttributes(file,
              BasicFileAttributes.class);

      if (attrs.isSymbolicLink()) {
        //Path target = file.readSymbolicLink();
        //assert file.isSameFile(file.resolve(target));
      }
    
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
