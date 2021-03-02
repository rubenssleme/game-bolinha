package com.mycompany.gamebolinha;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;


public class Texto {
	// Vari�veis
	private double x,y;
	private long tempo;
	private String s;
	private long start;
	
	//Construtores
	public Texto(double x, double y, long tempo, String s){
		this.x = x;
		this.y = y;
		this.tempo = tempo;
		this.s = s;
		start = System.nanoTime();
	}
	public boolean Refresh(){
		long elapsed = (System.nanoTime() - start) / 1000000;
		if (elapsed > tempo){
			return true;
		}
		return false;
	}
	
	public void desenha(Graphics2D g){
		g.setFont(new Font("Century Gothic",Font.PLAIN,12));
		
		long elapsed = (System.nanoTime() - start) / 1000000;
		int alpha = (int) (255 * Math.sin(3.14 * elapsed / tempo));
		if (alpha > 255)
			alpha = 255;
		g.setColor(new Color(255,255,255, alpha));
		int duracao = (int) g.getFontMetrics().getStringBounds(s,g).getWidth();
		g.drawString(s,(int) (x - (duracao / 2)), (int) y);
		
	}
}
