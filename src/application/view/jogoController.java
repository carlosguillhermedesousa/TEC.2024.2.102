package application.view;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class jogoController {

    @FXML
    private Canvas canva;


    private double bolaX = 200;
    private final double bolaY = 500;
    //private final double raio = 20;
    private final double raio = 16;
    private final double larguraTela = 360;
    private final double alturaTela = 600;
    private int pontuacao = 0;

    private ArrayList<Obstaculo> obstaculos = new ArrayList<>();
    private ArrayList<Rua> rua = new ArrayList<>();
    private Random random = new Random();
    private boolean esquerda, direita;
    
    private Image imagemBola;
    private Image imagemObstaculo;
    
    private MediaPlayer mediaPlayer;
    
    private boolean turbo = false;
    private int nivel = 1;
    
    

    @FXML
    public void initialize() {
        
    	// Carregar imagens com caminho absoluto e nome correto
        InputStream bolaStream = getClass().getResourceAsStream("f1_azul.png");
        InputStream obstaculoStream = getClass().getResourceAsStream("f1_vermelho.png");

        if (bolaStream == null || obstaculoStream == null) {
            System.out.println("Erro ao carregar imagens. Verifique os caminhos e nomes dos arquivos.");
            return; // Evita continuar se as imagens não forem encontradas
        }

        imagemBola = new Image(bolaStream);
        imagemObstaculo = new Image(obstaculoStream);
    	
        //musica
       /* String caminho = getClass().getResource("TopGear.mp3").toExternalForm();
        Media media = new Media(caminho);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // repetir indefinidamente
        mediaPlayer.play();*/
        String caminho = getClass().getResource("TopGear.mp3").toExternalForm();
        Media media = new Media(caminho);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        
    	canva.setFocusTraversable(true);
    	canva.requestFocus();
    	
    	//Canvas canvas = new Canvas(larguraTela, alturaTela);
        GraphicsContext gc = canva.getGraphicsContext2D();

        //Scene scene = new Scene(new javafx.scene.Group(canva));
        canva.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) esquerda = true;
            if (e.getCode() == KeyCode.RIGHT) direita = true;
            if (e.getCode() == KeyCode.SPACE) turbo = true; // ativa turbo
        });
        canva.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.LEFT) esquerda = false;
            if (e.getCode() == KeyCode.RIGHT) direita = false;
            if (e.getCode() == KeyCode.SPACE) turbo = false; // ativa turbo
        });
        
     // Inicializar a rua com faixas verticais
        for (int i = 0; i < alturaTela; i += 80) {
            rua.add(new Rua((larguraTela - 40) / 2, i));
        }

        AnimationTimer timer = new AnimationTimer() {
            long lastSpawn = 0;
            long lastSpawnNivel = 0;

            @Override
            public void handle(long now) {
                atualizar();
                desenhar(gc);
                long velocidade=1_000_000_000/nivel;
                long intervaloSpawn = turbo ? velocidade/2:velocidade;
                //long intervaloSpawn = turbo ? 500_000_000:1_000_000_000;
                //long intervaloSpawn = turbo ? 500_000_000 : 1_000_000_000; // 0.5s no turbo

                if (now - lastSpawn > intervaloSpawn) {
                // Gerar obstáculos a cada 1 segundo
               // if (now - lastSpawn > 1_000_000_000) {
                    obstaculos.add(new Obstaculo(random.nextInt((int) larguraTela - 40), -40));
                    rua.add(new Rua((larguraTela - 40) / 2, -40));
                    lastSpawn = now;
                }
                
                if (now - lastSpawnNivel > (intervaloSpawn*45)) {
                    // aumenta o nivel a cada 45 segundo
                	if (nivel>2) {
                    	nivel=1;
                    }
                	
                if (nivel<=2) {
                	nivel=nivel+1;
                }
                
                        lastSpawnNivel = now;
                    }
            }
        };

        timer.start();

        //stage.setTitle("Jogo: Desvie dos Obstáculos");
        //stage.setScene(scene);
        //stage.show();
    }
    
    private void atualizar() {
        if (esquerda && bolaX - raio > 0) bolaX -= 5;
        if (direita && bolaX + raio < larguraTela) bolaX += 5;
        
       // double velocidadeRua = turbo ? 6 : 3;
        //double velocidadeObstaculo = turbo ? 8 : 4;
        
        double velocidadeRua = 3+(nivel-1);
        double velocidadeObstaculo =  4+(nivel-1);
        
        if (turbo){
        	velocidadeRua=velocidadeRua*2;
        	velocidadeObstaculo=velocidadeObstaculo*2;
        } else {
        	
        }
        
        Iterator<Rua> r = rua.iterator();
        while (r.hasNext()) {
        	Rua obs = r.next();
            //obs.y += 3;
        	obs.y += velocidadeRua;
            
           /* if (obs.y > alturaTela) {
                r.remove();
                rua.add(new Rua((larguraTela - 40) / 2, -60));
            }*/
        }
        
        Iterator<Obstaculo> it = obstaculos.iterator();
        while (it.hasNext()) {
            Obstaculo obs = it.next();
            //obs.y += 4;
            obs.y += velocidadeObstaculo;

            // Verificação de colisão precisa
            double centroBolaX = bolaX+ raio;
            double centroBolaY = bolaY+ raio;

            double obsTopo = obs.y;
            double obsBase = obs.y + obs.altura;
            double obsEsquerda = obs.x;
            double obsDireita = obs.x + obs.largura;

            boolean colidiu = centroBolaX >= obsEsquerda &&
                              centroBolaX <= obsDireita &&
                              centroBolaY >= obsTopo &&
                              centroBolaY <= obsBase;
            /*
            double bolaTopo = bolaY - raio;
            double bolaBase = bolaY + raio;
            double bolaEsquerda = bolaX - raio;
            double bolaDireita = bolaX + raio;

            double obsTopo = obs.y;
            double obsBase = obs.y + obs.altura;
            double obsEsquerda = obs.x;
            double obsDireita = obs.x + obs.largura;

            boolean colidiu = bolaBase >= obsTopo &&
                              bolaTopo <= obsBase &&
                              bolaDireita >= obsEsquerda &&
                              bolaEsquerda <= obsDireita;
*/
            if (colidiu) {
                pontuacao = 0;
                it.remove();
            } else if (obs.y > alturaTela) {
                pontuacao++;
                it.remove();
            }
        }
    }
