package UnitTests;

import static org.junit.Assert.*;

import org.apache.poi.ss.formula.functions.FinanceLib;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import base.RateDAL;

public class PMT_Test {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void PMTtest() {
        double rate = RateDAL.getRate(700);
        double decimalrate = rate/100;
        double monthlyrate = decimalrate/12;
        Integer termmonths = 30 * 12;
        double housecost = 300000;
        double PMT = FinanceLib.pmt(monthlyrate,termmonths, housecost, 0, false);
		assertEquals("Method didn't work",-PMT,1432.25,.01);
	}
	

}
