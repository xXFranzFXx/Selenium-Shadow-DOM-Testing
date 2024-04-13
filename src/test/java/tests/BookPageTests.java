package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.BookPage;

public class BookPageTests extends BaseTest {
    BookPage bookPage;
    static final String pamphletURL = System.getProperty("pamphletURL");
    static final String borrowBookURL = System.getProperty("borrowBookURL");
    @Test(description = "login and download image files")
    public void firstTest() {
        bookPage = new BookPage(getDriver());
        bookPage.viewOrBorrowBook(pamphletURL, "view").savePages("pamphlet");
        Assert.assertEquals(bookPage.getCurrentPage("current"), bookPage.getCurrentPage("total"));
    }
    @Test(description = "login using the 'Login and Borrow' button")
    public void loginBorrowButton() {
        bookPage = new BookPage(getDriver());
        bookPage.viewOrBorrowBook(borrowBookURL, "borrow");
        Assert.assertTrue(bookPage.borrowButton().isDisplayed());
    }
    @Test
    public void loginBorrowTest() {
        bookPage = new BookPage(getDriver());
        bookPage.viewOrBorrowBook(borrowBookURL, "borrow");
        bookPage.clickBorrowButton();
        getDriver().navigate().refresh();
        bookPage.clickBorrowButton();
        Assert.assertTrue(bookPage.borrowButton().isDisplayed());
    }
}
