package clients;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Logo extends JPanel {
    private int   width      = 192;
    private int   height     = 40;
    private Image theLogo;

    Color maingray = new Color(0, 0, 0);

    public Logo(int aWidth, int aHeight)
    {
        width = aWidth;
        height= aHeight;
        setSize( width, height );
        loadImage();
    }

    public void paint( Graphics g )       // When 'Window' is first
    {                                     //  shown or damaged
        drawImage( (Graphics2D) g );
    }

    private void loadImage() {
        theLogo = new ImageIcon("images/MiniStore.jpg").getImage();
    }

    private void drawImage( Graphics2D g )
    {
        setSize( width, height );
        g.setPaint(maingray);
        g.fill( new Rectangle2D.Double( 0, 0, width, height ) );

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(theLogo, 0, 0, null);
    }
}
