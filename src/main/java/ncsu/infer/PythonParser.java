package ncsu.infer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * Class to parse a Python module and extract out everything that could be
 * independently imported into another module. Reports out: classes, constants,
 * and functions.
 *
 * @author Kai
 *
 */
public class PythonParser {

    private final String       filename;

    private final List<String> constants;
    private final List<String> functions;
    private final List<String> classes;

    public PythonParser ( final String filename ) {
        this.filename = filename;

        constants = new Vector<String>();
        functions = new Vector<String>();
        classes = new Vector<String>();
    }

    public PythonParser parse () {
        try {

            final BufferedReader br = new BufferedReader( new FileReader( filename ) );

            String readLine;

            String identifier;

            while ( ( readLine = br.readLine() ) != null ) {
                // System.out.println( readLine );
                /*
                 * Figure out how many leading spaces we have so we can see how
                 * many levels we are indented
                 */
                Integer indentation = 0;

                /* Declaration of a Python Class in the module */
                if ( readLine.startsWith( "class" ) ) {
                    System.out.println( "class" );
                    identifier = readLine.replaceAll( "class", "" );
                    identifier = identifier.replaceAll( "\\((.+)", "" );
                    classes.add( identifier );
                    continue;
                }

                final char[] chars = readLine.toCharArray();

                /*
                 * Figure out how indented into the code we are. If !0, we can't
                 * find an importable function or constant
                 */
                for ( int i = 0; i < chars.length && ' ' == chars[i]; indentation++, i++ ) {
                    ;
                }

                /*
                 * Declaration of a function in the module. Only grab
                 * outer-level functions as the rest aren't independently
                 * importable.
                 */
                if ( 0 == indentation && readLine.startsWith( "def" ) ) {
                    identifier = readLine.replaceAll( "def", "" );
                    identifier = identifier.replaceAll( "\\((.+)", "" );
                    functions.add( identifier );
                    continue;
                }

                /*
                 * Declaration of a constant in the module. Constants are only
                 * the outer-level ones.
                 */
                if ( 0 == indentation && readLine.contains( "=" ) ) {
                    final String[] bits = readLine.split( "=" );
                    constants.addAll( Arrays.asList( bits[0].replaceAll( " ", "" ).split( "," ) ) );

                }

            }

            br.close();
        }

        // handle exceptions
        catch ( final FileNotFoundException fnfe ) {
            System.out.println( "file not found" );
        }

        catch ( final IOException ioe ) {
            ioe.printStackTrace();
        }

        return this;

    }

    public PythonParser print () {
        System.out.println( "Classes: " );
        for ( final String s : classes ) {
            System.out.print( s + "," );
        }

        System.out.println( "\nFunctions: " );
        for ( final String s : functions ) {
            System.out.print( s + "," );
        }

        System.out.println( "\nConstants: " );
        for ( final String s : constants ) {
            System.out.print( s + "," );
        }

        return this;
    }

}
