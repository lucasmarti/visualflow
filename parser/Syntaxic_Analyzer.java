package parser;
import java.util.LinkedList;


public class Syntaxic_Analyzer implements TokenTypes, ParseExceptionTypes
{
	LinkedList<Token> token_list;
	int line_number;
	int current_pos;
	boolean ML = false;
	boolean VL = false;
	boolean OL = false;
	boolean TL = false;
	boolean RL = false;
	boolean SL = false;
	boolean PL = false;
	boolean CL = false;
	boolean EL = false;
	
	public void analyze(LinkedList<Token> token_list) throws ParseException
	{

		ML = false;
		VL = false;
		OL = false;
		TL = false;
		RL = false;
		SL = false;
		PL = false;
		
		this.token_list = token_list;
		current_pos = 0;
		line_number = 1;
		
		while(current_pos<token_list.size())
		{
			if(token_list.get(current_pos).type==NEW_LINE)
			{
				current_pos++;
				line_number++;
			}
			else if(token_list.get(current_pos).type==MODEL_LINE)
			{
				model_line();
			}
			else if(token_list.get(current_pos).type==VERSION_LINE)
			{
				version_line();
			}
			else if(token_list.get(current_pos).type==OUTPUT_LINE)
			{
				output_line();
			}
			else if(token_list.get(current_pos).type==TYPE_LINE)
			{
				type_line();
			}
			else if(token_list.get(current_pos).type==RUNS_LINE)
			{
				runs_line();
			}
			else if(token_list.get(current_pos).type==STEPS_LINE)
			{
				steps_line();
			}
			else if(token_list.get(current_pos).type==PRECISION_LINE)
			{
				precision_line();
			}
			else if(token_list.get(current_pos).type==COMPARTMENT_LINE)
			{
				compartment_line();
			}
			else if(token_list.get(current_pos).type==PARAMETER_LINE)
			{
				parameter_line();
			}
			else if(token_list.get(current_pos).type==EQUATION_LINE)
			{
				equation_line();
			}
			else
				throw new ParseException(DEFAULT_ERROR,line_number,INV_TOKEN);
		}
		if(VL==false) throw new ParseException(VERSION_LINE_ERROR,"VERSION line missing");
		if(ML==false) throw new ParseException(MODEL_LINE_ERROR,"MODEL line missing");
		if(TL==false) throw new ParseException(TYPE_LINE_ERROR,"TYPE line missing");
		if(SL==false) throw new ParseException(STEPS_LINE_ERROR,"STEPS line missing");
		if(RL==false) throw new ParseException(RUNS_LINE_ERROR,"RUNS line missing");
		if(PL==false) throw new ParseException(PRECISION_LINE_ERROR,"PRECISION line missing");
		if(OL==false) throw new ParseException(OUTPUT_LINE_ERROR,"OUTPUT line missing");
		if(CL==false) throw new ParseException(COMPARTMENT_LINE_ERROR,"COMPARTMENT line missing");
		if(EL==false) throw new ParseException(EQUATION_LINE_ERROR,"EQUATION line missing");
	}

	private void steps_line() throws ParseException 
	{
		if(SL)
			throw new ParseException(KEYWORD_DUPLICATION_ERROR,line_number,KEYWORD_DUPLICATION_MSG);
		SL=true;
		current_pos++;
		if(token_list.get(current_pos).type!=EQUAL)
			throw new ParseException(STEPS_LINE_ERROR,line_number,EQUAL_EXCEPTED);
		current_pos++;
		if(token_list.get(current_pos).type!=NUMBER)
			throw new ParseException(STEPS_LINE_ERROR,line_number,POS_INT_NUMBER);
		current_pos++;
	}

	private void precision_line() throws ParseException 
	{
		if(PL)
			throw new ParseException(KEYWORD_DUPLICATION_ERROR,line_number,KEYWORD_DUPLICATION_MSG);	
		PL=true;
		current_pos++;
		if(token_list.get(current_pos).type!=EQUAL)
			throw new ParseException(PRECISION_LINE_ERROR,line_number,EQUAL_EXCEPTED);
		current_pos++;
		if(token_list.get(current_pos).type!=NUMBER)
			throw new ParseException(PRECISION_LINE_ERROR,line_number,PRECISION_NUMBER_MSG);
		current_pos++;
	}

