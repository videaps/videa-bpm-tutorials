package services.videa.tutorials.bpm;

import org.camunda.bpm.application.ProcessApplication;

/**
 *
 */
@ProcessApplication("bpm")
public class BpmApplication {
	
	public static void main(String[] args) {
		System.out.println(new Exception().getStackTrace()[0].getMethodName());
	}
	
}
