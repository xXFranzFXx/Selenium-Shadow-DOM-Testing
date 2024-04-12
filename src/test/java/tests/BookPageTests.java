package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.BookPage;

public class BookPageTests extends BaseTest {
    BookPage bookPage;
    @Test
    public void firstTest() {
        bookPage = new BookPage(getDriver());
        bookPage.viewOrBorrowBook(System.getProperty("pamphletURL"), "view").savePages("pamphlet");
        Assert.assertEquals(bookPage.getCurrentPage("current"), bookPage.getCurrentPage("total"));
    }
    @Test
    public void loginWithBorrowButtonTest() {
        bookPage = new BookPage(getDriver());
        bookPage.viewOrBorrowBook(System.getProperty("borrowBookURL"), "borrow");
        Assert.assertTrue(bookPage.borrowButton().isDisplayed());
    }
    @Test

    public void loginAndBorrowTest() {
        bookPage = new BookPage(getDriver());
        bookPage.viewOrBorrowBook(System.getProperty("borrowBookURL"), "borrow");
        bookPage.clickBorrowButton();
        getDriver().navigate().refresh();
        bookPage.clickBorrowButton();
        Assert.assertTrue(bookPage.borrowButton().isDisplayed());
    }
}
