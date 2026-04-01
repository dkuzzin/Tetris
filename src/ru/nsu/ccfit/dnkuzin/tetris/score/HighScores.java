package ru.nsu.ccfit.dnkuzin.tetris.score;

import ru.nsu.ccfit.dnkuzin.tetris.exceptions.HighScoreFileException;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class HighScores {

    private static List<HighScoresEntry> entries;
    private static final int maxSize = 10;
    private final String dataPath = "data/highscores.csv";

    public HighScores(){
        try{
            entries = new ArrayList<>();
            load();
        } catch (HighScoreFileException e) {
            System.err.println("IO error. Cannot read data " + e.getMessage());
        }

    }
    public static int getMaxRecord(){
        return maxSize;
    }
    private void load() throws HighScoreFileException{
        Path path = Path.of(dataPath);
        if (!Files.exists(path)) return;
        try(BufferedReader reader = Files.newBufferedReader(path)){
            String line;
            while ((line = reader.readLine()) != null){
                String[] parts = line.split(";");
                if (parts.length != 2){
                    System.err.println("Cannot parse line (wrong format): " + line);
                    continue;
                }

                String name = parts[0].trim();

                int score;
                try{
                    score = Integer.parseInt(parts[1]);
                }catch(NumberFormatException _){
                    System.err.println("Cannot parse line (wrong score): " + line);
                    continue;
                }
                entries.add(new HighScoresEntry(name, score));
            }
            entries.sort((a, b) -> Integer.compare(b.score(), a.score()));
        } catch (IOException e) {
            throw new HighScoreFileException(e.getMessage());
        }
    }
    private void save() throws HighScoreFileException{
        try(BufferedWriter writer = Files.newBufferedWriter(Path.of(dataPath))){
            for (HighScoresEntry i: entries){
                writer.write(i.name() + ";" + i.score() + "\n");
            }
        } catch (IOException e) {
            throw new HighScoreFileException(e.getMessage());
        }
    }
    public static boolean isNameAlreadyExist(String target){
        Record result = entries.stream().filter(e -> e.name().equals(target)).findFirst().orElse(null);
        return result != null;
    }

    public void add(String name, int score){
        System.out.println("ADD CALLED: " + name + " " + score);
        if (entries.size() >= maxSize && score <= entries.getLast().score()){
            return;
        }

        HighScoresEntry record =  new HighScoresEntry(name, score);
        entries.add(record);
        entries.sort((a, b) -> Integer.compare(b.score(), a.score()));

        if (entries.size() > maxSize){
            entries.removeLast();
        }

        try{
            save();
        } catch (HighScoreFileException e) {
            System.err.println("IO error. Cannot read data " + e.getMessage());
        }
        load();
        System.out.println(entries);
    }

    public List<HighScoresEntry> getEntries(){
        return List.copyOf(entries);
    }
    public boolean isRecord(int score){
        return entries.size() < maxSize || entries.getLast().score() < score;
    }
}