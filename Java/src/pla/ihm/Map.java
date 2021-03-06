package pla.ihm;

import java.util.List;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import pla.Association;
import pla.Automate;
import pla.Cellule;
import pla.Personnage;
import pla.action.transition.Action_transition;
import pla.decor.*;

public class Map {

	private SpriteSheet ssmap;
	private static final int TILE_SIZE = 64;

	/* largeur de la map */
	private int nbCasesLargeur;

	/* longueur de la map */
	private int nbCasesHauteur;

	private int nbCasesHorsAutomate;

	// Matrice des Cases
	private Case cases[][];

	private static final int CERCLE = 360;
	private static final int DISTANCE = 0;

	public Map(int largeur, int hauteur, List<Personnage> personnages) {

	int lmax = largeurMax(personnages) * (personnages.size()+1);
	int hmax = hauteurMax(personnages) * (personnages.size()+1);
		this.nbCasesHauteur = (Math.max(lmax,hmax));//*(personnages.size()/2+1);
		this.nbCasesLargeur = (Math.max(lmax,hmax));//*(personnages.size()/2+1);
		cases = new Case[this.nbCasesHauteur][this.nbCasesLargeur];
		// Cr�ation de la matrice des cases
		for (int i = 0; i < this.nbCasesHauteur; i++) {
			for (int j = 0; j < this.nbCasesLargeur; j++) {
				cases[i][j] = new Case(i, j);
			}
		}
	}

	public void init() throws SlickException {
		this.ssmap = new SpriteSheet("res/sprite.png", TILE_SIZE, TILE_SIZE);
	}

	public int getNbCasesLargeur() {
		return nbCasesLargeur;
	}

	public int getNbCasesHauteur() {
		return nbCasesHauteur;
	}

	public int getLargeur() {
		return nbCasesLargeur * TILE_SIZE;
	}

	public int getHauteur() {
		return nbCasesHauteur * TILE_SIZE;
	}

	public void afficher() {
		// On indique � la SpriteSheet qu'on s'apprete � l'utiliser
		ssmap.startUse();
		// Pour chaque case de la map
		for (int i = 0; i < nbCasesHauteur; i++) {
			for (int j = 0; j < nbCasesLargeur; j++) {
				// d = le d�cor appartenant � la case aux coordonn�es i,j
				Decor d = cases[i][j].getDecor();
				// Afficher d'abord un sol normal(b�ton gris) de taille TILE_SIZE = 64
				ssmap.renderInUse(j * TILE_SIZE, i * TILE_SIZE, DecorSprite.SOL_NORMAL.getX(),
						DecorSprite.SOL_NORMAL.getY());
				// Afficher par dessus le d�cor d
				ssmap.renderInUse(j * TILE_SIZE, i * TILE_SIZE, d.getX(), d.getY());
			}
		}
		// On termine l'utilisation de la spriteSheet
		ssmap.endUse();
	}

