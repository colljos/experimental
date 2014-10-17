package com.java7developer.chapter2;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import static java.nio.file.StandardWatchEventKinds.*;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

/**
 * Code for listing 2_4 - Exploring the WatchService
 * 
 * TODO When build 130 is released, this entire example will be redundant
 * 
 * @author Ben Evans and Martijn Verburg
 */
public class Listing_2_4 {

  public static void main(String[] args) {

    try {
      WatchService watcher =
              FileSystems.getDefault().newWatchService();

      Path dir =
              FileSystems.getDefault().getPath("/usr/karianna");

      WatchKey key = dir.register(watcher, ENTRY_MODIFY);

      while (true) {
        key = watcher.take();
        for (WatchEvent<?> event : key.pollEvents()) {
          if (event.kind() == ENTRY_MODIFY) {
            System.out.println("Home dir changed!");
          }
        }
        key.reset();
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
