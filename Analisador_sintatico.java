import java.util.ArrayList;

public class Analisador_sintatico {
	private static ArrayList<String> listaTokens=new ArrayList<String>();
	private static int indiceListaTokens=0;
	private static ArrayList<String> saidaSintatico=new ArrayList<String>();
	private static String simbolo;
	private static int numeroDaLinha;
	private static int numeroDaLinhaAnt;
	
	private static ArrayList<String> S_programa=new ArrayList<String>(); //{lambda} vazio
	private static ArrayList<String> S_dc_c=new ArrayList<String>();
	private static ArrayList<String> S_dc_v=new ArrayList<String>();
	private static ArrayList<String> S_variaveis=new ArrayList<String>();
	private static ArrayList<String> S_lista_arg=new ArrayList<String>();
	private static ArrayList<String> S_comandos=new ArrayList<String>();
	private static ArrayList<String> S_cmd=new ArrayList<String>();
	private static ArrayList<String> S_tipo_var=new ArrayList<String>();
	private static ArrayList<String> S_dc_p=new ArrayList<String>();
	private static ArrayList<String> S_parametros=new ArrayList<String>();
	private static ArrayList<String> S_lista_par=new ArrayList<String>();
	private static ArrayList<String> S_corpo_p=new ArrayList<String>();
	private static ArrayList<String> S_condicao=new ArrayList<String>();
	private static ArrayList<String> S_fator=new ArrayList<String>();
	private static ArrayList<String> S_numero=new ArrayList<String>();
	
	public static void getSaidaLexico(ArrayList<String> saidaLexico)
	{
		listaTokens=saidaLexico;
	}
	
	public static ArrayList<String> getListaTokens()
	{
		return listaTokens;
	}
	
	public static ArrayList<String> getSaidaSintatico()
	{
		return saidaSintatico;
	}
	
	public static void addSeguidores()
	{
		S_dc_c.add("simb_var");
		S_dc_c.add("simb_proced");
		S_dc_c.add("simb_begin");
		
		S_dc_v.add("simb_proced");
		S_dc_v.add("simb_begin");
		
		S_variaveis.add("simb_dp");
		S_variaveis.add("simb_fechaprt");
		
		S_comandos.add("simb_end");
		
		S_cmd.add("simb_pv");
		S_cmd.add("simb_end");
		
		S_dc_p.add("begin");
		S_parametros.add("simb_pv");
		S_lista_par.add("simb_fechaprt");
		S_corpo_p.add("simb_proced");
		
		S_condicao.add("simb_fechaprt");
		S_condicao.add("simb_then");
		
		S_numero.add("simb_do");
		S_numero.add("simb_to");
		S_numero.add("simb_pv");
	}
	
	public static int tratamentoErro(ArrayList<String> c,ArrayList<String> s)
	{
		while(!c.contains(simbolo) && !s.contains(simbolo))
		{
			obter_simbolo();
		}
		
		if(c.contains(simbolo))
		{
			//continua na mesma
			return 1;
		}
		else
		{
			if(s.contains(simbolo))
			{
				//seguidor do pai
				return 2;
			}
		}
		return 0;
	}
	
	public static void obter_simbolo()
	{
		String token="";
		String tokenVetor[];
		if(indiceListaTokens<listaTokens.size())
		{
			token=listaTokens.get(indiceListaTokens);
			if(token.charAt(0)==',')
			{
				indiceListaTokens++;
				simbolo=token.substring(2,8);
				tokenVetor=token.split(",");
				simbolo=tokenVetor[2];
				numeroDaLinhaAnt=numeroDaLinha;
				numeroDaLinha=Integer.parseInt(tokenVetor[3]);
			}
			else
			{
				tokenVetor=token.split(",");
				if(tokenVetor[1].charAt(0)=='E') //o token e erro lexico
				{
					//retornar erro lexico
					indiceListaTokens++;
					simbolo=tokenVetor[1];
					tokenVetor=token.split(":");
					numeroDaLinhaAnt=numeroDaLinha;
					numeroDaLinha=Integer.parseInt(tokenVetor[1]);
					saidaSintatico.add(token);
				}
				else
				{
					if(tokenVetor[1].charAt(0)=='c') //o token e comentario, ignorar
					{
						indiceListaTokens++;
						obter_simbolo();
					}
					else
					{
						// e um token valido
						indiceListaTokens++;
						simbolo=tokenVetor[1];
						numeroDaLinhaAnt=numeroDaLinha;
						numeroDaLinha=Integer.parseInt(tokenVetor[2]);
					}
				}
			}
		}
	}
	
	public static void ASD()
	{
		addSeguidores();
		obter_simbolo();
		programa();

		if(indiceListaTokens>=listaTokens.size()) //chegou no fim da cadeia
		{
			//terminar programa
			//System.out.println("fim.");
			return;
		}
		else
		{
			//Erro
		}
	}
	
