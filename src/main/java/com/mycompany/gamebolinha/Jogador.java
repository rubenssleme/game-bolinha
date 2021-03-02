package com.mycompany.gamebolinha;
import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Graphics2D;


public class Jogador {

	// Vari�veis
	private int x,y,dx,dy,r; // r � o mesmo que raio da circunfer�ncia.
	private int velocidade,vidas,score;
	private int nivelDoPoder,poder;
	private int [] poderNecessario = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
	private boolean cima,baixo,direita,morto,
				esquerda,atirando,recover;
	
	private long TimerDoTiro,DelayDoTiro,recoverTimer;
	private Color cor1,cor2;
	
	//Construtores
	
	public Jogador(){
		x = PainelDoJogo.LARGURA / 2;
		y = PainelDoJogo.ALTURA / 2;
		r = 5;
		
		dx = 0;
		dy = 0;
		velocidade = 5;
		vidas = 3;
		morto = false;
		
		cor1 = Color.WHITE;
		cor2 = Color.RED;
	
		recover = false;
		recoverTimer = 0;
		score = 0;
		atirando = false;
		TimerDoTiro = System.nanoTime();
		if(nivelDoPoder > 4)
			DelayDoTiro = 150;
		else if(nivelDoPoder > 8)
			DelayDoTiro = 100;
		else
			DelayDoTiro = 200;
	}
	
	//Fun��es
	
	//---------------------------------------------
	//Getters e Setters 
	
