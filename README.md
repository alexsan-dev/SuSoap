<div  align="center">
	<h1>SuSoap ® - Java™</h1>
	<p>Juego de sopa de letras multijugador en consola con Java, proyecto 1 de Introducción a la programación y computadoras 1. Primer semestre 2020 USAC Facultad de Ingeniería.</p>
	<img  src="https://img.shields.io/badge/complete-38%25-yellow"/>
	<img src="https://img.shields.io/github/license/alexsan-dev/SuSoap"/>
	<img src="https://img.shields.io/badge/version-0.7-blue"/>
</div>

### Main Class ☕️
Compilar todos los archivos y ejecutar el archivo con la clase main en Soap.java ⚙️

```
    javac *.java
    java Soap
```

### Clase Juego 🎮
El archivo Game.java contiene los metodos para nueva partida dentro del juego, este tendra métodos públicos para acceder al historial de partidas y los puntos de cada jugador.

#### Game.java - Estructura 🏗

 1. Constructor()
 2. setData()
 3. menu()
	 1. insertWordsMenu()
		 1. insertWords()
		 2. updateWords()
		 3. deleteWords() 
	 2. playMenu()
	 3. exitGame
#### Game.java - Flujo 🌊

```
    String words[] 
    String matrix[][]
    
	setData() => matrix[n][n]
	
    while(!exitGame) => menu()
	    clearScreen()
	    print(whitError)
	    
	    while(!breakInsert) => insertWordsMenu()
		    while(!breakInsertWords) => insertWords()
			    while(wordCount < wordsSize) => words[wordCount] = currentWord
		    if(!isEmpty) => 
			    while(!breakSearch) => updateWords()
				    words[foundSearch] = replaceWord;
			    while(!breaDelkSearch) => deleteWords()
				    words[delWordIndex] =  "";
				    
		exitGame = true
```

### MIT LICENSE : 
Alex Santos 2020