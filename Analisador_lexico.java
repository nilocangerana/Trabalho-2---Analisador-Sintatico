import java.util.ArrayList;
import java.util.Hashtable;


public class Analisador_lexico {
	private static Hashtable<String, String> tabelaSimbReserv;
	private static Hashtable<String, String> tabelaSimb;
	private static ArrayList<String> saida;
	
	public static void inicializarTabelas() //Gera as tabelas de Simbolos e Simbolos Reservados do programa.
	{
		tabelaSimb = new Hashtable<String, String>();
		tabelaSimbReserv = new Hashtable<String, String>();
		saida = new ArrayList<String>();
		
		tabelaSimbReserv.put("program", "simb_program");
		tabelaSimbReserv.put("var", "simb_var");
		tabelaSimbReserv.put("begin", "simb_begin");
		tabelaSimbReserv.put("integer", "simb_integer");
		tabelaSimbReserv.put("real", "simb_real");
		tabelaSimbReserv.put("while", "simb_while");
		tabelaSimbReserv.put("read", "simb_read");
		tabelaSimbReserv.put("write", "simb_write");
		tabelaSimbReserv.put("if", "simb_if");
		tabelaSimbReserv.put("end", "simb_end");
		tabelaSimbReserv.put("else", "simb_else");
		tabelaSimbReserv.put("procedure", "simb_proced");
		tabelaSimbReserv.put("then", "simb_then");
		tabelaSimbReserv.put("const", "simb_const");
		tabelaSimbReserv.put("do", "simb_do");
		tabelaSimbReserv.put("for", "simb_for");
		tabelaSimbReserv.put("to", "simb_to");
		
		tabelaSimbReserv.put(":=", "simb_atrib");
		tabelaSimbReserv.put(":", "simb_dp");
		tabelaSimbReserv.put(">=", "simb_maior_igual");
		tabelaSimbReserv.put("<=", "simb_menor_igual");
		tabelaSimbReserv.put("<>", "simb_dif");
		tabelaSimbReserv.put("<", "simb_menor");
		tabelaSimbReserv.put(">", "simb_maior");
		
		tabelaSimbReserv.put("+", "simb_mais");
		tabelaSimbReserv.put("-", "simb_menos");
		tabelaSimbReserv.put("*", "simb_mult");
		tabelaSimbReserv.put("/", "simb_div");
		
		tabelaSimbReserv.put("=", "simb_igual");
		tabelaSimbReserv.put(";", "simb_pv");
		tabelaSimbReserv.put(",", "simb_vi");
		tabelaSimbReserv.put(".", "simb_pnt");
		tabelaSimbReserv.put("(", "simb_abreprt");
		tabelaSimbReserv.put(")", "simb_fechaprt");
		tabelaSimbReserv.put("{", "simb_abreachv");
		tabelaSimbReserv.put("}", "simb_fechachv");
	}
	
	public static void addTabelaSimb(String nome) //Adiciona o nome da variavel na tabela de simbolos.
	{
		tabelaSimb.put(nome, "id");
	}
	
	public static String getValorSimbReserv(String key) //Retorna o valor da tabela de simbolos reservados a partir da chave
	{
		return tabelaSimbReserv.get(key);
	}
	
	public static String getValorSimb(String key) //Retorna o valor da tabela de simbolos a partir da chave
	{
		return tabelaSimb.get(key);
	}
	
