package me.huijin.printService.pdfConvert;

import java.io.IOException;

import me.huijin.printService.commons.ReadHtmlException;
import me.huijin.printService.printer.Printable;

import org.apache.log4j.Logger;

/**
 * WebKit 浏览器渲染的HTML
 * 
 * @author 付乐
 * @createTime 2013-8-23
 */
public class WKHtmlToPdfImpl implements Convertable {
    
    private static final WKHtmlToPdfImpl wk     = new WKHtmlToPdfImpl();
    private static Logger                logger = Logger.getLogger(WKHtmlToPdfImpl.class);
    
    public static final String WK_KEY = "wkhtmltopdf.exe";
    
    private String                       wkhtmltoxPath;
    
    private WKHtmlToPdfImpl() {
        
    }
    
    public void setWkhtmltoxPath(String wkhtmltoxPath) {
        this.wkhtmltoxPath = wkhtmltoxPath;
    }
    
    /**
     * 获取WKHtmlToPdfImpl
     * 
     * @param wkhtmltoxPath
     * @return
     */
    public static WKHtmlToPdfImpl getWKHtmlToPdf(String wkhtmltoxPath) {
        wk.setWkhtmltoxPath(wkhtmltoxPath);
        return wk;
    }
    
    /**
     * 使用HTML创建pdf
     * 
     * @param htmlFilePath
     *            html文件路径（file:// 或者http://）
     * @param outFilePath
     *            输出文件路径
     * @return
     * @throws ReadHtmlException
     */
    
    public boolean HtmlFile2Pdf(String htmlFilePath, String outFilePath, PdfPage page)
        throws ReadHtmlException {
        if (htmlFilePath == null && "".equals(htmlFilePath)) {
            return false;
        }
        String osName = System.getProperty("os.name");
        if (osName.indexOf("Win") > -1) {
            
            outFilePath = outFilePath.replace("/", "\\");
            
            String sp = wkhtmltoxPath.substring(0, wkhtmltoxPath.indexOf(":") + 1);// 盘符
            String cmSp = "cd " + wkhtmltoxPath;
            
            StringBuffer sb = new StringBuffer();
            sb.append("wkhtmltopdf");
            if (!page.isQuality()) {// 清晰度
                sb.append(" -l");
            }
            if (page.getPageSize() != null && !"".equals(page.getPageSize())) {// 纸张大小
                sb.append(" -s " + page.getPageSize());
            }
            if (page.getPageOrientation() != null && !"".equals(page.getPageOrientation())) {// 纸张方向
                sb.append(" -O " + page.getPageOrientation());
            }
            sb.append(" \"" + htmlFilePath + "\"");
            sb.append(" \"" + outFilePath + "\"");
            String[] commondLines = new String[] {sp, cmSp, sb.toString() };
            logger.info("正在转化-->" + htmlFilePath);
            try {
                Printable.runCommond(commondLines);
                logger.info("======转换成功!---" + htmlFilePath + "---->" + outFilePath);
                return true;
            } catch (IOException e) {
                throw new ReadHtmlException(e.getMessage());
            } catch (InterruptedException e) {
                throw new ReadHtmlException(e.getMessage());
            }
        } else {
            return false;
        }
    }
    
    public boolean HtmlStr2Pdf(String htmStr, String outPath, String outFileName, PdfPage page)
        throws ReadHtmlException {
        return false;
    }
    
}
