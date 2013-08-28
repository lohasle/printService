package me.huijin.printService.printer;

import java.io.File;
import java.io.IOException;

import me.huijin.printService.commons.PrintException;

import org.apache.log4j.Logger;
/**
 * 在基本打印机上的拓展
 * @author 付乐
 * @createTime 2013-8-22
 */
public class MyPrinter extends Printable {
    
    private static Logger logger = Logger.getLogger(MyPrinter.class);
    
    public boolean acroRdprintPdf(String acroRdDir, String pdfFilePath,String printerName) throws PrintException {
        String osName = System.getProperty("os.name");
        if (osName.indexOf("Win") > -1) {
            
            acroRdDir = acroRdDir.replace("/", "\\");
            pdfFilePath ="\""+ pdfFilePath.replace("/", "\\")+"\"";
            
            String sp = acroRdDir.substring(0, acroRdDir.indexOf(":") + 1);// 盘符
            String cmSp = "cd " + acroRdDir;
            String cm_exe ;
            if(printerName!=null&&!"".equals(printerName)){
                cm_exe = "AcroRd32 /p /n /h /s /o /t " + pdfFilePath+" \""+printerName+"\"";
            }else{
                cm_exe = "AcroRd32 /p /n /h /s /o /t " + pdfFilePath;
            }
            
            String[] commondLines = new String[] {sp, cmSp, cm_exe };
            try {
                Printable.runCommond(commondLines);
                logger.info(pdfFilePath + "-->打印成功");
                return true;
            } catch (IOException e) {
                logger.info(pdfFilePath + "-->打印失败");
                throw new PrintException(e.getMessage());
            } catch (InterruptedException e) {
                throw new PrintException(e.getMessage());
            }
        } else {
            return false;
        }
    }
    
    public boolean bioPrintPdf(String bioDir, String pdfFilePath,String printerName) throws PrintException {
        String osName = System.getProperty("os.name");
        if (osName.indexOf("Win") > -1) {
            
            bioDir = bioDir.replace("/", "\\");
            pdfFilePath = pdfFilePath.replace("/", "\\");
            
            String str = System.getProperty("user.dir") + File.separator + "temp";
            File file = new File(str);
            if (!file.exists()) {
                file.mkdirs();
            }
            
            pdfFilePath = "\""+pdfFilePath+"\"";
            
            String sp = bioDir.substring(0, bioDir.indexOf(":") + 1);// 盘符
            String cmSp = "cd " + bioDir;
            String cm_exe ;
            if(printerName!=null&&!"".equals(printerName)){
                cm_exe = "acrowrap /t  " + pdfFilePath+" \""+printerName+"\"";//指定打印机
            }else{
                cm_exe = "acrowrap /t  " + pdfFilePath;
            }
            
            String[] commondLines = new String[] {sp, cmSp, cm_exe};
            try {
                Printable.runCommond(commondLines);
                logger.info(pdfFilePath + "-->打印成功");
                return true;
            } catch (IOException e) {
                logger.info(pdfFilePath + "-->打印失败");
                throw new PrintException(e.getMessage());
            } catch (InterruptedException e) {
                throw new PrintException(e.getMessage());
            }
        } else {
            return false;
        }
    }
    

    
}
