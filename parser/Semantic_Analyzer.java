package parser;
import java.util.LinkedList;


public class Semantic_Analyzer implements TokenTypes, ParseExceptionTypes
{
	LinkedList<Token> compartment_list;
	LinkedList<Token> parameter_list;
	LinkedList<Token> token_list;
	LinkedList<Token> equation_list;
	int line = 1;
	
	
	public void analyze(LinkedList<Token> token_list) throws ParseException
	{
		compartment_list = new LinkedList<Token>();
		parameter_list = new LinkedList<Token>();
		equation_list = new LinkedList<Token>();
		this.token_list = token_list;
		
		for(int i=0; i<token_list.size(); i++)
		{
			increment(i);
			verifyNameDuplication(i);
			verifyRuns(i);
			verifySteps(i);
			verifyVar(i);
			verifyPrecision(i);
			verifyEquation(i);
			verifyExistanceOfIdents(i);
		}
		verifyEquationCompartmentCount();
	
	}

	private void verifyExistanceOfIdents(int i) throws ParseException 
	{
		if(token_list.get(i).type==IDENT)
			if(!contains(compartment_list,token_list.get(i))&&!contains(parameter_list,token_list.get(i)))
				throw new ParseException(DEFAULT_ERROR,line,UNKNOWN_IDENT+token_list.get(i).payload);
	}

	private void verifyVar(int i) throws ParseException 
	{
		if(token_list.get(i).type==STDEV)
		{
			double d = new Double(token_list.get(i+2).payload).doubleValue();
			if(d<0)
				throw new ParseException(PARAMETER_LINE_ERROR,line,POS_NUMBER);
		}
	}

	private void verifySteps(int i) throws ParseException 
	{
		if(token_list.get(i).type==STEPS_LINE)
		{
			double d = new Double(token_list.get(i+2).payload).doubleValue();
			if(d<=0)
				throw new ParseException(STEPS_LINE_ERROR,line,POS_INT_NUMBER);
			Double e = new Double(d);
			if(1.0*e.intValue()!=d)
				throw new ParseException(STEPS_LINE_ERROR,line,POS_INT_NUMBER);
		}
		
	}

	private void increment(int i) 
	{
		if(token_list.get(i).type==NEW_LINE)
			line++;
	}

	private void verifyEquation(int i) throws ParseException 
	{
		if(token_list.get(i).type==EQUATION_LINE)
		{
			if(contains(compartment_list,token_list.get(i+1)))
			{
				if(contains(equation_list,token_list.get(i+1)))
					throw new ParseException(EQUATION_LINE_ERROR,line,COMP_NAME_DUPL);
				equation_list.add(token_list.get(i+1));
				return;
			}
			throw new ParseException(EQUATION_LINE_ERROR,line,UNKNOWN_COMP);
		}
	}

	private void verifyPrecision(int i) throws ParseException 
	{
		if(token_list.get(i).type==PRECISION_LINE)
		{
			Double d = new Double(token_list.get(i+2).payload);
			if(Math.log10(d)-new Double(Math.log10(d)).intValue()!=0)
				throw new ParseException(PRECISION_LINE_ERROR,line+PRECISION_NUMBER_MSG);
		}
	}

	private void verifyRuns(int i) throws ParseException 
	{
		if(token_list.get(i).type==RUNS_LINE)
		{
			double d = new Double(token_list.get(i+2).payload).doubleValue();
			if(d<=0)
				throw new ParseException("Line "+line+": select an integer number > 0");
			Double e = new Double(d);
			if(1.0*e.intValue()!=d)
				throw new ParseException("Line "+line+": select an integer number > 0");
		}
	}

	private void verifyNameDuplication(int i) throws ParseException 
	{
		if(token_list.get(i).type==COMPARTMENT_LINE)
		{
			if(contains(compartment_list,token_list.get(i+1))||contains(parameter_list,token_list.get(i+1)))
				throw new ParseException(COMPARTMENT_LINE_ERROR,line,COMP_NAME_DUPL);
			compartment_list.add(token_list.get(i+1));
		}
		if(token_list.get(i).type==PARAMETER_LINE)
		{
			if(contains(compartment_list,token_list.get(i+1))||contains(parameter_list,token_list.get(i+1)))
				throw new ParseException(PARAMETER_LINE_ERROR,line,PARAM_NAME_DUPL);
			parameter_list.add(token_list.get(i+1));
		}		
	}
	
	private boolean contains(LinkedList<Token> list, Token token)
	{
		for(int i=0; i<list.size();i++)
			if(list.get(i).payload.compareTo(token.payload)==0)
				return true;
		return false;
	}
	
	private void verifyEquationCompartmentCount() throws ParseException
	{
		if(equation_list.size()!=compartment_list.size())
			for(int i=0; i<compartment_list.size();i++)
				if(!contains(equation_list,compartment_list.get(i)))
			throw new ParseException(EQUATION_LINE_ERROR,line,EQU_MISSING+" "+compartment_list.get(i).payload);
	}
	
}
