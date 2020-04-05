package parser;
public interface ParseExceptionTypes 
{
	/**
	 * Die MODEL Zeile ist obligatorisch. In ihr können Sie dem Modell einen Namen geben.
	 * Syntax: MODEL="text"
	 * wobei text alle Zeichen ausser " enthalten darf. text darf auch leer sein; dies wird 
	 * aber nicht empfohlen.
	 */
	final int MODEL_LINE_ERROR 				= 100;
	
	/**
	 * Die VERSION Zeile ist obligatorisch. In ihr können Sie dem Modell einen Version geben.
	 * Syntax: VERSION="text"
	 * wobei text alle Zeichen ausser " enthalten darf. text darf auch leer sein; dies wird 
	 * aber nicht empfohlen. 
	 */
	final int VERSION_LINE_ERROR 			= 101;
	
	/**
	 * Die OUTPUT Zeile ist obligatorisch. In ihr können Sie den Namen einer Datei bestimmen, in
	 * welcher das Resultat gespeichert wird.
	 * Syntax: OUTPUT="text"
	 * wobei der Dateiname *.xls heissen sollte.
	 */
	final int OUTPUT_LINE_ERROR				= 102;
	
	/**
	 * Die TYPE Zeile ist obligatorisch. In ihr bestimmen sie den Typ der Simulation. Es stehen 
	 * Constant und Normal zur Verfügung. Bei der Auswahl von Normal werden die Werte als 
	 * Wahrscheinlichkeitsverteilung dargestellt. Das Setzen einer Varianz (VAR) ist erforderlich.
	 * Syntax: TYPE=Constant
	 * 		   TYPE=Normal VAR=x
	 * wobei x eine positive reelle Zahl ist
	 * 
	 */
	final int TYPE_LINE_ERROR				= 103;
	
	/**
	 * Die PRECISION Zeile ist obligatorisch. In ihr gibt man die Stelle an auf die gerundet 
	 * werden soll.
	 * Syntax: PRECISION=[1.0|0.1|0.01|..]
	 */
	final int PRECISION_LINE_ERROR 			= 104;
	
	/**
	 * Die STEPS Zeile gibt die Anzahl Iterationen an, welche pro Durchlauf berechnet werden.
	 * Sie ist obligatorisch
	 * Syntax: STEPS=x
	 * wobei x positiv und ganzzahlig ist
	 */
	final int STEPS_LINE_ERROR 				= 105;
	
	/**
	 * Die RUNS Zeile gibt die Anzahl Durchläufe an. Sie sollte nur bei TYPE=Normale grösser als 1 sein,
	 * da ansonsten n mal das selbe Resultat berechnet wird.
	 * Syntax: RUNS=x
	 * wobei x positiv und ganzzahlig ist
	 */
	final int RUNS_LINE_ERROR				= 106;
	
	/**
	 * In einer COMPARTMENT Zeile wird ein Compartment definiert.
	 * Syntax: COMPARTMENT name=x
	 * wobei der name mit einem Kleinbuchstaben beginnen muss und x eine reelle Zahl sein kann
	 * 
	 * Pro COMPARTMENT eine EUQATION
	 */
	final int COMPARTMENT_LINE_ERROR		= 107;
	
	/**
	 * Hier wird die Gleichung der Veränderung (dx/dt) angegeben. Pro COMPARTMENT eine EUQATION.
	 * Syntax: DELTA compartment_name = mathematischer Ausdruck
	 * Beispiel: DELTA f = 3 + 7 * ro --> df/dt=3+7*ro
	 * wobei ro ein Compartment oder ein Parameter sein muss
	 */
	final int EQUATION_LINE_ERROR			= 108;
	
	/**
	 * Hier wird ein Parameter definiert.
	 * Syntax: PARAMETER name=x [VAR=y]
	 * wobei der name mit einem Kleinbuchstaben beginnen muss, x und y zwei reelle Zahlen sind. 
	 */
	final int PARAMETER_LINE_ERROR			= 109;
	
	
	/**
	 * Die Schlüsselwörter MODEL, VERSION, OUTPUT,STEPS, RUNS und PRECISION müssen genau einmal vorkommen.
	 */
	final int KEYWORD_DUPLICATION_ERROR 	= 120;
	final String KEYWORD_DUPLICATION_MSG	= "Keyword Duplication";
	
	final int MATH_ERROR					= 121;
	final String MATH_MSG					= "bad formed math expression";
	
	final String EQUAL_EXCEPTED				= "= excepted";
	final String POS_INT_NUMBER				= "positive integer number excepted";
	final String POS_NUMBER					= "positive number excepted";
	final String PRECISION_NUMBER_MSG		= "precision number excepted";
	final String TYPE_EXCEPTED				= "Keyword 'Constant' or 'Normal' excepted";
	final String FILENAME_EXCEPTED			= "filename excepted";
	final String TEXT_EXCEPTED				= "text excepted";
	final String IDENT_EXCEPTED				= "Compartment or Parameter excepted";
	final String COMP_EXCEPTED				= "Compartment excepted";
	final String NAME_EXCEPTED				= "name excepted";
	final String NUMBER_EXCEPTED			= "number excepted";
	final String INV_TOKEN					= "invalid token at beginning of line";
	final String NO_FILE_SELECTED			= "no file selected";
	final String FILE_NOT_FOUND				= "file not found: ";
	final String ANFUEHRUNGSZEICHEN			= "\" missing";
	final String TOKEN_NOT_RECOGNIZED		= "token not recognized ";
	final String FILE_READING_ERROR_MSG		= "ERROR while reading file";
	final String UNKNOWN_IDENT				= "unknown ident ";
	final String COMP_NAME_DUPL				= "compartment name duplication";
	final String PARAM_NAME_DUPL			= "parameter name duplication";
	final String UNKNOWN_COMP				= "unknown compartment ";
	final String EQU_MISSING				= "missing equation ";
	
	final int DEFAULT_ERROR					= 99;
	
}
