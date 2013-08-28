package me.huijin.printService.servlet;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.huijin.printService.printer.Printable;

import org.apache.log4j.Logger;

public class PrintListServlet extends HttpServlet {
    
    /**
     * 
     */
    private static final long      serialVersionUID = -7462786818488278441L;
    
    private static Logger          logger           = Logger.getLogger(PrintListServlet.class);
    
    /**
     * Constructor of the object.
     */
    public PrintListServlet() {
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
     * 服务器打印机列表
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/javascript");
        String callbackparam = request.getParameter("callbackparam");// 回调参数

        // 查找所有的可用的打印服务
        String[] strs = Printable.getPrintList();
        logger.info("服务器->->打印机:"+Arrays.toString(strs));
        StringBuffer resultMsg = new StringBuffer();
        for(int i = 0;i<strs.length-1;i++){
            resultMsg.append("\""+strs[i]+"\",");
        }
        resultMsg.append("\""+strs[strs.length-1]+"\"");
        response.getWriter().print(callbackparam + "({\"msg\":[" + resultMsg.toString() + "]})");  
        
    }
    
  
    public void init() throws ServletException {
        // Put your code here
    }
    
}
