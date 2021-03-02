package com.mycompany.gamebolinha;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;


public class Explosao {
	
	/***
         * Atributos 
         */
	private double x,y;
	private int r;
	private int raioMaximo;
	
	/****
         * Construtores
         */ 
	
	public Explosao(double x, double y, int r, int max){
		this.x = x;
		this.y = y;
		this.r = r;
		raioMaximo = max;
	}
	
	public boolean Refresh(){
		r += 2;
		if(r >= raioMaximo){
		return true;
		}
		return false;
	}
	
	public void desenha(Graphics2D g){
		g.setColor(new Color(255,255,255,128));
		g.setStroke(new BasicStroke(3));
		g.drawOval((int) (x - r), (int) (y - r), 2 * r, 2 * r);
		g.setStroke(new BasicStroke(1));
	}

}