	public void placerDecorRandom() {
		Decor bat = new BatimentNeutre();
		Decor be = new BombeEau();
		Decor bp = new BombePeinture();
		Decor boe = new BoucheEgout();
		Decor g = new Gendarmerie();
		Decor mur = new Mur();
		Decor muret = new Muret();
		Decor sn = new SolNormal();
		Decor v = new Velo();
		int nbBat = (int) ((bat.getFrequence() / 100.0) * nbCasesHorsAutomate);
		int nbBe = (int) ((be.getFrequence() / 100.0) * nbCasesHorsAutomate);
		int nbBp = (int) ((bp.getFrequence() / 100.0) * nbCasesHorsAutomate);
		int nbBoe = (int) ((boe.getFrequence() / 100.0) * nbCasesHorsAutomate);
		int nbG = (int) ((g.getFrequence() / 100.0) * nbCasesHorsAutomate);
		int nbMur = (int) ((mur.getFrequence() / 100.0) * nbCasesHorsAutomate);
		int nbMuret = (int) ((muret.getFrequence() / 100.0) * nbCasesHorsAutomate);
		int nbSn = (int) ((sn.getFrequence() / 100.0) * nbCasesHorsAutomate);
		int nbV = (int) ((v.getFrequence() / 100.0) * nbCasesHorsAutomate);
		Random rand = new Random();

		int r;
		int binf;
		int bsup;
		// D'une salet� incomparable...

		for (int i = 0; i < nbCasesHauteur; i++) {
			for (int j = 0; j < nbCasesLargeur; j++) {
				binf = 0;
				bsup = (int) bat.getFrequence();
				if (!cases[i][j].estDansAutomate()) {
					//System.out.println(" i = "+i+" j = "+j);
					r = rand.nextInt(100);
					if (r >= binf && r < bsup) {
						if (nbBat > 0) {
							cases[i][j].setDecor(bat);
							nbBat--;
						}
					}
					binf = bsup;
					bsup = bsup + (int)be.getFrequence();
					if (r >= binf && r < bsup) {
						if (nbBe > 0) {
							cases[i][j].setDecor(be);
							nbBe--;
						}
					}
					binf = bsup;
					bsup = bsup + (int)bp.getFrequence();
					if (r >= binf && r < bsup) {
						if (nbBp > 0) {
							cases[i][j].setDecor(bp);
							nbBp--;
						}
					}
					binf = bsup;
					bsup = bsup + (int)boe.getFrequence();
					if (r >= binf && r < bsup) {
						if (nbBoe > 0) {
							cases[i][j].setDecor(boe);
							nbBoe--;
						}
					}
					binf = bsup;
					bsup = bsup + (int)g.getFrequence();
					if (r >= binf && r < bsup) {
						if (nbG > 0) {
							cases[i][j].setDecor(g);
							nbG--;
						}
					}
					binf = bsup;
					bsup = bsup + (int)mur.getFrequence();
					if (r >= binf && r < bsup) {
						if (nbMur > 0) {
							cases[i][j].setDecor(mur);
							nbMur--;
						}
					}
					binf = bsup;
					bsup = bsup + (int)muret.getFrequence();
					if (r >= binf && r < bsup) {
						if (nbMuret > 0) {
							cases[i][j].setDecor(muret);
							nbMuret--;
						}
					}					
					binf = bsup;
					bsup = bsup + (int)sn.getFrequence();
					if (r >= binf && r < bsup) {
						if (nbSn > 0) {
							cases[i][j].setDecor(sn);
							nbSn--;
						}
					}
					binf = bsup;
					bsup = bsup + (int)v.getFrequence();
					if (r >= binf && r < bsup) {
						if (nbV > 0) {
							cases[i][j].setDecor(v);
							nbV--;
						}
					}
				}
			}
		}
	}

	public void placerAutomate(Automate a, Color couleurPerso, Graphics g) {
		for (int i = 0; i < a.getNbLignes(); i++)
			for (int j = 0; j < a.getNbColonnes(); j++) {
				// pour chaque valeur dans le tableau action-transition, charger
				// l'image dans la case
				chargerDecor(a, g, i, j);
			}
	}

	public void chargerDecor(Automate a, Graphics g, int i, int j) {
		Action_transition at = a.getTabActionTransition()[i][j];
		Decor decor = Association.getDecor(at);
		if (decor instanceof SolAmi || decor instanceof SolEnnemi)
			decor = new SolNormal();
		modifierDecorCase(i + a.getPosX() / 64, j + a.getPosY() / 64, decor);
	}

	public Case[][] getCases() {
		return cases;
	}

	public void modifierDecorCase(int i, int j, Decor decor) {
		if (i < nbCasesHauteur && j < nbCasesLargeur) {
			cases[i][j].setDecor(decor);
		}

	}

	public int getNbCasesHorsAutomate() {
		return nbCasesHorsAutomate;
	}

	public void setNbCasesHorsAutomate() {
		nbCasesHorsAutomate = 0;
		for (int i = 0; i < nbCasesHauteur; i++) {
			for (int j = 0; j < nbCasesLargeur; j++) {
				if (!cases[i][j].estDansAutomate()) {
					nbCasesHorsAutomate++;
				}
			}
		}
	}

	public void setCasesEstDansAutomate(List<Personnage> personnages) {
		for (int i = 0; i < nbCasesHauteur; i++)
			for (int j = 0; j < nbCasesLargeur; j++) {
				cases[i][j].setEstDansAutomate(caseEstDansUnAutomate(cases[i][j], personnages));
			}
	}

