package com.esdeath;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.BufferedReader;

/**
 * Unit test for simple Ngrams.
 */
public class NgramsTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public NgramsTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( NgramsTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testReader()
    {
        Ngrams.setFileName("hamlet.txt");
        BufferedReader reader = Ngrams.getReader();
        assertNotNull(reader);//Test getReader() when filename is correct
        Ngrams.setFileName("hamlet1.txt");
        reader = Ngrams.getReader();
        assertNull(reader);//Test getReader() when filename is not correct
    }

    public void testWordList(){
        Ngrams.setN(3);
        Ngrams.setFileName("hamlet.txt");
        Ngrams.getReader();
        Ngrams.buildWordList();
        assertTrue(Ngrams.getWordList().size()!=0);//Test wordList is built successfully
        assertTrue(Character.isUpperCase(Ngrams.getWordList().getFirst().trim().charAt(0)));//Test the order of words in wordList is true.
    }

    public void testWordMap(){
        Ngrams.setN(3);
        Ngrams.setFileName("hamlet.txt");
        Ngrams.setReader(Ngrams.getReader());
        Ngrams.buildWordList();
        Ngrams.buildWordMap();
        assertTrue(!Ngrams.getWordMap().keySet().isEmpty());//Test next_word_map's keyset
        assertTrue(!Ngrams.getWordMap().values().isEmpty());//Test next_word_map's valueset
        //Test the number of words in key should be value of N
        assertTrue(Ngrams.getWordMap().keySet().iterator().next().trim().split(" ").length == 3);
        //Test the number of words in value should be greater than 0
        assertTrue(Ngrams.getWordMap().values().iterator().next().size()!=0);
        //Test the Strings in value should be a word
        assertTrue(Ngrams.getWordMap().values().iterator().next().get(0).split(" ").length == 1);
    }
}
