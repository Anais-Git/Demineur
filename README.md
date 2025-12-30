# Démineur en Java

Un jeu de Démineur écrit en Java avec une interface graphique (Swing).

## Fonctionnalités

- Génération automatique de la grille sans bombe au début
- Premier clic toujours sûr (aucune bombe dans la zone autour)
- Génération automatique des bombes

- Cliquez sur une case pour la révéler.
    - Si elle contient une bombe, la partie est perdue.
    - Si elle est vide, les cases voisines sont automatiquement révélées.

- Détection de victoire ou de défaite : gagnez en révélant toutes les cases sans bombe.
- Interface utilisateur avec Swing


## Structure du projet
```
Démineur/
├── lib/
│ └── Demineur.java # Logique principale du jeu
│ └── AbstractCell.java # Représentation d'une cellule
├── exceptions/
│ └── InvalidMoveException.java
│ └── GameLostException.java
│ └── GameWonException.java
├── gui/
│ └── DemineurUI.java # Interface graphique Swing
├── Application.java # Programme
└── README.md
```

## Choix des classes

J'ai choisi de créer uniquement 2 classes, AbstractCell (qui est concrète) et Demineur. Pour ne pas différencier en 2 classes les cellules bombes et celles de base, j'ai choisi de créer plutôt une variable "bomb" qui défini si la cellule est une bombe ou non. Cela me paraissait plus simple.
La classe Demineur défini toutes les fonctionnalités du jeu.

## Lancer le projet


### Compilation

javac exceptions/*.java lib/*.java gui/*.java Application.java

### Execution

java Application.java