	public static void programa()
	{
		if(simbolo.equals("simb_program"))
		{
			obter_simbolo();
		}
		else
		{
			//**************ERRO program
			ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
			conjuntoSeguidores.add("id");
			if(numeroDaLinha==numeroDaLinhaAnt)
			{
				saidaSintatico.add("Erro Sintatico: program esperado. Linha:"+(numeroDaLinha));
			}
			else
			{
				if(numeroDaLinha>numeroDaLinhaAnt)
				{
					saidaSintatico.add("Erro Sintatico: program esperado. Linha:"+(numeroDaLinha-1));
				}
			}
			tratamentoErro(conjuntoSeguidores,S_programa);
		}
		
		if(simbolo.equals("id"))
		{
			obter_simbolo();
		}
		else
		{
			//***************ERRO program id
			ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
			conjuntoSeguidores.add("simb_pv");
			if(numeroDaLinha==numeroDaLinhaAnt)
			{
				saidaSintatico.add("Erro Sintatico: identificador esperado. Linha:"+(numeroDaLinha));
			}
			else
			{
				if(numeroDaLinha>numeroDaLinhaAnt)
				{
					saidaSintatico.add("Erro Sintatico: identificador esperado. Linha:"+(numeroDaLinha-1));
				}
			}
			tratamentoErro(conjuntoSeguidores,S_programa);
		}
		
		if(simbolo.equals("simb_pv"))
		{
			obter_simbolo();
		}
		else
		{
			//***************ERRO program id ;
			ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
			conjuntoSeguidores.add("simb_const");
			conjuntoSeguidores.add("simb_var");
			conjuntoSeguidores.add("simb_proced");
			conjuntoSeguidores.add("simb_begin");
			if(numeroDaLinha==numeroDaLinhaAnt)
			{
				saidaSintatico.add("Erro Sintatico: ; esperado. Linha:"+(numeroDaLinha));
			}
			else
			{
				if(numeroDaLinha>numeroDaLinhaAnt)
				{
					saidaSintatico.add("Erro Sintatico: ; esperado. Linha:"+(numeroDaLinha-1));
				}
			}
			tratamentoErro(conjuntoSeguidores,S_programa);
		}
		
		dc_c();
		
		dc_v();
		
		dc_p();

		if(simbolo.equals("simb_begin"))
		{
			obter_simbolo();
		}
		else
		{
			//********************ERRO begin
			ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
			conjuntoSeguidores.add("simb_read");
			conjuntoSeguidores.add("simb_write");
			conjuntoSeguidores.add("simb_while");
			conjuntoSeguidores.add("simb_if");
			//conjuntoSeguidores.add("id");
			//conjuntoSeguidores.add("simb_begin");
			conjuntoSeguidores.add("simb_for");
			if(numeroDaLinha==numeroDaLinhaAnt)
			{
				saidaSintatico.add("Erro Sintatico: begin esperado. Linha:"+(numeroDaLinha));
			}
			else
			{
				if(numeroDaLinha>numeroDaLinhaAnt)
				{
					saidaSintatico.add("Erro Sintatico: begin esperado. Linha:"+(numeroDaLinha-1));
				}
			}
			tratamentoErro(conjuntoSeguidores,S_programa);
		}

		
			comandos();
		
		if(simbolo.equals("simb_end"))
		{
			obter_simbolo();
		}
		else
		{
			//********************ERRO end
			ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
			conjuntoSeguidores.add("simb_pnt");
			if(numeroDaLinha==numeroDaLinhaAnt)
			{
				saidaSintatico.add("Erro Sintatico: end esperado. Linha:"+(numeroDaLinha));
			}
			else
			{
				if(numeroDaLinha>numeroDaLinhaAnt)
				{
					saidaSintatico.add("Erro Sintatico: end esperado. Linha:"+(numeroDaLinha-1));
				}
			}
			tratamentoErro(conjuntoSeguidores,S_programa);
		}
		
		if(simbolo.equals("simb_pnt"))
		{
			obter_simbolo();
		}
		else
		{
			//********************ERRO .
			if(numeroDaLinha==numeroDaLinhaAnt)
			{
				saidaSintatico.add("Erro Sintatico: . esperado. Linha:"+(numeroDaLinha));
			}
			else
			{
				if(numeroDaLinha>numeroDaLinhaAnt)
				{
					saidaSintatico.add("Erro Sintatico: . esperado. Linha:"+(numeroDaLinha-1));
				}
			}
		}
		
	}
	
	public static void dc_c()
	{
		if(simbolo.equals("simb_const"))
		{
			obter_simbolo();

			if(simbolo.equals("id"))
			{
				obter_simbolo();
			}
			else
			{
				//******************ERRO const id
				ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
				conjuntoSeguidores.add("simb_igual");
				if(numeroDaLinha==numeroDaLinhaAnt)
				{
					saidaSintatico.add("Erro Sintatico: identificador esperado. Linha:"+(numeroDaLinha));
				}
				else
				{
					if(numeroDaLinha>numeroDaLinhaAnt)
					{
						saidaSintatico.add("Erro Sintatico: identificador esperado. Linha:"+(numeroDaLinha-1));
					}
				}
				int t=tratamentoErro(conjuntoSeguidores,S_dc_c);
				if(t==2)
				{
					return;
				}
			}
			
			if(simbolo.equals("simb_igual"))
			{
				obter_simbolo();
			}
			else
			{
				//****************ERRO const id =
				ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
				conjuntoSeguidores.add("numero_int");
				conjuntoSeguidores.add("numero_real");
				if(numeroDaLinha==numeroDaLinhaAnt)
				{
					saidaSintatico.add("Erro Sintatico: = esperado. Linha:"+(numeroDaLinha));
				}
				else
				{
					if(numeroDaLinha>numeroDaLinhaAnt)
					{
						saidaSintatico.add("Erro Sintatico: = esperado. Linha:"+(numeroDaLinha-1));
					}
				}
				int t=tratamentoErro(conjuntoSeguidores,S_dc_c);
				if(t==2)
				{
					return;
				}
			}
			
			numero();
			
			if(simbolo.equals("simb_pv"))
			{
				//numeroDaLinhaAnt=numeroDaLinha
				obter_simbolo();
			}
			else
			{
				//****************ERRO const id = numero ;
				ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
				conjuntoSeguidores.add("simb_const");
				if(numeroDaLinha==numeroDaLinhaAnt)
				{
					saidaSintatico.add("Erro Sintatico: ; esperado. Linha:"+(numeroDaLinha));
				}
				else
				{
					if(numeroDaLinha>numeroDaLinhaAnt)
					{
						saidaSintatico.add("Erro Sintatico: ; esperado. Linha:"+(numeroDaLinha-1));
					}
				}
				int t=tratamentoErro(conjuntoSeguidores,S_dc_c);
				if(t==2)
				{
					return;
				}
			}
			
			dc_c();
		}
	}
	
	
	public static void dc_v()
	{
		if(simbolo.equals("simb_var"))
		{
				obter_simbolo();
				
				variaveis();
				
				if(simbolo.equals("simb_dp"))
				{
					obter_simbolo();
				}
				else
				{
					//********************ERRO var variaveis :
					ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
					conjuntoSeguidores.add("simb_integer");
					conjuntoSeguidores.add("simb_real");
					if(numeroDaLinha==numeroDaLinhaAnt)
					{
						saidaSintatico.add("Erro Sintatico: : esperado. Linha:"+(numeroDaLinha));
					}
					else
					{
						if(numeroDaLinha>numeroDaLinhaAnt)
						{
							saidaSintatico.add("Erro Sintatico: : esperado. Linha:"+(numeroDaLinha-1));
						}
					}
					int t=tratamentoErro(conjuntoSeguidores,S_dc_v);
					if(t==2)
					{
						return;
					}
				}
				
				tipo_var();
				
				if(simbolo.equals("simb_pv"))
				{
					obter_simbolo();
				}
				else
				{
					//********************ERRO ;
					ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
					conjuntoSeguidores.add("simb_var");
					if(numeroDaLinha==numeroDaLinhaAnt)
					{
						saidaSintatico.add("Erro Sintatico: ; esperado. Linha:"+(numeroDaLinha));
					}
					else
					{
						if(numeroDaLinha>numeroDaLinhaAnt)
						{
							saidaSintatico.add("Erro Sintatico: ; esperado. Linha:"+(numeroDaLinha-1));
						}
					}
					int t=tratamentoErro(conjuntoSeguidores,S_dc_v);
					if(t==2)
					{
						return;
					}
				}
				
				if(simbolo.equals("simb_var"))
				{
					dc_v();	
				}
		}
	}
	
