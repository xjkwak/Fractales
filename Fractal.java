//Fractal.java  draw tree

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Fractal extends Frame
{
  private static int w1W           = 600;
  private static int w1H           = 500;
  private static float height      = 80.0f;
  private static float width       = 20.0f;
  private static float left_alpha  =  2.0f;
  private static float right_alpha =  2.2f;
  private static float left_angle  = 20.0f;
  private static float right_angle = 26.0f;
  private static int level_in      = 14;
  private static float left_width_factor;
  private static float right_width_factor;
  private static float left_height_factor;
  private static float right_height_factor;
  private static int level; 
  private static float x, y, x1, y01;
  private static float turtle_x, turtle_y, turtle_r, turtle_theta;

  Fractal()
  {
    setTitle("Fractal");
    setSize(w1W,w1H);
    setBackground(Color.white);
    setForeground(Color.black);
    addWindowListener(new WindowAdapter()
    {
      public void windowClosing(WindowEvent e)
      {
        System.exit(0);
      }
    });
    setVisible(true);
    this.addMouseListener (new mousePressHandler());
  }

  void drawbranch(Graphics g, float width, int x0, int y0, int x1, int y1)
  {
    if(width < 1.5f)
    {
      g.drawLine(x0, y0, x1, y1);
      return;
    }
    int h = (int)(width/2.0f+0.5f); // half width
    int x[] = new int[4];
    int y[] = new int[4];
    if(Math.abs(x1-x0)<Math.abs(y1-y0)) // more vertical
    {
      x[0] = x0 - h;
      x[1] = x0 + h;
      x[2] = x1 + h;
      x[3] = x1 - h;
      if(y0<y1)
      {
        y[0] = y0 - h;
        y[1] = y0 - h;
        y[2] = y1 + h;
        y[3] = y1 + h;
      }
      else
      {
        y[0] = y0 + h;
        y[1] = y0 + h;
        y[2] = y1 - h;
        y[3] = y1 - h;
      }
    }
    else
    {
      y[0] = y0 - h;
      y[1] = y0 + h;
      y[2] = y1 + h;
      y[3] = y1 - h;
      if(x0<x1)
      {
        x[0] = x0 - h;
        x[1] = x0 - h;
        x[2] = x1 + h;
        x[3] = x1 + h;
      }
      else
      {
        x[0] = x0 + h;
        x[1] = x0 + h;
        x[2] = x1 - h;
        x[3] = x1 - h;
      }
    }
    g.fillPolygon(x, y, 4);
  }
  
  void generate(Graphics g, float x, float y, float width, float height,
                     float angle, int level)
  {
    float x1, y01;

    turtle_x = x;
    turtle_y = y;
    turtle_r = height;
    step();
    x1 = turtle_x;
    y01 = turtle_y;
    level--;
    if(level<3)
    {
      g.setColor(Color.green);
      drawbranch(g, width, (int)x, w1H-(int)y, (int)x1, w1H-(int)y01);
    }
    else
    {
      g.setColor(Color.black);
      drawbranch(g, width, (int)x, w1H-(int)y, (int)x1, w1H-(int)y01);
    }
    if(level>0)
    {
        turtle_theta = point(x, y, x1, y01);
        turn(left_angle);
        generate(g, turtle_x, turtle_y, left_width_factor*width,
                 left_height_factor*height, left_angle, level);
        turtle_theta = point(x, y, x1, y01);
        turn(-right_angle);
        generate(g, x1, y01, left_width_factor*width,
                 left_height_factor*height, right_angle, level);

    }
    retardo();
  } // end generate

  void turn(float angle)
  {
    turtle_theta += angle;
  } // end turn

  float point(float x1, float y01, float x2, float y2)
  {
    float theta;

    if((x2-x1)==0.0)
    {
        if(y2>y01) theta=90.0f;
        else       theta=270.0f;
    }
    else
    {
        theta=(float)Math.atan((y2-y01)/(x2-x1))*57.295779f;
    }
    if(x1>x2) theta += 180.0f;
    return theta;
  } // end point

  void step()
  {
    turtle_x += turtle_r*Math.cos(turtle_theta*0.017453292);
    turtle_y += turtle_r*Math.sin(turtle_theta*0.017453292);
  } // end step


  class mousePressHandler extends MouseAdapter
  {
    public void mousePressed (MouseEvent e)
    {
      requestFocus();
      System.out.println("press"); // debug print
      repaint();
      System.exit(0);
    }
  } // end mousePressHandler

  public void paint(Graphics g)
  {
    level = level_in;
    left_width_factor   = (float)Math.pow(2.0, -1/left_alpha);
    right_width_factor  = (float)Math.pow(2.0, -1/right_alpha);
    left_height_factor  = (float)Math.pow(2.0, -2/(3*left_alpha));
    right_height_factor = (float)Math.pow(2.0, -2/(3*right_alpha));

    x = (float)w1W/2.0f;
    y = (float)w1H/10.0f;

    x1 = x;
    y01 = y+height;
    // at width
    drawbranch(g, width, (int)x, w1H-(int)y, (int)x1, w1H-(int)y01); // at line_width
   // retardo();
    turtle_r = height;
    turtle_theta = point(x, y, x1, y01);
    turtle_x = x;
    turtle_y = y;
    turn(left_angle);
    generate(g, x1, y01, left_width_factor*width,
             left_height_factor*height, left_angle, level);
    //retardo();
    turtle_theta = point(x, y, x1, y01);
    turn(-right_angle);
    generate(g, x1, y01, left_width_factor*width,
             left_height_factor*height, right_angle, level);
    //retardo();
    g.drawString("Fractal.java", 10, 10);
  } // end paint

public void retardo(){
  //	try{Thread.sleep(10);}catch(Exception err){}
  	}
  public static void main(String args[])
  {
    /* may read command line */
    System.out.println("Fractal.java tree from \n"+
                       "height"+height+
                       ", width="+width+
                       ", left_alpha="+left_alpha+
                       ", right_alpha="+right_alpha+
                       ",\n left_angle="+left_angle+
                       ", right_angle="+right_angle+
                       ", level_in="+level_in);
    new Fractal();
  }
  
  
} // end Frame.java

