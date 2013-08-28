package me.huijin.printService.commons;

/**
 * 不是标准的HTML
 * 
 * @author fule
 */
public class ReadHtmlException extends Exception {
    /**
	 * 
	 */
    private String            msg;
    private static final long serialVersionUID = 1L;
    
    public ReadHtmlException() {
        super();
    }
    
    public ReadHtmlException(String message) {
        super(message);
        msg = message;
    }
    
    @Override
    public String getMessage() {
        // TODO Auto-generated method stub
        return msg == null || "".equals(msg) ? "不是标准的Html"
            : msg;
    }
    
}
