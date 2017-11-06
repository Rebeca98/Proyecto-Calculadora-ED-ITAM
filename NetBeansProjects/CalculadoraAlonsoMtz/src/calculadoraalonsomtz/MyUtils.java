/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadoraalonsomtz;

import java.util.Arrays;

/**
 *
 * @author downvec
 */
public class MyUtils {
    private static String[] coleccionOperadores = new String[]{"+","-","*","/"};
    private static String[] coleccionNumeros = new String[]{"1","2","3","4","5","6","7","8","9","0"};
    private static String[] coleccionParentesis = new String[]{"(",")"};
    
    
    
    public static  boolean isOperador(String in){
        return (Arrays.asList(coleccionOperadores).contains(in)); 
    }
    
    public static boolean isParentesis(String in){
        return (Arrays.asList(coleccionParentesis).contains(in)); 
    }
    
    public static boolean isPuntoDecimal(String in){
        return (in.equals(".")); 
    }
    
    public static boolean isNumero(String in){
        return (Arrays.asList(coleccionNumeros).contains(in)); 
    }
    
    public static int getPrioridad(String c){
        int ans = 0; 
        switch(c){
            case ("+"): 
                ans = 1; 
                break; 
            case("-"): 
                ans = 1; 
                break;
            case("*"): 
                ans = 2; 
                break; 
            case("/"): 
                ans = 2; 
                break;
        }
        return ans; 
    }
    
}
