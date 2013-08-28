package me.huijin.printService.pdfConvert;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import me.huijin.printService.commons.ReadHtmlException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.util.XRRuntimeException;
import org.xml.sax.SAXException;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;

/**
 * Itext fly pdf 转化类
 * 
 * @author 付乐
 * @createTime 2013-8-20
 */
public class FlyConvertPdfImpl implements Convertable {
    
    private static Logger                  logger   = Logger.getLogger(FlyConvertPdfImpl.class);
    
    private static String                  BASE_CLASS_PATH;
    
    static {
        URL url = FlyConvertPdfImpl.class.getResource("/fonts");
        BASE_CLASS_PATH = url.getFile().replaceAll("%20", " ")+File.separator;
        if (BASE_CLASS_PATH.indexOf("/") == 0) {
            BASE_CLASS_PATH = BASE_CLASS_PATH.substring(1, BASE_CLASS_PATH.length());
        }
        
        logger.info("默认字体路径---->" + BASE_CLASS_PATH);
    }
    
    private static final FlyConvertPdfImpl instance = new FlyConvertPdfImpl();
    
    private FlyConvertPdfImpl() {
        
    }
    
    /**
     * 获取转化实例
     * @return FlyConvertPdfImpl
     */
    public static FlyConvertPdfImpl getInstance() {
        return instance;
    }
    
    /**
     * 使用html字符创建PDF
     * 
     * @param htmStr
     * @param outPath
     * @param outFileName
     * @return
     * @throws ReadHtmlException
     */
    public boolean HtmlStr2Pdf(String htmStr, String outPath, String outFileName,PdfPage page)
        throws ReadHtmlException {
        if (outPath == null && "".equals(outPath)) {
            return false;
        }
        
        File file = new File(outPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        DocumentBuilder builder;
        try {
            
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(htmStr.getBytes());
            Document doc = builder.parse(tInputStringStream);
            ITextRenderer renderer = new ITextRenderer();
            
            // 解决中文乱码
            ITextFontResolver fontResolver = renderer.getFontResolver();

            fontResolver.addFont(BASE_CLASS_PATH +  DEFAULT_FONT,
                BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            
            renderer.setDocument(doc, null);
            OutputStream os = new FileOutputStream(outPath + File.separator + outFileName);
            renderer.layout();
            renderer.createPDF(os);
            os.close();
            logger.info("======转换成功!pdf文件输出---->"+outPath + File.separator + outFileName);
            return true;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            throw new ReadHtmlException(e.getMessage());
        } catch (SAXException e) {
            e.printStackTrace();
            throw new ReadHtmlException(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new ReadHtmlException(e.getMessage());
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new ReadHtmlException(e.getMessage());
        }
    }
    
    /**
     * 使用HTML创建pdf
     * 
     * @param htmlFilePath
     *            html文件路径
     * @param outFilePath
     *            输出文件路径
     * @return
     * @throws ReadHtmlException
     */
    public boolean HtmlFile2Pdf(String htmlFilePath, String outFilePath,PdfPage page)
        throws ReadHtmlException {
        OutputStream os;
        try {
            os = new FileOutputStream(outFilePath);
            ITextRenderer renderer = new ITextRenderer();
            ITextFontResolver fontResolver = renderer.getFontResolver();
            fontResolver.addFont(BASE_CLASS_PATH  + DEFAULT_FONT,
                BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            String url = new File(htmlFilePath).toURI().toURL().toString();
            logger.info("=============url: " + url);
            renderer.setDocument(url);
            renderer.layout();
            renderer.createPDF(os);
            os.close();
            logger.info("======转换成功!---"+htmlFilePath+"---->"+outFilePath);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            throw new ReadHtmlException(e.getMessage());
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new ReadHtmlException(e.getMessage());
        }catch (XRRuntimeException e) {
            e.printStackTrace();
            throw new ReadHtmlException(e.getMessage());        }
    }
    
}
