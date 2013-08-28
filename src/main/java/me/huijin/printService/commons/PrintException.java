package me.huijin.printService.commons;

/**
 * 打印异常
 * 
 * @author fule
 */
public class PrintException extends Exception {
    /**
	 * 
	 */
    private String            msg;
    private static final long serialVersionUID = 1L;
    
    public PrintException() {
        super();
    }
    
    public PrintException(String message) {
        super(message);
        msg = message;
    }
    
    public String getMessage() {
        // TODO Auto-generated method stub
        return msg == null || "".equals(msg) ? "打印异常"
            : msg;
    }
    
}