	private void runs_line() throws ParseException 
	{
		if(RL)
			throw new ParseException(KEYWORD_DUPLICATION_ERROR,line_number,KEYWORD_DUPLICATION_MSG);
		RL=true;
		current_pos++;
		if(token_list.get(current_pos).type!=EQUAL)
			throw new ParseException(RUNS_LINE_ERROR,line_number,EQUAL_EXCEPTED);
		current_pos++;
		if(token_list.get(current_pos).type!=NUMBER)
			throw new ParseException(RUNS_LINE_ERROR,line_number,POS_INT_NUMBER);
		current_pos++;
	}

	private void type_line() throws ParseException 
	{
		if(TL)
			throw new ParseException(KEYWORD_DUPLICATION_ERROR,line_number,KEYWORD_DUPLICATION_MSG);
		TL=true;
		current_pos++;
		if(token_list.get(current_pos).type!=EQUAL)
			throw new ParseException(TYPE_LINE_ERROR,line_number,EQUAL_EXCEPTED);
		current_pos++;
		if(token_list.get(current_pos).type!=CONSTANT&&token_list.get(current_pos).type!=NORMAL)
			throw new ParseException(TYPE_LINE_ERROR,line_number,TYPE_EXCEPTED);
		current_pos++;
	}

	private void output_line() throws ParseException 
	{
		if(OL)
			throw new ParseException(KEYWORD_DUPLICATION_ERROR,line_number,KEYWORD_DUPLICATION_MSG);	
		OL=true;
		current_pos++;
		if(token_list.get(current_pos).type!=EQUAL)
			throw new ParseException(OUTPUT_LINE_ERROR,line_number,EQUAL_EXCEPTED);
		current_pos++;
		if(token_list.get(current_pos).type!=TEXT)
			throw new ParseException(OUTPUT_LINE_ERROR,line_number,FILENAME_EXCEPTED);
		current_pos++;
	}

	private void version_line() throws ParseException 
	{
		if(VL)
			throw new ParseException(KEYWORD_DUPLICATION_ERROR,line_number,KEYWORD_DUPLICATION_MSG);	
		VL=true;
		current_pos++;
		if(token_list.get(current_pos).type!=EQUAL)
			throw new ParseException(VERSION_LINE_ERROR,line_number,EQUAL_EXCEPTED);
		current_pos++;
		if(token_list.get(current_pos).type!=TEXT)
			throw new ParseException(VERSION_LINE_ERROR,line_number,TEXT_EXCEPTED);
		current_pos++;
	}

	private void model_line() throws ParseException 
	{
		if(ML)
			throw new ParseException(KEYWORD_DUPLICATION_ERROR,line_number,KEYWORD_DUPLICATION_MSG);
		ML=true;
		current_pos++;
		if(token_list.get(current_pos).type!=EQUAL)
			throw new ParseException(MODEL_LINE_ERROR,line_number,EQUAL_EXCEPTED);
		current_pos++;
		if(token_list.get(current_pos).type!=TEXT)
			throw new ParseException(MODEL_LINE_ERROR,line_number,TEXT_EXCEPTED);
		current_pos++;		
	}

	private void equation_line() throws ParseException 
	{
		EL = true;
		current_pos++;
		if(token_list.get(current_pos).type!=IDENT)
			throw new ParseException(EQUATION_LINE_ERROR,line_number,COMP_EXCEPTED);
		current_pos++;
		if(token_list.get(current_pos).type!=EQUAL)
			throw new ParseException(EQUATION_LINE_ERROR,line_number,EQUAL_EXCEPTED);
		current_pos++;
		equation_body();
		while(token_list.get(current_pos).type!=NEW_LINE)
		{
			current_pos++;
		}
	}

	private void equation_body() throws ParseException 
	{
		int min = current_pos;
		int max = current_pos;
		while(token_list.get(max).type!=NEW_LINE)
		{
			max++;
		}
		max--;
		if(!func_e(min,max))
			throw new ParseException(MATH_ERROR,line_number,MATH_MSG);
	}

