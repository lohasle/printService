package me.huijin.printService.JoinTest;

import java.io.File;
import java.net.URL;

import me.huijin.printService.commons.HtmlReader;
import me.huijin.printService.pdfConvert.Convertable;
import me.huijin.printService.pdfConvert.FlyConvertPdfImpl;
import me.huijin.printService.pdfConvert.PdfPage;
import me.huijin.printService.printer.MyPrinter;
import me.huijin.printService.printer.MyPrinterTest;
import me.huijin.printService.printer.Printable;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class joinTest {
    private static String          testHtml;
    private static String          baseOutPath;
    
    private static final Printable printable = new MyPrinter();
    
    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        URL url = MyPrinterTest.class.getResource("/files/dingdan3.html");
        testHtml = url.getFile();
        baseOutPath = joinTest.class.getResource("").getFile();
    }
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }
    
    @Before
    public void setUp() throws Exception {
    }
    
    @After
    public void tearDown() throws Exception {
    }
    
    /**
     * 
     * 以Test dingdan3.html
     */
    @Test
    public final void test() throws Exception {
        for (int i = 0; i < 1; i++) {
            printUnit(i);
        }
    }
    
    private void printUnit(int index) throws Exception {
        // 读取文件
        File file = HtmlReader.readHtmlToFile(new File(testHtml), baseOutPath + File.separator
            + "订单"+index+".html");
        
        //转化成PDF
        Convertable co = FlyConvertPdfImpl.getInstance();
        String pdfPath = baseOutPath + "currentTest"+index+".pdf";
        co.HtmlFile2Pdf(file.getAbsolutePath(), pdfPath, new PdfPage());
        
        //执行打印
        String acroRdDir = "C:\\Program Files (x86)\\bioPDF\\Acrobat Wrapper";
        if (pdfPath.indexOf("/") == 0) {
            pdfPath = pdfPath.substring(1, pdfPath.length());
        }
        
        printable.bioPrintPdf(acroRdDir, pdfPath,null);
        //SimpleUtil.delete(file.getAbsolutePath());
        //SimpleUtil.delete(pdfPath);
    }
    
}
