package com.labfolder.domain;

public class KeywordFrequency {

    private int frequency;
    private String[] similarWords;

    public KeywordFrequency(int frequency, String[] similarWords) {
        this.frequency = frequency;
        this.similarWords = similarWords;
    }


    public int getFrequency() {
        return frequency;
    }


    public String[] getSimilarWords() {
        return similarWords;
    }


}
