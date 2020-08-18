package com.bilingoal.bilingoalcards.models;

import com.bilingoal.bilingoalcards.database.entities.Word;
import java.util.List;

public class Router {
    private final List<Word> wordList;
    private int position = 0;
    private Word currentWord;

    public Router(List<Word> wordList) {
        this.wordList = wordList;
        this.currentWord = wordList.get(0);
    }

    public boolean hasNextWord() {
        return position < wordList.size() - 1;
    }

    public void toNextWord() {
        position++;
        currentWord = wordList.get(position);
    }

    public Word getCurrentWord() {
        return currentWord;
    }
}