	public static void variaveis()
	{
		if(simbolo.equals("id"))
		{
			obter_simbolo();
		}
		else
		{
			//********************ERRO id
			ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
			conjuntoSeguidores.add("simb_vi");
			if(numeroDaLinha==numeroDaLinhaAnt)
			{
				saidaSintatico.add("Erro Sintatico: identificador esperado. Linha:"+(numeroDaLinha));
			}
			else
			{
				if(numeroDaLinha>numeroDaLinhaAnt)
				{
					saidaSintatico.add("Erro Sintatico: identificador esperado. Linha:"+(numeroDaLinha-1));
				}
			}
			int t=tratamentoErro(conjuntoSeguidores,S_variaveis);
			if(t==2)
			{
				return;
			}
		}
		
		int flagVariaveis=1;
		
		while(flagVariaveis==1)
		{
			if(simbolo.equals("simb_vi"))
			{
				obter_simbolo();
				
				if(simbolo.equals("id"))
				{
					obter_simbolo();
				}
				else
				{
					//********************ERRO id
					ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
					conjuntoSeguidores.add("simb_vi");
					if(numeroDaLinha==numeroDaLinhaAnt)
					{
						saidaSintatico.add("Erro Sintatico: identificador esperado. Linha:"+(numeroDaLinha));
					}
					else
					{
						if(numeroDaLinha>numeroDaLinhaAnt)
						{
							saidaSintatico.add("Erro Sintatico: identificador esperado. Linha:"+(numeroDaLinha-1));
						}
					}
					int t=tratamentoErro(conjuntoSeguidores,S_variaveis);
					if(t==2)
					{
						return;
					}
				}
			}
			else
			{
				flagVariaveis=0;
			}
		}
	}
	
	public static void lista_arg()
	{
		if(simbolo.equals("simb_abreprt"))
		{
			obter_simbolo();
			
			if(simbolo.equals("id"))
			{
				obter_simbolo();
			}
			else
			{
				//********************ERRO id
				ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
				conjuntoSeguidores.add("simb_fechaprt");
				if(numeroDaLinha==numeroDaLinhaAnt)
				{
					saidaSintatico.add("Erro Sintatico: identificador esperado. Linha:"+(numeroDaLinha));
				}
				else
				{
					if(numeroDaLinha>numeroDaLinhaAnt)
					{
						saidaSintatico.add("Erro Sintatico: identificador esperado. Linha:"+(numeroDaLinha-1));
					}
				}
				tratamentoErro(conjuntoSeguidores,S_lista_arg);

			}
			
			int flagListaArg=1;
			
			while(flagListaArg==1)
			{
				if(simbolo.equals("simb_pv"))
				{
					obter_simbolo();
					
					if(simbolo.equals("id"))
					{
						obter_simbolo();
					}
					else
					{
						//********************ERRO id
						ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
						conjuntoSeguidores.add("simb_fechaprt");
						if(numeroDaLinha==numeroDaLinhaAnt)
						{
							saidaSintatico.add("Erro Sintatico: identificador esperado. Linha:"+(numeroDaLinha));
						}
						else
						{
							if(numeroDaLinha>numeroDaLinhaAnt)
							{
								saidaSintatico.add("Erro Sintatico: identificador esperado. Linha:"+(numeroDaLinha-1));
							}
						}
						tratamentoErro(conjuntoSeguidores,S_lista_arg);
					}
				}
				else
				{
					flagListaArg=0;
				}
			}
			
			if(simbolo.equals("simb_fechaprt"))
			{
				obter_simbolo();
			}
			else
			{
				//********************ERRO )
				ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
				conjuntoSeguidores.add("simb_pv");
				if(numeroDaLinha==numeroDaLinhaAnt)
				{
					saidaSintatico.add("Erro Sintatico: ) esperado. Linha:"+(numeroDaLinha));
				}
				else
				{
					if(numeroDaLinha>numeroDaLinhaAnt)
					{
						saidaSintatico.add("Erro Sintatico: ) esperado. Linha:"+(numeroDaLinha-1));
					}
				}
				tratamentoErro(conjuntoSeguidores,S_lista_arg);
			}
		}
	}
	
