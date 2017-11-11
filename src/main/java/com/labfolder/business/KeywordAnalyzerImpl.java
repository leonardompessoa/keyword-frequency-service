package com.labfolder.business;

import com.labfolder.domain.KeywordFrequency;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

@Service("keywordAnalyzer")
public class KeywordAnalyzerImpl implements KeywordAnalyzer {

    @Override
    public KeywordFrequency analyze(final InputStream noteEntry, final String keyword) {

        Pattern pattern = Pattern.compile(keyword);
        final int [] count = {0};
        Set<String> similarWords = new HashSet<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(noteEntry));
        reader.lines().forEach(
                (line) -> {
                    String [] words = line.trim().split(("\\s+"));
                    for(String word : words) {
                        word = word.replaceAll("\\.","");
                        word = word.replaceAll("\\,","");
                        word = word.replaceAll("\\!","");
                        word = word.replaceAll("\\?","");
                        if(word.equals(keyword)){
                            count[0]++;
                        } else if(StringUtils.getLevenshteinDistance(keyword, word) <= 1 ) {
                            similarWords.add(word);
                        }
                    }
                }
        );
        String [] similar = similarWords.toArray(new String[similarWords.size()]);
        return new KeywordFrequency(count[0], similar);
    }

}


