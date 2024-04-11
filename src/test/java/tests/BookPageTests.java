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
        bookPage.login().viewOrBorrowBook(pamphletURL, "view").savePages("pamphlet");
        Assert.assertTrue(bookPage.isLastPage());
    }
}
