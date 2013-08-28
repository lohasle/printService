/**
 * 
 */
package me.huijin.printService.pdfConvert;

import java.io.File;
import java.net.URL;

import me.huijin.printService.commons.HtmlReader;
import me.huijin.printService.commons.ReadHtmlException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author 付乐
 * @createTime 2013-8-20
 */
public class FlyConvertPdfImplTest {
    
    private static String testHtml ;
    private static String baseOutPath;
    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        URL url = FlyConvertPdfImplTest.class.getResource("/files/dingdan3.html");
        testHtml = url.getFile();
        baseOutPath = FlyConvertPdfImplTest.class.getResource("").getFile();
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
     * Test method for {@link me.huijin.printService.pdfConvert.FlyConvertPdfImpl#HtmlStr2Pdf(java.lang.String, java.lang.String, java.lang.String)}.
     * @throws ReadHtmlException 
     */
    @Test
    public final void testHtmlFile2Pdf() throws ReadHtmlException {
        Convertable co =  FlyConvertPdfImpl.getInstance();
        System.out.println(baseOutPath);
        co.HtmlFile2Pdf(testHtml, baseOutPath+File.separator+"dingdan.pdf",new PdfPage());
    }
    
    /**
     * Test method for {@link me.huijin.printService.pdfConvert.FlyConvertPdfImpl#HtmlFile2Pdf(java.lang.String, java.lang.String)}.
     * @throws ReadHtmlException 
     */
    @Test
    public final void testHtmlStr2Pdf() throws ReadHtmlException {
        Convertable co =  FlyConvertPdfImpl.getInstance();
        String html  = HtmlReader.readHtmlToString(new File(testHtml));
        co.HtmlStr2Pdf(html, baseOutPath, "dingdan2.pdf",new PdfPage());
    }
    
}
