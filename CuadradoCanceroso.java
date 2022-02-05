import java.awt.*;

public class CuadradoCanceroso extends Frame
{
	public Color aleatorio()
	{
		int r = (int)(Math.random()*256);
		int g = (int)(Math.random()*256);
		int b = (int)(Math.random()*256);
		
		return new Color(r,g,b);
	}	
	public void dibujarCuadrado(Graphics g, double radio, int x0, int y0)
	{
		if(radio < 10)return;
		double theta;
		int xf, yf, xi=0, yi=0;
		g.setColor(aleatorio());
		for (int angulo = 0; angulo <= 360; angulo = angulo + 90)
		{
			theta = Math.toRadians(angulo);
			xf = x0 + (int)(radio*Math.cos(theta));
			yf = y0 + (int)(radio*Math.sin(theta));
			
			if (angulo > 0)
				g.drawLine(xi, yi, xf, yf);
			xi = xf;
			yi = yf;
		}
		retardo();
		dibujarCuadrado(g, radio/2, (int)(x0+radio*3/4), (int)(y0-radio*3/4));
		dibujarCuadrado(g, radio/2, (int)(x0+radio*3/4), (int)(y0+radio*3/4));
		dibujarCuadrado(g, radio/2, (int)(x0-radio*3/4), (int)(y0+radio*3/4));
		dibujarCuadrado(g, radio/2, (int)(x0-radio*3/4), (int)(y0-radio*3/4));
	}
	public void retardo()
	{
		try{
			Thread.sleep(500);
		}catch(InterruptedException err){}
	}
	
	public void paint(Graphics g)
	{
		dibujarCuadrado(g, 150, 300, 300);
	}
	
	public static void main(String ar[])
	{
		CuadradoCanceroso x = new CuadradoCanceroso();
		x.setSize(800,600);
		x.show();
	}
}