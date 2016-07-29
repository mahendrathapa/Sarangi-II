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

In the `App` folder, run `mvn package`
```
mvn package
```

## Running the application
Assuming you have all necessary jar files in `target/`
```
java -cp ".:target/*" com.sarangi.app.App
```
