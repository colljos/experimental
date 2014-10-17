package com.java7developer.chapter2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Code for listing 2_7 - Asynchronous I/O - Future style
 * 
 * TODO When build 130 is released, this entire example will be redundant
 * 
 * @author Ben Evans and Martijn Verburg
 */
public class Listing_2_7 {

  public static void main(String[] args) {
    try {
      Path file = Paths.get("/usr/karianna/foobar.txt");

      AsynchronousFileChannel channel = AsynchronousFileChannel.open(file);

      ByteBuffer buffer = ByteBuffer.allocate(100_000);
      Future<Integer> result = channel.read(buffer, 0);

      while (!result.isDone()) {
        System.out.println("Pretend Business Process"); 
        Thread.sleep(500);
      }

      Integer bytesRead = result.get();
      System.out.println("Bytes read [" + bytesRead + "]");
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
