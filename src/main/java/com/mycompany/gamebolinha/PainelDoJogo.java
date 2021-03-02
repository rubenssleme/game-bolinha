package com.mycompany.gamebolinha;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.*;
import java.text.DecimalFormat;
import java.util.*;

import javax.swing.JPanel;

public class PainelDoJogo extends JPanel implements Runnable, KeyListener{

	//Campos de variaveis
	public static int LARGURA = 500, ALTURA = 500; // static porque essa variavel podera ser 
	private Thread thread;						  // usada em todas as classes.
	private boolean Rodando,hordaInicio;
	private BufferedImage imagem;
	private Graphics2D g;
	public static Jogador jogador;
	public static ArrayList<Tiro> tiros;
	public static ArrayList<Inimigo> inimigos;
	public static ArrayList<PowerUp> powerUps;
	public static ArrayList<Explosao> explosoes;
	public static ArrayList<Texto> textos;
	private int FPS = 30,hordaNumero,hordaDelay = 2000; // 2000 milissegundos = 2 segundos.
	private double averageFPS;
	private long hordaTimerInicio,hordaDifTimerInicio;
	private long bulletTimeTimer, bulletTimeTimerDif;
	private int bulletTimeDuracao = 7000;
	private boolean Pwr2,Pwr3,Pwr4,Pwr5,Pwr6,Pwr7,Pwr8,Pwr9,Pwr10,FinalPwr;
	
	//Construtores
	
	public PainelDoJogo(){
		super();
		setPreferredSize(new Dimension(LARGURA, ALTURA));
		setFocusable(true); //faz com que seja possavel o foco da tela
		requestFocus();		// busca o foco
	}
	// Funaaes
	public void addNotify(){
		super.addNotify();
		if (thread == null){
			thread = new Thread(this);
			thread.start();
			addKeyListener(this);		
		}
	}
	
