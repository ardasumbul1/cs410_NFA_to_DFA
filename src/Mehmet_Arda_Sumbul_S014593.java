import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;


public class Mehmet_Arda_Sumbul_S014593{
    public ArrayList<String> alphabet_NFA = new ArrayList<String>();
    public ArrayList<String> states_NFA = new ArrayList<String>(); 
    public ArrayList<String> start_NFA = new ArrayList<String>();
    public ArrayList<String> final_NFA = new ArrayList<String>();
    public HashMap<String, String> transitions_NFA  = new HashMap<String, String>();
    public String file_name;
    HashMap<String, String> transitions_DFA  = new HashMap<String,String>();
    ArrayList<String> states_DFA = new ArrayList<String>();
    public Mehmet_Arda_Sumbul_S014593(String name){
        file_name = name;
    }
    public String read_file() {
        String empty_string = "";
        try{
            FileInputStream input_file = new FileInputStream(file_name);

            Scanner in = new Scanner(input_file);

            while(in.hasNext()){
                String text = in.nextLine();
                empty_string += text + ";";
            }
            in.close();
        } catch(FileNotFoundException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return empty_string;
    }
    public void read_and_construct() {

        String string_to_parse = read_file();
        String[] elements = string_to_parse.split(";");
        int alphabet_index = 0;
        int states_index = 0;
        int start_index = 0;
        int final_index = 0;
        int transition_index = 0;
        int end_index = elements.length-1;

        for (int i = 0; i<elements.length; i++){
            if(elements[i].equals("ALPHABET")){
                alphabet_index = i;
            }
            if(elements[i].equals("STATES")){
                states_index = i;
            }
            if(elements[i].equals("START")){
                start_index = i;
            }
            if(elements[i].equals("FINAL")){
                final_index = i;
            }
            if(elements[i].equals("TRANSITIONS")){
                transition_index = i;
            }
           
        }
        for(int i=alphabet_index+1; i<states_index; i++){
            this.alphabet_NFA.add(elements[i]);
        }
        for(int i= states_index+1; i< start_index; i++){
            this.states_NFA.add(elements[i]);
        }
        for(int i= start_index+1; i< final_index; i++){
            this.start_NFA.add(elements[i]);
        }
        for(int i= final_index+1; i< transition_index; i++){
            this.final_NFA.add(elements[i]);
        }
        for(int i= transition_index+1; i<end_index; i++){
            String temp_value = "";
            String key = elements[i].charAt(0)+Character.toString(elements[i].charAt(2));
            
            if(transitions_NFA.containsKey(key)){
                temp_value = transitions_NFA.get(key) + Character.toString(elements[i].charAt(4));    
                transitions_NFA.put(key,temp_value);
            }
            else{
                transitions_NFA.put(key,Character.toString(elements[i].charAt(4)));
            }
        }
    }
    public String removeDuplicates(String key_word){
        StringBuilder string_bulider_to_return = new StringBuilder(key_word);
        for(int i = 0; i < string_bulider_to_return.length()-1;i++){
            for(int j = i+1; j < string_bulider_to_return.length();j++){
                if(string_bulider_to_return.charAt(i) == string_bulider_to_return.charAt(j)){
                    string_bulider_to_return.deleteCharAt(j);
                }    
            }
        }

        return string_bulider_to_return.toString();
    }

    public void convert_FA(){
        states_DFA.add(start_NFA.get(0));
        int i = 0;
        while(i<states_DFA.size()){

            for(int k = 0; k<2;k++){
                String state =states_DFA.get(i);
                String temp = state +Integer.toString(k);
                String temp_state ="";
                if(transitions_NFA.get(state+Integer.toString(k))!= null){
                    temp_state = transitions_NFA.get(state+Integer.toString(k));
                }

                String temp_string="";                  
                if(!states_DFA.contains(temp_state)){

                    states_DFA.add(temp_state);
                }
                
                if(state.length() == 1){

                    if(transitions_DFA.containsKey(temp)){
                        temp_string = transitions_DFA.get(temp) + transitions_NFA.get(temp);
                        temp_string = removeDuplicates(temp_string);
                        transitions_DFA.put(temp,temp_string);    
                    }
                    else{
                        transitions_DFA.put(temp,transitions_NFA.get(temp)); 
                    }
                }                 
                if((state.length() > 1)) {
                    for(int j = 0; j<state.length();j++){
                        String letter = Character.toString(temp.charAt(j));
                        if(transitions_NFA.containsKey(letter+Integer.toString(k))){
                            temp_string = temp_string + transitions_NFA.get(letter+Integer.toString(k));
                        }
                    }
                    temp_string = removeDuplicates(temp_string);
                    transitions_DFA.put(temp,temp_string);  

                    if(!states_DFA.contains(temp_string)){
                        states_DFA.add(temp_string);
                    }
                                 
                }
                }   
                i++;
            }
        }
        public void print_DFA(){
            System.out.println("ALPHABET");
            for(int i = 0; i<alphabet_NFA.size(); i++){
            System.out.println(alphabet_NFA.get(i));
            }
            System.out.println("STATES");
            for(int j = 0; j<states_DFA.size();j++){
                if(states_DFA.get(j)!=""){
                System.out.println(states_DFA.get(j));
                }
            }
            System.out.println("START");
            System.out.println(start_NFA.get(0));
            System.out.println("FINAL");
            for(int k = 0; k<states_DFA.size(); k++){
                if(states_DFA.get(k).contains(final_NFA.get(0))){
                    System.out.println(states_DFA.get(k));
                }
            }
            System.out.println("TRANSITIONS");
            for(int t = 0; t<transitions_DFA.size(); t ++){
                if(transitions_DFA.values().toArray()[t]==null){
                    continue;
                }
                else{    
                System.out.println(transitions_DFA.keySet().toArray()[t]+ " " +transitions_DFA.values().toArray()[t]);
                }
            }
            System.out.println("END");
        }
    

public static void main(String[] args){
    Mehmet_Arda_Sumbul_S014593 obj = new Mehmet_Arda_Sumbul_S014593("NFA2.txt"); // CHANGE HERE TO INSERT ANOTHER NFA
    
    obj.read_and_construct();
    obj.convert_FA();
    obj.print_DFA();
}
}