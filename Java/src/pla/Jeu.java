package pla;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import pla.ihm.Map;

public class Jeu extends BasicGame {
	private Map map = new Map(); // carte du jeu
	private List<Personnage> personnages = new ArrayList<Personnage>(); // Liste
																		// des
																		// personnages
	private GameContainer gc; // conteneur
	// private static final int PAUSE = 25; // temps de latence

	// private float zoom = 0.1f;
	Music sound;

	/*
	 * private float z1 = 0.01f; private float z2 = 0.01f;
	 */

	public Jeu(String titre) {
		super(titre); // Nom du jeu

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
	public void init(GameContainer gc) throws SlickException {

		this.gc = gc;
		this.map.init();
		ajouterPersonnage(new Personnage("res/thugBleu.png", 420.f, 420.f, 2, 0, 64, 64, new Automate(), Color.blue));
		ajouterPersonnage(new Personnage("res/thugRouge.png", 320.f, 320.f, 1, 0, 64, 64, new Automate(), Color.green));

		// Marche pas => Revoir sprite policier
		ajouterPersonnage(new Personnage("res/Bernard.png", 200.f, 200.f, 3, 0, 64, 64, new Automate(), Color.green));

		for (Personnage p : personnages) {
			p.init();
			// this.map.placerAutoRandom(personnages);
			this.map.placerAutomate(p.getAutomate(), p.getCouleur(), gc.getGraphics());

		}
		// this.map.placerPersonnageRandom(personnages);
	//	sound = new Music("res/thug.ogg");
	//	sound.loop();
	}

	// Affiche le contenu du jeu
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		this.map.afficher();
		for (Personnage p : personnages) {
			p.afficher(g);
		}
	}

	// Met � jour les �l�ments de la sc�ne en fonction du delta temps
	// survenu.
	// C'est ici que la logique du jeu est enferm�e.
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		// TODO Auto-generated method stub
		for (Personnage p : personnages) {
			deplacerPersonnage(p, delta);
		}

		/*
		 * if (gc.getInput().isKeyPressed(Input.KEY_UP)) {
		 * gc.getGraphics().scale(zoom,zoom); }
		 */
		if (gc.getInput().isKeyPressed(Input.KEY_M) && gc.isMusicOn()) {
			sound.resume();
		}
		if (gc.getInput().isKeyPressed(Input.KEY_S)) {
			sound.stop();
		}
		if (gc.getInput().isKeyPressed(Input.KEY_P)) {
			sound.pause();			
		}
		/*
		 * if(gc.getInput().isKeyDown(Input.KEY_DOWN)){ z1+=0.5f; z2+=0.5f;
		 * gc.getGraphics().scale(z1,z2); }
		 */
	}

	// Arreter correctement le jeu en appuyant sur ECHAP
	@Override
	public void keyReleased(int key, char c) {

		if (Input.KEY_ESCAPE == key) {
			gc.exit();
		}
	}

	public void deplacerPersonnage(Personnage p, int delta) {

		ArrayList<Integer> indexPossibles = new ArrayList<Integer>();
		int etatCourantId = p.getAutomate().getEtatCourant().getId();
		boolean conditionVerifiee;
		Random r = new Random();
		int indexChoisi = 0;

		for (int i = 0; i < p.getAutomate().getNbLignes(); i++) {
			Condition c = p.getAutomate().getTabCondition()[i][etatCourantId];
			conditionVerifiee = true;

			if (!c.estVerifiee(p, map)) {
				conditionVerifiee = false;
			}

			if (conditionVerifiee) {
				indexPossibles.add(i);
			}
		}

		if (!indexPossibles.isEmpty()) {
			// Prendre un index au hasard dans la liste
			indexChoisi = indexPossibles.get(r.nextInt(indexPossibles.size()));	
			p.getAutomate().setEtatCourant(p.getAutomate().getTabEtatSuivant()[indexChoisi][etatCourantId]);
		}
		else{
			p.getAutomate().setEtatCourant(p.getAutomate().getEtatInitial());
		}	
		p.deplacer(delta);
	}
}
