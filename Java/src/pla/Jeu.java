package pla;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.MouseOverArea;

import pla.action.transition.*;
import pla.decor.*;
import pla.TimerFin;

import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import pla.ihm.Camera;

import pla.ihm.Case;

import pla.ihm.Map;
import pla.util.Musique;

public class Jeu extends BasicGameState{
	private Map map; // carte du jeu
	private List<Personnage> personnages = new ArrayList<Personnage>(); // Liste
																		// des
	public static final int ID = 1; // personnages

	private GameContainer gc; // conteneur


	Image ImageNoire;
	
	Music test2;
	
	private boolean MusicEnable = false; 
	Image play, play2, play3;
	

	private int SIZE_WINDOW_X ;
	private int SIZE_WINDOW_Y ;

	Musique musique;

	private Image inventaire_rouge,inventaire_rouge_eau,inventaire_rouge_bombe,inventaire_rouge_bike,inventaire_rouge_eau_bike,inventaire_rouge_bombe_bike;
	private Image inventaire_bleu,inventaire_bleu_eau,inventaire_bleu_bombe,inventaire_bleu_bike,inventaire_bleu_eau_bike,inventaire_bleu_bombe_bike;
	private Image score_rouge, score_bleu;
	private Image rougegagnant, bleugagnant,egaliter;
	private Image bouton_fin;
	
	int rouge_score, bleu_score;
	String rouge_score1, bleu_score1;
	
	MouseOverArea ms;
	
	//private static final int P_BAR_X = 15;
	//private static final int P_BAR_Y = 25;




	static Timer t;

	// private static final int PAUSE = 25; // temps de latence

	// private float zoom = 0.1f;

	/*
	 * private float z1 = 0.01f; private float z2 = 0.01f;
	 */

	Image timerI;
	
	public Jeu(int largeur, int hauteur) {
		SIZE_WINDOW_X = largeur;

		SIZE_WINDOW_Y = hauteur;

		personnages = new ArrayList<Personnage>();
	}

	public void ajouterPersonnage(Personnage p) {
		if (p != null) {
			this.personnages.add(p);
		} else {
			System.out.println("Le personnage que vous voulez ajouter dans la liste des personnages est vide");
		}
	}

	public void supprimerPersonnage(Personnage p) {
		if (p != null && this.personnages.contains(p)) {
			this.personnages.remove(p);
		} else {
			System.out.println("Le personnage que vous voulez supprimer n'est pas dans la liste ou est nul");
		}
	}

