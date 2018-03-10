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
        assertNotNull(reader);
        Ngrams.setFileName("hamlet1.txt");
        reader = Ngrams.getReader();
        assertNull(reader);
    }

    public void testWordList(){
        Ngrams.setN(3);
        Ngrams.setFileName("hamlet.txt");
        Ngrams.getReader();
        Ngrams.buildWordList();
    }

}
