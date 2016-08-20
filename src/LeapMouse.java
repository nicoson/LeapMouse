//package LeapMouse;

/******************************************************************************\
* Author: Alberto Vaccari
* LeapMouse.java
* 
* This app simulates a mouse, based on the Sample.java for LeapMotion
*				
*				
\******************************************************************************/

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

import com.leapmotion.leap.*;

class LeapListener extends Listener {
	
	//True for Debugging
	boolean DEBUG = false;
	
	//0 = Key Tap 
	//1 = Finger Tap
	int CLICK_TYPE = 0;
	

	boolean USE_CALIBRATED_SCREEN = true;
	
	//Just to control the speed, it can be changed accordingly to needs
	int SLOW = 10;
	
	//Screen resolution, it should match the current screen resolution for more precise movements
	int SCREEN_X = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
	int SCREEN_Y = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;

	
	float cur_x = 0, cur_y = 0;
	
	int fingers_count = 0;
	int prev_fingers_count = 0;
	
	boolean Lclicked = false;
	boolean Rclicked = false;
	boolean keystroke = false;
	boolean LHold = false;
	
	boolean Swype = false;
	boolean Circle = false;
	
	public void onInit(Controller controller) {
		System.out.println("Initialized");
		System.out.println("Current screen resolution: " + SCREEN_X +"x" + SCREEN_Y);
	}

	public void onConnect(Controller controller) {
		System.out.println("Connected");
	}

	public void onDisconnect(Controller controller) {
		System.out.println("Disconnected");
	}

	public void onExit(Controller controller) {
		System.out.println("Exited");
		System.exit(0);
	}

	public void onFrame(Controller controller) {
		// Get the most recent frame and report some basic information

		InteractionBox iBox = controller.frame().interactionBox();
		Pointable pointable = controller.frame().pointables().frontmost();

		Vector leapPoint = pointable.stabilizedTipPosition();
		Vector normalizedPoint = iBox.normalizePoint(leapPoint, true);

		float appX = normalizedPoint.getX() * SCREEN_X;
		float appY = (1 - normalizedPoint.getY()) * SCREEN_Y;
		//float appY = normalizedPoint.getZ() * SCREEN_Y;
		
		Robot mouseHandler;
		
		
		try {
			
			mouseHandler = new Robot();
			mouseHandler.mouseMove((int)appX, (int)appY);
			
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	
	public void moveMouse(float x, float y)
	{
		Robot mouseHandler;
		
		if(cur_x != x || cur_y != y){
			
			cur_x = x;
			cur_y = y;
			
			try {
				
				mouseHandler = new Robot();
				mouseHandler.mouseMove((int)x, (int)y);
				
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	// 0: Left
	// 1: Right
	// 2: Middle	-not implemented yet-
	public void clickMouse(int value)
	{
		int input;
		
		switch(value){
			case 0: input = InputEvent.BUTTON1_MASK; break;
			case 1: input = InputEvent.BUTTON3_MASK; break;
			case 2: input = InputEvent.BUTTON2_MASK; break;
			default: input = 0;
		}
		
		Robot mouseHandler;
		
		
		try {
			
			mouseHandler = new Robot();
			mouseHandler.mousePress(input);
			
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	// 0: Left
	// 1: Right
	// 2: Middle	-not implemented yet-
	public void releaseMouse(int value)
	{
		int input;
		
		switch(value){
			case 0: input = InputEvent.BUTTON1_MASK; break;
			case 1: input = InputEvent.BUTTON3_MASK; break;
			case 2: input = InputEvent.BUTTON2_MASK; break;
			default: input = 0;
		}
		
		Robot mouseHandler;
		
		
		try {
			
			mouseHandler = new Robot();
			mouseHandler.mouseRelease(input);
			
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		
		
	}	
	
	
	public void showHideDesktop()
	{
		Robot keyHandler;
		
		
		try {
			
			keyHandler = new Robot();
			keyHandler.keyPress(KeyEvent.VK_WINDOWS);
			keyHandler.keyPress(KeyEvent.VK_D);
			keyHandler.keyRelease(KeyEvent.VK_WINDOWS);
			keyHandler.keyRelease(KeyEvent.VK_D);
			
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		
		
	}

	public void copy()
	{
		Robot keyHandler;
		
		
		try {
			
			keyHandler = new Robot();
			keyHandler.keyPress(KeyEvent.VK_CONTROL);
			keyHandler.keyPress(KeyEvent.VK_C);
			keyHandler.keyRelease(KeyEvent.VK_CONTROL);
			keyHandler.keyRelease(KeyEvent.VK_V);
			
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		
		
	}

	public void paste()
	{
		Robot keyHandler;
		
		
		try {
			
			keyHandler = new Robot();
			keyHandler.keyPress(KeyEvent.VK_CONTROL);
			keyHandler.keyPress(KeyEvent.VK_V);
			keyHandler.keyRelease(KeyEvent.VK_CONTROL);
			keyHandler.keyRelease(KeyEvent.VK_V);
			
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		
		
	}
	public void switchApplication()
	{
		Robot keyHandler;
		
		
		try {
			
			keyHandler = new Robot();
			keyHandler.keyPress(KeyEvent.VK_ALT);
			keyHandler.keyPress(KeyEvent.VK_TAB);
			keyHandler.keyRelease(KeyEvent.VK_ALT);
			keyHandler.keyRelease(KeyEvent.VK_TAB);
			
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	public void setDebug(boolean d){
		DEBUG = d;
	}
	
	public void setClickType(int i){
		CLICK_TYPE = i;
	}
	
	public void setCalibratedScren(boolean d){
		USE_CALIBRATED_SCREEN = d;
	}
}

class LeapMouse {
	
	public static void main(String[] args) throws IOException {
		
		// Create a sample listener and controller
		LeapListener listener = new LeapListener();
		Controller controller = new Controller();
		controller.enableGesture( Gesture.Type.TYPE_KEY_TAP );
		//	controller.enableGesture( Gesture.Type.TYPE_SWIPE );
		//	controller.enableGesture( Gesture.Type.TYPE_CIRCLE );
		
		// Have the sample listener receive events from the controller
		controller.addListener(listener);

		// Keep this process running until Enter is pressed
		System.out.println("Press Enter to quit...");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Remove the sample listener when done
		controller.removeListener(listener);
	}
}
