package test.java;


import main.java.Person;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestPerson {
  @Test(timeout=1000)
  public void testPerson() {
      
	  PersonInterface p = new Person();
      p.setAge(20);
      p.setName("Fird Birfle");
      p.setSalary(195750.22);
      
      assertEquals(215325.242, p.calculateBonus(), 0.01);
      assertEquals("The Honorable Fird Birfle", p.becomeJudge());
      assertEquals(30, p.timeWarp());
      assertEquals(10,p.wasteTime());
      
	  
  }
}
