package me.huijin.printService.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.huijin.printService.commons.PrintException;
import me.huijin.printService.commons.ReadHtmlException;
import me.huijin.printService.commons.ResourceLoadUtil;
import me.huijin.printService.commons.SimpleUtil;
import me.huijin.printService.pdfConvert.Convertable;
import me.huijin.printService.pdfConvert.PdfPage;
import me.huijin.printService.pdfConvert.WKHtmlToPdfImpl;
import me.huijin.printService.printer.MyPrinter;
import me.huijin.printService.printer.Printable;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class DoPrintServlet extends HttpServlet {
    
    /**
     * 
     */
    private static final long      serialVersionUID = -7462786818488278441L;
    private static final Printable printable        = new MyPrinter();
    
    private static Logger          logger           = Logger.getLogger(DoPrintServlet.class);
    
    /**
     * Constructor of the object.
     */
    public DoPrintServlet() {
        super();
    }
    
    /**
     * Destruction of the servlet. <br>
     */
    public void destroy() {
        super.destroy(); // Just puts "destroy" string in log
        // Put your code here
    }
    
    /**
     * The doGet method of the servlet. <br>
     * This method is called when a form has its tag value method equals to get.
     * 
     * @param request
     *            the request send by the client to the server
     * @param response
     *            the response send by the server to the client
     * @throws ServletException
     *             if an error occurred
     * @throws IOException
     *             if an error occurred
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        doPost(request, response);
    }
    
    /**
     * 打印服务
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/javascript");
        String callbackparam = request.getParameter("callbackparam");// 回调参数

        
            String basePath = System.getProperty("printService.root");//项目绝对路径
            String printurl = request.getParameter("printurl");// 请求地址
            
            //打印机名称
            String printName =request.getParameter("printname");
            if(printName!=null&&!"".equals(printName)){
                printName = new String(printName.getBytes("ISO-8859-1"),"utf-8");
            }
            
            // page
            String p = request.getParameter("pagesize"); // 纸张大小 A4 ...
            String o = request.getParameter("islandscape");// 是否横向 y n
            String q = request.getParameter("quality");// 质量 h l
            
            String pageSize = (p != null && !"".equals(p)) ? p
                : PdfPage.A4;
            String orientation = PdfPage.PORTRAIT;
            if (o != null && !"".equals(o) && "y".equals(o)) {
                orientation = PdfPage.LANDSCAPE;
            }
            
            boolean quality = (q != null && !"".equals(q)) ? ("h".equalsIgnoreCase(q) ? true
                : false)
                : false;
            
            // String distag = request.getParameter("distagid"); // 需要去除标签的id
            // String noScript = request.getParameter("ns"); // 是否保留脚本
            
            String tempPath = basePath + File.separator + "printTemp";// 每一份打印的存的基础目录
            
            SimpleUtil.removeDir(tempPath);//清空打印临时目录
            
            File tempFileDir = new File(tempPath);
            tempFileDir.mkdirs();

            
            logger.info("打印地址-->" + printurl);
            
            // boolean noS = "Y".equalsIgnoreCase(noScript) ? true
            // : false;
            
            String resultMsg = "faild";
            try {
                // String[] filterTag =
                // (distag==null||"".equals(distag))?null:distag.split(",");
                // //需要过滤的标签
                // 取得Html文件
                // File htmlFile = HtmlReader.readHtmlToFile(new URL(printurl),
                // tempPath + File.separator + "printOrder.html",
                // filterTag, noS);
                
                // 转化PDF TODO
                // Convertable co = FlyConvertPdfImpl.getInstance();
                // co.HtmlFile2Pdf(htmlFile.getAbsolutePath(), tempPath +
                // File.separator + "printOrder.pdf");
                
                // 系统常量
                Properties re_p = ResourceLoadUtil.loadPropertiesFromXML("/resources.xml",
                    DoPrintServlet.class);
                Convertable co = WKHtmlToPdfImpl.getWKHtmlToPdf(re_p
                    .getProperty(WKHtmlToPdfImpl.WK_KEY));
                String pdf_file = tempPath + File.separator + "printOrder.pdf";
                PdfPage page = new PdfPage();
                page.setPageSize(pageSize);
                page.setPageOrientation(orientation);
                page.setQuality(quality);
                co.HtmlFile2Pdf(printurl, pdf_file, page);
                // 打印
                printable.bioPrintPdf(re_p.getProperty(Printable.ACROBAT_KEY), pdf_file,printName);
                
                resultMsg = "success";
            } catch (ReadHtmlException e) {
                e.printStackTrace();
                resultMsg = "html解析错误";
            } catch (PrintException e) {
                e.printStackTrace();
                resultMsg = "打印错误";
            }
            // 输出回调函数
            response.getWriter().print(callbackparam + "({\"msg\":\"" + resultMsg + "\"})");
       
                  
        
        
    }
    
    /**
     * 初始化log4j
     */
    public void init() throws ServletException {
        // Put your code here
        String realPath = getServletContext().getRealPath("/");
        System.setProperty("printService.root", getServletContext().getRealPath("/"));
        String file = getInitParameter("log4j");
        if (file != null) {
            /**
             * 读取配置文件的方法 BasicConfigurator.configure ()： 自动快速地使用缺省Log4j环境。
             * PropertyConfigurator.configure ( String configFilename) ：读取使用Java
             * 的特性文件编写的配置文件。 DOMConfigurator.configure ( String filename )
             * ：读取XML形式的配置文件。
             */
            PropertyConfigurator.configure(realPath + file);
            logger.info("LOG4J模块初始化成功!");
        }
        logger.info("项目绝对路径保存在系统变量printService.root中,可通过System.getProperty(printService.root)获取,项目绝对路径:"+realPath);
        //设置接口地址
        logger.info("init OK"); 
    }
    
}
