package com.labfolder.business;

import com.labfolder.domain.KeywordFrequency;

import java.io.InputStream;

public interface KeywordAnalyzer {
    KeywordFrequency analyze(InputStream noteEntry, String keyword);
}
