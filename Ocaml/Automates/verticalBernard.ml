

(* EXEMPLE D'AUTOMATE *)

let (list_etat:etat list) =
  [
    EtatInit(0, Avancer(S));
    Etat(1, Avancer(S));
  ];;

let autTest =
  [ (0, Decor(Sol(Ami),C), PeindreNeutre,1);
    (1, Decor(Sol(Ennemi),C), PeindreNeutre,0);
  ] ;;