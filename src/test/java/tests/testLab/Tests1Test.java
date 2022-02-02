package tests.testLab;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.BaseTest;

/************************************************************************
 Description : Test class example
 Created by : Tomasz Zulawnik

 Class History
 -------------------------------------------------------------------------
 Date 		Author		 							    Reason
 2022-02-02	Tomasz Zulawnik                             Class created
 ************************************************************************/

@Test(singleThreaded = true)
public class Tests1Test extends BaseTest {

    @BeforeClass
    public void classSetup(){
        driver.get(baseUrl);
    }

    @Test(priority = 1)
    public void firstTest(){
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(1,2);
        softAssert.assertEquals(2,3);
        softAssert.assertAll();
    }

    @Test(priority = 2, dependsOnMethods = "firstTest")
    public void secondTest(){
//        driver.findElement(By.xpath("."));
        Assert.assertEquals(1,2);
        Assert.assertEquals(2,3);
    }

    @Test(priority = 3, dependsOnMethods = "secondTest")
    public void thirdTest(){
    }

    @Test(priority = 4, dependsOnMethods = "thirdTest")
    public void fourthTest(){
    }

    @Test(priority = 5, dependsOnMethods = "fourthTest")
    public void fifthTest(){
    }
}
