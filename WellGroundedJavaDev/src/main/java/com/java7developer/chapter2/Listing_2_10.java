package com.java7developer.chapter2;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.StandardProtocolFamily;
import java.net.StandardSocketOptions;

import java.nio.channels.DatagramChannel;
import java.nio.channels.MembershipKey;

/**
 * Code for listing 2_10 - Multicast
 * 
 * TODO When build 130 is released, this entire example will be redundant
 * 
 * @author Ben Evans and Martijn Verburg
 */
public class Listing_2_10 {

  public static void main(String[] args) {
    try {
      NetworkInterface networkInterface =
              NetworkInterface.getByName("net1");

      DatagramChannel dc =
              DatagramChannel.open(StandardProtocolFamily.INET);

      dc.setOption(StandardSocketOptions.SO_REUSEADDR, true);
      dc.bind(new InetSocketAddress(8080));
      dc.setOption(StandardSocketOptions.IP_MULTICAST_IF, networkInterface);

      InetAddress group = InetAddress.getByName("180.90.4.12");
      MembershipKey key = dc.join(group, networkInterface);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