	// Initialise le contenu du jeu, charge les graphismes, la musique, etc..
	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {

		this.gc = gc;

		
		ImageNoire = new Image("res/ImageNoire.png");
		

		timerI = new Image("res/modif.png");

		ajouterPersonnage(new Personnage(TypePersonnage.BLEU, 2, 64, 64, new Automate()));
		ajouterPersonnage(new Personnage(TypePersonnage.ROUGE, 1, 64, 64, new Automate()));

		// Marche pas => Revoir sprite policier
		ajouterPersonnage(new Personnage(TypePersonnage.BERNARD, 3, 64, 64, new Automate()));

		map = new Map((int) SIZE_WINDOW_X, (int) SIZE_WINDOW_Y, personnages);

		this.map.init();
		Camera.initCamera(map, SIZE_WINDOW_X, SIZE_WINDOW_Y);
		for (Personnage p : personnages) {
			p.init();


		

		}
		
		if (MusicEnable) {
			test2 = new Music("res/thug.ogg");
			//test2.loop();

		}

		this.map.placerAutoRandom(personnages, gc.getGraphics());		
		this.map.setCasesEstDansAutomate(personnages);
		this.map.setNbCasesHorsAutomate();
		// System.out.println("nb Cases hors automate :
		// "+map.getNbCasesHorsAutomate());
		// System.out.println("Nombre de case total :
		// "+map.getNbCasesHauteur()*map.getNbCasesLargeur());
		this.map.placerDecorRandom();
		this.map.placerPersonnageRandom(personnages);

                
		play = new Image("res/menu/pause/reprendre.png");
		play2 = new Image("res/menu/pause/accueil.png");
		play3 = new Image("res/menu/pause/quitter.png");

 
		
		//Image inventaire
		
		this.inventaire_rouge = new Image("res/hud/rouge/rougevide.png");
		this.inventaire_rouge_eau = new Image("res/hud/rouge/rougevide_eau.png");
		this.inventaire_rouge_bombe = new Image("res/hud/rouge/rougevide_bombe.png");
		this.inventaire_rouge_bike = new Image("res/hud/rouge/rougevide_bike.png");
		this.inventaire_rouge_eau_bike = new Image("res/hud/rouge/rougevide_eau_bike.png");
		this.inventaire_rouge_bombe_bike = new Image("res/hud/rouge/rougevide_bombe_bike.png");
		this.inventaire_bleu  = new Image("res/hud/bleu/bleuvide.png");
		this.inventaire_bleu_eau   = new Image("res/hud/bleu/bleuvide_eau.png");
		this.inventaire_bleu_bombe = new Image("res/hud/bleu/bleuvide_bombe.png");
		this.inventaire_bleu_bike  = new Image("res/hud/bleu/bleuvide_bike.png");
		this.inventaire_bleu_eau_bike  = new Image("res/hud/bleu/bleuvide_eau_bike.png");
		this.inventaire_bleu_bombe_bike  = new Image("res/hud/bleu/bleuvide_bombe_bike.png");
		
		//Image score
		this.score_rouge=new Image("res/hud/score/scorerouge.png");
		this.score_bleu=new Image("res/hud/score/scorebleu.png");		
		
		//Image fin jeu
		this.rougegagnant  = new Image("res/rougegagnant.png");
		this.bleugagnant  = new Image("res/bleugagnant.png");
		this.egaliter  = new Image("res/egaliter.png");
		this.bouton_fin  = new Image("res/bouton_fin.png");

		//bouton fin cliquable
		// ms = new MouseOverArea(gc, bouton_fin, (gc.getWidth()/2)-80, (gc.getHeight()/2)+150, 245, 110);




	}

	// Affiche le contenu du jeu
	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		TimerFin.getFinJeu();
		
		if (!gc.isPaused()) {
			Camera.moveCamera(g);

			this.map.afficher();
			for (Personnage p : personnages) {
				p.afficher(g);
			}
			g.drawString(Mouse.getX() + " " +  Mouse.getY(), 10,30);
			
		} else {


			Camera.moveCamera(g);

			this.map.afficher();
			for (Personnage p : personnages) {
				p.afficher(g);
			}
			g.resetTransform();
			ImageNoire.setAlpha(0.7f);
			ImageNoire.draw(0,0,gc.getWidth(),gc.getHeight());   // Taille de la fenetre modifiée par Antoine
			play.draw(gc.getWidth()/2-175,12*gc.getHeight()/20-37);
			play2.draw(gc.getWidth()/2-175,15*gc.getHeight()/20-37);
			play3.draw(gc.getWidth()/2-175,18*gc.getHeight()/20-37);
			g.setColor(Color.white);
			g.drawString("PAUSE",gc.getWidth()/2-20,4*gc.getHeight()/10);
			g.drawString(Mouse.getX() + " " +  Mouse.getY(), 10,30);
		
		

		}

		TimerFin.afficherTimer( g, timerI);
		

		//inventaire
		
