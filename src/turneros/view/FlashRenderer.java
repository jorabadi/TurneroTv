package turneros.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.Timer;

import turneros.configuration.Configuration;

class FlashRenderer extends DefaultTableCellRenderer implements ActionListener
{
    JTable table;
    int flashRowSelection;
    Color selectionColor;
    Timer flashTimer;
    int tamano;

  
    public FlashRenderer(JTable table,int tamano)
    {
        this.table = table;
        flashRowSelection = 0;
        selectionColor = Color.red;
        flashTimer = new Timer(2000,this);
        flashTimer.setRepeats(false);
        this.tamano = tamano;
    }
  
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row, int column) 
    {
    	
    	Component c =  super.getTableCellRendererComponent(table, value, isSelected,
                                            hasFocus, row, column);
    	c.setFont(new Font("Arial", Font.BOLD, this.tamano));
        if(row == flashRowSelection && flashTimer.isRunning()) {
            setBackground(Color.red);
            super.setForeground(Color.white);
            
        } else if(row == flashRowSelection) {
            super.setForeground(Color.red);
            super.setBackground(Color.decode("#e7e4cf"));
        } else {
        	super.setForeground(Color.decode("#388479"));
            super.setBackground(Color.decode("#e7e4cf"));
        } 
        return this;
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
    		flashTimer.stop();
    		table.repaint();
    }
  
    public void setFlashRowSelection(int frs)
    {
        flashRowSelection = frs;
    }
  
    public void flashRow()
    {
        if(!flashTimer.isRunning())
            flashTimer.start();
        Rectangle r = table.getCellRect(flashRowSelection, 0, true);
        table.scrollRectToVisible(r);
        table.repaint();
    }
    
  
}