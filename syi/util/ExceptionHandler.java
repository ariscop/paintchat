package syi.util;

import java.lang.Thread.UncaughtExceptionHandler;

import paintchat_client.Me;

public class ExceptionHandler implements UncaughtExceptionHandler {
	private static boolean isGui = false;
	private static Boolean Lock = new Boolean(false);

	public static void setGui(boolean isGui) {
		ExceptionHandler.isGui = isGui;
	}
	
	private void setHandler() {
		try {
			Thread.setDefaultUncaughtExceptionHandler(this);
		} catch(Throwable e) {
			Thread.currentThread().setUncaughtExceptionHandler(this);
		}
	}
	
	public ExceptionHandler() {
		setHandler();
	}

	public ExceptionHandler(boolean gui) {
		isGui = gui;
		setHandler();
		//ExceptionHandler();
	}

	public static void handleException(Throwable e) {
		exceptionAlert(Thread.currentThread(), e);
	}
	
	private static void exceptionAlert(Thread t, Throwable e) {
		try {
		synchronized(Lock) {
			//give up, shits broke
			if(Lock) throw new Exception();
			Lock = true;
			
			String s = "Exception in thread " + t.getName() + "\n"; 
			s += e.toString() + "\n";
			
			StackTraceElement[] trace = e.getStackTrace();
			for (int i=0; i < trace.length; i++)
				s += ("\tat " + trace[i] + "\n");
		
			e.printStackTrace();
			if(isGui) Me.alert(s);
			
			Lock = false;
		}
		} catch(Throwable e2) {
			//ignore exceptions here cause nothing can be done
			Lock = false;
		}
		//TODO: send me these exceptions. need to know where they happen in practice
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		exceptionAlert(t, e);
	}
}