	public static void comandos()
	{
		if(simbolo.equals("simb_read") || simbolo.equals("simb_write") || simbolo.equals("simb_while") || simbolo.equals("simb_if") || simbolo.equals("id") || simbolo.equals("simb_begin") ||simbolo.equals("simb_for"))
		{
			cmd();
			
			if(simbolo.equals("simb_pv"))
			{
				obter_simbolo();
			}
			else
			{
				//********************ERRO ;
				ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
				conjuntoSeguidores.add("simb_read");
				conjuntoSeguidores.add("simb_write");
				conjuntoSeguidores.add("simb_while");
				conjuntoSeguidores.add("simb_if");
				conjuntoSeguidores.add("id");
				conjuntoSeguidores.add("simb_begin");
				conjuntoSeguidores.add("simb_for");
				if(numeroDaLinha==numeroDaLinhaAnt)
				{
					saidaSintatico.add("Erro Sintatico: ; esperado. Linha:"+(numeroDaLinha));
				}
				else
				{
					if(numeroDaLinha>numeroDaLinhaAnt)
					{
						saidaSintatico.add("Erro Sintatico: ; esperado. Linha:"+(numeroDaLinha-1));
					}
				}
				if(tratamentoErro(conjuntoSeguidores,S_comandos)==2)
				{
					return;
				}
			}
			
			if(simbolo.equals("simb_read") || simbolo.equals("simb_write") || simbolo.equals("simb_while") || simbolo.equals("simb_if") || simbolo.equals("id") || simbolo.equals("simb_begin") ||simbolo.equals("simb_for"))
			{
				comandos();
			}
		}
	}
	
