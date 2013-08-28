/**
 * 
 */
package me.huijin.printService.printer;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import me.huijin.printService.commons.HtmlReader;
import me.huijin.printService.commons.PrintException;
import me.huijin.printService.commons.ReadHtmlException;
import me.huijin.printService.pdfConvert.Convertable;
import me.huijin.printService.pdfConvert.FlyConvertPdfImpl;
import me.huijin.printService.pdfConvert.FlyConvertPdfImplTest;
import me.huijin.printService.pdfConvert.PdfPage;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author 付乐
 * @createTime 2013-8-20
 */
public class MyPrinterTest {
    
    private static String testHtml;
    private static String baseOutPath;
    
    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        URL url = MyPrinterTest.class.getResource("/files/dingdan3.html");
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
     * Test method for
     * {@link me.huijin.printService.printer.MyPrinter#acroRdprintPdf(java.lang.String, java.lang.String)}
     * .
     * 
     * @throws IOException
     * @throws ReadHtmlException
     * @throws PrintException
     */
    @Test
    public final void testAcroRdprintPdf() throws ReadHtmlException, IOException, PrintException {
        Printable printable = new MyPrinter();
        String htmlFilePath = getHtmlFilePath();
        
        Convertable co = FlyConvertPdfImpl.getInstance();
        String pdfPath = baseOutPath + "lianhe.pdf";
        co.HtmlFile2Pdf(htmlFilePath, pdfPath,new PdfPage());
        
        String acroRdDir = "C:\\Program Files (x86)\\Adobe\\Reader 11.0\\Reader";
        if (pdfPath.indexOf("/") == 0) {
            pdfPath = pdfPath.substring(1, pdfPath.length());
        }
        System.out.println(pdfPath);
        
        printable.acroRdprintPdf(acroRdDir, pdfPath,null);
    }
    
    /**
     * Test method for
     * {@link me.huijin.printService.printer.MyPrinter#bioPrintPdf(java.lang.String, java.lang.String)}
     * .
     * 
     * @throws IOException
     * @throws ReadHtmlException
     * @throws PrintException
     */
    @Test
    public final void testBioPrintPdf() throws ReadHtmlException, IOException, PrintException {
        Printable printable = new MyPrinter();
        String htmlFilePath = getHtmlFilePath();
        
        Convertable co = FlyConvertPdfImpl.getInstance();
        String pdfPath = baseOutPath + "lianhe.pdf";
        co.HtmlFile2Pdf(htmlFilePath, pdfPath,new PdfPage());
        
        String acroRdDir = "C:\\Program Files (x86)\\bioPDF\\Acrobat Wrapper";
        if (pdfPath.indexOf("/") == 0) {
            pdfPath = pdfPath.substring(1, pdfPath.length());
        }
        System.out.println(pdfPath);
        
        printable.bioPrintPdf(acroRdDir, pdfPath,null);
    }
    
    
    private String getHtmlFilePath() throws ReadHtmlException, IOException {
        File file = HtmlReader.readHtmlToFile(new File(testHtml), baseOutPath + File.separator
            + "订单.html");
        return file.getAbsolutePath();
    }
    
    
    /**
     * 打印机列表
     */
    @Test
    public void testgetPrintList(){
        String[] strs = Printable.getPrintList();
        Assert.assertNotNull(strs);
        for(String s:strs){
            System.out.println(s);
        }
    }
}
