package com.mycompany.gamebolinha;
import javax.swing.JFrame;


public class Jogo extends JFrame {
	
	public static void main(String[] args) {
		JFrame janela = new JFrame("SuperBol");
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setContentPane(new PainelDoJogo());
		janela.pack();
		janela.setVisible(true);
		janela.setLocationRelativeTo(null);
		
	}

}