	public static void cmd()
	{
		if(simbolo.equals("simb_read") || simbolo.equals("simb_write") || simbolo.equals("simb_while") || simbolo.equals("simb_if") || simbolo.equals("id") || simbolo.equals("simb_begin") ||simbolo.equals("simb_for")) {
		if(simbolo.equals("simb_read"))
		{
			obter_simbolo();
			
			if(simbolo.equals("simb_abreprt"))
			{
				obter_simbolo();
			}
			else
			{
				//********************ERRO (
				ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
				conjuntoSeguidores.add("id");
				if(numeroDaLinha==numeroDaLinhaAnt)
				{
					saidaSintatico.add("Erro Sintatico: ( esperado. Linha:"+(numeroDaLinha));
				}
				else
				{
					if(numeroDaLinha>numeroDaLinhaAnt)
					{
						saidaSintatico.add("Erro Sintatico: ( esperado. Linha:"+(numeroDaLinha-1));
					}
				}
				if(tratamentoErro(conjuntoSeguidores,S_cmd)==2)
				{
					return;
				}
			}
			
			variaveis();
			
			if(simbolo.equals("simb_fechaprt"))
			{
				obter_simbolo();
			}
			else
			{
				//********************ERRO )
				ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
				if(numeroDaLinha==numeroDaLinhaAnt)
				{
					saidaSintatico.add("Erro Sintatico: ) esperado. Linha:"+(numeroDaLinha));
				}
				else
				{
					if(numeroDaLinha>numeroDaLinhaAnt)
					{
						saidaSintatico.add("Erro Sintatico: ) esperado. Linha:"+(numeroDaLinha-1));
					}
				}
				if(tratamentoErro(conjuntoSeguidores,S_cmd)==2)
				{
					return;
				}
			}
		}
		else
		{
			if(simbolo.equals("simb_write"))
			{
				obter_simbolo();
				
				if(simbolo.equals("simb_abreprt"))
				{
					obter_simbolo();
				}
				else
				{
					//********************ERRO (
					ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
					conjuntoSeguidores.add("id");
					if(numeroDaLinha==numeroDaLinhaAnt)
					{
						saidaSintatico.add("Erro Sintatico: ( esperado. Linha:"+(numeroDaLinha));
					}
					else
					{
						if(numeroDaLinha>numeroDaLinhaAnt)
						{
							saidaSintatico.add("Erro Sintatico: ( esperado. Linha:"+(numeroDaLinha-1));
						}
					}
					if(tratamentoErro(conjuntoSeguidores,S_cmd)==2)
					{
						return;
					}
				}
				
				variaveis();
				
				if(simbolo.equals("simb_fechaprt"))
				{
					obter_simbolo();
				}
				else
				{
					//********************ERRO )
					ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
					if(numeroDaLinha==numeroDaLinhaAnt)
					{
						saidaSintatico.add("Erro Sintatico: ) esperado. Linha:"+(numeroDaLinha));
					}
					else
					{
						if(numeroDaLinha>numeroDaLinhaAnt)
						{
							saidaSintatico.add("Erro Sintatico: ) esperado. Linha:"+(numeroDaLinha-1));
						}
					}
					if(tratamentoErro(conjuntoSeguidores,S_cmd)==2)
					{
						return;
					}
				}

			}
			else
			{
				if(simbolo.equals("simb_while"))
				{
					obter_simbolo();
					
					if(simbolo.equals("simb_abreprt"))
					{
						obter_simbolo();
					}
					else
					{
						//********************ERRO (
						ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
						conjuntoSeguidores.add("simb_mais");
						conjuntoSeguidores.add("simb_menos");
						conjuntoSeguidores.add("id");
						conjuntoSeguidores.add("numero_int");
						conjuntoSeguidores.add("numero_real");
						conjuntoSeguidores.add("simb_abreprt");
						if(numeroDaLinha==numeroDaLinhaAnt)
						{
							saidaSintatico.add("Erro Sintatico: ( esperado. Linha:"+(numeroDaLinha));
						}
						else
						{
							if(numeroDaLinha>numeroDaLinhaAnt)
							{
								saidaSintatico.add("Erro Sintatico: ( esperado. Linha:"+(numeroDaLinha-1));
							}
						}
						if(tratamentoErro(conjuntoSeguidores,S_cmd)==2)
						{
							return;
						}
					}
					
					condicao();
					
					if(simbolo.equals("simb_fechaprt"))
					{
						obter_simbolo();
					}
					else
					{
						//********************ERRO )
						ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
						conjuntoSeguidores.add("simb_do");
						if(numeroDaLinha==numeroDaLinhaAnt)
						{
							saidaSintatico.add("Erro Sintatico: ) esperado. Linha:"+(numeroDaLinha));
						}
						else
						{
							if(numeroDaLinha>numeroDaLinhaAnt)
							{
								saidaSintatico.add("Erro Sintatico: ) esperado. Linha:"+(numeroDaLinha-1));
							}
						}
						if(tratamentoErro(conjuntoSeguidores,S_cmd)==2)
						{
							return;
						}
					}
					
					if(simbolo.equals("simb_do"))
					{
						obter_simbolo();
					}
					else
					{
						//********************ERRO do
						ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
						conjuntoSeguidores.add("simb_read");
						conjuntoSeguidores.add("simb_write");
						conjuntoSeguidores.add("simb_while");
						conjuntoSeguidores.add("simb_if");
						conjuntoSeguidores.add("id");
						conjuntoSeguidores.add("simb_begin");
						conjuntoSeguidores.add("simb_for");
						if(numeroDaLinha==numeroDaLinhaAnt)
						{
							saidaSintatico.add("Erro Sintatico: do esperado. Linha:"+(numeroDaLinha));
						}
						else
						{
							if(numeroDaLinha>numeroDaLinhaAnt)
							{
								saidaSintatico.add("Erro Sintatico: do esperado. Linha:"+(numeroDaLinha-1));
							}
						}
						if(tratamentoErro(conjuntoSeguidores,S_cmd)==2)
						{
							return;
						}
					}
					
					cmd();
					
				}
				else
				{
					if(simbolo.equals("simb_if"))
					{
						obter_simbolo();
						
						condicao();
						
						if(simbolo.equals("simb_then"))
						{
							obter_simbolo();
						}
						else
						{
							//********************ERRO then
							ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
							conjuntoSeguidores.add("simb_read");
							conjuntoSeguidores.add("simb_write");
							conjuntoSeguidores.add("simb_while");
							conjuntoSeguidores.add("simb_if");
							conjuntoSeguidores.add("id");
							conjuntoSeguidores.add("simb_begin");
							conjuntoSeguidores.add("simb_for");
							conjuntoSeguidores.add("simb_else");
							if(numeroDaLinha==numeroDaLinhaAnt)
							{
								saidaSintatico.add("Erro Sintatico: then esperado. Linha:"+(numeroDaLinha));
							}
							else
							{
								if(numeroDaLinha>numeroDaLinhaAnt)
								{
									saidaSintatico.add("Erro Sintatico: then esperado. Linha:"+(numeroDaLinha-1));
								}
							}
							if(tratamentoErro(conjuntoSeguidores,S_cmd)==2)
							{
								return;
							}
						}
						
						cmd();
						
						p_falsa();
					}
					else
					{
						if(simbolo.equals("id"))
						{
							obter_simbolo();
							if(simbolo.equals("simb_atrib") || simbolo.equals("simb_abreprt")) {
							if(simbolo.equals("simb_atrib"))
							{
								obter_simbolo();
								
								expressao();
							}
							else
							{
								lista_arg();
							}
							}
							else
							{
								ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
								conjuntoSeguidores.add("simb_read");
								conjuntoSeguidores.add("simb_write");
								conjuntoSeguidores.add("simb_while");
								conjuntoSeguidores.add("simb_if");
								conjuntoSeguidores.add("simb_begin");
								conjuntoSeguidores.add("simb_for");
								conjuntoSeguidores.add("simb_else");
								if(numeroDaLinha==numeroDaLinhaAnt)
								{
									saidaSintatico.add("Erro Sintatico: := ou ( esperado. Linha:"+(numeroDaLinha));
								}
								else
								{
									if(numeroDaLinha>numeroDaLinhaAnt)
									{
										saidaSintatico.add("Erro Sintatico: := ou ( esperado. Linha:"+(numeroDaLinha-1));
									}
								}
								if(tratamentoErro(conjuntoSeguidores,S_cmd)==2)
								{
									return;
								}
							}
						}
						else
						{
							if(simbolo.equals("simb_begin"))
							{
								obter_simbolo();
								
								comandos();
								
								if(simbolo.equals("simb_end"))
								{
									obter_simbolo();
								}
								else
								{
									//********************ERRO end
									ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
									if(numeroDaLinha==numeroDaLinhaAnt)
									{
										saidaSintatico.add("Erro Sintatico: end esperado. Linha:"+(numeroDaLinha));
									}
									else
									{
										if(numeroDaLinha>numeroDaLinhaAnt)
										{
											saidaSintatico.add("Erro Sintatico: end esperado. Linha:"+(numeroDaLinha-1));
										}
									}
									if(tratamentoErro(conjuntoSeguidores,S_cmd)==2)
									{
										return;
									}
								}
							}
							else
							{
								if(simbolo.equals("simb_for"))
								{
									obter_simbolo();
									
									if(simbolo.equals("id"))
									{
										obter_simbolo();
									}
									else
									{
										//********************ERRO id
										ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
										conjuntoSeguidores.add("simb_atrib");
										if(numeroDaLinha==numeroDaLinhaAnt)
										{
											saidaSintatico.add("Erro Sintatico: identificador esperado. Linha:"+(numeroDaLinha));
										}
										else
										{
											if(numeroDaLinha>numeroDaLinhaAnt)
											{
												saidaSintatico.add("Erro Sintatico: identificador esperado. Linha:"+(numeroDaLinha-1));
											}
										}
										if(tratamentoErro(conjuntoSeguidores,S_cmd)==2)
										{
											return;
										}
									}
									
									if(simbolo.equals("simb_atrib"))
									{
										obter_simbolo();
									}
									else
									{
										//********************ERRO :=
										ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
										conjuntoSeguidores.add("numero_int");
										conjuntoSeguidores.add("numero_real");
										if(numeroDaLinha==numeroDaLinhaAnt)
										{
											saidaSintatico.add("Erro Sintatico: := esperado. Linha:"+(numeroDaLinha));
										}
										else
										{
											if(numeroDaLinha>numeroDaLinhaAnt)
											{
												saidaSintatico.add("Erro Sintatico: := esperado. Linha:"+(numeroDaLinha-1));
											}
										}
										if(tratamentoErro(conjuntoSeguidores,S_cmd)==2)
										{
											return;
										}
									}
									
									numero();
									
									if(simbolo.equals("simb_to"))
									{
										obter_simbolo();
									}
									else
									{
										//********************ERRO to
										ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
										conjuntoSeguidores.add("numero_int");
										conjuntoSeguidores.add("numero_real");
										if(numeroDaLinha==numeroDaLinhaAnt)
										{
											saidaSintatico.add("Erro Sintatico: to esperado. Linha:"+(numeroDaLinha));
										}
										else
										{
											if(numeroDaLinha>numeroDaLinhaAnt)
											{
												saidaSintatico.add("Erro Sintatico: to esperado. Linha:"+(numeroDaLinha-1));
											}
										}
										if(tratamentoErro(conjuntoSeguidores,S_cmd)==2)
										{
											return;
										}
									}
									
									numero();
									
									if(simbolo.equals("simb_do"))
									{
										obter_simbolo();
									}
									else
									{
										//********************ERRO do
										ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
										conjuntoSeguidores.add("simb_read");
										conjuntoSeguidores.add("simb_write");
										conjuntoSeguidores.add("simb_while");
										conjuntoSeguidores.add("simb_if");
										conjuntoSeguidores.add("id");
										conjuntoSeguidores.add("simb_begin");
										conjuntoSeguidores.add("simb_for");
										if(numeroDaLinha==numeroDaLinhaAnt)
										{
											saidaSintatico.add("Erro Sintatico: do esperado. Linha:"+(numeroDaLinha));
										}
										else
										{
											if(numeroDaLinha>numeroDaLinhaAnt)
											{
												saidaSintatico.add("Erro Sintatico: do esperado. Linha:"+(numeroDaLinha-1));
											}
										}

										if(tratamentoErro(conjuntoSeguidores,S_cmd)==2)
										{
											return;
										}
									}
									
									cmd();
								}
								else
								{
									ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
									conjuntoSeguidores.add("simb_read");
									conjuntoSeguidores.add("simb_write");
									conjuntoSeguidores.add("simb_while");
									conjuntoSeguidores.add("simb_if");
									conjuntoSeguidores.add("id");
									conjuntoSeguidores.add("simb_begin");
									conjuntoSeguidores.add("simb_for");
									if(numeroDaLinha==numeroDaLinhaAnt)
									{
										saidaSintatico.add("Erro Sintatico: read|write|while|if|id|begin|for esperado. Linha:"+(numeroDaLinha));
									}
									else
									{
										if(numeroDaLinha>numeroDaLinhaAnt)
										{
											saidaSintatico.add("Erro Sintatico: read|write|while|if|id|begin|for esperado. Linha:"+(numeroDaLinha-1));
										}
									}

									if(tratamentoErro(conjuntoSeguidores,S_cmd)==2)
									{
										return;
									}
								}
							}
						}
					}
				}
			}
		}
		}
		else
		{
			ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
			conjuntoSeguidores.add("simb_read");
			conjuntoSeguidores.add("simb_write");
			conjuntoSeguidores.add("simb_while");
			conjuntoSeguidores.add("simb_if");
			conjuntoSeguidores.add("id");
			conjuntoSeguidores.add("simb_begin");
			conjuntoSeguidores.add("simb_for");

			saidaSintatico.add("Erro Sintatico: read|write|while|if|id|begin|for esperado. Linha:"+(numeroDaLinha));
			

			if(tratamentoErro(conjuntoSeguidores,S_cmd)==2)
			{
				return;
			}
		}
	}
	
	
	//Grafo 7
		public static void p_falsa() 
		{	
			if(simbolo.equals("simb_else")) 
			{
				obter_simbolo();
				cmd();
			}
		}
		
