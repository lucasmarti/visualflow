package parser;
public interface TokenTypes 
{
	final int EQUATION_LINE		= 80;
	final int PARAMETER_LINE	= 81;
	final int COMPARTMENT_LINE 	= 82;
	final int OUTPUT_LINE		= 83;
	final int TYPE_LINE			= 84;
	final int RUNS_LINE			= 85;
	final int STEPS_LINE		= 86;
	final int PRECISION_LINE	= 87;	
	final int MODEL_LINE		= 88;
	final int VERSION_LINE		= 89;		
	
	final int STDEV				= 50;
	final int CONSTANT			= 77;
	final int NORMAL			= 78;

	
	final int NUMBER			= 3;
	final int IDENT				= 9;
	final int TEXT				= 30;
	
	final int PLUS 				= 4;
	final int MINUS 			= 5;
	final int TIMES 			= 6;
	final int DIV 				= 7;

	final int EXP				= 10;
	final int POW				= 11;
	final int LOG				= 12;
	
	final int PARANTHESE_O  	= 18;
	final int PARANTHESE_C  	= 19;
	final int EQUAL				= 8;	
	final int KOMMA				= 31;
	
	final int NEW_LINE			= 40;
	
}
