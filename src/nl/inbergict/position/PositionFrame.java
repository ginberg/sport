package nl.inbergict.position;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class PositionFrame extends JFrame implements ActionListener {

	//width of the soccer field
	private int width;
	//length of the soccer field
	private int length;
	//radius of the circle in the center of the field
	private double radius;
	
	private int[] centerPixels = new int[2];
	private int[] centerMeters = new int[2];
	
	private int[] radiusPixels = new int[2];
	private int[] radiusMeters = new int[2];
	
	private int[] borderPixels = new int[2];	
	private int[] borderMeters = new int[2];
	
	private double[] singletonPixels = new double[2];
	
	private double[] scale = new double[2];
	
	private int clickCounter = -1;
	
	//output
	private JTextField position = new JTextField();
	private JTextField comment = new JTextField();
	
	//input
	private JTextField inputWidth = new JTextField();
	private JTextField inputLength = new JTextField();
	private JTextField radiusWidth = new JTextField();
	
	private JButton openButton = new JButton("Open");
	private JButton saveButton = new JButton("SaveInput");
	private BufferedImage img;
	
	public PositionFrame(){
		Container contentPane = getContentPane();
	    	    
	    JPanel panel = new JPanel();
	    panel.setLayout(new GridLayout(7, 1));
	    openButton.addActionListener(new OpenL(this));
	    saveButton.addActionListener(new SaveL(this));
	    panel.add(inputWidth);
	    panel.add(inputLength);
	    panel.add(radiusWidth);
	    panel.add(saveButton);
	    panel.add(comment);
	    
	    panel.add(openButton);
	    panel.add(position);
	    panel.setVisible(true);
	    contentPane.add(panel, BorderLayout.SOUTH);
	    position.setEditable(false);
	    comment.setEditable(false);
	    
	    comment.setText("Please enter field lengths first, then click save, than click the 3 reference points");
	    inputWidth.setText("enter width");
	    inputLength.setText("enter length");
	    radiusWidth.setText("enter radius");
	    
	    contentPane.addMouseListener(new MouseListener()
        {
            
            @Override
            public void mouseClicked(MouseEvent e)
            {                
            	System.out.println("I've been clicked!");
            	if (clickCounter == 0){
            		position.setText("First point: Xpos, Ypos: " + e.getX() + "," + e.getY());
            		centerPixels[0] = e.getX();
            		centerPixels[1] = e.getY();
            	}
            	else if (clickCounter == 1){
            		position.setText("Second point: Xpos, Ypos: " + e.getX() + "," + e.getY());
            		radiusPixels[0] = e.getX();
            		radiusPixels[1] = e.getY();
            	}
            	else if (clickCounter == 2){
            		position.setText("Third point: Xpos, Ypos: " + e.getX() + "," + e.getY());
            		borderPixels[0] = e.getX();
            		borderPixels[1] = e.getY();
            		//do the math
            		scale[0] = (centerPixels[0] - radiusPixels[0]) / (radius);
            		scale[1] = (centerPixels[1] - borderPixels[1]) / (width/2);
            		singletonPixels[0] = centerPixels[0] - ((length/2) * (scale[0]));
            		singletonPixels[1] = borderPixels[1];
            	}
            	else{
            		double x = (e.getX() - singletonPixels[0]) / scale[0];
            		double y = (e.getY() - singletonPixels[1]) / scale[1];
            		position.setText("Position (x,y): " + x + ", " + y);
            	}
            	clickCounter++;
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
            	// TODO Auto-generated method stub
            	
            }
            @Override
            public void mouseExited(MouseEvent e) {
            	// TODO Auto-generated method stub
            	
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            	// TODO Auto-generated method stub
            	
            }
            @Override
            public void mousePressed(MouseEvent e) {
            	// TODO Auto-generated method stub
            	
            }
        });
	}
	
	public void paint(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }  
	

  class SaveL implements ActionListener {
	  
	private PositionFrame frame;
	
    public SaveL(PositionFrame positionFrame) {
		this.frame = positionFrame;
	}

	public void actionPerformed(ActionEvent e) {
		frame.width = Integer.valueOf(frame.inputWidth.getText());
		frame.radius = Double.valueOf(frame.radiusWidth.getText());
		frame.length = Integer.valueOf(frame.inputLength.getText());
		frame.inputWidth.setEditable(false);
		frame.radiusWidth.setEditable(false);
		frame.inputLength.setEditable(false);
		frame.saveButton.setEnabled(false);
		frame.comment.setText("Sizes are set, open the file now and click the 3 reference points");
		frame.clickCounter = 0;
    }
  }
  
  class OpenL implements ActionListener {
	  
	private PositionFrame frame;
		
    public OpenL(PositionFrame positionFrame) {
		this.frame = positionFrame;
	}

	public void actionPerformed(ActionEvent e) {
      JFileChooser c = new JFileChooser();
      // Demonstrate "Open" dialog:
      int rVal = c.showOpenDialog(PositionFrame.this);
      if (rVal == JFileChooser.APPROVE_OPTION) {
    	  comment.setText(c.getSelectedFile().getName() + " " + c.getCurrentDirectory().toString());
        //TODO load image
        try {
            img = ImageIO.read(new File(c.getSelectedFile().getPath()));
        } catch (IOException ex) {
        	System.out.println(ex.getMessage());
        }
        frame.repaint();        
      }
      if (rVal == JFileChooser.CANCEL_OPTION) {
    	  comment.setText("You pressed cancel");
      }
    }
  }
  
  
    @Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
	}

}