		if (gc.getInput().isKeyDown(Input.KEY_I)) {
			//rouge
			if (getPersonnageParType(TypePersonnage.ROUGE).getObjet()==null && !getPersonnageParType(TypePersonnage.ROUGE).hasVelo()){
				g.resetTransform(); 
				g.drawImage(this.inventaire_rouge, 15, 40);
			}
			if (getPersonnageParType(TypePersonnage.ROUGE).getObjet() instanceof BombePeinture && !getPersonnageParType(TypePersonnage.ROUGE).hasVelo()){
				g.resetTransform();  
				g.drawImage(this.inventaire_rouge_bombe, 15, 40);
			}
			if (getPersonnageParType(TypePersonnage.ROUGE).getObjet() instanceof BombeEau && !getPersonnageParType(TypePersonnage.ROUGE).hasVelo()){
				g.resetTransform();  
				g.drawImage(this.inventaire_rouge_eau, 15, 40);
			}
			if (getPersonnageParType(TypePersonnage.ROUGE).hasVelo()){
				g.resetTransform();  
				g.drawImage(this.inventaire_rouge_bike, 15, 40);
			}
			if (getPersonnageParType(TypePersonnage.ROUGE).getObjet() instanceof BombeEau && getPersonnageParType(TypePersonnage.ROUGE).hasVelo()){
				g.resetTransform();  
				g.drawImage(this.inventaire_rouge_eau_bike, 15, 40);
			}
			if (getPersonnageParType(TypePersonnage.ROUGE).getObjet() instanceof BombePeinture && getPersonnageParType(TypePersonnage.ROUGE).hasVelo()){
				g.resetTransform();  
				g.drawImage(this.inventaire_rouge_bombe_bike, 15, 40);
			}
			//bleu
			if (getPersonnageParType(TypePersonnage.BLEU).getObjet()==null && !getPersonnageParType(TypePersonnage.BLEU).hasVelo()){
				g.resetTransform();  
				g.drawImage(this.inventaire_bleu, 15, 105);
			}
			if (getPersonnageParType(TypePersonnage.BLEU).getObjet() instanceof BombePeinture && !getPersonnageParType(TypePersonnage.BLEU).hasVelo()){
				g.resetTransform();  
				g.drawImage(this.inventaire_bleu_bombe, 15, 105);
			}
			if (getPersonnageParType(TypePersonnage.BLEU).getObjet() instanceof BombeEau && !getPersonnageParType(TypePersonnage.BLEU).hasVelo()){
				g.resetTransform();  
				g.drawImage(this.inventaire_bleu_eau, 15, 105);
			}
			if (getPersonnageParType(TypePersonnage.BLEU).hasVelo()){
				g.resetTransform();  
				g.drawImage(this.inventaire_bleu_bike, 15, 105);
			}
			if (getPersonnageParType(TypePersonnage.BLEU).getObjet() instanceof BombeEau && getPersonnageParType(TypePersonnage.BLEU).hasVelo()){
				g.resetTransform();  
				g.drawImage(this.inventaire_bleu_eau_bike, 15, 105);
			}
			if (getPersonnageParType(TypePersonnage.BLEU).getObjet() instanceof BombePeinture && getPersonnageParType(TypePersonnage.BLEU).hasVelo()){
				g.resetTransform();  
				g.drawImage(this.inventaire_bleu_bombe_bike, 15, 105);
			}
		}
		