	public int getScore(){
		return score;
	}
	public boolean isMorto() {
		return morto;
	}
	public boolean isRecover() {
		return recover;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getR() {
		return r;
	}
	public int getVidas() {
		return vidas;
	}
	public int getNivelDoPoder() {
		return nivelDoPoder;
	}
	public int getPoder() {
		return poder;
	}
	public int getPoderNecessario() {
		return poderNecessario[nivelDoPoder];
	}
	public void setCima(boolean b) {
		cima = b;
	}
	public void setBaixo(boolean b) {
		baixo = b;
	}
	public void setDireita(boolean b) {
		direita = b;
	}
	public void setEsquerda(boolean b) {
		esquerda = b;
	}
	public void setAtira(boolean b){
		atirando = b;
	}
	public void setScore(int i){
		score += i;
	}
	public void setMorto(boolean b) {
		morto = b;
	}
	//Getters e Setters
	//----------------------------------------------
	
	public void ganhaVida(){
		vidas++;
	}
	
	public void perdaDeVida(){
		vidas--;
		recover = true;
		recoverTimer = System.nanoTime();
		if(vidas == 0)
			morto = true;
	}
	
	public void aumentaPoder(int i){
		poder += i;
		if (nivelDoPoder == 11){
			if (poder > poderNecessario[nivelDoPoder]){
				poder = poderNecessario[nivelDoPoder];
			}
			return;
		}
		if (poder >= poderNecessario[nivelDoPoder]){
			poder -= poderNecessario[nivelDoPoder];
			nivelDoPoder++;
		}
	}
	
	public void Refresh(){
		if(direita){
			dx = velocidade;
		}
		if (esquerda){
			dx = -velocidade;
		}
		if (cima){
			dy = -velocidade;
		}
		if (baixo){
			dy = velocidade;
		}
		
		x += dx;
		y += dy;
		
		if(x < r) x = r;
		if(y < r) y = r;
		if(x > PainelDoJogo.LARGURA - r)
			x = PainelDoJogo.LARGURA - r;
		if(y > PainelDoJogo.ALTURA - r)
			y = PainelDoJogo.ALTURA - r;
		
		dx = 0;
		dy = 0;
		
		// Tipos de Tiros
		if(atirando){
			long elapsed = (System.nanoTime() - TimerDoTiro) / 1000000;
			
			if(elapsed > DelayDoTiro){
				
				TimerDoTiro = System.nanoTime();
				
				switch (nivelDoPoder){
					case( 2 ):{
						PainelDoJogo.tiros.add(new Tiro(270, x + 5, y, 1));
						PainelDoJogo.tiros.add(new Tiro(270, x - 5, y, 1));
						break;
					}
					case( 3 ):{
						PainelDoJogo.tiros.add(new Tiro(275, x + 5, y, 1));
						PainelDoJogo.tiros.add(new Tiro(265, x - 5, y, 1));
						PainelDoJogo.tiros.add(new Tiro(270, x, y, 1));
						break;
					}
					case( 4 ):{
						PainelDoJogo.tiros.add(new Tiro(270, x, y, 2));
						break;
					}
					case( 5 ):{
						PainelDoJogo.tiros.add(new Tiro(270, x + 5, y, 2));
						PainelDoJogo.tiros.add(new Tiro(270, x - 5, y, 2));
						break;
					}
					case( 6 ):{
						PainelDoJogo.tiros.add(new Tiro(275, x + 5, y, 2));
						PainelDoJogo.tiros.add(new Tiro(265, x - 5, y, 2));
						PainelDoJogo.tiros.add(new Tiro(270, x, y, 2));
						break;
					}
					case( 7 ):{
						PainelDoJogo.tiros.add(new Tiro(270, x, y, 3));
						break;
					}
					case( 8 ):{
						PainelDoJogo.tiros.add(new Tiro(270, x, y, 4));
						break;
					}
					case( 9 ):{
						PainelDoJogo.tiros.add(new Tiro(90, x, y, 5)); //baixo
						PainelDoJogo.tiros.add(new Tiro(270, x, y, 4)); // cima
						break;
					}
					case( 10 ):{
						PainelDoJogo.tiros.add(new Tiro(90, x, y, 5)); // baixo
						PainelDoJogo.tiros.add(new Tiro(180, x, y, 6)); // esquerda
						PainelDoJogo.tiros.add(new Tiro(270, x, y, 4)); // cima
						PainelDoJogo.tiros.add(new Tiro(360, x, y, 7)); // direita
						break;
					}
					case(11):{
						PainelDoJogo.tiros.add(new Tiro(270, x, y, 8));
						PainelDoJogo.tiros.add(new Tiro(90, x, y, 8));
						PainelDoJogo.tiros.add(new Tiro(135, x, y, 8));
						PainelDoJogo.tiros.add(new Tiro(180, x, y, 8));
						PainelDoJogo.tiros.add(new Tiro(225, x, y, 8));
						PainelDoJogo.tiros.add(new Tiro(270, x, y, 8));
						PainelDoJogo.tiros.add(new Tiro(315, x, y, 8));
						PainelDoJogo.tiros.add(new Tiro(360, x, y, 8));
						PainelDoJogo.tiros.add(new Tiro(45, x, y, 8));
						break;
					}
					default:{
						PainelDoJogo.tiros.add(new Tiro(270, x, y, 1));
						break;
					}
				}	
			}
		}
		
		
		if(recover){
			long elapsed = (System.nanoTime() - recoverTimer) / 1000000;
			if (elapsed > 1000){
				recover = false;
				recoverTimer = 0;
			}
		}
	}

	public void Desenha(Graphics2D g){
		if(recover){
			g.setColor(cor2);
			g.fillOval(x - r, y - r, 2 * r, 2 * r );
			g.setStroke(new BasicStroke(3));
			g.setColor(cor2.darker());
			g.drawOval(x - r, y - r, 2 * r, 2 * r);
			g.setStroke(new BasicStroke(1));
		}else{
			g.setColor(cor1);
			g.fillOval(x - r, y - r, 2 * r, 2 * r); // x - raio e y - raio s�o as coordenadas da bolinha, enquanto 2 * raio
			g.setStroke(new BasicStroke(3));										// e 2 * raio s�o os di�metros horizontais e verticais da bolinha.
			// Grossura da bolinha
		
			g.setColor(cor1.darker()); // Borda da bolinha
			g.drawOval(x - r, y - r, 2 * r, 2 * r);
			g.setStroke(new BasicStroke(1));
		}
	}
}