		//Grafo 8
		public static void tipo_var()
		{
			if(simbolo.equals("simb_integer") || simbolo.equals("simb_real") ) {
				obter_simbolo();
			}
			else
			{
				ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
				conjuntoSeguidores.add("simb_pv");
				conjuntoSeguidores.add("simb_fechaprt");
				if(numeroDaLinha==numeroDaLinhaAnt)
				{
					saidaSintatico.add("Erro Sintatico: real ou integer esperado. Linha:"+(numeroDaLinha));
				}
				else
				{
					if(numeroDaLinha>numeroDaLinhaAnt)
					{
						saidaSintatico.add("Erro Sintatico: real ou integer esperado. Linha:"+(numeroDaLinha-1));
					}
				}
				int t=tratamentoErro(conjuntoSeguidores,S_tipo_var);
				if(t==2)
				{
					return;
				}
			}
		}
		
		//Grafo 9
		public static void dc_p()
		{
			if(simbolo.equals("simb_proced")) {
				obter_simbolo();
				
				if(simbolo.equals("id")) 
				{
					obter_simbolo();
				}
				else {
					ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
					conjuntoSeguidores.add("simb_igual");
					conjuntoSeguidores.add("simb_pv");
					if(numeroDaLinha==numeroDaLinhaAnt)
					{
						saidaSintatico.add("Erro Sintatico: identificador esperado. Linha:"+(numeroDaLinha));
					}
					else
					{
						if(numeroDaLinha>numeroDaLinhaAnt)
						{
							saidaSintatico.add("Erro Sintatico: identificador esperado. Linha:"+(numeroDaLinha-1));
						}
					}
					int t=tratamentoErro(conjuntoSeguidores,S_dc_p);
					if(t==2)
					{
						return;
					}
				}
				parametros();
				
				if(simbolo.equals("simb_pv")) 
				{
					obter_simbolo();
				}
				else {
					ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
					conjuntoSeguidores.add("simb_var");
					conjuntoSeguidores.add("simb_proced");
					if(numeroDaLinha==numeroDaLinhaAnt)
					{
						saidaSintatico.add("Erro Sintatico: ; esperado. Linha:"+(numeroDaLinha));
					}
					else
					{
						if(numeroDaLinha>numeroDaLinhaAnt)
						{
							saidaSintatico.add("Erro Sintatico: ; esperado. Linha:"+(numeroDaLinha-1));
						}
					}
					int t=tratamentoErro(conjuntoSeguidores,S_dc_p);
					if(t==2)
					{
						return;
					}
				}
				corpo_p();
				dc_p();
			}
		}
		
