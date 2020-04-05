package model;
import parser.*;
import java.util.LinkedList;




public class Model_Builder implements TokenTypes 
{
	int count = 0;
	LinkedList<Token> token_list;
	Model model;
	int current_pos = 0;	
	public Model build(LinkedList<Token> token_list)
	{
		this.token_list = token_list;
		model = new Model();
		while(current_pos<token_list.size())
		{
			if(token_list.get(current_pos).type==MODEL_LINE)
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
				current_pos++;
		}
		return model;
	}


	private void equation_line() 
	{
		Compartment dest;
		current_pos++;
		String name = token_list.get(current_pos).payload;
		dest = model.getCompartment(name);
		current_pos++;
		current_pos++;
		//--- START BODY
		int min = current_pos;
		int max = current_pos;
		while(token_list.get(max).type!=NEW_LINE)
		{
			max++;
		}
		max--;
		model.equations.add(new Equation(dest,func_e(min,max)));
		count=0;
		//--- END BODY
		while(token_list.get(current_pos).type!=NEW_LINE)
		{
			current_pos++;
		}

	}	
		
	private void parameter_line() 
	{
		current_pos++;
		String name = token_list.get(current_pos).payload;
		current_pos++;
		current_pos++;
		double value = new Double(token_list.get(current_pos).payload).doubleValue();
		if(token_list.get(current_pos+1).type==STDEV)
		{
			current_pos++;
			current_pos++;
			current_pos++;
			double variance = new Double(token_list.get(current_pos).payload).doubleValue();
			model.parameters.add(new Parameter(name, value, variance));
		}
		else
			model.parameters.add(new Parameter(name, value));
	}

	private void compartment_line() 
	{
		current_pos++;
		String name = token_list.get(current_pos).payload;
		current_pos++;
		current_pos++;
		double value = new Double(token_list.get(current_pos).payload).doubleValue();
		model.compartments.add(new Compartment(name, value));
	}

	private void precision_line() 
	{
		current_pos++;
		current_pos++;
		model.precision = new Double(token_list.get(current_pos).payload).doubleValue();
	}

	private void steps_line() 
	{
		current_pos++;
		current_pos++;
		model.steps = new Integer(token_list.get(current_pos).payload).intValue();
	}

	private void runs_line() 
	{
		current_pos++;
		current_pos++;
		model.runs = new Integer(token_list.get(current_pos).payload).intValue();	
	}

	private void type_line() 
	{
		current_pos++;
		current_pos++;
		model.type = token_list.get(current_pos).type;
	}

	private void output_line() 
	{
		current_pos++;
		current_pos++;
		model.output = token_list.get(current_pos).payload;
	}

	private void version_line() 
	{
		current_pos++;
		current_pos++;
		model.version = token_list.get(current_pos).payload;
	}

	private void model_line() 
	{
		current_pos++;
		current_pos++;
		model.name = token_list.get(current_pos).payload;
	}
	private Computable func_e(int min, int max)
	{
		if(min>max)
			return null;
		/**
		 * right first derivation
		 */
		//for(int i=min+1; i<max; i++)
		for(int i=max-1; i>min;i--)
		{
			if(token_list.get(i).type==PLUS||token_list.get(i).type==MINUS)
			{
				Computable a = func_e(min,i-1);
				Computable b = null;
				if(a!=null)
					b = func_t(i+1,max);
				if(a!=null&&b!=null&&token_list.get(i).type==PLUS)
					return new Plus(a,b);
				if(a!=null&&b!=null&&token_list.get(i).type==MINUS)
					return new Minus(a,b);
			}
		}	
		Computable a = func_t(min,max); 
		if(a!=null)
			return a;
		return null;
	}
	
	public Computable func_t(int min, int max)
	{
		if(min>max)
			return null;
		for(int i=max-1; i>min; i--)
		{
			if(token_list.get(i).type==TIMES||token_list.get(i).type==DIV)
			{
				Computable a = func_t(min,i-1);
				Computable b = null;
				if(a!=null)
					b = func_f(i+1,max);
				if(a!=null&&b!=null&&token_list.get(i).type==TIMES)
					return new Times(a,b);
				if(a!=null&&b!=null&&token_list.get(i).type==DIV)
					return new Div(a,b);
			}
		}
		Computable c = func_f(min,max); 
		if(c!=null)
			return c;
		return null;
	}
	public Computable func_f(int min, int max)
	{
		Computable a = null;
		if(min>max)
			return null;
		if(token_list.get(min).type==MINUS)
		{
			a = func_f(min+1,max);
			if(a!=null)
				return new Minus(new Number(0.0),a);
		}
		
		if(token_list.get(min).type==PARANTHESE_O&&token_list.get(max).type==PARANTHESE_C)
		{
			a = func_e(min+1,max-1);
			if(a!=null)
				return a;
		}
		
		if(min==max)
		{
			a = func_id(min);
			if(a!=null)
				return a;
		}
		
		//--- BEGIN POW(E,E) AND LOG(E,E) 
		
		if(token_list.get(min).type==POW)
		{
			int komma_pos = min;
			for(int i=min+2; i<max-1;i++)
				if(token_list.get(i).type==KOMMA)
				{
					komma_pos = i;
					Computable y = null;
					Computable x = func_e(min+2,komma_pos-1);
					if(x!=null)
						y = func_e(komma_pos+1,max-1);
					if(x!=null&&y!=null)
						return new Pow(x,y);
				}
		}
		
		if(token_list.get(min).type==LOG)
		{
			int komma_pos = min;
			for(int i=min+2; i<max-1;i++)
				if(token_list.get(i).type==KOMMA)
				{
						komma_pos = i;
						Computable y = null;
						Computable x = func_e(min+2,komma_pos-1);
						if(x!=null)
							y = func_e(komma_pos+1,max-1);
						if(x!=null&&y!=null)
							return new Pow(x,y);
					}
			}
		if(token_list.get(min).type==EXP)
			return new Exp(func_e(min+2,max-1));
		
		return null;
	}
	public Computable func_id(int pos)
	{
		Computable c = null;
		if(token_list.get(pos).type==IDENT)
		{
			c = model.getCompartment(token_list.get(pos).payload);
			if(c==null)
				c = model.getParameter(token_list.get(pos).payload);
		}			
		else if(token_list.get(pos).type==NUMBER)
			c = new Number(token_list.get(pos).payload);
		else
			c = null;
		return c;
	}

}