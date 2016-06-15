package pla;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

import pla.decor.BombePeinture;
import pla.decor.BombeEau;
import pla.decor.Velo;

import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


import pla.action.transition.Construire;
import pla.action.transition.Demolir;



import pla.ihm.Camera;

import pla.ihm.Map;
import pla.util.Musique;


public class Jeu extends BasicGameState {
	private Map map ; // carte du jeu
	private List<Personnage> personnages = new ArrayList<Personnage>(); // Liste
																		// des
	public static final int ID = 1;										// personnages
	
	private GameContainer gc; // conteneur

	Musique musique;
	private Image inventaire_rouge,inventaire_rouge_eau,inventaire_rouge_bombe,inventaire_rouge_bike,inventaire_rouge_eau_bike,inventaire_rouge_bombe_bike;
	private Image inventaire_bleu,inventaire_bleu_eau,inventaire_bleu_bombe,inventaire_bleu_bike,inventaire_bleu_eau_bike,inventaire_bleu_bombe_bike;

	
	//private static final int P_BAR_X = 15;
	//private static final int P_BAR_Y = 25;

	private int SIZE_WINDOW_X ;
	private int SIZE_WINDOW_Y ;
	// private static final int PAUSE = 25; // temps de latence

	// private float zoom = 0.1f;
	

	/*
	 * private float z1 = 0.01f; private float z2 = 0.01f;
	 */


	public Jeu(int largeur,int hauteur) {
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
		
		ajouterPersonnage(new Personnage("res/thugBleu.png", 2, 64, 64, new Automate(), Color.blue));
		ajouterPersonnage(new Personnage("res/thugRouge.png", 1, 64, 64, new Automate(), Color.red));

		// Marche pas => Revoir sprite policier
		ajouterPersonnage(new Personnage("res/Bernard.png", 3, 64, 64, new Automate(), Color.black));
		
		map = new Map((int)SIZE_WINDOW_X, (int)SIZE_WINDOW_Y, personnages);

		this.map.init();
		Camera.initCamera(map, SIZE_WINDOW_X, SIZE_WINDOW_Y);
		for (Personnage p : personnages) {
			p.init();

		
		// this.map.placerPersonnageRandom(personnages);
		//sound = new Music("res/thug.ogg");
		//musique = new Musique();
		

			//this.map.placerAutomate(p.getAutomate(), p.getCouleur(), gc.getGraphics());


			//this.map.placerAutomate(p.getAutomate(), p.getCouleur(), gc.getGraphics());
		}

        this.map.placerAutoRandom(personnages, gc.getGraphics());
		this.map.placerPersonnageRandom(personnages);
                //new Construire().executer(personnages.get(0), map.getCaseFromCoord(0, 0), 0);
                //System.out.println(map.getCaseFromCoord(0, 0).getDecor());
	//	sound = new Music("res/thug.ogg");
	//	sound.loop();
		//code sale puissance 1000
		
		this.inventaire_rouge = new Image("res/rougevide.png");
		this.inventaire_rouge_eau = new Image("res/rougevide_eau.png");
		this.inventaire_rouge_bombe = new Image("res/rougevide_bombe.png");
		this.inventaire_rouge_bike = new Image("res/rougevide_bike.png");
		this.inventaire_rouge_eau_bike = new Image("res/rougevide_eau_bike.png");
		this.inventaire_rouge_bombe_bike = new Image("res/rougevide_bombe_bike.png");
		this.inventaire_bleu  = new Image("res/bleuvide.png");
		this.inventaire_bleu_eau   = new Image("res/bleuvide_eau.png");
		this.inventaire_bleu_bombe = new Image("res/bleuvide_bombe.png");
		this.inventaire_bleu_bike  = new Image("res/bleuvide_bike.png");
		this.inventaire_bleu_eau_bike  = new Image("res/bleuvide_eau_bike.png");
		this.inventaire_bleu_bombe_bike  = new Image("res/bleuvide_bombe_bike.png");
		
				
	}

