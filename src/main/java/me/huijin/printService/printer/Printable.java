package me.huijin.printService.printer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;

import me.huijin.printService.commons.PrintException;

import org.apache.log4j.Logger;

/**
 * 打印功能模板
 * 
 * @author 付乐
 * @createTime 2013-8-20
 */
public abstract class Printable {
    private static Logger      logger       = Logger.getLogger(Printable.class);
    
    /**
     * acrobat 访问key
     */
    public static final String ACROBAT_KEY  = "acrobat.exe";
    
    /**
     * acroRd32 访问key
     */
    public static final String ACRORD32_KEY = "acroRd32.exe";
    
    /**
     * 打印图片或者文本
     * 
     * @param fileSrc
     * @param printerName
     * @throws IOException
     * @throws javax.print.PrintException
     */
    public static void printFile(String fileSrc, String printerName)
        throws IOException, javax.print.PrintException {
        File file = new File(fileSrc); // 获取选择的文件
        // 构建打印请求属性集
        HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        // 设置打印格式，因为未确定类型，所以选择autosense
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        // 查找所有的可用的打印服务
        PrintService printService[] = getPrintServices(pras,flavor);
        
        for (PrintService s : printService) {
            // HP Officejet Pro 8600
            if (printerName.equals(s.getName())) {
                DocPrintJob job = s.createPrintJob(); // 创建打印作业
                FileInputStream fis = new FileInputStream(file); // 构造待打印的文件流
                DocAttributeSet das = new HashDocAttributeSet();
                Doc doc = new SimpleDoc(fis, flavor, das);
                job.print(doc, pras);
                fis.close();
                logger.info(fileSrc + "文件打印成功");
            }
        }
    }
    
    /**
     * 取得本机打印机列表
     * @return
     */
    public static String[] getPrintList(){
        // 查找所有的可用的打印服务
        PrintService printService[] = getPrintServices(new HashPrintRequestAttributeSet(),DocFlavor.INPUT_STREAM.AUTOSENSE);
        String[] results = new String[printService.length];
        for (int i =0;i<results.length;i++) {
            results[i] = printService[i].getName();
        }
        return results;
    }
    
    private static PrintService[] getPrintServices(HashPrintRequestAttributeSet pras,DocFlavor flavor){
        // 查找所有的可用的打印服务
        return PrintServiceLookup.lookupPrintServices(flavor, pras);
    }
    
    /**
     * 使用acroRd 服务进行打印
     * 
     * @param acroRdDir
     *            acroRd 路径
     * @param pdfFilePath
     *            pdf 路径
     * @param printerName
     *            打印机名称  没有指定就为默认打印机
     * @throws PrintException
     * @throws IOException
     */
    public abstract boolean acroRdprintPdf(String acroRdDir, String pdfFilePath,String printerName)
        throws PrintException;
    
    /**
     * 使用bio 服务进行打印
     * 
     * @param bioDir
     *            bio 路径
     * @param pdfFilePath
     *            pdf 路径
     * @param printerName
     *            打印机名称 没有指定就为默认打印机
     * @throws PrintException
     * @throws IOException
     */
    public abstract boolean bioPrintPdf(String bioDir, String pdfFilePath,String printerName) throws PrintException;
    
    /**
     * cmd命令执行器
     * 
     * @param commondLines
     * @throws IOException
     * @throws InterruptedException
     */
    public synchronized static void runCommond(String[] commondLines)
        throws IOException, InterruptedException {
        String str = System.getProperty("user.dir") + File.separator + "temp";
        File file = new File(str);
        if (!file.exists()) {
            file.mkdirs();
        }
        String exePath = file.getAbsolutePath() + File.separator + "printCmd.bat";
        BufferedWriter bw = new BufferedWriter(
            new OutputStreamWriter(new FileOutputStream(exePath)));
        for (String s : commondLines) {
            logger.info("执行命令\t\t" + s);
            bw.write(s);
            bw.newLine();
        }
        bw.flush();
        bw.close();
        Process p = Runtime.getRuntime().exec("cmd /c " + exePath);
        
        final InputStream is1 = p.getInputStream();
        // 获取进城的错误流
        final InputStream is2 = p.getErrorStream();
        // 启动两个线程，一个线程负责读标准输出流，另一个负责读标准错误流
        new Thread() {
            public void run() {
                BufferedReader br1 = new BufferedReader(new InputStreamReader(is1));
                try {
                    String line1 = null;
                    while ((line1 = br1.readLine()) != null) {
                        if (line1 != null) {
                            logger.info("输出--->"+line1);
                        }
                    }
                } catch (IOException e) {
                    logger.debug("cmd输出流异常："+e.getMessage());
                    e.printStackTrace();
                } finally {
                    try {
                        is1.close();
                    } catch (IOException e) {
                        logger.debug("cmd流关闭异常："+e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        new Thread() {
            public void run() {
                BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
                try {
                    String line2 = null;
                    while ((line2 = br2.readLine()) != null) {
                        if (line2 != null) {
                            logger.info("输出--->"+line2);
                        }
                    }
                } catch (IOException e) {
                    logger.debug("cmd错误信息："+e.getMessage());
                    e.printStackTrace();
                } finally {
                    try {
                        is2.close();
                    } catch (IOException e) {
                        logger.debug("cmd流关闭异常："+e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        p.waitFor();
    }
}
