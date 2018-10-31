package com.labfolder.business;

import com.labfolder.domain.KeywordFrequency;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service("keywordAnalyzer")
public class KeywordAnalyzerImpl implements KeywordAnalyzer {

    @Override
    public KeywordFrequency analyze(final InputStream noteEntry, final String keyword) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(noteEntry));
        Function<String, Stream<String>> splitIntoWords = line -> {
            line = line.trim()
                    .replaceAll("\\.", "")
                    .replaceAll("\\,", "")
                    .replaceAll("\\!", "")
                    .replaceAll("\\?", "");
            return Pattern.compile(" ").splitAsStream(line);
        };

        List<String> listOfWords = reader.lines()
                .flatMap(splitIntoWords)
                .collect(Collectors.toList());

        Set<String> similarWords = listOfWords.stream()
                .filter(word -> !word.equals(keyword))
                .filter(word -> StringUtils.getLevenshteinDistance(keyword, word) <= 1)
                .collect(Collectors.toSet());

        Long equalWords = listOfWords.stream()
                .flatMap(splitIntoWords)
                .filter(word -> word.equals(keyword))
                .collect(Collectors.counting());


        String[] similar = similarWords.toArray(new String[similarWords.size()]);
        return new KeywordFrequency(equalWords.intValue(), similar);
    }

}


