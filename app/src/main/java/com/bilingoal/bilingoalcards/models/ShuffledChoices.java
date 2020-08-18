package com.bilingoal.bilingoalcards.models;

import com.bilingoal.bilingoalcards.database.entities.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShuffledChoices {
    private final List<Word> words;
    private final String currentEngWord;

    private ShuffledChoices(List<Word> words, String currentEngWord) {
        this.words = words;
        this.currentEngWord = currentEngWord;
    }

    public List<String> toList() {
        List<String> list = new ArrayList<>();

        for(Word word : words) {
            if(!word.getEng().trim().toLowerCase().equals(currentEngWord.trim().toLowerCase())) {
                list.add(word.getEng());
            }
        }
        Collections.shuffle(list);

        list = list.subList(0, 2);
        list.add(currentEngWord);
        Collections.shuffle(list);

        return list;
    }

    public static ShuffledChoices create(List<Word> words, String currentEngWord) {
        return new ShuffledChoices(words, currentEngWord);
    }
}