/*
    private void atualizar() {
        if (esquerda && bolaX - raio > 0) bolaX -= 5;
        if (direita && bolaX + raio < larguraTela) bolaX += 5;

        Iterator<Obstaculo> it = obstaculos.iterator();
        while (it.hasNext()) {
            Obstaculo obs = it.next();
            obs.y += 4;
            
            double margem = 5;
            // Colisão
            if (obs.y + obs.altura >= bolaY - raio &&
                obs.x < bolaX + raio - margem &&
                obs.x + obs.largura > bolaX - raio + margem) {
                pontuacao = 0; // Resetar pontuação
                it.remove();   // Remover obstáculo
            } else if (obs.y > alturaTela) {
                pontuacao++;
                it.remove();
            }
        }
    }
    */
    private void desenhar(GraphicsContext gc) {
        gc.setFill(Color.GRAY);//LIGHTSKYBLUE
        gc.fillRect(0, 0, larguraTela, alturaTela);

      //rua
        gc.setFill(Color.WHITE);// DARKGRAY
        for (Rua obs : rua) {
        	 gc.fillRect(obs.x, obs.y, 20, 60);
        }
        
        //gc.setFill(Color.RED);
        //	gc.fillOval(bolaX - raio, bolaY - raio, raio * 2, raio * 2);
        gc.drawImage(imagemBola, bolaX - raio, bolaY - raio, raio * 4, raio * 4);
        
        /*gc.setFill(Color.DARKGRAY);
        for (Obstaculo obs : obstaculos) {
            gc.fillRect(obs.x, obs.y, obs.largura, obs.altura);
        }*/
        for (Obstaculo obs : obstaculos) {
            gc.drawImage(imagemObstaculo, obs.x, obs.y, obs.largura, obs.altura);
        }
        
        

        gc.setFill(Color.BLACK);
        gc.setFont(javafx.scene.text.Font.font(18)); // 👈 Adicione esta linha
        gc.fillText("Pontuação: " + pontuacao, 10, 20);
    }
    
/*
    private void desenhar(GraphicsContext gc) {
        gc.setFill(Color.LIGHTSKYBLUE);
        gc.fillRect(0, 0, larguraTela, alturaTela);

        gc.setFill(Color.RED);
        gc.fillOval(bolaX - raio, bolaY - raio, raio * 2, raio * 2);

        gc.setFill(Color.DARKGRAY);
        for (Obstaculo obs : obstaculos) {
            gc.fillRect(obs.x, obs.y, obs.largura, obs.altura);
        }

        gc.setFill(Color.BLACK);
        gc.fillText("Pontuação: " + pontuacao, 10, 20);
    }

    */

    // Classe interna para obstáculos
    class Obstaculo {
        double x, y;
        //final double largura = 40;
       // final double altura = 20;
        
        final double largura = 70;
        final double altura = 60;

        Obstaculo(double x, double y) {
            this.x = x;
            this.y = y;
        }
        
        
    }
    class Rua {
        double x, y;
        //final double largura = 40;
       // final double altura = 20;
        
        final double largura = 70;
        final double altura = 60;

        Rua(double x, double y) {
            this.x = x;
            this.y = y;
        }
}

}