	public static ArrayList<String> getListaSaida()
	{
		return saida;
	}
	
	
	public static ArrayList<String> analisadorLexico(String input) //Processa a string de entrada do programa lido.
	{															   //Retorna uma array list com todas as palavras e seus respectivos tokens, na ordem que foram lidas do programa.
		String chave = "";
		char c;
		int i=0;
		int numeroDaLinha=1;
		
		while(i<input.length()) //i varia até todos caracteres do input terem sido lidos.
		{
			c=input.charAt(i);
			chave="";
			switch (c)
			{
			case ' ': //Consome o caracter ' ' espaço em branco
				i++;
				break;
			case '\t': //Consome o caracter de tab \t
				i++;
				break;
			case '\n': //Consome o caracter de nova linha \n
				i++;
				numeroDaLinha++;
				break;
			case ';':	//Processa Símbolos Unitários
				chave=chave+c;
				saida.add(chave + "," + tabelaSimbReserv.get(chave)+","+numeroDaLinha);
				i++;
				break;
			case '.':
				chave=chave+c;
				saida.add(chave + "," + tabelaSimbReserv.get(chave)+","+numeroDaLinha);
				i++;
				break;
			case '=':
				chave=chave+c;
				saida.add(chave + "," + tabelaSimbReserv.get(chave)+","+numeroDaLinha);
				i++;
				break;
			case '+':
				chave=chave+c;
				saida.add(chave + "," + tabelaSimbReserv.get(chave)+","+numeroDaLinha);
				i++;
				break;
			case '-':
				chave=chave+c;
				saida.add(chave + "," + tabelaSimbReserv.get(chave)+","+numeroDaLinha);
				i++;
				break;
			case '*':
				chave=chave+c;
				saida.add(chave + "," + tabelaSimbReserv.get(chave)+","+numeroDaLinha);
				i++;
				break;
			case '/':
				chave=chave+c;
				saida.add(chave + "," + tabelaSimbReserv.get(chave)+","+numeroDaLinha);
				i++;
				break;
			case '(':
				chave=chave+c;
				saida.add(chave + "," + tabelaSimbReserv.get(chave)+","+numeroDaLinha);
				i++;
				break;
			case ')':
				chave=chave+c;
				saida.add(chave + "," + tabelaSimbReserv.get(chave)+","+numeroDaLinha);
				i++;
				break;
			case ',':
				chave=chave+c;
				saida.add(chave + "," + tabelaSimbReserv.get(chave)+","+numeroDaLinha);
				i++;
				break;
			case ':': //Processa Símbolos Compostos
				chave=chave+c;
				i++;
				if(i<input.length())
				{
					c=input.charAt(i);
				}
				if(c=='=') // operador lido é o :=
				{
					chave=chave+c;
					saida.add(chave + "," + tabelaSimbReserv.get(chave)+","+numeroDaLinha);
					i++;
				}
				else
				{
					if( (((int)c>=65) && ((int)c<=90)) || (((int)c>=97) && ((int)c<=122)) || (((int)c>=48) && ((int)c<=57)) || (c=='\n') || (c=='\t') || (c==' ')) //operador é : seguido de letra, numero ou \n \t ' '
					{
						saida.add(chave + "," + tabelaSimbReserv.get(chave)+","+numeroDaLinha);
					}
					else
					{
						chave=chave+c;
						saida.add(chave + ",Erro Lexico (\"Operando invalido\") Linha:"+numeroDaLinha);
						i++;
					}
				}
				break;
			case '<':
				chave=chave+c;
				i++;
				if(i<input.length())
				{
					c=input.charAt(i);
				}
				if(c=='>') // operador lido é o <>
				{
					chave=chave+c;
					saida.add(chave + "," + tabelaSimbReserv.get(chave)+","+numeroDaLinha);
					i++;
				}
				else
				{
					if( (((int)c>=65) && ((int)c<=90)) || (((int)c>=97) && ((int)c<=122)) || (((int)c>=48) && ((int)c<=57)) || (c=='\n') || (c=='\t') || (c==' ') || (c=='-') || (c=='(') || (c=='+')) //operador é < seguido de letra, numero ou \n \t ' ' ( + -
					{
						saida.add(chave + "," + tabelaSimbReserv.get(chave)+","+numeroDaLinha);
					}
					else
					{
						if(c=='=') // operador lido é o <=
						{
							chave=chave+c;
							saida.add(chave + "," + tabelaSimbReserv.get(chave)+","+numeroDaLinha);
							i++;
						}
						else
						{
							chave=chave+c;
							saida.add(chave + ",Erro Lexico (\"Operando invalido\") Linha:"+numeroDaLinha);
							i++;
						}
					}
				}
				break;
			case '>': 
				chave=chave+c;
				i++;
				if(i<input.length())
				{
					c=input.charAt(i);
				}
				if(c=='=') // operador lido é o >=
				{
					chave=chave+c;
					saida.add(chave + "," + tabelaSimbReserv.get(chave)+","+numeroDaLinha);
					i++;
				}
				else
				{
					if( (((int)c>=65) && ((int)c<=90)) || (((int)c>=97) && ((int)c<=122)) || (((int)c>=48) && ((int)c<=57)) || (c=='\n') || (c=='\t') || (c==' ') || (c=='-') || (c=='(') || (c=='+')) //operador é > seguido de letra, numero ou \n \t ' ' ( + -
					{
						saida.add(chave + "," + tabelaSimbReserv.get(chave)+","+numeroDaLinha);
					}
					else
					{
						chave=chave+c;
						saida.add(chave + ",Erro Lexico (\"Operando invalido\") Linha:"+numeroDaLinha);
						i++;
					}
				}
				break;
			default:	//é lido qualquer outro caracter entre a..z  0..9  A..Z ou simbolos invalidos
				if( (((int)c>=65) && ((int)c<=90)) || (((int)c>=97) && ((int)c<=122))  ) //obriga a comecar com letra a..z A..Z  (para palavras reservadas e identificadores)
				{
						//((((int)c>=65) && ((int)c<=90)) || (((int)c>=97) && ((int)c<=122)) || (((int)c>=48) && ((int)c<=57)))
					while(c!=' ' && c!='\t' && c!='\n' && c!=',' && c!=';'&& c!=':'&& c!='='&& c!='+'&& c!='-'&& c!='*'&& c!='/'&& c!='('&& c!='{'&& c!='<'&& c!='>'&& c!='.'&& c!=')'&& c!='}')
					{														 
						chave=chave+c;
						i++;
						
						if(i<input.length())
						{
							c=input.charAt(i);
						}
						else
						{
							break;
						}
					}
					int flagId=0;
					for(int j=0;j<chave.length();j++)
					{
						if( (((chave.charAt(j)>=65) && (chave.charAt(j)<=90)) || ((chave.charAt(j)>=97) && (chave.charAt(j)<=122)) || ((chave.charAt(j)>=48) && (chave.charAt(j)<=57))) )
						{
							
						}
						else
						{
							flagId=1;
						}
					}
					
					if(flagId==1)
					{
						saida.add(chave + ",Erro Lexico (\"Identificador invalido\") Linha:"+numeroDaLinha);
					}
					else
					{
						if(tabelaSimbReserv.containsKey(chave))//Define se é simbolo reservado ou identificador
						{
							saida.add(chave + "," + tabelaSimbReserv.get(chave)+","+numeroDaLinha);  //É símbolo reservado
						}
						else
						{
							addTabelaSimb(chave); //é identificador
							saida.add(chave + "," + tabelaSimb.get(chave)+","+numeroDaLinha);
						}
					}
					
				}
				else
				{
					if((int)c==123) //começa com { é comentário
					{
						int flag=0;
						while((int)c!=125)
						{
							chave=chave+c;
							i++;
							if(i<input.length())
							{
								c=input.charAt(i);
							}
							else //comentario no fim do arquivo
							{
								if(c!='}')
								{
									flag=1;
								}
								break;
							}

							if(c=='\n')
							{
								flag=1;
								break;
							}
						}
						
						if(flag==1)
						{
							i++;
							saida.add(chave + ",Erro Lexico (\"Falta de '}' para fechamento do comentário\") Linha:"+numeroDaLinha);
						}
						else
						{
							chave=chave+c;
							i++;
							saida.add(chave + ",comentario"+","+numeroDaLinha);
						}
					}
					else
					{
						if(((int)c>=48) && ((int)c<=57)) //Começa com números 
						{
							//logica para numeros e tratamento de erro de numeros invalidos
						
							int flagErro = 0;
							int flagPonto = 0;
							int flagLetra = 0;
							
							while(flagErro == 0) {
							while(((int)c>=48) && ((int)c<=57) || c == '.') {
								if(((int)c>=48) && ((int)c<=57))
								{
									chave = chave + c;
									i++;
									c = input.charAt(i);
								}
								
								else
								if(c == '.'){
									flagPonto++;
									chave = chave + c;
									i++;
									c = input.charAt(i);
										}
									}
							
								if(((int)c<=48 && (int)c>=57) || c != '.') {
									if(c == ';' || c==':'||c==')' || c=='+' || c == '-' || c == '/' || c == '*' || c == '<' || c == '>' || ((c == '>')&&(input.charAt(i+1)=='=')) || ((c == '<')&&(input.charAt(i+1)=='=')) || c == ' '  || c == '\t' ) {
									
									flagErro = 1;
									}
									else
										{
										flagLetra++;
										chave = chave + c;
										i++;
										c = input.charAt(i);	
										}
									}}
								
								if(flagPonto > 1) {
									saida.add(chave + ",Erro Lexico (\"Numero mal formatado\") Linha:"+numeroDaLinha);
								}
								else {
								if(flagPonto == 0 && flagLetra == 0) {
								saida.add(chave + ",numero_int"+","+numeroDaLinha );
								}
								else {
									if(flagPonto == 1 && flagLetra == 0) {
									saida.add(chave + ",numero_real"+","+numeroDaLinha );
									}
									}
									}
								if(flagLetra>=1) {
									saida.add(chave + ",Erro Lexico (\"Numero invalido\") Linha:"+numeroDaLinha);	
								}
							
							
						}
						else //Processa caracteres inválidos como @ # $ ...
						{
							chave=chave+c;
							i++;
							saida.add(chave + ",Erro Lexico (\"Caractere invalido\") Linha:"+numeroDaLinha);
						}
						

					}
				}
				
				break;
			}

		}
		
		return saida; //retorna uma array list com todas as palavras e seus respectivos tokens, na ordem que foram lidas do programa.
	}
	
}
