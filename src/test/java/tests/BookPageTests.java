package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.BookPage;

public class BookPageTests extends BaseTest {
    BookPage bookPage;
    @Test
    @Parameters({"pamphletURL"})
    public void firstTest(String pamphletURL) {
        bookPage = new BookPage(getDriver());
        bookPage.viewOrBorrowBook(pamphletURL, "view").savePages("pamphlet");
        Assert.assertEquals(bookPage.getCurrentPage("current"), bookPage.getCurrentPage("total"));
    }
    @Test
    @Parameters({"borrowBookURL"})
    public void loginWithBorrowButtonTest(String borrowBookURL) {
        bookPage = new BookPage(getDriver());
        bookPage.viewOrBorrowBook(borrowBookURL, "borrow");
        Assert.assertTrue(bookPage.borrowButton().isDisplayed());
    }
    @Test
    @Parameters({"borrowBookURL"})
    public void loginAndBorrowTest(String borrowBookURL) {
        bookPage = new BookPage(getDriver());
        bookPage.viewOrBorrowBook(borrowBookURL, "borrow");
        bookPage.clickBorrowButton();
        getDriver().navigate().refresh();
        bookPage.clickBorrowButton();
        Assert.assertTrue(bookPage.borrowButton().isDisplayed());
    }
}
