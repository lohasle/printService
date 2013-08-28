package me.huijin.printService.commons;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

/**
 * 资源加载工具
 * 
 * @author 付乐
 * @createTime 2013-8-21
 */
public class ResourceLoadUtil {
    
    /**
     * 加载xml Properties
     * 
     * @param XMLPath  XML 路径  /绝对路径   不加/相对路径
     * @param cz
     * @return
     * @throws IOException
     */
    public static Properties loadPropertiesFromXML(String XMLPath, Class cz) throws IOException {
        URL url = cz.getResource(XMLPath);
        InputStream is = null;
        URLConnection con = null;
        Properties p = new Properties();
        try {
            con = url.openConnection();
            p.loadFromXML(is = con.getInputStream());
        } catch (IOException e) {
            throw e;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }
        return p;
    }
    
}