	public boolean caseEstDansAutomate(Case c, Automate a){
		boolean estPresent = false;
		float autoX=a.getPosX();
		float autoY=a.getPosY();
		float caseX=c.getIndexJ()*TILE_SIZE;
		float caseY=c.getIndexI()*TILE_SIZE;
		
		if (caseX >= autoY && caseY >= autoX
				&& caseX + TILE_SIZE <= autoY + a.getNbColonnes() * TILE_SIZE
				&& caseY + TILE_SIZE <= autoX + a.getNbLignes() * TILE_SIZE) {
			estPresent = true;
		}
		return estPresent;
	}
	
	public boolean caseEstDansUnAutomate(Case c, List<Personnage> personnages) {
		boolean estPresent = false;
		float autoX=0;
		float autoY=0;
		float caseX=0;
		float caseY=0;
		for (Personnage p : personnages) {
			autoX = p.getAutomate().getPosX();
			autoY = p.getAutomate().getPosY();
			caseX = c.getIndexJ() * TILE_SIZE;
			caseY = c.getIndexI() * TILE_SIZE;

			//System.out.println("autoX = " + autoX + " autoY = " + autoY + " caseX = " + caseX + " caseY " + caseY);

			if (caseX >= autoY && caseY >= autoX
					&& caseX + TILE_SIZE <= autoY + p.getAutomate().getNbColonnes() * TILE_SIZE
					&& caseY + TILE_SIZE <= autoX + p.getAutomate().getNbLignes() * TILE_SIZE) {

				estPresent = true;
			}
		}
		return estPresent;
	}

	public Case getCase(Case caseCourante, Cellule cellule) {
		try {
			switch (cellule) {
			case Nord:
				return cases[caseCourante.getIndexI() - 1][caseCourante.getIndexJ()];
			case Sud:
				return cases[caseCourante.getIndexI() + 1][caseCourante.getIndexJ()];
			case Est:
				return cases[caseCourante.getIndexI()][caseCourante.getIndexJ() + 1];
			case Ouest:
				return cases[caseCourante.getIndexI()][caseCourante.getIndexJ() - 1];
			case Case:
			default:
				return caseCourante;
			}
		} catch (ArrayIndexOutOfBoundsException e) { // Si on sort de la map
			return caseCourante;
		}
	}

	public Case getCaseFromCoord(int posX, int posY) {
		try {
			return cases[(posY / TILE_SIZE)][(posX / TILE_SIZE)];
		} catch (ArrayIndexOutOfBoundsException e) {
			// A changer
			return cases[0][0];
		}
	}

	public void placerAutoRandom(List<Personnage> lPersonnage, Graphics g) {
		Random rand = new Random();

		int nbAutomate = lPersonnage.size();
		int rayonCercle, centreCercleX, centreCercleY, anglePersonnage, firstAngle, hmax, lmax;

		hmax = hauteurMax(lPersonnage);
		lmax = largeurMax(lPersonnage);

		if (hmax <= lmax) {
			rayonCercle = (lmax * (nbAutomate) / 4);
		} else {
			rayonCercle = (hmax * (nbAutomate) / 4);
		}
		centreCercleX = nbCasesLargeur / 2-lmax;
		centreCercleY = nbCasesHauteur / 2-hmax+2;

		anglePersonnage = CERCLE / nbAutomate;
		firstAngle = rand.nextInt(CERCLE + 1);
		lPersonnage.get(0).getAutomate()
				.setPosX((int) (rayonCercle * Math.cos(firstAngle * 2 * Math.PI / 360) + centreCercleY) * TILE_SIZE);
		lPersonnage.get(0).getAutomate()
				.setPosY((int) (rayonCercle * Math.sin(firstAngle * 2 * Math.PI / 360) + centreCercleX) * TILE_SIZE);
		placerAutomate(lPersonnage.get(0).getAutomate(), lPersonnage.get(0).getCouleur(), g);
		
		for (int i = 1; i < nbAutomate; i++) {
			firstAngle = (anglePersonnage + firstAngle) % CERCLE;
			lPersonnage.get(i).getAutomate().setPosX(
					(int) (rayonCercle * Math.cos(firstAngle * 2 * Math.PI / 360) + centreCercleY) * TILE_SIZE);
			lPersonnage.get(i).getAutomate().setPosY(
					(int) (rayonCercle * Math.sin(firstAngle * 2 * Math.PI / 360) + centreCercleX) * TILE_SIZE);
			placerAutomate(lPersonnage.get(i).getAutomate(), lPersonnage.get(i).getCouleur(), g);
		}
	}

