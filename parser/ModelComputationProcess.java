package parser;
import java.util.LinkedList;


import model.Model;
import model.Model_Builder;

public class ModelComputationProcess 
{
	public static String compute(String sourcefile)
	{
		LinkedList<Token> token_list;
		Lexicographic_Analyzer lex = new Lexicographic_Analyzer();
		Syntaxic_Analyzer syx = new Syntaxic_Analyzer();
		Semantic_Analyzer sem = new Semantic_Analyzer();
		Model_Builder mb = new Model_Builder();
		try{
			
			token_list = lex.parseFile(sourcefile);
			
			syx.analyze(token_list);
			
			sem.analyze(token_list);
			
			Model m = mb.build(token_list);
			
			m.compute();
			m.writeHeadFile();
			m.writeDataFile();
			m.writeXLSFile();
			
		}catch(ParseException e)
		{
			return e.msg;
		}
		return null;
	}
}
