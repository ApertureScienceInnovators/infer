package ncsu.infer;

/**
 * Hello world!
 *
 */
public class App {
    public static void main ( final String[] args ) {
        final PythonParser pp = new PythonParser( "C:/development/workspace/infer" );
        pp.parseAll();
        pp.print();
    }
}