	private int hauteurMax(List<Personnage> lPersonnage) {
		int hmax = 0;
		for (int i = 0; i < lPersonnage.size(); i++) {
			if (lPersonnage.get(i).getAutomate().getNbLignes() > hmax) {
				hmax = lPersonnage.get(i).getAutomate().getNbLignes();
			}
		}
		return hmax;
	}

	private int largeurMax(List<Personnage> lPersonnage) {
		int lmax = 0;
		for (int i = 0; i < lPersonnage.size(); i++) {
			if (lPersonnage.get(i).getAutomate().getNbColonnes() > lmax) {
				lmax = lPersonnage.get(i).getAutomate().getNbColonnes();
			}
		}
		return lmax;
	}

	// Place un personnage pseudo-al�atoirement sur la map
	public void placerPersonnageRandom(List<Personnage> lPersonnage) {
		Random rand = new Random();

		int posX, posY;
		int w = getCases()[0].length; // w = largeur de la map en nombre de cases
		int h = getCases().length; // h = hauteur de la map en nombre de cases

		for (int i = 0; i < lPersonnage.size(); i++) {
			do {
				// Calcul des coordonn�es futures du personnages tant que cela ne tombe pas sur un automate
				posX = rand.nextInt(w) * TILE_SIZE + TILE_SIZE / 2; 
				posY = rand.nextInt(h) * TILE_SIZE + TILE_SIZE / 2;			
			} while (automatePresent(lPersonnage, posX, posY, lPersonnage.size()));
			
			// Modification des coordon�es du personnages
			lPersonnage.get(i).setX(posX % (w * TILE_SIZE));
			lPersonnage.get(i).setY(posY % (h * TILE_SIZE));
			
			// On ajoute le personnage a la liste des personnaes de la case sur laquelle va apparaitre le personnage 
			this.getCaseFromCoord((int) lPersonnage.get(i).getX(), (int) lPersonnage.get(i).getY())
					.ajouterPersonnage(lPersonnage.get(i));
		}

	}

	public void placerPersonnageRandom(Personnage personnage, List<Personnage> lPersonnage) {
		Random rand = new Random();

		int posX, posY;
		int w = getCases()[0].length;
		int h = getCases().length;

		do {
			posX = rand.nextInt(w) * TILE_SIZE + TILE_SIZE / 2;
			posY = rand.nextInt(h) * TILE_SIZE + TILE_SIZE / 2;
		} while (automatePresent(lPersonnage, posX, posY, 0));
		personnage.setX(posX % (w * TILE_SIZE));
		personnage.setY(posY % (h * TILE_SIZE));
	}

	private boolean automatePresent(List<Personnage> lPersonnage, int posX, int posY, int i) {
		boolean present = false;
		int intiAPosX, intiAPosY, endAPosX, endAPosY;
		for (int j = 0; j < i; j++) {
			intiAPosX = lPersonnage.get(j).getAutomate().getPosX();
			intiAPosY = lPersonnage.get(j).getAutomate().getPosX();
			endAPosX = lPersonnage.get(j).getAutomate().getNbColonnes() + intiAPosX;
			endAPosY = lPersonnage.get(j).getAutomate().getNbLignes() + intiAPosY;
			if (intiAPosX < posX && endAPosX > posX && intiAPosY < posY && endAPosY > posY) {
				present = true;
			}
		}
		return present;
	}

	
	public Case getCase(int i, int j){
            if(i>=0) {
                if(j>=0) {
                    return cases[i%nbCasesHauteur][j%nbCasesLargeur];
                } else {
                    return cases[i%nbCasesHauteur][j%nbCasesLargeur+nbCasesLargeur];
                }
            } else {
                if(j>=0) {
                    return cases[i%nbCasesHauteur+nbCasesHauteur][j%nbCasesLargeur];
                } else {
                    return cases[i%nbCasesHauteur+nbCasesHauteur][j%nbCasesLargeur+nbCasesLargeur];
                }
            }
                
	}
	
	

}
