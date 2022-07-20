import java.util.LinkedList;
import java.util.Map;
/**
 * Class Or. Extends BinaryExpression abstract class, and let us calculate expression1 Or expression2.
 */
public class Or extends BinaryExpression {
    /**
     * Constructor.
     * @param expression the first expression.
     * @param expression2 the second expression.
     */
    public Or(Expression expression, Expression expression2) {
        super(expression, expression2);
    }
    @Override
    public Boolean evaluate() throws Exception {
        return this.getExpression().evaluate() || this.getExpression2().evaluate();
    }
    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return this.getExpression().evaluate(assignment) || this.getExpression2().evaluate(assignment);
    }
    @Override
    public Expression assign(String var, Expression expression) {
        return new Or(this.getExpression().assign(var, expression), this.getExpression2().assign(var, expression));
    }
    @Override
    public Expression nandify() {
        Expression exp1Nand = this.getExpression().nandify();
        Expression exp2Nand = this.getExpression2().nandify();
        return new Nand(new Nand(exp1Nand, exp1Nand), new Nand(exp2Nand, exp2Nand));
    }
    @Override
    public Expression norify() {
        Expression exp1Nor = this.getExpression().norify();
        Expression exp2Nor = this.getExpression2().norify();
        return new Nor(new Nor(exp1Nor, exp2Nor), new Nor(exp1Nor, exp2Nor));
    }
    @Override
    public Expression simplify() {
        //we'll create an Or with this expressions simplified.
        Or exp1 = new Or((this.getExpression()).simplify(), (this.getExpression2()).simplify());
        try {
            //we'll try to evaluate and return a Val object with its value.
            return new Val(exp1.evaluate());
        } catch (Exception e) {
            Expression a1, a2;
            try {
                //we'll try to evaluate the simplified first expression.
                a1 = exp1.getExpression();
                if (a1.evaluate()) {
                    //if it's true, we'll return Val of true.
                    return new Val(true);
                }
                //if the first was false, we'll return the simplified second.
                return exp1.getExpression2();
            } catch (Exception e1) {
                //if we couldn't evaluate it, we'll try to evaluate the simplified second expression.
                a2 = exp1.getExpression2();
                try {
                    //we'll try to evaluate the second.
                    boolean b = a2.evaluate();
                    if (b) {
                        //if it's true we'll return Val of true.
                        return new Val(true);
                    }
                    //if it's false we'll return the simplified first.
                    return exp1.getExpression();
                } catch (Exception e2) {
                    //If we couldn't calculate the second, we don't have to do nothing.
                }
            }
            //if the expressions equals we'll return the simplified first.
            if ((exp1.getExpression()).equals(exp1.getExpression2())) {
                return exp1.getExpression();
            }
            //if we reached that place we'll return Or of the simplified expressions.
            return exp1;
        }
    }
    @Override
    public String toString() {
        return "(" + this.getExpression().toString() + " | " + this.getExpression2().toString() + ")";
    }
    @Override
    public boolean isTheGivenType(String type) {
        if (type == null) {
            return false;
        }
        return type.equals("Or");
    }
    @Override
    public java.util.List<Expression> getExpressions() {
        java.util.List<Expression> lst = new LinkedList<Expression>();
        lst.add(this.getExpression());
        lst.add(this.getExpression2());
        return lst;
    }
    @Override
    public boolean equals(Expression e) {
        //we'll check if also the given expression is Or.
        if (e.isTheGivenType("Or")) {
            //we'll get a list of the expressions in the given expression.
            java.util.List<Expression> lst = e.getExpressions();
            /*
             * if the list of the expressions wasn't in length of 2, something is wrong so we'll return false.
             * It might not happen.
             */
            if (lst.size() != 2) {
                return false;
            }
            //we'll check if these expressions equal (also for commutative).
            Expression e1 = lst.get(0);
            Expression e2 = lst.get(1);
            Expression thisE1 = this.getExpression();
            Expression thisE2 = this.getExpression2();
            return (e1.equals(thisE1) && e2.equals(thisE2)) || (e1.equals(thisE2) && e2.equals(thisE1));
        }
        return false;
    }
}
