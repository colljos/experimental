package com.java7developer.chapter11.listing_11_15;

import java.math.BigDecimal;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.junit.*;
import static org.junit.Assert.*;

public class TestTicketHibernateDao {

  private static SessionFactory factory;
  private static TicketHibernateDao ticketDao;

  @BeforeClass
  public static void baseSetUp() {
    factory = new Configuration().configure().buildSessionFactory();
    ticketDao = new TicketHibernateDao(factory);
  }

  @Before
  public void setUpTest()
  {
    Ticket ticket = new Ticket(1);
    ticketDao.save(ticket);
    Ticket ticket2 = new Ticket(2);
    ticketDao.save(ticket2);
  }

  @Test
  public void findTicketByIdHappyPath() throws Exception {
    Ticket ticket = ticketDao.findTicketById(1);
    assertEquals(new BigDecimal("30.0"), ticket.getDiscountPrice());
  }

  @AfterClass
  public static void baseTeardown() {
    factory.close();
  }

}