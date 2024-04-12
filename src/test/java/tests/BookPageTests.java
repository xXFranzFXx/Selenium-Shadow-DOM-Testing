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
        bookPage.pause(5).borrowButton().click();
        getDriver().navigate().refresh();
        bookPage.pause(5).returnBookButton().click();

        bookPage.pause(3);
        Assert.assertTrue(bookPage.borrowButton().isDisplayed());
    }
}
