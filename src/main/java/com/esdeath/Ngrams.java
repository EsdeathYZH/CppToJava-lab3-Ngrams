package com.esdeath;

 import java.io.BufferedReader;
 import java.io.File;
 import java.io.FileNotFoundException;
 import java.io.FileReader;
 import java.util.*;


public class Ngrams
{
    private static int value_n;
    private static String filename;
    private static BufferedReader bufferedReader = null;
    private static LinkedList<String> word_list;
    private static HashMap<String,ArrayList<String>> next_word_map;
    private static ArrayList<String>keystring_list;

    public static void main( String[] args )
    {
        //User input N
        setN(inputN());
        setFileName(inputFilename());
        setReader(getReader());

        while(bufferedReader == null) {
            System.out.println("Can't open this file!");
            setReader(getReader());
        }

        buildWordList();
        buildWordMap();

        int word_num;
        Scanner scanner = new Scanner(System.in);
        //Main process:random write
        while (true){
            System.out.print("# of random words to generate(0 to quit)?");
            word_num = scanner.nextInt();
            // Quit if input 0
            if(word_num == 0) break;
            while(word_num <= value_n){
                System.out.println("The number of random words should be greater than N!");
                System.out.print("# of random words to generate(0 to quit)?");
                word_num = scanner.nextInt();
            }
            randomWrite(word_num);
        }
        System.out.println("Exiting...");
        return ;
    }

    public static void randomWrite(int word_num){
        //Use nano time as random number seed,generate a Random object
        long seed = System.nanoTime();
        Random random= new Random(seed);
        int random_num = random.nextInt(keystring_list.size());
        //If the begin char of string is not uppercase,repeat
        char start_char = keystring_list.get(random_num).charAt(0);
        while(!Character.isUpperCase(start_char))
        {
            random_num = random.nextInt(keystring_list.size());
            start_char = keystring_list.get(random_num).charAt(0);
        }
        //generate words
        String current_word = keystring_list.get(random_num);

        String end_word = "";
        for(int i=0; i<word_num; i++){
            ArrayList<String> word_n = next_word_map.get(current_word);
            random_num = random.nextInt(word_n.size());
            String next_word = word_n.get(random_num);

            String[]next_word_list = current_word.split(" ");
            end_word =next_word_list[0];
            System.out.print(end_word+" ");
            current_word="";
            for(int j=1; j<next_word_list.length; j++){
                current_word += next_word_list[j]+" ";
            }
            current_word += next_word+" ";
        }
        //If end_word don't end with '?' ,'!' or '.',Continue to generate words.
        char end_char = end_word.charAt(end_word.length()-1);
        while(end_char!='?'&&end_char!='!'&&end_char!='.'){
            ArrayList<String> word_n = next_word_map.get(current_word);
            random_num = random.nextInt(word_n.size());
            String next_word = word_n.get(random_num);

            String[]next_word_list = current_word.split(" ");
            end_word =next_word_list[0];
            System.out.print(end_word+" ");
            current_word="";
            for(int j=1; j<next_word_list.length; j++){
                current_word += next_word_list[j]+" ";
            }
            current_word += next_word+" ";
            end_char = end_word.charAt(end_word.length()-1);
        }
        System.out.println();
    }

    public static BufferedReader getReader(){
        //build file reader
        File file = new File(filename);
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            return bufferedReader;
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static String inputFilename(){
        System.out.print("Please input file name:");
        Scanner scanner = new Scanner(System.in);
        String filename = scanner.nextLine();
        return filename;
    }

    public static int inputN(){
        int n = 0;
        while(n <= 0){
            System.out.print("Please input the value of N:");
            Scanner scanner = new Scanner(System.in);
            n = scanner.nextInt();
            if(n <= 0) System.out.println("The value of N must be greater than 0!");
        }
        return n;
    }

    public static void buildWordList(){
        word_list = new LinkedList<String>();
        //read file and build word_list
        String temp = null;
        try{
            while((temp=bufferedReader.readLine())!=null){
                temp = temp.trim();//Trim every line
                if(temp.length()==0) continue;
                String[] words = temp.split(" ");
                for(int i=0; i<words.length; i++) {
                    word_list.add(words[i]);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void buildWordMap(){
        //Build three-words to one-word map
        next_word_map = new HashMap<String,ArrayList<String>>();
        keystring_list = new ArrayList<String>();
        int index = 0;
        for(int i=0; i<word_list.size(); i++){
            String key_string = "";
            int temp_index = index;
            for(int j=0; j<value_n; j++){
                key_string += (word_list.get(temp_index)+" ");
                temp_index++;
                if(temp_index == word_list.size()) temp_index = 0;
            }
            if(next_word_map.containsKey(key_string)){
                next_word_map.get(key_string).add(word_list.get(temp_index));
            }else{
                ArrayList<String> temp_array = new ArrayList<String>();
                temp_array.add(word_list.get(temp_index));
                next_word_map.put(key_string,temp_array);
            }
            keystring_list.add(key_string);
            index++;
        }
    }

    public static void setN(int n){
        value_n = n;
    }

    public static void setFileName(String name){
        filename = name;
    }

    public static void setReader(BufferedReader reader){
        bufferedReader = reader;
    }
    public static LinkedList<String> getWordList(){
        return word_list;
    }

    public static HashMap<String, ArrayList<String>> getWordMap(){
        return next_word_map;
    }
}
