/*********************************************
 * OPL 12.8.0.0 Model
 * Author: hp
 * Creation Date: 1 May 2020 at 17:56:32
 *********************************************/
int nbObjet=...; 
int poidsMax=...;

range objets = 1..nbObjet;
int poids[objets] = ...;
int valeur[objets] = ...;
dvar boolean x[objets];

maximize   sum(i in objets) valeur[i] * x[i]; 

subject to {
  sum( i in objets )          poids[i] * x[i] <= poidsMax; 
  } 