		public static void parametros()
		{
			if(simbolo.equals("simb_abreprt")) 
			{
				obter_simbolo();
				
				lista_par();
				
				if(simbolo.equals("simb_fechaprt")) {
					obter_simbolo();
				}
				else {
					ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
					if(numeroDaLinha==numeroDaLinhaAnt)
					{
						saidaSintatico.add("Erro Sintatico: ) esperado. Linha:"+(numeroDaLinha));
					}
					else
					{
						if(numeroDaLinha>numeroDaLinhaAnt)
						{
							saidaSintatico.add("Erro Sintatico: ) esperado. Linha:"+(numeroDaLinha-1));
						}
					}
					int t=tratamentoErro(conjuntoSeguidores,S_parametros);
					if(t==2)
					{
						return;
					}
				}
			}			
		}
		
		public static void lista_par()
		{
			variaveis();
			if(simbolo.equals("simb_dp")) 
			{
				obter_simbolo();
			}
			else {
				ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
				conjuntoSeguidores.add("simb_integer");
				conjuntoSeguidores.add("simb_real");
				if(numeroDaLinha==numeroDaLinhaAnt)
				{
					saidaSintatico.add("Erro Sintatico: : esperado. Linha:"+(numeroDaLinha));
				}
				else
				{
					if(numeroDaLinha>numeroDaLinhaAnt)
					{
						saidaSintatico.add("Erro Sintatico: : esperado. Linha:"+(numeroDaLinha-1));
					}
				}
				int t=tratamentoErro(conjuntoSeguidores,S_lista_par);
				if(t==2)
				{
					return;
				}
			}
			
			tipo_var();
			
			if(simbolo.equals("simb_pv")) 
			{
				obter_simbolo();
				lista_par();
			}
		}
		
		public static void corpo_p() 
		{
			dc_v();
			if(simbolo.equals("simb_begin")) {
				obter_simbolo();
			}
			else {
				ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
				conjuntoSeguidores.add("simb_read");
				conjuntoSeguidores.add("simb_write");
				conjuntoSeguidores.add("simb_if");
				conjuntoSeguidores.add("simb_id");
				conjuntoSeguidores.add("simb_begin");
				conjuntoSeguidores.add("simb_for");
				if(numeroDaLinha==numeroDaLinhaAnt)
				{
					saidaSintatico.add("Erro Sintatico: Begin esperado. Linha:"+(numeroDaLinha));
				}
				else
				{
					if(numeroDaLinha>numeroDaLinhaAnt)
					{
						saidaSintatico.add("Erro Sintatico: Begin esperado. Linha:"+(numeroDaLinha-1));
					}
				}
				int t=tratamentoErro(conjuntoSeguidores,S_corpo_p);
				if(t==2)
				{
					return;
				}
			}
			comandos();
			if(simbolo.equals("simb_end")) {
				obter_simbolo();
			}
			else {
				ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
				conjuntoSeguidores.add("simb_pv");
				if(numeroDaLinha==numeroDaLinhaAnt)
				{
					saidaSintatico.add("Erro Sintatico: End esperado. Linha:"+(numeroDaLinha));
				}
				else
				{
					if(numeroDaLinha>numeroDaLinhaAnt)
					{
						saidaSintatico.add("Erro Sintatico: End esperado. Linha:"+(numeroDaLinha-1));
					}
				}
				int t=tratamentoErro(conjuntoSeguidores,S_corpo_p);
				if(t==2)
				{
					return;
				}
			}
			if(simbolo.equals("simb_pv")) {
				obter_simbolo();
			}
			else {
				ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
				conjuntoSeguidores.add("simb_proced");
				if(numeroDaLinha==numeroDaLinhaAnt)
				{
					saidaSintatico.add("Erro Sintatico: ; esperado. Linha:"+(numeroDaLinha));
				}
				else
				{
					if(numeroDaLinha>numeroDaLinhaAnt)
					{
						saidaSintatico.add("Erro Sintatico: ; esperado. Linha:"+(numeroDaLinha-1));
					}
				}
				int t=tratamentoErro(conjuntoSeguidores,S_corpo_p);
				if(t==2)
				{
					return;
				}
			}
		}
		///////////////////////////////////////////////////////////////////////////////
		
