package com.mycompany.gamebolinha;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;


public class PowerUp {
	//Vari�veis
	
	private double x,y;
	private int r,tipo;
	private Color cor1;
	
	//tipos
	// 1 -- 1+ vida
	// 2 -- 1+ poder
	// 3 -- 2+ poder
	// 4 -- Bullet Time
	
	//Contrutores
	
	public PowerUp(int tipo, double x, double y){
		this.tipo = tipo;
		this.y = y;
		this.x = x;
		
		switch(tipo){
			case(1):{
				cor1 = Color.GREEN;
				r = 3;
				break;
			}
			case(2):{
				cor1 = Color.YELLOW;
				r = 3;
				break;
			}
			case(3):{
				cor1 = Color.ORANGE;
				r = 5;
				break;
			}
			case(4):{
				cor1 = Color.GRAY;
				r = 3;
				break;
			}
		}
	}
	
	//Fun��es
	
	//----------------------------------
	//Getters e Setters
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
	public int getR() {
		return r;
	}
	public int getTipo() {
		return tipo;
	}
	//Getters e Setters
	//----------------------------------
	
	public boolean Refresh(){
		y += 2;
		if( y > PainelDoJogo.ALTURA + r) {
			return true;
		}
		return false;
	}
	
	public void desenha(Graphics2D g){
		g.setColor(cor1);
		g.fillRect((int) (x - r), (int) (y - r), 2 * r, 2 * r);
		g.setStroke(new BasicStroke(3));
		
		g.setColor(cor1.darker());
		g.drawRect((int) (x - r), (int) (y - r), 2 * r, 2 * r);
		g.setStroke(new BasicStroke(1));
	}
}
