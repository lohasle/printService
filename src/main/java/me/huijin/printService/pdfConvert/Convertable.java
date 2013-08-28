package me.huijin.printService.pdfConvert;

import me.huijin.printService.commons.ReadHtmlException;

/**
 * pdf转化接口
 * @author 付乐
 * @createTime 2013-8-20
 */
public interface Convertable {
    
    // 默认字体 宋体
    final String  DEFAULT_FONT = "SIMSUN.TTC";
    /**
     * 使用html字符创建PDF
     * 
     * @param htmStr
     * @param outPath
     * @param outFileName
     * @return
     * @throws ReadHtmlException
     */
    boolean HtmlStr2Pdf(String htmStr, String outPath, String outFileName,PdfPage page) throws ReadHtmlException;
    
    
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
    boolean HtmlFile2Pdf(String htmlFilePath, String outFilePath,PdfPage page)
        throws ReadHtmlException ;
    
    
    
}
