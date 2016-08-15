package turneros.view;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import turneros.configuration.Configuration;

public class TvTunerView extends JPanel {
	public TvTunerView() {
		
	}
	
	
	
	@Override
    protected void paintComponent(Graphics g) {
		BufferedImage imagen;
		try {
			imagen = ImageIO.read(new File(Configuration.getPreference("imagenFondo")));
			super.paintComponent(g);
	        g.drawImage(imagen, 0, 0, this.getWidth(), this.getHeight(), null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

}
