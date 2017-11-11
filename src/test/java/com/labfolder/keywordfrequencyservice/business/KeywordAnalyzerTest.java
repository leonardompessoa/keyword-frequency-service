package com.labfolder.keywordfrequencyservice.business;

import com.labfolder.business.KeywordAnalyzer;
import com.labfolder.domain.KeywordFrequency;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KeywordAnalyzerTest {

    @Autowired
    private KeywordAnalyzer keywordAnalyzer;

    @Test
    public void analyze1Frequency3Similar() throws Exception {

        String keyword = "Word";
        String entryStr = "Word Words Wor word";
        Set expectedSet = new HashSet();

        expectedSet.add("Words");
        expectedSet.add("word");
        expectedSet.add( "Wor");

        InputStream entry = new ByteArrayInputStream(entryStr.getBytes(StandardCharsets.UTF_8.name()));

        KeywordFrequency keywordFrequency = keywordAnalyzer.analyze(entry, keyword);

        assertEquals(1, keywordFrequency.getFrequency());
        assertEquals(3, keywordFrequency.getSimilarWords().length);
        for(String similar : keywordFrequency.getSimilarWords()) {
            assertTrue(expectedSet.contains(similar));
        }

    }

    @Test
    public void analyze3FrequencyNoSimilar() throws Exception {

        String keyword = "Word";
        String entryStr = "Word Word Word";
        InputStream entry = new ByteArrayInputStream(entryStr.getBytes(StandardCharsets.UTF_8.name()));

        KeywordFrequency keywordFrequency = keywordAnalyzer.analyze(entry, keyword);

        assertEquals(3, keywordFrequency.getFrequency());
        assertEquals(0, keywordFrequency.getSimilarWords().length);
        assertArrayEquals(new String[]{}, keywordFrequency.getSimilarWords());

    }

    @Test
    public void analyzeNoFrequencyNoSimilar() throws Exception {

        String keyword = "Word";
        String entryStr = "Apple Orange Pineapple";
        InputStream entry = new ByteArrayInputStream(entryStr.getBytes(StandardCharsets.UTF_8.name()));

        KeywordFrequency keywordFrequency = keywordAnalyzer.analyze(entry, keyword);

        assertEquals(0, keywordFrequency.getFrequency());
        assertEquals(0, keywordFrequency.getSimilarWords().length);
        assertArrayEquals(new String[]{}, keywordFrequency.getSimilarWords());

    }

    @Test
    public void analyzeNoFrequency3Similar() throws Exception {

        String keyword = "word";
        String entryStr = "Word wor words";
        Set expectedSet = new HashSet();

        expectedSet.add("Word");
        expectedSet.add("words");
        expectedSet.add( "wor");

        InputStream entry = new ByteArrayInputStream(entryStr.getBytes(StandardCharsets.UTF_8.name()));

        KeywordFrequency keywordFrequency = keywordAnalyzer.analyze(entry, keyword);

        assertEquals(0, keywordFrequency.getFrequency());
        assertEquals(3, keywordFrequency.getSimilarWords().length);
        for(String similar : keywordFrequency.getSimilarWords()) {
            assertTrue(expectedSet.contains(similar));
        }


    }
}
