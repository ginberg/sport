package nl.inbergict.position;


public class Positioner {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*TODO 
		1: start a GUI
		2: load picture
		3: get input field sizes (to measure scale)
		3: get coordinates mouseclick
		*/
						
		PositionFrame positionFrame = new PositionFrame();
		 
		//This will center the JFrame in the middle of the screen
		positionFrame.setLocationRelativeTo(null);
		positionFrame.setSize(800, 500);
		positionFrame.setResizable(false);
		// Show it!
		positionFrame.setVisible(true);
	}

}
