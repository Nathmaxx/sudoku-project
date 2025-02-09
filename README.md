# Projet Sudoku Solver

## Prérequis

Avant de lancer le projet, assurez-vous d'avoir les éléments suivants installés sur votre machine :

- **Java Development Kit (JDK) 11 ou supérieur**
- **JavaFX SDK** (compatible avec votre version de JDK)

## Installation

### 1. Installation de JavaFX

Si JavaFX n'est pas inclus dans votre JDK, téléchargez-le depuis :  
[JavaFX SDK - Gluon](https://gluonhq.com/products/javafx/)

Extrayez l'archive dans un répertoire accessible, par exemple :

- `C:\javafx` sous **Windows**
- `~/javafx` sous **Linux/Mac**

### 2. Ajout de JavaFX au projet

Si vous utilisez un IDE comme **IntelliJ IDEA** ou **Eclipse** :

1. Ajoutez JavaFX comme une bibliothèque externe en incluant les fichiers JAR situés dans `lib` du dossier JavaFX.
2. Ajoutez les arguments VM suivants lors de l'exécution du programme :

#### Windows :

```sh
--module-path "C:\javafx\lib" --add-modules javafx.controls,javafx.fxml
```

#### Linux/Mac :

```sh
--module-path ~/javafx/lib --add-modules javafx.controls,javafx.fxml
```

## Compilation et Exécution

### Avec IntelliJ IDEA :

1. Ouvrez le projet.
2. Configurez **JavaFX** dans les paramètres du projet.
3. Ajoutez les arguments VM mentionnés ci-dessus.
4. Exécutez la classe principale contenant `public static void main(String[] args)`.

### Avec la ligne de commande :

#### Compilation :

```sh
javac --module-path "path/to/javafx" --add-modules javafx.controls,javafx.fxml -d out $(find src -name "*.java")
```

#### Exécution :

```sh
java --module-path "path/to/javafx" --add-modules javafx.controls,javafx.fxml -cp out Main
```
