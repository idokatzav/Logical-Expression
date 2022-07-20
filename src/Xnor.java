import java.util.LinkedList;
import java.util.Map;
/**
 * Class Xnor. Extends BinaryExpression abstract class, and let us calculate expression1 Xnor expression2.
 */
public class Xnor extends BinaryExpression {
    /**
     * Constructor.
     * @param expression the first expression.
     * @param expression2 the second expression.
     */
    public Xnor(Expression expression, Expression expression2) {
        super(expression, expression2);
    }
    @Override
    public Boolean evaluate() throws Exception {
        boolean a = this.getExpression().evaluate();
        boolean b = this.getExpression2().evaluate();
        return (a && b) || (!a && !b);
    }
    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        boolean a = this.getExpression().evaluate(assignment);
        boolean b = this.getExpression2().evaluate(assignment);
        return (a && b) || (!a && !b);
    }
    @Override
    public Expression assign(String var, Expression expression) {
        return new Xnor(this.getExpression().assign(var, expression), this.getExpression2().assign(var, expression));
    }
    @Override
    public Expression nandify() {
        Expression exp1Nand = this.getExpression().nandify();
        Expression exp2Nand = this.getExpression2().nandify();
        return new Nand(new Nand(new Nand(exp1Nand, exp1Nand), new Nand(exp2Nand, exp2Nand)),
                new Nand(exp1Nand, exp2Nand));
    }
    @Override
    public Expression norify() {
        Expression exp1Nor = this.getExpression().norify();
        Expression exp2Nor = this.getExpression2().norify();
        return new Nor(new Nor(exp1Nor, new Nor(exp1Nor, exp2Nor)), new Nor(exp2Nor, new Nor(exp1Nor, exp2Nor)));
    }
    @Override
    public Expression simplify() {
        //we'll create a Xnor of this two expression - both simplified.
        Xnor exp1 = new Xnor((this.getExpression()).simplify(), (this.getExpression2()).simplify());
        //we'll check if this two simplified expressions equal, if equal, we'll return Val of true.
        if ((exp1.getExpression()).equals(exp1.getExpression2())) {
            return new Val(true);
        }
        try {
            //we'll try to evaluate this expression when its expressions are simplified.
            return new Val(exp1.evaluate());
        } catch (Exception e) {
            //we'll return a Xnor object of this simplified expressions.
            return new Xnor(exp1.getExpression(), exp1.getExpression2());
        }
    }
    @Override
    public String toString() {
        return "(" + this.getExpression().toString() + " # " + this.getExpression2().toString() + ")";
    }
    @Override
    public boolean isTheGivenType(String type) {
        if (type == null) {
            return false;
        }
        return type.equals("Xnor");
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
        //we'll check if the given expression is also Xnor.
        if (e.isTheGivenType("Xnor")) {
            //if it's also Xnor, we'll ask for list of the expressions in the given one.
            java.util.List<Expression> lst = e.getExpressions();
            //if somehow the list isn't in length of 2, something is wrong (it might not happen) so we'll return false.
            if (lst.size() != 2) {
                return false;
            }
            //we'll check if equals (also for commutative).
            Expression e1 = lst.get(0);
            Expression e2 = lst.get(1);
            Expression thisE1 = this.getExpression();
            Expression thisE2 = this.getExpression2();
            return (e1.equals(thisE1) && e2.equals(thisE2)) || (e1.equals(thisE2) && e2.equals(thisE1));
        }
        return false;
    }
}
