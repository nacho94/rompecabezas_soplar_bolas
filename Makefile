
all:
	javac rompecabezas.java Tablero.java Celda.java

run: all
	java rompecabezas < entrada.txt

.PHONY: clean

clean:
	rm -f *.class
