package com.pastebin.pastebin;

import java.util.List;

public class LineComparer {

    public static List<Integer> findChangedLineRange(String original, String modified) {
        String[] originalLines = original.split("\n");
        String[] modifiedLines = modified.split("\n");

        int minLen = Math.min(originalLines.length, modifiedLines.length);

        int startLine = -1;
        int endLineOriginal = originalLines.length - 1;
        int endLineModified = modifiedLines.length - 1;

        for (int i = 0; i < minLen; i++) {
            if (!originalLines[i].equals(modifiedLines[i])) {
                startLine = i;
                break;
            }
        }

        if (startLine == -1 && originalLines.length != modifiedLines.length) {
            startLine = minLen;
        }

        while (endLineOriginal >= startLine && endLineModified >= startLine
                && originalLines[endLineOriginal].equals(modifiedLines[endLineModified])) {
            endLineOriginal--;
            endLineModified--;
        }

        List<Integer> result = new java.util.ArrayList<>();
        result.add(startLine);
        if (startLine == -1) {
            return result;
        } else {
            result.add(endLineModified);
            return result;
        }
    }
}
