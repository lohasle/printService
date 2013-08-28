/**
 * 
 */
package me.huijin.printService.commons;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author 付乐
 * @createTime 2013-8-20
 */
public class HtmlReaderTest {
    private static String testHtml ;

    
    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        URL url = HtmlReaderTest.class.getResource("/files/dingdan3.html");
        testHtml = url.getFile();
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
     * Test method for {@link me.huijin.printService.commons.HtmlReader#readHtmlToString(java.io.File)}.
     * @throws ReadHtmlException 
     */
    @Test
    public final void testReadHtmlToString() throws ReadHtmlException {
        String html  = HtmlReader.readHtmlToString(new File(testHtml));
        System.out.println(html);
    }
    
    @Test
    public final void testReadHtmlToStringURL() throws ReadHtmlException, MalformedURLException {
        URL url = new URL("http://192.168.123.2:9090/serverWeb/print/order_print.jsp?orderId=511&orderType=pre_order");
        String html  = HtmlReader.readHtmlToString(url);
        System.out.println(html);
    }
    
}
