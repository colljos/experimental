package com.java7developer.chapter2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.net.StandardSocketOptions;
import java.nio.channels.NetworkChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Set;

/**
 * Code for listing 2_9 - NetworkChannel;
 * 
 * TODO When build 130 is released, this entire example will be redundant
 * 
 * @author Ben Evans and Martijn Verburg
 */
public class Listing_2_9 {

  public static void main(String[] args) {
    SelectorProvider provider = SelectorProvider.provider();
    try {
      NetworkChannel socketChannel = provider.openSocketChannel();
      SocketAddress address = new InetSocketAddress(3080);
      socketChannel = socketChannel.bind(address);

      Set<SocketOption<?>> socketOptions =
              socketChannel.supportedOptions();

      System.out.println(socketOptions.toString());
      socketChannel.setOption(StandardSocketOptions.IP_TOS, 3);
      socketChannel.getOption(StandardSocketOptions.SO_KEEPALIVE);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
