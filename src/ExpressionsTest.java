import java.util.Map;
import java.util.TreeMap;
/**
 * Expression Test Class.
 */
public class ExpressionsTest {
    /**
     * The main function runs a short Test.
     * @param args arguments from command line.
     */
    public static void main(String[] args) {
        Expression exp = new And(new Xor(new Var("x"), new Xor(new Var("z"), new Val(false))),
                new Or(new And(new Var("x"), new Var("y")), new And(new Var("y"), new Var("x"))));
        System.out.println(exp.toString());
        Map<String, Boolean> assignment = new TreeMap<>();
        assignment.put("x", true);
        assignment.put("y", true);
        assignment.put("z", false);
        try {
            System.out.println("" + exp.evaluate(assignment));
        } catch (Exception e) {
            System.out.println("Couldn't calculate");
        }
        System.out.println(exp.nandify().toString());
        System.out.println(exp.norify().toString());
        System.out.println(exp.simplify().toString());
    }
}