	// Affiche le contenu du jeu
	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {

		Camera.moveCamera(g);

		this.map.afficher();
		for (Personnage p : personnages) {
			p.afficher(g);
		}
		/*
		if (gc.getInput().isKeyDown(Input.KEY_I)) {
				  g.resetTransform();
				  g.drawImage(this.inventaire_rouge, 15, 25);
				  g.drawImage(this.inventaire_bleu, 15, 90);
		}
		*/
		if (gc.getInput().isKeyDown(Input.KEY_I)) {
			if (object instanceof BombePeinture){
				  g.drawImage(this.inventaire_rouge, 15, 25);
			}
			if (object instanceof BombePeinture){
				  g.drawImage(this.inventaire_rouge_bombe, 15, 25);
			}
			if (object instanceof BombeEau){
				  g.drawImage(this.inventaire_rouge_eau, 15, 25);
			}
			if (object instanceof Velo){
				  g.drawImage(this.inventaire_rouge_bike, 15, 25);
			}
			if (object instanceof BombeEau && object instanceof Velo){
				  g.drawImage(this.inventaire_rouge_eau_bike, 15, 25);
			}
			if (object instanceof BombePeinture && object instanceof Velo){
				  g.drawImage(this.inventaire_rouge_bombe_bike, 15, 25);
			}
		}
	}

	// Met � jour les �l�ments de la sc�ne en fonction du delta temps
	// survenu.
	// C'est ici que la logique du jeu est enferm�e.
	@Override
	public void update(GameContainer gc, StateBasedGame game, int delta) throws SlickException {
		// TODO Auto-generated method stub
		for (Personnage p : personnages) {
			if(p.isDeplacementTermine()){
				changerEtatAutomate(p, delta);
			}
			deplacerPersonnage(p, delta);			
		}



		if (gc.getInput().isKeyPressed(Input.KEY_M) && gc.isMusicOn()) {
			musique.resumeJeu();
		}
		if (gc.getInput().isKeyPressed(Input.KEY_S)) {
			musique.stopJeu();
		}
		if (gc.getInput().isKeyPressed(Input.KEY_P)) {
			musique.pauseJeu();
		}	
		if (gc.getInput().isKeyDown(Input.KEY_UP)) {
			Camera.cameraUP();		
		}
		if (gc.getInput().isKeyDown(Input.KEY_DOWN)) {
			Camera.cameraDown();
		} 
		if(gc.getInput().isKeyDown(Input.KEY_RIGHT)){
			Camera.cameraRIGHT();		
		}
		if (gc.getInput().isKeyDown(Input.KEY_LEFT)) {
			Camera.cameraLEFT();	
		} 

		if (gc.getInput().isKeyDown(Input.KEY_A)) {
			Camera.cameraZoom(map);
		}
		if (gc.getInput().isKeyDown(Input.KEY_B)) {
			Camera.cameraDezoom(map);
		}
		if(gc.getInput().isKeyPressed(Input.KEY_F1)){
			gc.setPaused(!gc.isPaused());
		}

	}
	
	public void mouseWheelMoved(int change) {
		if(change<0){
			Camera.cameraDezoom(map);
		}
		else{
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
		int etatCourantId = p.getAutomate().getEtatCourant().getId();
		Random r = new Random();
		int indexChoisi = 0;
		
		for (int i = 0; i < p.getAutomate().getNbLignes(); i++) {
			Condition c = p.getAutomate().getTabCondition()[i][etatCourantId];
			if(c.nombreConditions()!=0&&c.estVerifiee(p, map)){
				indexPossibles.add(i);
			}
		}
		
		// Affichage test
		System.out.println(p.toString());
		System.out.println(this.map.getCaseFromCoord((int)p.getX(), (int)p.getY()).getDecor().toString());
		
		if (!indexPossibles.isEmpty()) {
			// Prendre un index au hasard dans la liste
			indexChoisi = indexPossibles.get(r.nextInt(indexPossibles.size()));	
			System.out.println("index choisi : "+indexChoisi);
			System.out.println("etat suivant : "+p.getAutomate().getTabEtatSuivant()[indexChoisi][etatCourantId].getId());
			p.getAutomate().setEtatCourant(p.getAutomate().getTabEtatSuivant()[indexChoisi][etatCourantId]);
		}
		else{
			p.getAutomate().setEtatCourant(p.getAutomate().getEtatInitial());
		}			
		// initier le mouvement
		System.out.println("action etat courant : "+p.getAutomate().getEtatCourant().getActionEtat().toString());
		p.setDeplacementCourant(0);	
		
	}
	
	public void deplacerPersonnage(Personnage p, int delta){		
		p.deplacer(delta,map.getLargeur(),map.getHauteur());
	}


	@Override
	public int getID() {
		return ID;
	}
		

	
}
