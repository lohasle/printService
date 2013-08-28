package me.huijin.printService.commons;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * HTML 读取类
 * 
 * @author 付乐
 * @createTime 2013-8-20
 */
public class HtmlReader {
    
    private static Logger       logger                = Logger.getLogger(HtmlReader.class);
    
    /**
     * 默认读取的编码utf-8
     */
    private static final String DEFAULT_CHARSET       = "utf-8";
    
    /**
     * 默认超时时间 5000 毫秒
     */
    private static final int    DEFAULT_TIMEOUTMILLIS = 5000;
    
    /**
     * 读取html到字符串
     * 
     * @param htmlFile
     * @return
     * @throws ReadHtmlException
     */
    public static String readHtmlToString(File htmlFile) throws ReadHtmlException {
        try {
            Document doc = Jsoup.parse(htmlFile, DEFAULT_CHARSET);
            String html = doc.html();
            return html;
        } catch (IOException e) {
            logger.debug("读取Html文件异常-->" + htmlFile.getAbsolutePath());
            throw new ReadHtmlException(e.getMessage());
        }
    }
    
    /**
     * 读取html到字符串
     * 
     * @param htmlFile
     * @return
     * @throws ReadHtmlException
     */
    public static String readHtmlToString(URL url) throws ReadHtmlException {
        try {
            Document doc = Jsoup.parse(url, DEFAULT_TIMEOUTMILLIS);
            String html = doc.html();
            return html;
        } catch (IOException e) {
            logger.debug("读取Html文件异常-->" + url.getPath());
            throw new ReadHtmlException(e.getMessage());
        }
    }
    
    /**
     * 读取html到文件
     * 
     * @param htmlFile
     * @return
     * @throws ReadHtmlException
     */
    public static File readHtmlToFile(File htmlFile, String outFilePath)
        throws ReadHtmlException, IOException {
        String html = readHtmlToString(htmlFile);
        saveStringToFile(html, outFilePath);
        return new File(outFilePath);
    }
    
    /**
     * 读取html到文件
     * 
     * @param htmlFile
     * @return
     * @throws ReadHtmlException
     */
    public static File readHtmlToFile(URL url, String outFilePath)
        throws ReadHtmlException, IOException {
        String html = readHtmlToString(url);
        saveStringToFile(html, outFilePath);
        return new File(outFilePath);
    }
    
    /**
     * 读取html到文件
     * 
     * @param url
     *            请求地址
     * @param outFilePath
     *            输出目录
     * @param disTagIds
     *            去除的标签ID集合
     * @param noScript
     *            是否需要脚本
     * @return
     * @throws ReadHtmlException
     * @throws IOException
     */
    public static File readHtmlToFile(URL url, String outFilePath, String[] disTagIds,
        boolean noScript) throws ReadHtmlException, IOException {
        Document doc = Jsoup.parse(url, DEFAULT_TIMEOUTMILLIS);
        if (noScript) {
            doc.getElementsByTag("script").remove();
        }
        if (disTagIds != null) {
            for (String s : disTagIds) {
                doc.getElementById(s).remove();
            }
        }
        String html = doc.html();
        saveStringToFile(html, outFilePath);
        return new File(outFilePath);
    }
    
    /**
     * 字符保存文件
     * 
     * @param content
     * @param filePath
     * @throws IOException
     */
    private static void saveStringToFile(String content, String filePath) throws IOException {
        PrintWriter prWriter = new PrintWriter(new File(filePath), "UTF-8");
        prWriter.write(content);
        prWriter.flush();
        prWriter.close();
        
    }
}