		//Grafo 10
		public static void condicao() 
		{
			expressao();
			if(simbolo.equals("simb_igual") || simbolo.equals("simb_dif") ||simbolo.equals("simb_maior_igual") ||simbolo.equals("simb_menor_igual") ||
			simbolo.equals("simb_maior") ||simbolo.equals("simb_menor")) 
			{
				obter_simbolo();
			}
			else {
				ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
				conjuntoSeguidores.add("simb_abreprt");
				conjuntoSeguidores.add("numero_int");
				conjuntoSeguidores.add("numero_real");
				if(numeroDaLinha==numeroDaLinhaAnt)
				{
					saidaSintatico.add("Erro Sintatico: Comparador esperado. Linha:"+(numeroDaLinha));
				}
				else
				{
					if(numeroDaLinha>numeroDaLinhaAnt)
					{
						saidaSintatico.add("Erro Sintatico: Comparador esperado. Linha:"+(numeroDaLinha-1));
					}
				}
				int t=tratamentoErro(conjuntoSeguidores,S_condicao);
				if(t==2)
				{
					return;
				}
			}
			expressao();
		}
		
		//Grafo 11
		public static void expressao() {
			termo();
			outros_termos();
		}
		
		public static void termo() 
		{
			if(simbolo.equals("simb_mais")) 
			{
				obter_simbolo();
			}
			else 
			{
				if(simbolo.equals("simb_menos"))
				{
					obter_simbolo();
				}
			}
			
			fator();
			mais_fatores();
		}
		
		public static void outros_termos() 
		{
			if(simbolo.equals("simb_mais") || simbolo.equals("simb_menos") ) {
				obter_simbolo();
				termo();
				outros_termos();
			}
		}
		
		public static void fator() {
			if(simbolo.equals("id")|| simbolo.equals("numero_int") || simbolo.equals("numero_real") ||simbolo.equals("simb_abreprt")) 
			{
				if(simbolo.equals("id")) 
				{
				obter_simbolo();		
				}
				else
				{
					if(simbolo.equals("numero_int") || simbolo.equals("numero_real")) 
					{
						obter_simbolo();
					}
					else 
					{
						if(simbolo.equals("simb_abreprt")) 
						{
							obter_simbolo();
							expressao();
							if(simbolo.equals("simb_fechaprt")) 
							{
								obter_simbolo();
							}
							else 
							{
								ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
								conjuntoSeguidores.add("simb_mais");
								conjuntoSeguidores.add("simb_menos");
								conjuntoSeguidores.add("simb_mult");
								conjuntoSeguidores.add("simb_div");
								conjuntoSeguidores.add("simb_fechaprt");
								conjuntoSeguidores.add("simb_dp");
								if(numeroDaLinha==numeroDaLinhaAnt)
								{
									saidaSintatico.add("Erro Sintatico: ) esperado. Linha:"+(numeroDaLinha));
								}
								else
								{
									if(numeroDaLinha>numeroDaLinhaAnt)
									{
										saidaSintatico.add("Erro Sintatico: ) esperado. Linha:"+(numeroDaLinha-1));
									}
								}
								int t=tratamentoErro(conjuntoSeguidores,S_fator);
								if(t==2)
								{
									return;
								}
							}
						}
					}
				}
			}
			else { 
				ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
				conjuntoSeguidores.add("simb_mais");
				conjuntoSeguidores.add("simb_menos");
				conjuntoSeguidores.add("simb_mult");
				conjuntoSeguidores.add("simb_div");
				conjuntoSeguidores.add("simb_fechaprt");
				conjuntoSeguidores.add("simb_pv");
				if(numeroDaLinha==numeroDaLinhaAnt)
				{
					saidaSintatico.add("Erro Sintatico: identificador ou numero ou ( esperado. Linha:"+(numeroDaLinha));
				}
				else
				{
					if(numeroDaLinha>numeroDaLinhaAnt)
					{
						saidaSintatico.add("Erro Sintatico: identificador ou numero ou ( esperado. Linha:"+(numeroDaLinha-1));
					}
				}
				int t=tratamentoErro(conjuntoSeguidores,S_fator);
				if(t==2)
				{
					return;
				}
			}
		}
		
		public static void mais_fatores() {
			if(simbolo.equals("simb_mult") || simbolo.equals("simb_div") ) {
				obter_simbolo();
				fator();
				mais_fatores();
			}
		}
		////////////////////////////////////////////////////////////////////////////
		
		//Grafo 12
		public static void numero() {
			if(simbolo.equals("numero_int") || simbolo.equals("numero_real") ) {
				obter_simbolo();
			}
			else
			{
				ArrayList<String> conjuntoSeguidores=new ArrayList<String>();
				conjuntoSeguidores.add("simb_dp");
				conjuntoSeguidores.add("simb_mais");
				conjuntoSeguidores.add("simb_menos");
				conjuntoSeguidores.add("simb_mult");
				conjuntoSeguidores.add("simb_div");
				conjuntoSeguidores.add("simb_igual");
				conjuntoSeguidores.add("simb_fechaprt");
				conjuntoSeguidores.add("simb_maior");
				conjuntoSeguidores.add("simb_menor");
				conjuntoSeguidores.add("simb_maior_igual");
				conjuntoSeguidores.add("simb_menor_igual");
				conjuntoSeguidores.add("simb_dif");
				conjuntoSeguidores.add("simb_vi");
				conjuntoSeguidores.add("simb_pnt");			
				if(numeroDaLinha==numeroDaLinhaAnt)
				{
					saidaSintatico.add("Erro Sintatico: numero inteiro ou real esperado esperado. Linha:"+(numeroDaLinha));
				}
				else
				{
					if(numeroDaLinha>numeroDaLinhaAnt)
					{
						saidaSintatico.add("Erro Sintatico: numero inteiro ou real esperado. Linha:"+(numeroDaLinha-1));
					}
				}
				int t=tratamentoErro(conjuntoSeguidores,S_numero);
				if(t==2)
				{
					return;
				}
			}
		}

}
