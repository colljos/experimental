package com.java7developer.chapter2;

import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import static java.nio.file.attribute.PosixFilePermission.*;

/**
 * Code for listing 2_2 - File Attribute Support
 * 
 * TODO When build 130 is released, this entire example will be redundant
 * 
 * @author Ben Evans and Martijn Verburg
 */
public class Listing_2_2 {

  public static void main(String[] args) {
    try {
      Path profile = Paths.get("/user/karianna/.profile");

      PosixFileAttributeView view = Files.getFileAttributeView(profile, PosixFileAttributeView.class);
      PosixFileAttributes attrs = view.readAttributes();
      Set<PosixFilePermission> posixPermissions = attrs.permissions();

      posixPermissions.clear();
      view.setPermissions(posixPermissions);

      String owner = attrs.owner().getName();
      String perms = PosixFilePermissions.toString(posixPermissions);
      System.out.format("%s %s%n", owner, perms);

      posixPermissions.add(OWNER_READ);
      posixPermissions.add(GROUP_READ);
      posixPermissions.add(OWNER_READ);
      posixPermissions.add(OWNER_WRITE);
      view.setPermissions(posixPermissions);

      perms = PosixFilePermissions.toString(posixPermissions);
      System.out.format("%s %s%n", owner, perms);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
