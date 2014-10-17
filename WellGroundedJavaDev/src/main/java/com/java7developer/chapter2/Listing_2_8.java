package com.java7developer.chapter2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Code for listing 2_8 - Asynchronous I/O - Callback style
 * 
 * TODO When build 130 is released, this entire example will be redundant
 * 
 * @author Ben Evans and Martijn Verburg
 */
public class Listing_2_8 {

  public static void main(String[] args) {
    try {
      Path file = Paths.get("/usr/karianna/foobar.txt");
      AsynchronousFileChannel channel =
              AsynchronousFileChannel.open(file);

      ByteBuffer buffer = ByteBuffer.allocate(100_000);

      channel.read(buffer, 0, buffer,
              new CompletionHandler<Integer, ByteBuffer>() {

                public void completed(Integer result, ByteBuffer attachment) {
                  System.out.println("Bytes read [" + result + "]");
                }

                public void failed(Throwable exception, ByteBuffer attachment) {
                  exception.printStackTrace();
                }
              });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
