/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculadoraalonsomtz;

import java.util.Stack;

/**
 *
 * @author downvec
 */
public class MySolver {
    
    
    public static boolean checarSintaxis(String input){
        String c, c1; 
        int indexLastClose = 0; 
        int indexLastOpen = 0; 
        boolean esValido = true; 
        boolean parentesisSimetrico = true; 
        
        String[] inputSplit = input.trim().split(" "); 
        
        //Si está vacio, no es expresion válida, 
        if (inputSplit.length <=0) {
            
            calculadoraUI.refreshDisplay("Error. Operacion vacia"); 
            return false; 
        }
        
        //Si el primer o ultimo caracter son operadores, no tiene operando: error
        if (MyUtils.isOperador(inputSplit[0]) || MyUtils.isOperador(inputSplit[inputSplit.length -1]) ){
            esValido = false; 
           
            calculadoraUI.refreshDisplay("Error. Operador sin operando inmediato"); 
        }
        
        //Checar caracter por caracter
        for (int i=0; i < inputSplit.length; i++){
            c = inputSplit[i]; 
            
            //Obtiene el caracter siguiente al detectar que es operador. Si tambien es operador: error
            if (i < inputSplit.length-1 && MyUtils.isOperador(c) ){
                c1 = inputSplit[i+1]; 
                if(MyUtils.isOperador(c1)){
                    esValido = false; 
                    calculadoraUI.refreshDisplay("Error. Dos operadores contiguos"); 
                }
            }

            //Loop que inicia si se detecta (. Para hasta encontrar ). Si no se encuentra: error. 
            if (c.equals("(") && parentesisSimetrico){
                indexLastOpen = i;  
                for (int j = i+1; j < inputSplit.length; j++){
                    if (inputSplit[j].equals(")") && j > indexLastClose){
                        indexLastClose = j; 
                        parentesisSimetrico = true; 
                        break; 
                    }else{
                        parentesisSimetrico = false;
                    }
                }
            }
            
            //Loop que inicia si de detecta ). Para hasta encontrar (. Si no se encuentra: error. 
            if (c.equals(")") && parentesisSimetrico){
                for (int j = i; j >= 0; j--){
                    if (inputSplit[j].equals("(") && j <= indexLastOpen){
                        indexLastOpen = j; 
                        parentesisSimetrico = true; 
                        break;
                    }else{
                        parentesisSimetrico = false;
                    }
                }
            }
            
            
        }

        if (!parentesisSimetrico){
            calculadoraUI.refreshDisplay("Error. Parentesis desbalanceado"); 
            esValido = false; 
        }
        
        return esValido; 
    }
    
    
    public static String convierteAPostfijo(String inputInfix){
        //TODO: Tomar lo que este entre espcacios como numeros individuales. 
        String[] inputSplit = inputInfix.trim().split("");         
        
        Stack<String> pilaOperadores = new Stack<String>(); 
        String resultadoPostfijo = ""; 
        
        for (int j = 0; j < inputSplit.length ; j++){
            String ch = inputSplit[j]; 
            
            if(MyUtils.isNumero(ch) || MyUtils.isPuntoDecimal(ch) || ch.equals(" ") || ch.equals("~")){
                resultadoPostfijo = resultadoPostfijo + ch; 
            } else if (MyUtils.isOperador(ch)){
                while (!pilaOperadores.isEmpty() && MyUtils.getPrioridad(ch) <= MyUtils.getPrioridad(pilaOperadores.peek())){
                    resultadoPostfijo = resultadoPostfijo + " " + pilaOperadores.pop(); 
                }
                pilaOperadores.push(ch); 
            } else if (ch.equals("(")){
                pilaOperadores.push(ch); 
            } else if (ch.equals(")")){
                while (!pilaOperadores.isEmpty() && !pilaOperadores.peek().equals("(")){
                    resultadoPostfijo = resultadoPostfijo + pilaOperadores.pop(); 
                }
                pilaOperadores.pop(); 
                
            }

        }
        
        while(!pilaOperadores.isEmpty()){
            resultadoPostfijo = resultadoPostfijo + " " + pilaOperadores.pop(); 
        }

        return resultadoPostfijo; 
        
    }
    
    
    public static double evaluaPostfijo(String inputPostfijo){
        String[] inputProcesado = inputPostfijo.split(" "); 
        
        Stack<Double> pilaOperandos = new Stack<Double>(); 
        double operando1 = 0; 
        double operando2 = 0; 
        double resultado = 0; 
        

        for (int i=0; i < inputProcesado.length ; i++){
            String c = inputProcesado[i];

            if (!c.equals("")){
                System.out.println(c); 
                System.out.println(String.valueOf(c.charAt(0))); 
                if(!MyUtils.isOperador(c)){
                    if (String.valueOf(c.charAt(0)).equals("~")){
                        
                        char[] newCharList = c.toCharArray(); 
                        newCharList[0] = '-';
                      
                        pilaOperandos.push(Double.parseDouble(String.valueOf(newCharList))); 
                    }else{
                        pilaOperandos.push(Double.parseDouble(c)); 
                    }
                    
                }else {
                    operando1 = (double) pilaOperandos.pop(); 
                    operando2 = (double) pilaOperandos.pop();
                    resultado = ejecutarOperacion(c, operando1, operando2); 
                    pilaOperandos.push(resultado); 
                }
            }
        }
        
        double resultadoFin = (double) pilaOperandos.pop(); 
       
        return resultadoFin; 
    }
    
    public static double ejecutarOperacion(String operador, double operando1, double operando2){
        double res = 0; 
        switch(operador){
            case ("+"): 
                res = operando1 + operando2; 
                break; 
            case ("-"): 
                res = operando1 - operando2; 
                break; 
            case ("*"): 
                res = operando1 * operando2; 
                break; 
            case ("/"): 
                if (operando2 != 0){
                    res = operando1 / operando2; 
                }else {
                    calculadoraUI.refreshDisplay("División entre 0 no definida");  
                }
        }
        return res; 
    }
 
  
    
}
