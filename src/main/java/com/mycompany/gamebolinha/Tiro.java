package com.mycompany.gamebolinha;
import java.awt.*;


public class Tiro {
	
	//Vari�veis
	private double x,y,dx,dy;
	private double rad,velocidade;
	private int r,resistencia,tipo = 1;
	private Color cor1;
	private boolean anula;
	
	// Construtores
	
	public Tiro(double angulo, int x, int y, int tipo){
		this.x = x;
		this.y = y;
		this.tipo = tipo;
		anula = false;
		
		switch (tipo){
			case(2):{
				cor1 = Color.GREEN;
				r = 2;
				resistencia = 2;
				velocidade = 15;
				rad = Math.toRadians(angulo);
				dx = Math.cos(rad) * velocidade;
				dy = Math.sin(rad) * velocidade;
				break;
			}
			case(3):{
				cor1 = Color.WHITE;
				r = 10;
				resistencia = 4;
				velocidade = 15;
				rad = Math.toRadians(angulo);
				dx = Math.cos(rad) * velocidade;
				dy = Math.sin(rad) * velocidade;
				break;
			}
			case(4):{
				cor1 = (new Color(156, 192, 245, 128));
				r = 15;
				resistencia = 9;
				velocidade = 15;
				rad = Math.toRadians(angulo);
				dx = Math.cos(rad) * velocidade;
				dy = Math.sin(rad) * velocidade;
				break;
			}
			// Outras Dire��es do tipo 4
			case(5):{
				cor1 = (new Color(156, 192, 245, 128));
				r = 15;
				resistencia = 9;
				velocidade = 15;
				rad = Math.toRadians(angulo);
				dx = Math.cos(rad) * velocidade;
				dy = Math.sin(rad) * velocidade;
				break;
			}
			case(6):{
				cor1 = (new Color(156, 192, 245, 128));
				r = 15;
				resistencia = 9;
				velocidade = 15;
				rad = Math.toRadians(angulo);
				dx = Math.cos(rad) * velocidade;
				dy = Math.sin(rad) * velocidade;
				break;
			}
			case(7):{
				cor1 = (new Color(156, 192, 245, 128));
				r = 15;
				resistencia = 9;
				velocidade = 15;
				rad = Math.toRadians(angulo);
				dx = Math.cos(rad) * velocidade;
				dy = Math.sin(rad) * velocidade;
				break;
			}
			case(8):{
				cor1 = (new Color(255, 127, 39, 128));
				r = 7;
				resistencia = 9;
				velocidade = 15;
				rad = Math.toRadians(angulo);
				dx = Math.cos(rad) * velocidade;
				dy = Math.sin(rad) * velocidade;
				break;
			}
			default:{
				cor1 = Color.ORANGE;
				r = 2;
				resistencia = 1;
				velocidade = 10;
				rad = Math.toRadians(angulo);
				dx = Math.cos(rad) * velocidade;
				dy = Math.sin(rad) * velocidade;
				break;
			}
		}
	}
	
	//Fun��es
	//--------------------------------------
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
	public int getResistencia() {
		return resistencia;
	}
	
	//---------------------------------------
	//Getters e Setters

	public void perfuracao(){
		if( tipo == 3 )
			resistencia -= 2;
		resistencia--;
	}

	public boolean refresh(){
		
		x += dx;
		y += dy;
		
		if(x < -r || x > PainelDoJogo.LARGURA + r || y < -r || y > PainelDoJogo.ALTURA + r){
			return true;	
		}
		return false;
	}

	public void desenha(Graphics2D g){
		
		switch(tipo){
			case(2):{
				g.setColor(cor1);
				g.fillRect((int) (x - r/2),(int) (y - r),r, 5 * r);
				break;
			}
			case(3):{
				g.setColor(cor1);
				g.drawArc((int) (x - r), (int) (y - r), 2 * r, 7, 360, 180);
				g.fillArc((int) (x - r), (int) (y - r), 2 * r, 7, 360, 180);
				break;
			}
			case(4):{ // cima
				g.setColor(cor1);
				g.drawArc((int) (x - r), (int) (y - r), 2 * r, 7, 360, 180);
				g.fillArc((int) (x - r), (int) (y - r), 2 * r, 7, 360, 180);
				break;
			}
		// Outras dire��es do tiro 4
		
			case(5):{ // baixo
				g.setColor(cor1);
				g.drawArc((int) (x - r), (int) (y + r), 2 * r, 7, 180, 180);
				g.fillArc((int) (x - r), (int) (y + r), 2 * r, 7, 180, 180);
				break;
			}
			case(6):{ // esquerda
				g.setColor(cor1);
				g.drawArc((int) (x - r * 2), (int) (y - r), 7, 2 * r, 90, 180);
				g.fillArc((int) (x - r * 2), (int) (y - r), 7, 2 * r, 90, 180);
				break;
			}
			case(7):{ // direita
				g.setColor(cor1);
				g.drawArc((int) (x + r), (int) (y - r), 7, 2 * r, 270, 180);
				g.fillArc((int) (x + r), (int) (y - r), 7, 2 * r, 270, 180);
				break;
			}
			case(8):{
				g.setColor(cor1);
				g.fillOval((int) (x - r),(int) (y - r),2 * r, 2 * r);
				break;
			}
			default:{
				g.setColor(cor1);
				g.fillOval((int) (x - r),(int) (y - r),2 * r, 2 * r);
			}
		}
	}
}
