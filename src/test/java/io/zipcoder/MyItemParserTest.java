package io.zipcoder;

import org.junit.Assert;
import org.junit.Test;

public class MyItemParserTest {
    MyItemParser testParser = new MyItemParser();

    @Test
    public void parseTest(){
        String testString = "lkjhlkjh##asdfasdf##cvbncvbn##";
        String expected = "asdfasdf";
        testParser.parse(testString);

        String actual = testParser.stringList.get(1);

        Assert.assertEquals(expected,actual);
    }

    @Test
    public void formattingTestTrue(){
        int expected = 0;
        String passInStr = "naMe:Co0kieS;pRice:2.25;type:Food;expiration:1/25/2016";
        testParser.formattingCheck(passInStr);

        int actual = testParser.errorCount;

        Assert.assertEquals(expected,actual);
    }

    @Test
    public void formattingTestFalse(){
        int expected = 1;
        String passInStr = "naMe:;price:3.23;type:Food;expiration:1/25/2016";
        testParser.formattingCheck(passInStr);

        int actual = testParser.errorCount;

        Assert.assertEquals(expected,actual);
    }


    @Test
    public void matchTestTrue(){
        Assert.assertTrue(testParser.match("naMe:Co0kieS;pRice:2.25;type:Food;expiration:1/25/2016"));
    }

    @Test
    public void matchTestFalse(){
        Assert.assertFalse(testParser.match("naMe:;pRice:2.25;type:Food;expiration:1/25/2016"));
    }

    @Test
    public void createItemTest(){
        double expected = 2.25;
        MyItem testItem = testParser.createItem("naMe:Co0kieS;pRice:2.25;type:Food;expiration:1/25/2016");
        double actual = testItem.getPrice();

        Assert.assertEquals(expected,actual,0);
    }

    @Test
    public void separatorTestTrue(){
        Assert.assertTrue(testParser.separator(':'));
    }

    @Test
    public void separatorTestFalse(){
        Assert.assertFalse(testParser.separator('/'));
    }

    @Test
    public void addItemToMapTest(){
        MyItem testItem1 = new MyItem("milk","1.23","food","1/11/1111");
        MyItem testItem2 = new MyItem("milk","6.23","food","1/11/0001");
        MyItem testItem3 = new MyItem("bread","6.23","food","1/11/0001");

        int expected = 2;

        testParser.addItemToItemMap(testItem1);
        testParser.addItemToItemMap(testItem2);
        testParser.addItemToItemMap(testItem3);

        int actual = testParser.itemMap.size();

        Assert.assertEquals(expected,actual);
    }

    @Test
    public void toStringTest(){
        String expected = "\n" +
                "name:   bread\t\tseen: 1  time\n" +
                "=============\t\t=============\n" +
                "Price:\t 6.23\t\tseen: 1  time\n" +
                "-------------\t\t-------------\n" +
                "\n" +
                "name:    milk\t\tseen: 2 times\n" +
                "=============\t\t=============\n" +
                "Price:\t 1.23\t\tseen: 1  time\n" +
                "-------------\t\t-------------\n" +
                "Price:\t 6.23\t\tseen: 1  time\n" +
                "-------------\t\t-------------\n" +
                "\n" +
                "Errors\t\t\t\tseen: 0 times";
        MyItem testItem1 = new MyItem("milk","1.23","food","1/11/1111");
        MyItem testItem2 = new MyItem("milk","6.23","food","1/11/0001");
        MyItem testItem3 = new MyItem("bread","6.23","food","1/11/0001");
        testParser.addItemToItemMap(testItem1);
        testParser.addItemToItemMap(testItem2);
        testParser.addItemToItemMap(testItem3);
        String actual = testParser.toString();
        Assert.assertEquals(expected,actual);
    }
}

