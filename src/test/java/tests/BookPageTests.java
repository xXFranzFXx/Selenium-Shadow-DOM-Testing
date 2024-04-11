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
    public void borrowTest(String borrowBookURL) {
        bookPage = new BookPage(getDriver());
        bookPage.login().viewOrBorrowBook(borrowBookURL, "borrow");
        Assert.assertFalse(bookPage.borrowButton().isDisplayed());
        bookPage.clickReturnBook();
        Assert.assertTrue(bookPage.borrowButton().isDisplayed());
    }
}
