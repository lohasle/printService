package me.huijin.printService.commons;

import java.io.File;

import org.apache.log4j.Logger;

/**
 * 简答的工具代码
 * 
 * @author 付乐
 * @createTime 2013-8-22
 */
public class SimpleUtil {
    private static Logger logger = Logger.getLogger(SimpleUtil.class);
    
    /**
     * 删除文件夹或者文件
     * 
     * @param strDir
     * @return
     */
    public static boolean removeDir(String strDir) {
        File rmDir = new File(strDir);
        if (rmDir.isDirectory() && rmDir.exists()) {
            String[] fileList = rmDir.list();
            
            for (int i = 0; i < fileList.length; i++) {
                String subFile = strDir + File.separator + fileList[i];
                File tmp = new File(subFile);
                if (tmp.isFile())
                    tmp.delete();
                else if (tmp.isDirectory())
                    removeDir(subFile);
            }
            rmDir.delete();
            logger.info("删除文件"+rmDir.getAbsolutePath());
        } else {
            return rmDir.exists()?rmDir.delete():false;
        }
        return true;
    }
}
