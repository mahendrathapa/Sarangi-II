# Sarangi
Sarangi is a Music Classification Application that can classify mp3 files based on Genre and Mood.
The feature extraction necessary for the classification is also part of this project. 

## Compilation

Assuming mvn is installed on your machine, just move into the `App` folder and run `mvn compile`

```
cd App/
mvn compile
```

## Creating a jar file

In the `App` folder, run `mvn package assembly:single`
```
mvn package assembly:single
```

## Running the application
Assuming you have all necessary jar files in `target/`, you can run one of four commands:
* `extract`: Extract features of songs in given folder.

```
java -cp ".:target/*" com.sarangi.app.App extract -F SongFolder -f features.txt
```

* `train`: Train a classifier from the extracted features.

```
java -cp ".:target/*" com.sarangi.app.App train -f features.txt -c classifier.txt -C SVM -l 0
```
 - Option "-C"
   - SVM: Support Vector Machine
   - ANN: Artificial Neural Network
 - Option "-l" 
   - 0: Genre
   - 1: Arousal
   - 2: Valence
    
* `test`: Run K-fold validation on the given song features.

```
java -cp ".:target/*" com.sarangi.app.App test -k features.txt -C SVM 
```

* `classify`:

```
java -cp ".:target/*" com.sarangi.app.App classify -c classifier.txt -C SVM -f awesomeSong.mp3
```

Or just type `--help`

```
java -cp ".:target/*" com.sarangi.app.App --help
```
# Running the jar
If you have created the jar.
```
java -jar App-2.0-jar-with-dependencies.jar
```
