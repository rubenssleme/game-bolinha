package com.mycompany.gamebolinha;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;


public class Inimigo {
	// Vari�veis
	
	private double x,y,dx,dy;
	private double angulo, rad,velocidade;
	private int r;
	private int vida,tipo,rank;
	private Color cor1;
	private boolean pronto,morto,acertado,lento;
	private long timerDeAcerto;
	
	// Contrutores
	
	public Inimigo(int tipo, int rank){
		this.tipo = tipo;
		this.rank = rank;
		
		// Inimigo Padr�o
		if(tipo == 1){
			//cor1 = Color.CYAN;
			cor1 = (new Color(0,0,255,125)); // O terceiro campo, depois do campo B (RGB), refere-se � transpar�ncia
			switch(rank){
			
				case (2):{
					velocidade = 3;
					r = 10;
					vida = 2;
					break;
				}
				case (3):{
					velocidade = 2;
					r = 20;
					vida = 3;
					break;
				}
				case (4):{
					velocidade = 1;
					r = 40;
					vida = 4;
					break;
				}
				case (5):{
					velocidade = 1;
					r = 50;
					vida = 7;
					break;
				}
				case(6):{
					velocidade = 1 ;
					r = 70;
					vida = 500;
					break;
				}
				case(7):{
					velocidade = 0.90;
					r = 400;
					vida = 1000;
					break;
				}
				case(8):{
					velocidade = 0.9;
					r = 700;
					vida = 4000;
					break;
				}
				default:{
					velocidade = 3;
					r = 5;
					vida = 1;
					break;
				}
			}
		}
		
		//Inimigo padr�o mais forte e r�pido
		if(tipo == 2 ){
			//cor1 = Color.BLUE;
			cor1 = (new Color(0,255,0,125));
			switch(rank){
				case (2):{
					velocidade = 4;
					r = 10;
					vida = 4;
					break;
				}
				case(3):{
					velocidade = 3;
					r = 20;
					vida = 5;
					break;
				}
				case(4):{
					velocidade = 2;
					r = 30;
					vida = 6;
					break;
				}
				default:{
					velocidade = 4;
					r = 5;
					vida = 3;
				}
			}
		}
		
		//Inimigo padr�o Lento, por�m resistente
		if(tipo == 3){
			//cor1 = Color.GRAY;
			cor1 = (new Color(255,0,0,125));
			switch(rank){
			
				case(2):{
					velocidade = 1.5;
					r = 15;
					vida = 5;
					break;
				}
				case(3):{
					velocidade = 1;
					r = 25;
					vida = 7;
					break;
				}
				case(4):{
					velocidade = 0.75;
					r = 55;
					vida = 10;
					break;
				}
				default:{
					velocidade = 1.5;
					r = 5;
					vida = 4;
					break;
				}
			}
		}
		// Inimigo R�pido, por�m fr�gil
		if ( tipo == 4){
			cor1 = (new Color(255,255,0, 128));
			switch(rank){
				
				case(2):{
					velocidade = 7;
					r = 5;
					vida = 1;
					break;
				}
				case(3):{
					velocidade = 8;
					r = 10;
					vida = 2;
					break;
				}
				default:{
					velocidade = 7;
					r = 2;
					vida = 1;
				}
			}
		}
		x = Math.random() * PainelDoJogo.LARGURA / 2 + PainelDoJogo.LARGURA / 4; // Math.random() ser� a fun��o usada
		y = -r;																// para que a coordenada seja aleat�ria
		
		double angulo = Math.random() * 140 + 20;
		rad = Math.toRadians(angulo);
		
		dx = Math.cos(rad) * velocidade;
		dy = Math.sin(rad) * velocidade;
		
		pronto = false;
		morto = false;
		
		acertado = false;
		timerDeAcerto = 0;
	
	}

	//Fun��es
	//-----------------------------------
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

	public void setVelocidade(double velocidade) {
		this.velocidade = velocidade;
	}
	
	public void setLento(boolean b){
		lento = b;
	}

	public int getRank() {
		return rank;
	}
	public boolean estaMorto(){
		return morto;
	}
	// Getters e Setters
	//--------------------------------------
	
	public void acerto(){
		vida--;
		if(vida <= 0){
			morto = true;
		}
		acertado = true;
		timerDeAcerto = System.nanoTime();
	}
	
	public void explode(){
	
		if(rank > 1){
			
			int quantidade = 0;
			if(tipo == 1)
				quantidade = 3;
			if(tipo == 2)
				quantidade = 4;
			if(tipo == 3)
				quantidade = 5;
			if(tipo == 4)
				quantidade = 5;
			for (int i = 0; i < quantidade; i++){
				Inimigo in = new Inimigo(getTipo(), getRank() - 1);
				in.x = this.x;
				in.y = this.y;
				double angulo = 0;
				if(!pronto){
					angulo = Math.random() * 140 + 20;
				}
				else {
					angulo = Math.random() * 360;
				}
				in.rad = Math.toRadians(angulo);
				
				PainelDoJogo.inimigos.add(in);
			}
		}
	}
	
	
	public void refresh(){
		
		if(lento){
			x += dx * 0.3;
			y += dy * 0.3;
		}else{
		x += dx;
		y += dy;
		}
		if (!pronto){
			if(x > r && x < PainelDoJogo.ALTURA && y < r && y > PainelDoJogo.ALTURA){
				pronto = true;
			}
		}
		
		if( x < r && dx < 0)
			dx = -dx;
		if (y < r && dy < 0)
			dy = -dy;
		if (x > PainelDoJogo.LARGURA - r && dx > 0)
			dx = -dx;
		if (y > PainelDoJogo.ALTURA - r && dy > 0 )
			dy = -dy;
		
		if(acertado){
			long elapsed = (System.nanoTime() - timerDeAcerto) / 1000000;
			if(elapsed > 50){
				acertado = false;
				timerDeAcerto = 0;
			}
		}
	}
	
	public void desenha(Graphics2D g){
		
		if(acertado){
			g.setColor(Color.WHITE);
			g.fillOval((int) (x - r), (int) (y - r), 2 * r, 2 * r);
			g.setStroke(new BasicStroke(3)); // Grossura da bolinha
			
			g.setColor(Color.WHITE.darker()); //Borda da bolinha
			g.drawOval((int)(x - r), (int)(y - r), 2 * r, 2 * r);
			g.setStroke(new BasicStroke(1));
		}
		else{
			g.setColor(cor1);
			g.fillOval((int) (x - r), (int) (y - r), 2 * r, 2 * r);
			g.setStroke(new BasicStroke(3)); // Grossura da bolinha
		
			g.setColor(cor1.darker()); //Borda da bolinha
			g.drawOval((int)(x - r), (int)(y - r), 2 * r, 2 * r);
			g.setStroke(new BasicStroke(1));
		}
	}
	
	

}
