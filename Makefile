
all:
	javac rompecabezas.java

run: all
	java rompecabezas

.PHONY: clean

clean:
	rm -f *.class