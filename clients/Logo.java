package clients;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;


import java.awt.image.BufferedImage;
import java.io.*;

public class Logo extends Canvas {
    private int   width      = 192;
    private int   height     = 40;
    private Image theLogo = null;

    public Logo()
    {
        setSize( width, height );
    }

    public Logo(int aWidth, int aHeight)
    {
        width = aWidth;
        height= aHeight;
        setSize( width, height );
    }

    public void set( ImageIcon ic )
    {
        theLogo = ic.getImage();         // Image to be drawn
        repaint();
    }

    public void clear()
    {
        theLogo = null;                  // clear picture
        repaint();                          // Force repaint
    }

    public void paint( Graphics g )       // When 'Window' is first
    {                                     //  shown or damaged
        drawImage( (Graphics2D) g );
    }

    public void update( Graphics g )      // Called by repaint
    {                                     //
        drawImage( (Graphics2D) g );        // Draw picture
    }

    Color maingray = new Color(0, 0, 0);

    Image image = getToolkit( ).getImage("images/MiniStore.png");

    public void drawImage( Graphics2D g )
    {
        setSize( width, height );
        g.setPaint(maingray);
        g.fill( new Rectangle2D.Double( 0, 0, width, height ) );
        g.drawImage( image, 0, 0, this );
    }
}
