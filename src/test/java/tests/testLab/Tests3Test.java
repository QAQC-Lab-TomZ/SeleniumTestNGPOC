package tests.testLab;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.BaseTest;

/************************************************************************
 Description : Test class
 - example of soft and hard assertions
 - tests executed with unpredictable order
 - tests methods don't use dependency

 Created by : Tomasz Zulawnik

 Class History
 -------------------------------------------------------------------------
 Date 		Author		 							    Reason
 2022-02-02	Tomasz Zulawnik                             Class created
 ************************************************************************/

@Test(singleThreaded = true)
public class Tests3Test extends BaseTest {

    @BeforeClass
    public void classSetup() {
        driver.get(baseUrl);
    }

    private final int a = 1;
    private final int b = 2;
    private final int c = 3;

    //Soft assertion example
    @Test
    public void firstTest() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(a, b);
        softAssert.assertEquals(b, c);
        softAssert.assertAll();
    }

    //Hard assertion example
    @Test
    public void secondTest() {
        Assert.assertEquals(a, b);
        Assert.assertEquals(b, c);
    }

    @Test
    public void thirdTest() {
        //your test code here
    }

    @Test
    public void fourthTest() {
        //your test code here
    }

    @Test
    public void fifthTest() {
        //your test code here
    }
}
