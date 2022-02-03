package tests.testLab;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.BaseTest;

/************************************************************************
 Description : Test class
 - example of soft and hard assertions
 - tests executed in a particular order (priority)
 - tests methods don't use dependency

 Created by : Tomasz Zulawnik

 Class History
 -------------------------------------------------------------------------
 Date 		Author		 							    Reason
 2022-02-02	Tomasz Zulawnik                             Class created
 ************************************************************************/

@Test(singleThreaded = true)
public class Tests2Test extends BaseTest {

    @BeforeClass
    public void classSetup() {
        driver.get(baseUrl);
    }

    private final int a = 1;
    private final int b = 2;
    private final int c = 3;

    //Soft assertion example
    @Test(priority = 1)
    public void firstTest() {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(a, b);
        softAssert.assertEquals(b, c);
        softAssert.assertAll();
    }

    //Hard assertion example
    @Test(priority = 2)
    public void secondTest() {
        Assert.assertEquals(a, b);
        Assert.assertEquals(b, c);
    }

    @Test(priority = 3)
    public void thirdTest() {
        //your test code here
    }

    @Test(priority = 4)
    public void fourthTest() {
        //your test code here
    }

    @Test(priority = 5)
    public void fifthTest() {
        //your test code here
    }
}
