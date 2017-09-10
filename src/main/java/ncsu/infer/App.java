package ncsu.infer;

/**
 * Hello world!
 *
 */
public class App {
    public static void main ( final String[] args ) {
        final PythonParser pp = new PythonParser( "decoder.py" );
        pp.parse().print();
    }
}