	public void run(){
		
		 Pwr2 = true;
		 Pwr3 = true;
		 Pwr4 = true;
		 Pwr5 = true;
		 Pwr6 = true;
		 Pwr7 = true;
		 Pwr8 = true;
		 Pwr9 = true;
		 Pwr10 = true;
		 FinalPwr = true;
		 
		 Rodando = true;
		 imagem = new BufferedImage(LARGURA, ALTURA, BufferedImage.TYPE_INT_RGB);
		 g = (Graphics2D) imagem.getGraphics();
		 
		 g.setRenderingHint(
				 RenderingHints.KEY_ANTIALIASING,  // Anti-Analise da tela, jogador, inimigos, etc.
				 RenderingHints.VALUE_ANTIALIAS_ON	);
		 g.setRenderingHint(
				 RenderingHints.KEY_TEXT_ANTIALIASING, // Anti-Analise do texto
				 RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		 
		 jogador = new Jogador();
		 tiros = new ArrayList<Tiro>();
		 inimigos = new ArrayList<Inimigo>();
		 powerUps = new ArrayList<PowerUp>();
		 explosoes = new ArrayList<Explosao>();
		 textos = new ArrayList<Texto>();
		 
		 hordaTimerInicio = 0;
		 hordaDifTimerInicio = 0;
		 hordaNumero = 0;
		 hordaInicio = true;
		 
		 long startTime;
		 long URDTimeMillis;
		 long waitTime;
		 long totalTime = 0;
		 int frameCount = 0;
		 int maxFrameCount = 30;
		 long targetTime = 1000 / FPS; // 1000 porque  sao 1000 milissegundos, o que resulta em 1 segundo.
		 								//O resultado dara cerca de 33 milissegundos, ou seja,
		 								// ele rodara o loop em cada 33 milissegundos
		 
		 //Loop do jogo	 
		 while (Rodando){
			 
			 startTime = System.nanoTime(); // Funaao do java que pega o tipo de tempo em nanosegundos
			 
			 gameRefresh();
			 gameRender();
			 gameDraw();
			 
			 URDTimeMillis = (System.nanoTime() - startTime) / 1000000; // Sempre dividido por 1 milhao, pois queremos os milissegundos.
			 waitTime = targetTime - URDTimeMillis; // waitTime a assim pois precisa-se de um 
			 										//tempo de espera entre esses 33 milissegundos, 
			 										//o gameRender fara seu trabalho em 20 millis aprox, 
			 										//portanto, a necessario o restante do tempo como espera
			 try{
				 Thread.sleep(waitTime);
			 }
			 catch (Exception e){
			 }
			 totalTime += System.nanoTime() - startTime;
			 frameCount ++;
			 if (frameCount == maxFrameCount){
				 averageFPS = 1000.0 / ((totalTime / frameCount) / 1000000); // Sempre dividido por 1 milhao, pois queremos os milissegundos.
				 frameCount = 0;
				 totalTime = 0;
			 }
		 }
		 // Mensagem Game Over
		 g.setColor(new Color(0,100,255));
		 g.fillRect(0, 0, LARGURA, ALTURA);
		 g.setColor(Color.WHITE);
		 g.setFont(new Font("Comic Sans", Font.PLAIN,16));
		 String go ="G A M E  O V E R";
		 String s = "Score Total: "+ jogador.getScore();
		 String h = "Última Horda: "+hordaNumero;
		 int comprimento = (int) g.getFontMetrics().getStringBounds(go,g).getWidth();
		 g.drawString(go, (LARGURA-comprimento)/2, ALTURA / 2);
		 comprimento = (int) g.getFontMetrics().getStringBounds(s,g).getWidth();
		 g.drawString(s, (LARGURA-comprimento)/2, ALTURA / 2 + 30);
		 comprimento = (int) g.getFontMetrics().getStringBounds(h,g).getWidth();
		 g.drawString(h, (LARGURA-comprimento)/2, ALTURA / 2 + 60);
		 gameDraw();
	}
	
	private void gameRefresh(){ //Funaao que sera responsavel pelos refreshs e sua lagica
		
		// Nova Horda
		if(hordaTimerInicio == 0 && inimigos.size() == 0){
			hordaNumero++;
			hordaInicio = false;
			hordaTimerInicio = System.nanoTime();
		}else{
			hordaDifTimerInicio = (System.nanoTime() - hordaTimerInicio) / 1000000; // Sempre dividido por 1 milhao, pois queremos os milissegundos.
			if(hordaDifTimerInicio > hordaDelay){
				hordaInicio = true;
				hordaTimerInicio = 0;
				hordaDifTimerInicio = 0;	
			}
		}
		
		//criar inimigos
		if(hordaInicio && inimigos.size() == 0){
			criarNovosInimigos();
		}
	
		//Refresh do jogador
		jogador.Refresh();
		
		//Refresh dos tiros
		for (int i = 0;i < tiros.size();i++){
			boolean remove = tiros.get(i).refresh();
			if (remove){
				tiros.remove(i);
				i--;
			}
		}
		//Refresh dos inimigos
		for (int i = 0; i < inimigos.size(); i++){
			inimigos.get(i).refresh();
		}
		
		//Refresh dos PowerUps
		for (int i = 0; i < powerUps.size(); i++){
			boolean remove = powerUps.get(i).Refresh();
			if (remove){
				powerUps.remove(i);
				i--;
			}
		}
		
		//Refresh da explosao
		for (int i = 0; i < explosoes.size(); i++){
			boolean remove = explosoes.get(i).Refresh();
			if(remove){
				explosoes.remove(i);
				i--;
			}
		}
		
		//Refresh dos textos
		for(int i = 0; i < textos.size(); i++){
			boolean remove = textos.get(i).Refresh();
			if(remove){
				textos.remove(i);
				i--;
			}
		}
		
		// Colisao entre tiros e inimigos
		for (int i = 0; i < tiros.size(); i++){
			Tiro t = tiros.get(i);
			double tx = t.getX();
			double ty = t.getY();
			double tr = t.getR();
			
			for (int j = 0; j < inimigos.size(); j++){
				Inimigo in = inimigos.get(j);
				double inx = in.getX();
				double iny = in.getY();
				double inr = in.getR();
				
				double dx = tx - inx;
				double dy = ty - iny;
				double dist = Math.sqrt(dx * dx + dy * dy);
				
				if (dist < tr + inr){
					in.acerto();
					t.perfuracao();
					int res = t.getResistencia();
					if (res == 0){
					tiros.remove(i);
					i--;
					break;
					}
				}
			}
		}
		
		// checagem de inimigos mortos
		for( int i = 0; i < inimigos.size(); i++){
			if(inimigos.get(i).estaMorto()){
				
				Inimigo in = inimigos.get(i);
				
				//chance de drop PowerUp
				double rand = Math.random(); // Funaao matematica que gera um namero aleatario
				if (rand < 0.005)
					powerUps.add(new PowerUp(1, in.getX(), in.getY()));
				else if (rand < 0.010)
					powerUps.add(new PowerUp(3, in.getX(), in.getY()));
				else if (rand < 0.030)
					powerUps.add(new PowerUp(4, in.getX(), in.getY()));
				else if (rand < 0.200)
					powerUps.add(new PowerUp(2, in.getX(), in.getY()));
				else
					powerUps.add(new PowerUp(3, in.getX(), in.getY()));
				//-----------------------
				
				jogador.setScore(in.getTipo() + in.getRank());
				inimigos.remove(i);
				i--;
				
				in.explode();
				explosoes.add(new Explosao(in.getX(),in.getY(),in.getR(),in.getR() + 30));
			}
		}
		
		// colisao inimigo-jogador
		if(!jogador.isRecover()){
			int jx = jogador.getX();
			int jy = jogador.getY();
			int jr = jogador.getR();
			for (int i = 0; i < inimigos.size(); i++){
				Inimigo in = inimigos.get(i);
				double inx = in.getX();
				double iny = in.getY();
				double inr = in.getR();
				double dx = jx - inx;
				double dy = jy - iny;
				double dist = Math.sqrt(dx * dx + dy * dy);
				
				if (dist < jr + inr)
					jogador.perdaDeVida();
			}
		}
		
		// Colisao Jogador-PowerUp
		int jX = jogador.getX();
		int jY = jogador.getY();
		int jR = jogador.getR();
		for (int i = 0; i < powerUps.size(); i++){
			PowerUp p = powerUps.get(i);
			double x = p.getX();
			double y = p.getY();
			double r = p.getR();
			double dx = jX - x;
			double dy = jY - y;
			double dist = Math.sqrt((dx * dx) + (dy * dy));
			
			//Coletando PowerUps
			
			if ( dist < jR + r){
				int tipo = p.getTipo();
				
				if(tipo == 1){
					jogador.ganhaVida();
					textos.add(new Texto(jogador.getX(),jogador.getY(), 2000,"Vida +1!"));
				}
				if(tipo == 2)
					jogador.aumentaPoder(1);
				if(tipo == 3)
					jogador.aumentaPoder(2);
				if(tipo == 4){
					bulletTimeTimer = System.nanoTime();
					for (int j = 0; j < inimigos.size(); j++){
						inimigos.get(j).setLento(true);						
					}
					textos.add(new Texto(jogador.getX(),jogador.getY(), 2000,"Bullet Time!"));
				}
				int nvPower = jogador.getNivelDoPoder();
				if(nvPower == 2 && Pwr2 == true){
					textos.add(new Texto(jogador.getX(),jogador.getY(), 2000,"Double Shot!"));
					Pwr2 = false;
				}
				if(nvPower == 3 && Pwr3 == true){
					textos.add(new Texto(jogador.getX(),jogador.getY(), 2000,"Spread Shot!"));
					Pwr3 = false;
				}
				if(nvPower == 4 && Pwr4 == true){
					textos.add(new Texto(jogador.getX(),jogador.getY(), 2000,"Laser Shot!"));
					Pwr4 = false;
				}
				if(nvPower == 5 && Pwr5 == true){
					textos.add(new Texto(jogador.getX(),jogador.getY(), 2000,"Double Laser!"));
					Pwr5 = false;
				}
				if(nvPower == 6 && Pwr6 == true){
					textos.add(new Texto(jogador.getX(),jogador.getY(), 2000,"Spread Laser!"));
					Pwr6 = false;
				}
				if(nvPower == 7 && Pwr7 == true){
					textos.add(new Texto(jogador.getX(),jogador.getY(), 2000,"Wave Shot!"));
					Pwr7 = false;
				}
				if(nvPower == 8 && Pwr8 == true){
					textos.add(new Texto(jogador.getX(),jogador.getY(), 2000,"Mega Wave Shot!"));
					Pwr8 = false;
				}
				if(nvPower == 9 && Pwr9 == true){
					textos.add(new Texto(jogador.getX(),jogador.getY(), 2000,"Double Mega Wave Shot!"));
					Pwr9 = false;
				}
				if(nvPower == 10 && Pwr10 == true){
					textos.add(new Texto(jogador.getX(),jogador.getY(), 2000,"4 Directions Mega Wave Shot!"));
					Pwr10 = false;
				}
				if(nvPower == 11 && FinalPwr == true){
					textos.add(new Texto(jogador.getX(),jogador.getY(), 2000,"Final Shot!"));
					FinalPwr = false;
				}
				powerUps.remove(i);
				i--;
			}
			
		}
		//checar jogador morto
		if(jogador.isMorto()){
			Rodando = false;
		}
		
		// Refresh Bullet Time
		if(bulletTimeTimer != 0){
			bulletTimeTimerDif = (System.nanoTime() - bulletTimeTimer) / 1000000;
			if (bulletTimeTimerDif > bulletTimeDuracao){
				bulletTimeTimer = 0;
				for (int i = 0; i < inimigos.size(); i++){
					inimigos.get(i).setLento(false);						
				}
			}
		}
	}
	private void gameRender(){ // Funaao que sera responsavel pelo doublebuffering, ou seja, 
							   //pra-definir todos os sprites, renderiza-los e deixa-los prontos
		//render da tela
		g.setColor(new Color(110,115,250)); // (new Color(,,,)) a o parametro que se adiciona cores no padrao RGB
		g.fillRect(0, 0, LARGURA, ALTURA);
		g.setColor(Color.WHITE);
		
		//render efeito Bullet Time
		if(bulletTimeTimer != 0){
			g.setColor(new Color(255,255,255,64)); // o quarto elemento refere-se a transparancia
			g.fillRect(0, 0, LARGURA, ALTURA);
		}
		//Informaaaes de console
		DecimalFormat fps = new DecimalFormat ("0");
		g.setFont(new Font ("Lucida Console", Font.PLAIN, 13));
		g.drawString("FPS: " + fps.format(averageFPS), 230, 10);
		g.drawString("Na de tiros: "+ tiros.size(),230,45);
		g.drawString("Na de inimigos:"+ inimigos.size(), 230,35);
		g.drawString("Horda: "+hordaNumero,230,55);
		g.drawString("Coordenadas:"+jogador.getX()+","+jogador.getY(),230,20);
		g.drawString("| Score: "+jogador.getScore(), LARGURA - 110,10);
		
		//render do jogador
		jogador.Desenha(g);
		
		//render dos tiros
		for (int i = 0; i < tiros.size(); i++)
			tiros.get(i).desenha(g);
		
		//render do inimigo
		for (int i = 0;i < inimigos.size(); i++)
			inimigos.get(i).desenha(g);
		
		//render do PowerUps
		for (int i = 0; i < powerUps.size(); i++)
			powerUps.get(i).desenha(g);
		
		//render da explosao
		for (int i = 0; i < explosoes.size(); i++)
			explosoes.get(i).desenha(g);
		
		//render do texto
		for (int i = 0; i < textos.size(); i++)
			textos.get(i).desenha(g);
			
		//render nova horda
		if(hordaTimerInicio != 0){
			if (hordaNumero == 10){
				g.setFont(new Font("Lucida Console", Font.PLAIN, 25));
				String s = "- B O S S  - ";
				int comprimento = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
				int alpha = (int) (255 * Math.sin(3.14 * hordaDifTimerInicio / hordaDelay));
				if (alpha > 255)
					alpha = 255;
				g.setColor(new Color(255,255,255, alpha));
				g.drawString(s, (LARGURA - comprimento) / 2, ALTURA / 2 );
			}
			else if (hordaNumero == 11){
				g.setFont(new Font("Lucida Console", Font.PLAIN, 20));
				String s = "- B O S S  \n D E R R O T A D O  - ";
				int comprimento = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
				int alpha = (int) (255 * Math.sin(3.14 * hordaDifTimerInicio / hordaDelay));
				if (alpha > 255)
					alpha = 255;
				g.setColor(new Color(255,255,255, alpha));
				g.drawString(s, (LARGURA - comprimento) / 2, ALTURA / 2 );
			}
			else{
			g.setFont(new Font("Lucida Console", Font.PLAIN, 25));
			String s = "- H O R D A  "+ hordaNumero + "  - ";
			int comprimento = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
			int alpha = (int) (255 * Math.sin(3.14 * hordaDifTimerInicio / hordaDelay));
			if (alpha > 255)
				alpha = 255;
			g.setColor(new Color(255,255,255, alpha));
			g.drawString(s, (LARGURA - comprimento) / 2, ALTURA / 2 );
			}	
		}
		
		//Barrinha do Bullet Time
		if(bulletTimeTimer != 0){
			g.setColor(Color.WHITE);
			g.drawRect(20, 60, 100, 8);
			g.fillRect(20,60,(int) (100 - 100 * bulletTimeTimerDif / bulletTimeDuracao), 8);
		}
		
		//render vidas
		for (int i = 0; i < jogador.getVidas(); i++){
			g.setColor(Color.GREEN);
			g.fillOval(20+(20*i), 20, jogador.getR()*2, jogador.getR()*2);
			g.setStroke(new BasicStroke(3));
			g.setColor(Color.GREEN.darker());
			g.drawOval(20+(20*i), 20, jogador.getR()*2, jogador.getR()*2);
			g.setStroke(new BasicStroke(1));
		}
		
		//render Barrinha de Poderes
		g.setColor(Color.YELLOW);
		g.fillRect(20, 40, jogador.getPoder() * 8,8);
		g.setColor(Color.YELLOW.darker());
		g.setStroke(new BasicStroke(2));
		for (int i = 0; i < jogador.getPoderNecessario(); i++){
			g.drawRect(20 + 8 * i, 40, 8, 8);
		}
		g.setStroke(new BasicStroke(1));
		
	}

	private void gameDraw(){ // Funaao que sera responsavel por desenhar na tela
							//aquilo que a render preparou
		Graphics g2 = this.getGraphics();
		g2.drawImage(imagem, 0, 0, null);
		g2.dispose();
	}
	
	public void criarNovosInimigos(){
		
		inimigos.clear();
		Inimigo in;
		
		switch(hordaNumero){
		
			case(1):{
				for(int i = 0; i < 4;i++){
					inimigos.add(new Inimigo(1,1));
				}	
				break;
			}
			case(2):{
				for(int i = 0; i < 10;i++){
					inimigos.add(new Inimigo(1,1));
				}
				inimigos.add(new Inimigo(1,2));
				inimigos.add(new Inimigo(1,2));
				break;
			}
			case(3):{
				for(int i = 0; i < 4;i++){
					inimigos.add(new Inimigo(2,1));	
				}
				inimigos.add(new Inimigo(2,2));
				inimigos.add(new Inimigo(2,2));
				break;
			}
			case(4):{
				for(int i = 0; i < 10;i++){
					inimigos.add(new Inimigo(2,1));	
				}
				inimigos.add(new Inimigo(1,4));
				inimigos.add(new Inimigo(1,2));
				break;
			}
			case(5):{
				inimigos.add(new Inimigo(1,4));
				inimigos.add(new Inimigo(2,3));
				inimigos.add(new Inimigo(3,2));
				break;
			}
			case(6):{
				for(int i = 0; i < 15;i++){
					inimigos.add(new Inimigo(4,1));	
				}
				break;
			}
			case(7):{
				for(int i = 0; i < 30;i++){
					inimigos.add(new Inimigo(4,1));	
				}
				inimigos.add(new Inimigo(4,2));
				inimigos.add(new Inimigo(4,2));
				break;
			}
			case(8):{
				inimigos.add(new Inimigo(4,1));
				inimigos.add(new Inimigo(4,2));
				inimigos.add(new Inimigo(4,3));
				inimigos.add(new Inimigo(4,3));
				inimigos.add(new Inimigo(4,3));
				inimigos.add(new Inimigo(4,3));
				inimigos.add(new Inimigo(4,3));
				inimigos.add(new Inimigo(4,3));
				break;
			}
			case(9):{
				for( int i = 0; i < 30; i++)
					inimigos.add(new Inimigo(1,1));
				inimigos.add(new Inimigo(1,2));
				inimigos.add(new Inimigo(1,3));
				inimigos.add(new Inimigo(1,4));
				inimigos.add(new Inimigo(1,5));
				inimigos.add(new Inimigo(1,6));
				break;
			}
			case(10):{
				inimigos.add(new Inimigo(1,8));
				break;
			}
			default:{
				Rodando = false;
				break;
			}
		}
	} 

	@Override
	public void keyPressed(KeyEvent tecla) {
		int comando = tecla.getKeyCode();
		if(comando == KeyEvent.VK_LEFT)
			jogador.setEsquerda(true);
		if(comando == KeyEvent.VK_RIGHT)
			jogador.setDireita(true);
		if(comando == KeyEvent.VK_UP)
			jogador.setCima(true);
		if(comando == KeyEvent.VK_DOWN)
			jogador.setBaixo(true);
		if(comando == KeyEvent.VK_Z)
			jogador.setAtira(true);
	}

	@Override
	public void keyReleased(KeyEvent tecla) {
		int comando = tecla.getKeyCode();
		if(comando == KeyEvent.VK_LEFT)
			jogador.setEsquerda(false);
		if(comando == KeyEvent.VK_RIGHT)
			jogador.setDireita(false);
		if(comando == KeyEvent.VK_UP)
			jogador.setCima(false);
		if(comando == KeyEvent.VK_DOWN)
			jogador.setBaixo(false);
		if (comando == KeyEvent.VK_Z)
			jogador.setAtira(false);
	}

	@Override
	public void keyTyped(KeyEvent tecla) {
		// TODO Auto-generated method stub
	}
	
	
}
