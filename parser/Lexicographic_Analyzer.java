package parser;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;


public class Lexicographic_Analyzer implements TokenTypes, ParseExceptionTypes
{
	LinkedList<Token>  token_list;
	StringTokenizer stk;

	public LinkedList<Token> parseFile(String filename) throws ParseException
	{
		if(filename==null)
			throw new ParseException("ERROR: "+NO_FILE_SELECTED);
		String line;
		token_list = new LinkedList<Token>();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			int d=1;
			while((line=br.readLine())!=null)
			{
				parseLine(line, d);
				d++;
			}
			//--- MERGE OF NEGATIVE NUMBERS
			int mode = 0;
			int i=0;
			while(i<token_list.size())
			{
				if(token_list.get(i).type==EQUATION_LINE)
					mode = EQUATION_LINE;
				if(token_list.get(i).type==COMPARTMENT_LINE)
					mode = COMPARTMENT_LINE;
				if(token_list.get(i).type==PARAMETER_LINE)
					mode = PARAMETER_LINE;
				if(token_list.get(i).type==MINUS&&token_list.get(i+1).type==NUMBER&&mode!=EQUATION_LINE)
				{
					token_list.get(i+1).payload = "-"+token_list.get(i+1).payload;
					token_list.remove(i);
				}
				else 
					i++;
			};
			return token_list;
			
		} catch (FileNotFoundException e){
			throw new ParseException("ERROR "+FILE_NOT_FOUND+filename);
		} catch (IOException e) {
			throw new ParseException(FILE_READING_ERROR_MSG);
		}

	}
	
	private void parseLine(String line, int lineNumber) throws ParseException
	{
		String currentToken;
		stk = new StringTokenizer(line,"+*-/()# \"=,",true);
		
		//*** empty or commented lines
		if(!stk.hasMoreTokens())
		{
			token_list.add(new Token("",NEW_LINE));
			return;
		}
		currentToken = stk.nextToken();
		if(currentToken.compareTo("#")==0)
		{
			token_list.add(new Token("",NEW_LINE));
			return;
		}
		//***
		do
		{
			/**
			 * subroutine for parsing text
			 * returns error if line ends before second "
			 */ 
			if(currentToken.compareTo("\"")==0)
			{
				String w ="";
				boolean noMoreTokens = false;
				do{
					if(stk.hasMoreTokens())
					{
						currentToken =stk.nextToken();
						if(currentToken.compareTo("\"")==0)
							break;
						else
							w = w + currentToken;
					}
					else
					{
						noMoreTokens = true;
						break;
					}
				}while(true);
				if(noMoreTokens)
					throw new ParseException(DEFAULT_ERROR,lineNumber,ANFUEHRUNGSZEICHEN);
				else
					token_list.add(new Token(w,TEXT));
			}
			/**
			 * end of subroutine
			 */
			
			//-------- THE LINE KEYWORDS
			 
			else if(currentToken.compareTo("MODEL")==0)
			{
				token_list.add(new Token("",MODEL_LINE));
			}
			else if(currentToken.compareTo("VERSION")==0)
			{
				token_list.add(new Token("",VERSION_LINE));
			}
			else if(currentToken.compareTo("OUTPUT")==0)
			{
				token_list.add(new Token("",OUTPUT_LINE));
			}
			else if(currentToken.compareTo("TYPE")==0)
			{
				token_list.add(new Token("",TYPE_LINE));
			}
			else if(currentToken.compareTo("RUNS")==0)
			{
				token_list.add(new Token("",RUNS_LINE));
			}
			else if(currentToken.compareTo("STEPS")==0)
			{
				token_list.add(new Token("",STEPS_LINE));
			}
			else if(currentToken.compareTo("PRECISION")==0)
			{
				token_list.add(new Token("",PRECISION_LINE));
			}
			else if(currentToken.compareTo("COMPARTMENT")==0)
			{
				token_list.add(new Token("",COMPARTMENT_LINE));
			}
			else if(currentToken.compareTo("PARAMETER")==0)
			{
				token_list.add(new Token("",PARAMETER_LINE));
			}
			else if(currentToken.compareTo("DELTA")==0)
			{
				token_list.add(new Token("",EQUATION_LINE));
			}
			//---- END OF LINE KEYWORDS
			
			else if(currentToken.compareTo(" ")==0)
			{
				
			}
			else if(currentToken.compareTo(",")==0)
			{
				token_list.add(new Token("",KOMMA));
			}
			else if(currentToken.compareTo("STD")==0)
			{
				token_list.add(new Token("",STDEV));
			}
			else if(currentToken.compareTo("Constant")==0)
			{
				token_list.add(new Token("",CONSTANT));
			}
			else if(currentToken.compareTo("Normal")==0)
			{
				token_list.add(new Token("",NORMAL));
			}
			
			
			else if(currentToken.compareTo("+")==0)
			{
				token_list.add(new Token("",PLUS));
			}
			else if(currentToken.compareTo("-")==0)
			{
				token_list.add(new Token("",MINUS));	
			}
			else if(currentToken.compareTo("*")==0)
			{
				token_list.add(new Token("",TIMES));
			}
			else if(currentToken.compareTo("/")==0)
			{
				token_list.add(new Token("",DIV));
			}
			
			else if(currentToken.compareTo("=")==0)
			{
				token_list.add(new Token("",EQUAL));
			}
			
			else if(currentToken.compareTo("POW")==0)
			{
				token_list.add(new Token("",POW));
			}
			else if(currentToken.compareTo("LOG")==0)
			{
				token_list.add(new Token("",LOG));
			}
			else if(currentToken.compareTo("EXP")==0)
			{
				token_list.add(new Token("",EXP));
			}
			
			else if(currentToken.compareTo("(")==0)
			{
				token_list.add(new Token("",PARANTHESE_O));
			}
			else if(currentToken.compareTo(")")==0)
			{
				token_list.add(new Token("",PARANTHESE_C));
			}
			
			else if(isIdent(currentToken))
			{
				token_list.add(new Token(currentToken,IDENT));
			}
			else if(isNumber(currentToken))
			{
				
				token_list.add(new Token(currentToken,NUMBER));
			}
			/**
			 * if none of the above tests matches the token is not recognized
			 */
			else
			{	
				throw new ParseException(DEFAULT_ERROR,lineNumber,TOKEN_NOT_RECOGNIZED+currentToken);
			}
			if(stk.hasMoreTokens())
				currentToken = stk.nextToken();
			else
				break;
		}while(true);
		
		/**
		 * add an end-line
		 */
		token_list.add(new Token("",NEW_LINE));
	}
	private boolean isIdent(String currentToken) 
	{
		final int E0 = 0;
		final int E1 = 1;
		int state = E0;
		for(int i=0; i<currentToken.length(); i++)
		{
			if(state==E0&&Character.isLowerCase(currentToken.charAt(i)))
				state=E1;
			else if(state==E1&&Character.isLetterOrDigit(currentToken.charAt(i)))
				state=E1;
			else
				return false;
		}
		
		return(state==E1);
	}

	private boolean isNumber(String currentToken) 
	{
		final int E0 = 0;
		final int E1 = 1;
		final int E2 = 2;
		final int E3 = 3;
		int state = E0;
		for(int i=0; i<currentToken.length(); i++)
		{
			if(state==E0&&Character.isDigit(currentToken.charAt(i)))
				state=E1;
			else if(state==E1&&Character.isDigit(currentToken.charAt(i)))
				state=E1;
			else if(state==E1&&currentToken.charAt(i)==".".charAt(0))
				state=E2;
			else if(state==E2&&Character.isDigit(currentToken.charAt(i)))
				state=E3;
			else if(state==E3&&Character.isDigit(currentToken.charAt(i)))
				state=E3;			
			else
				return false;
		}
		if(state==E1||state==E3)
			return true;
		else return false;
	}	
}
