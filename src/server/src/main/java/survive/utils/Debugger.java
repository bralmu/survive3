package survive.utils;

public class Debugger {
	
	private static boolean enabled = false;
	
    public static void log(Object o){
        if(enabled)
        	System.out.println(o.toString());
    }
    
    public static void enable() {
    	enabled = true;
    }
    
    public static void disable() {
    	enabled = false;
    }
}