	private void parameter_line() throws ParseException 
	{
		current_pos++;
		if(token_list.get(current_pos).type!=IDENT)
			throw new ParseException(PARAMETER_LINE_ERROR,line_number,NAME_EXCEPTED);
		current_pos++;
		if(token_list.get(current_pos).type!=EQUAL)
			throw new ParseException(PARAMETER_LINE_ERROR,line_number,EQUAL_EXCEPTED);
		current_pos++;
		if(token_list.get(current_pos).type!=NUMBER)
			throw new ParseException(PARAMETER_LINE_ERROR,line_number,NUMBER_EXCEPTED);
		current_pos++;
		//---- OPTIONAL PART:  VAR=33.7
		if(token_list.get(current_pos).type==STDEV)
		{
			current_pos++;
			if(token_list.get(current_pos).type!=EQUAL)
				throw new ParseException(PARAMETER_LINE_ERROR,line_number,EQUAL_EXCEPTED);
			current_pos++;
			if(token_list.get(current_pos).type!=NUMBER)
				throw new ParseException(PARAMETER_LINE_ERROR,line_number,NUMBER_EXCEPTED);		
			current_pos++;
		}

	}

	private void compartment_line() throws ParseException 
	{
		CL = true;
		current_pos++;
		if(token_list.get(current_pos).type!=IDENT)
			throw new ParseException(COMPARTMENT_LINE_ERROR,line_number,NAME_EXCEPTED);
		current_pos++;
		if(token_list.get(current_pos).type!=EQUAL)
			throw new ParseException(COMPARTMENT_LINE_ERROR,line_number,EQUAL_EXCEPTED);
		current_pos++;
		if(token_list.get(current_pos).type!=NUMBER)
			throw new ParseException(COMPARTMENT_LINE_ERROR,line_number,NUMBER_EXCEPTED);		
		current_pos++;
	}
	private boolean func_e(int min, int max)
	{
		if(min>max)
			return false;
		for(int i=min; i<=max; i++)
		{
			if(token_list.get(i).type==PLUS)
			{
				if(func_t(min,i-1)&&func_e(i+1,max))
					return true;
			}
		}	
		for(int i=min; i<=max; i++)
		{
			if(token_list.get(i).type==MINUS)
			{
				if(func_t(min,i-1)&&func_e(i+1,max))
					return true;
			}
		}	
		if(func_t(min,max))
			return true;
		return false;
	}
	
	public boolean func_t(int min, int max)
	{
		if(min>max)
			return false;
		for(int i=min; i<=max; i++)
		{
			if(token_list.get(i).type==TIMES)
			{
				if(func_f(min,i-1)&&func_t(i+1,max))
					return true;
			}
		}	
		for(int i=min; i<=max; i++)
		{
			if(token_list.get(i).type==DIV)
			{
				if(func_f(min,i-1)&&func_t(i+1,max))
					return true;
			}
		}	
		if(func_f(min,max))
			return true;
		return false;
	}
	public boolean func_f(int min, int max)
	{
		if(min>max)
			return false;
		if(token_list.get(min).type==MINUS&&func_f(min+1,max))
			return true;
		if(token_list.get(min).type==PARANTHESE_O&&func_e(min+1,max-1)&&token_list.get(max).type==PARANTHESE_C)
			return true;
		if(min==max&&func_id(min))
			return true;
		if(token_list.get(min).type==POW
				&&token_list.get(min+1).type==PARANTHESE_O
				&&func_e2(min+2,max-1)
				&&token_list.get(max).type==PARANTHESE_C)
			return true;
		if(token_list.get(min).type==LOG
				&&token_list.get(min+1).type==PARANTHESE_O
				&&func_e2(min+2,max-1)
				&&token_list.get(max).type==PARANTHESE_C)
			return true;
		if(token_list.get(min).type==EXP
				&&token_list.get(min+1).type==PARANTHESE_O
				&&func_e(min+2,max-1)
				&&token_list.get(max).type==PARANTHESE_C)
			return true;

		return false;
	}
	private boolean func_e2(int min, int max) 
	{
		for(int i=min; i<=max; i++)
		{
			if(token_list.get(i).type==KOMMA)
			{
				if(func_e(min,i-1)&&func_e(i+1,max))
					return true;
			}
		}
		return false;
	}

	public boolean func_id(int pos)
	{
		if(token_list.get(pos).type==IDENT||token_list.get(pos).type==NUMBER)
			return true;
		else
			return false;
	}

}