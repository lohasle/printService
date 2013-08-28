package me.huijin.printService.pdfConvert;

/**
 * pdf 文件属性
 * 
 * @author 付乐
 * @createTime 2013-8-23
 */
public class PdfPage {
    
    public final static String A4          = "A4";
    
    public final static String A5          = "A5";
    
    public final static String B4          = "B4";
    
    public final static String B5          = "B5";
    
    /**
     * 横向
     */
    public final static String LANDSCAPE   = "Landscape";
    
    /**
     * 纵向
     */
    public final static String PORTRAIT    = "Portrait";
    
    /**
     * 低质量
     */
    public final static String LOW_QUALITY = "l";
    
    /**
     * 生成的纸张大小
     */
    private String             pageSize;
    
    /**
     * 生成的纸张方向 Landscape Portrait
     */
    private String             pageOrientation;
    
    /**
     * 生成的pdf质量
     */
    private boolean            quality;
    
    public String getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
    
    public String getPageOrientation() {
        return pageOrientation;
    }
    
    public void setPageOrientation(String pageOrientation) {
        this.pageOrientation = pageOrientation;
    }
    
    public boolean isQuality() {
        return quality;
    }
    
    public void setQuality(boolean quality) {
        this.quality = quality;
    }
    
}