		//afficher score
		if (gc.getInput().isKeyDown(Input.KEY_TAB)) {
			g.resetTransform();
			g.drawImage(this.score_rouge, (gc.getWidth()/2)-80, (gc.getHeight()/2));
			g.drawImage(this.score_bleu, (gc.getWidth()/2)+80, (gc.getHeight()/2));
			g.setColor(Color.yellow);
			g.drawString(this.bleu_score1, (gc.getWidth()/2)+140, (gc.getHeight()/2)+75);
			g.drawString(this.rouge_score1, (gc.getWidth()/2)-24, (gc.getHeight()/2)+75);
		}
		//fin jeu

		
		if(TimerFin.getFinJeu()){
			gc.pause();
			if(rouge_score<bleu_score){
				g.resetTransform();  
				g.drawImage(this.rougegagnant, (gc.getWidth()/2)-220, (gc.getHeight()/2)-350);
				g.drawImage(this.bouton_fin, (gc.getWidth()/2)-80, (gc.getHeight()/2)+150);
				g.drawImage(this.score_rouge, (gc.getWidth()/2)-250, (gc.getHeight()/2));
				g.drawImage(this.score_bleu, (gc.getWidth()/2)+200, (gc.getHeight()/2));
				g.setColor(Color.yellow);
				g.drawString(this.bleu_score1, (gc.getWidth()/2)+260, (gc.getHeight()/2)+75);
				g.drawString(this.rouge_score1, (gc.getWidth()/2)-194, (gc.getHeight()/2)+75);
				//if (ms.isMouseOver()) {
				//	ms.render(gc, g);
				//}
			}
			else if(rouge_score>bleu_score){
				g.resetTransform();  
				g.drawImage(this.bleugagnant, (gc.getWidth()/2)-220, (gc.getHeight()/2)-350);
				g.drawImage(this.bouton_fin, (gc.getWidth()/2)-80, (gc.getHeight()/2)+150);
				g.drawImage(this.score_rouge, (gc.getWidth()/2)-250, (gc.getHeight()/2));
				g.drawImage(this.score_bleu, (gc.getWidth()/2)+200, (gc.getHeight()/2));
				g.setColor(Color.yellow);
				g.drawString(this.bleu_score1, (gc.getWidth()/2)+260, (gc.getHeight()/2)+75);
				g.drawString(this.rouge_score1, (gc.getWidth()/2)-194, (gc.getHeight()/2)+75);

				//if (ms.isMouseOver()) {
				//	ms.render(gc, g);
				//}
			}
			else if(rouge_score==bleu_score){
				g.resetTransform();  
				g.drawImage(this.egaliter, (gc.getWidth()/2)-220, (gc.getHeight()/2)-350);
				g.drawImage(this.bouton_fin, (gc.getWidth()/2)-80, (gc.getHeight()/2)+150);
				g.drawImage(this.score_rouge, (gc.getWidth()/2)-250, (gc.getHeight()/2));
				g.drawImage(this.score_bleu, (gc.getWidth()/2)+200, (gc.getHeight()/2));
				g.setColor(Color.yellow);
				g.drawString(this.bleu_score1, (gc.getWidth()/2)+260, (gc.getHeight()/2)+75);
				g.drawString(this.rouge_score1, (gc.getWidth()/2)-194, (gc.getHeight()/2)+75);

				//if (ms.isMouseOver()) {
				//	ms.render(gc, g);
				//}
			}
		}

	}

	// Met � jour les �l�ments de la sc�ne en fonction du delta temps
	// survenu.
	// C'est ici que la logique du jeu est enferm�e.
	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		int posX = Mouse.getX();
		int posY = Mouse.getY();
		
		
		for (Personnage p : personnages) {
			if (p.isDeplacementTermine()) {
				changerEtatAutomate(p, delta);				
			}
			// A tester
			map.getCaseFromCoord((int) p.getX(), (int) p.getY()).supprimerPersonnage(p);
			deplacerPersonnage(p, delta);
			map.getCaseFromCoord((int) p.getX(), (int) p.getY()).ajouterPersonnage(p);
		
		}
		personnages.get(0).getAutomate().afficher();
		if (gc.getInput().isKeyPressed(Input.KEY_M) && gc.isMusicOn() && MusicEnable) {

			test2.resume();

		}

		if (gc.getInput().isKeyPressed(Input.KEY_P) && MusicEnable) {

			test2.pause();
		}	

		if (gc.getInput().isKeyDown(Input.KEY_UP)) {
			Camera.cameraUP();
		}
		if (gc.getInput().isKeyDown(Input.KEY_DOWN)) {
			Camera.cameraDown();
		}
		if (gc.getInput().isKeyDown(Input.KEY_RIGHT)) {
			Camera.cameraRIGHT();
		}
		if (gc.getInput().isKeyDown(Input.KEY_LEFT)) {
			Camera.cameraLEFT();
		}

		
		if(gc.getInput().isKeyPressed(Input.KEY_F1)){

			gc.setPaused(!gc.isPaused());

		}
		

		if(gc.isPaused()) {
			
			if((posX>gc.getWidth()/2-175 && posX<gc.getWidth()/2+175)&&(posY>8*gc.getHeight()/20-37 && posY<8*gc.getHeight()/20+37)){ // Reprendre
				if(Mouse.isButtonDown(0)){
					gc.setPaused(!gc.isPaused());
				}
			}
			
			if((posX>gc.getWidth()/2-175 && posX<gc.getWidth()/2+175)&&(posY>5*gc.getHeight()/20-37 && posY<5*gc.getHeight()/20+37)){ // Accueil
				if(Mouse.isButtonDown(0)){
					gc.setPaused(!gc.isPaused());
					game.enterState(0);
					gc.reinit();
				}
			}
			
			if((posX>gc.getWidth()/2-175 && posX<gc.getWidth()/2+175)&&(posY>2*gc.getHeight()/20-37 && posY<2*gc.getHeight()/20+37)){ // Quitter
				if(Mouse.isButtonDown(0)){
					gc.exit();
				}
			}
		}

		bleu_score=getPersonnageParType(TypePersonnage.BLEU).compterScore(map);
		rouge_score=getPersonnageParType(TypePersonnage.ROUGE).compterScore(map);
		bleu_score1=Integer.toString(bleu_score);
		rouge_score1=Integer.toString(rouge_score);
	
		}	


	
	
	public void mouseWheelMoved(int change) {
		if (change < 0) {
			Camera.cameraDezoom(map);
		} else {
			Camera.cameraZoom(map);
		}
	}

	// Arreter correctement le jeu en appuyant sur ECHAP
	@Override
	public void keyReleased(int key, char c) {

		if (Input.KEY_ESCAPE == key) {
			gc.exit();
		}
	}

	public void changerEtatAutomate(Personnage p, int delta) {

		ArrayList<Integer> indexPossibles = new ArrayList<Integer>();
		int etatCourantId = p.getEtatCourant().getId();
		Random r = new Random();
		int indexChoisi = 0;

		for (int i = 0; i < p.getAutomate().getNbLignes(); i++) {
			Condition c = p.getAutomate().getTabCondition()[i][etatCourantId];
			if (c.nombreConditions() != 0 && c.estVerifiee(p, map)) {
				indexPossibles.add(i);
			}
		}

		// Affichage test

	

		

		if (!indexPossibles.isEmpty()) {
			// Prendre un index au hasard dans la liste
			indexChoisi = indexPossibles.get(r.nextInt(indexPossibles.size()));
                        
                        p.getAutomate().getTabActionTransition()[indexChoisi][etatCourantId].executer(p, map.getCaseFromCoord((int)p.getX(), (int)p.getY()), this, 0);
			// System.out.println("index choisi : "+indexChoisi);
			// System.out.println("etat suivant :
			

			p.setEtatCourant(p.getAutomate().getTabEtatSuivant()[indexChoisi][etatCourantId]);

		} else {
			p.setEtatCourant(p.getAutomate().getEtatInitial());
		}
		// initier le mouvement


		// System.out.println("action etat courant :
		

		//System.out.println("action etat courant : " + p.getEtatCourant().getActionEtat().toString());

		p.setDeplacementCourant(0);


	}

	public void deplacerPersonnage(Personnage p, int delta) {
		p.deplacer(delta, map.getLargeur(), map.getHauteur());
	}

	public Map getMap() {
		return map;
	}

	@Override
	public int getID() {
		return ID;
	}


    public List<Personnage> getPersonnages() {
        return personnages;
    }

    public void enter(GameContainer gc, StateBasedGame game) {
    	
    	if (MusicEnable) {
    		test2.loop();
    	}
    	System.out.println(">>>>>>> ENTRER DANS JEU <<<<<<<"); 
    }
    
    public void leave(GameContainer gc, StateBasedGame game) {
    	System.out.println(">>>>>>> SORTIE DE JEU <<<<<<<");
    }

   
    


	
	public static void finDuJeu(){
	    t = new Timer();
	    t.schedule(new TimerFin(), 0, 1*1000);
	}
        
        public Personnage getPersonnageParType(TypePersonnage t) {
            for(Personnage p : personnages) {
                if(p.getTypePersonnage() == t)
                    return p;
            }
            return null;
        }


        	
}
