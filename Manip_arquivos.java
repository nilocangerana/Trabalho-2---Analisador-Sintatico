import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Manip_arquivos {
	
	public static String leitor(String path) throws IOException { //L� o arquivo de entrada
        BufferedReader buffRead = new BufferedReader(new FileReader(path));
        String linha = "";
        String codigoEntrada = "";
        
        while (true) {
        	linha = buffRead.readLine();
            if (linha != null) {	//Gera uma string com o arquivo inteiro lido.
            	linha=linha+"\n";
                codigoEntrada = codigoEntrada.concat(linha);
  
            } else
                break;
        }
        buffRead.close();
        codigoEntrada = codigoEntrada.substring(0, codigoEntrada.length()-1);
        return codigoEntrada;	//A string gerada � armazenada na vari�vel codigoEntrada que � retornada ao programa principal.
    }
	
	
	public static void escritor(String path,ArrayList<String> listaSaida) throws IOException { //Escreve o arquivo de sa�da
        BufferedWriter buffWrite = new BufferedWriter(new FileWriter(path));
       Iterator<String> iter = listaSaida.iterator(); 
       while (iter.hasNext()) { 
    	   buffWrite.append(iter.next() + "\n");
       } 
       buffWrite.close();
    }
	
	
}
