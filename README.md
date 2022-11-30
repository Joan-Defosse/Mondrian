# **ART ABSTRAIT ET ARBRES**
**PROJET ASD3**

## Introduction
Le but de ce projet est d’utiliser les structures vues en cours pour générer
des images à la manière des tableaux de Mondrian.

![image](https://user-images.githubusercontent.com/85215478/197715114-70cab004-c6e0-4436-95fe-a30cd76fdbb8.png)

Proposer un outil pour générer des peintures de manière aléatoire, avec plusieurs méthodes de génération : 
- une méthode pour convertir un tableau en représentation textuelle 
- une méthode pour convertir une représentation textuelle en tableau

![image](https://user-images.githubusercontent.com/85215478/197714933-aca793ad-fc80-47a9-98f3-967201ebb12b.png)

## Projet 

**<h3 style="color: #236fea">Conversion arbre→peinture</h3>** 
Les arbres que vous allez implémenter et utiliser dans ce projet sont une `variante
des 2d-arbres` vus en cours. Dans votre cours les 2d-arbres alternaient une
division sur les X, et une division sur les Y, et contenaient des points pour
connaître les coordonnées de la division. Ici, l’arbre doit permettre de :
- faire une découpe selon l’axe des Y au noeud racine
- faire plusieurs découpes selon le même axe d’affilée
- représenter des couleurs dans les noeuds

A partir de cet arbre un tableau peut être généré. Des lignes doivent
être ajoutées, avec une largeur largeurLigne (en nombre de pixels), pour finalement obtenir une image, comme celle en Figure 2c.

**<h3 style="color: #236fea">Génération aléatoire d’arbres</h3>**
Pour générer aléatoirement un arbre (donc un tableau), on se donne une `limite
nbFeuilles` , à ne pas dépasser, sur le nombre de feuilles de l’arbre final. 

On part d’un arbre initial qui **contient uniquement une racine/feuille blanche** (donc un tableau blanc), et on le transforme en plusieurs étapes. 
A chaque étape :
- une feuille de l’arbre courant est séléctionnée pour être divisée en deux, jusqu’à
ce que le nombre maximum nbFeuilles de feuilles soit atteint, ou qu’on ne
puisse plus diviser (voir la Section 2.2.1). 
- Lors de la création d’une feuille, on lui assigne la couleur de son père avec une certaine probabilité, sinon une `couleur choisie aléatoirement` parmi l’ensemble de couleurs possible. Le tableau représenté par l’arbre est ensuite dessiné.

**<h3 style="color: #236fea">Choix de la feuille à diviser</h3>**
On associe à chaque feuille un poids défini comme suit. Soit F une feuille de
l’arbre, notons w et h la largeur et la hauteur de la région représentée par la
feuille. Le poids de la feuille est défini comme

$\omega (F) = \frac{\omega h}{(\omega + h)^{1,5}}$

La feuille choisie pour être divisée est la feuille de **plus grand poids** parmi les
feuilles dont les dimensions sont encore assez grandes pour être divisées. L’idée
de ce poids est d’essayer de diviser prioritairement les feuilles carrées. En revanche il ne faut pas diviser les régions trop petites, au risque de se retrouver avec uniquement des traits. Pour s’en empêcher, on se donne une valeur
minDimensionCoupe, et 
- si la taille dans au moins une des deux dimensions
de la région est plus petite que *minDimensionCoupe* 
- on ne peut pas diviser la feuille. Si, à la fin, toutes les régions sont trop petites et que le nombre de feuilles voulu nbFeuilles n’est pas atteint, ce n’est pas grave, la procédure s’arrête sans avoir atteint le nombre de feuilles.

**<h3 style="color: #236fea">Choix de l’axe/coordonnée de la division</h3>**
Quand une feuille est choisie pour être divisée, il faut `choisir l’axe de la division`, ainsi que la `coordonnée`. L’axe est choisi en fonction de la forme de la région à diviser. 
On va privilégeir de **diviser selon le grand axe**, plutôt que selon le petit.
Soient *w* et *h* la largeur et la hauteur de la feuille choisie. L’axe choisi est l’axe des X avec probabilité $\frac{\omega}{\omega + h}$, et l’axe des Y sinon (donc avec probabilité $\frac{h}{\omega + h}$).
Pour la coordonnée, il ne faut pas que la découpe se fasse trop près d’un
des deux bords. On se donne donc un paramètre *proportionCoupe* entre 0
et 1 qui détermine la région où l’on s’autorise à découper. Soit *s* la taille de
la dimension découpée, la découpe se fera selon un axe choisi aléatoirement
entre ⌊s · proportionCoupe⌋ et ⌈s · (1 − proportionCoupe)⌉. Par exemple, si
proportionCoupe vaut 0.1, sur une découpe verticale, on s’interdit de découper
dans la partie 10% à gauche ou 10% à droite.

**<h3 style="color: #236fea">Choix de la couleur</h3>**
Pour le choix de la couleur, un paramètre *memeCouleurProb* est fixé. Chaque
nouvelle feuille va prendre la `couleur de son parent` avec une probabilité de
memeCouleurProb, ou alors une couleur `choisie aléatoirement` parmi un ensemble fixé de couleurs (rouge, bleu, jaune, noir, blanc) avec probabilité (1 −
memeCouleurProb)/5 pour chacune des **cinq couleurs**.

**<h3 style="color: #236fea">Un mot sur l’aléatoire</h3>**
Les algorithmes aléatoires doivent être reproductibles **(2)**. Pour cela, les générateurs aléatoires (tels que java.util.Random) proposent de fixer une graine aléatoire, `seed`, en fonction de laquelle sont construits de manière déterministe les nombres aléatoires produits par le générateur. Il faut que votre programme, si appelé deux fois avec les mêmes paramètres et la même graine aléatoire, génère le même arbre.

**<h3 style="color: #236fea">Amélioration de la stratégie</h3>**
Pour le projet, vous devrez implémenter dans un premier temps la méthode
définie ci-dessus. Cette méthode a plusieurs défauts artistiques **(3)**. Vous devez proposer une nouvelle méthode, et l’implémenter pour corriger un des défauts.
Dans votre rapport vous montrerez avec images à l’appui que votre stratégie corrige un défaut, et vous mettrez en avant les défauts de votre nouvelle approche.
Votre nouvelle stratégie ne peut pas se contenter d’une petite modification de l’approche pr´esent´ee ci-dessus (par exemple changer la fonction de poids ou les couleurs n’est pas suffisant), mais doit être une nouvelle idée. 

  **(2)** Vérifiez votre programme pour que cela soit bien le cas. </br>
  **(3)** Par exemple, vous pourrez constater que certains types de tableaux ne peuvent pas être construits avec la méthode fournie.

**<h3 style="color: #236fea"> Résumé</h3>**
Votre programme doit permettre à l’utilisateur·ice de sélectionner les paramètres
suivants :
- <span style="color: #ed4040">strategy</span>, la stratégie de génération d’arbre, celle de l’énoncé ou celle
que vous avez créée. Les paramètres suivants concernent la stratégie de
l’énoncé. Si votre stratégie nécessite des paramètres, il doit être possible
pour l’utilisateur·ice de les spécifier
- <span style="color: #ed4040">largeur</span>, hauteur, la hauteur et largeur (en nombre de pixels) de l’image
générée
- <span style="color: #ed4040">nbFeuilles</span>, le nombre de feuilles maximum de l’arbre
- <span style="color: #ed4040">minDimensionCoupe</span>, la taille minimum des dimensions d’une région pour
pouvoir la diviser
- <span style="color: #ed4040">proportionCoupe</span>, la valeur qui permet de ne pas découper trop proche
des bords de la région
- <span style="color: #ed4040">memeCouleurProb</span>, la probabilité de garder la couleur du parent lors d’une division.
- <span style="color: #ed4040">largeurLigne</span>, la largeur (en pixels) de la ligne qui sépare les régions
- <span style="color: #ed4040">seed</span>, la graine aléatoire utilis´ee pour g´en´erer l’arbre.
Votre code doit absolument contenir (avec ce nom) les fonctions :
- <span style="color: #ed4040">chooseLeaf</span> (ou choisirFeuille) qui choisit une feuille à diviser
- <span style="color: #ed4040">chooseDivision</span> (ou choisirDivision) qui choisit l’axe et la coordonnée
de la division d’une feuille donnée
- <span style="color: #ed4040">chooseColor</span> (ou choisirCouleur) qui choisit la couleur d’une feuille
nouvellement créée
- <span style="color: #ed4040">generateRandomTree</span> (ou genereArbreAleatoire) qui génère un arbre
aléatoire selon la procédure vue plus haut
- <span style="color: #ed4040">generateBetterRandomTree</span> (ou genereMeilleurArbreAleatoire) qui
génère un arbre aléatoire selon la procédure que vous avez créée
- <span style="color: #ed4040">toImage</span> qui à partir d’un arbre, génère l’image au format PNG du tableau.
</br>
Vous choisirez également des noms clairs pour vos structures de données, par exemple Quadtree, et/ou ABR, et/ou AVL, etc.
</br></br>
<span style="color: #ed4040">**Important**</span>. Votre programme doit implémenter efficacement la procédure indiqu´ee, en r´ealisant une complexit´e au pire minimum pour l’ensemble du traitement. Pour cela, à chaque étape de la procédure (et surtout dans le choix de la feuille) vous devez choisir et implémenter avec soin vos structures de données.
