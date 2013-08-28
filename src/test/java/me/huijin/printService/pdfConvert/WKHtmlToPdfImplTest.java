/**
 * 
 */
package me.huijin.printService.pdfConvert;

import static org.junit.Assert.*;

import me.huijin.printService.commons.ReadHtmlException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author 付乐
 * @createTime 2013-8-23
 */
public class WKHtmlToPdfImplTest {
    
    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }
    
    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }
    
    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
    }
    
    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }
    
    /**
     * Test method for {@link me.huijin.printService.pdfConvert.WKHtmlToPdfImpl#HtmlFile2Pdf(java.lang.String, java.lang.String, me.huijin.printService.pdfConvert.PdfPage)}.
     * @throws ReadHtmlException 
     */
    @Test
    public final void testHtmlFile2Pdf() throws ReadHtmlException {
        // file url  测试 
        String wkhtmltoxPath  = "C:\\Program Files (x86)\\wkhtmltopdf";
        Convertable co = WKHtmlToPdfImpl.getWKHtmlToPdf(wkhtmltoxPath);
        PdfPage page = new  PdfPage();
        page.setPageSize(PdfPage.A4);
        page.setPageOrientation(PdfPage.LANDSCAPE);
        page.setQuality(false);
        co.HtmlFile2Pdf("http://localhost:8080/serverWeb/print/order_print.jsp?orderId=275&orderType=pre_order", "C:\\D\\test.pdf", page);
    }
    
   
    
}
