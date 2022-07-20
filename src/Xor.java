import java.util.LinkedList;
import java.util.Map;
/**
 * Class Xor. Extends BinaryExpression. Lets us calculate the Xor of two expressions.
 */
public class Xor extends BinaryExpression {
    /**
     * Constructor.
     * @param expression the first expression.
     * @param expression2 the second expression.
     */
    public Xor(Expression expression, Expression expression2) {
        super(expression, expression2);
    }
    @Override
    public Boolean evaluate() throws Exception {
        boolean a = this.getExpression().evaluate();
        boolean b = this.getExpression2().evaluate();
        return (a && !b) || (!a && b);
    }
    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        boolean a = this.getExpression().evaluate(assignment);
        boolean b = this.getExpression2().evaluate(assignment);
        return (a && !b) || (!a && b);
    }
    @Override
    public Expression assign(String var, Expression expression) {
        return new Xor(this.getExpression().assign(var, expression), this.getExpression2().assign(var, expression));
    }
    @Override
    public Expression nandify() {
        Expression exp1Nand = this.getExpression().nandify();
        Expression exp2Nand = this.getExpression2().nandify();
        return new Nand(new Nand(exp1Nand, new Nand(exp1Nand, exp2Nand)),
                new Nand(exp2Nand, new Nand(exp1Nand, exp2Nand)));
    }
    @Override
    public Expression norify() {
        Expression exp1Nor = this.getExpression().norify();
        Expression exp2Nor = this.getExpression2().norify();
        return new Nor(new Nor(new Nor(exp1Nor, exp1Nor), new Nor(exp2Nor, exp2Nor)), new Nor(exp1Nor, exp2Nor));
    }
    @Override
    public Expression simplify() {
        //we'll create a new Xor of the simplified expressions of this Xor.
        Xor exp1 = new Xor((this.getExpression()).simplify(), (this.getExpression2()).simplify());
        //we'll check if the expressions equals (also for commutative).
        if ((exp1.getExpression()).equals(exp1.getExpression2())) {
            //if these are the same we'll return Val of false.
            return new Val(false);
        }
        try {
            //we'll try to evaluate the expression. if we could, we'll return a Val of this value.
            return new Val(exp1.evaluate());
        } catch (Exception e) {
            try {
                //we'll try to evaluate the first expression.
                boolean a = (exp1.getExpression()).evaluate();
                if (a) {
                    /*
                     * if it's true, we'll return Not of the second one (we know that we couldn't evaluate the Xor, so
                     * one of the expressions isn't evaluatable, so we have to return its Not).
                     */
                    return new Not(exp1.getExpression2());
                }
                //if the first is false, we'll return the second simplified.
                return exp1.getExpression2();
            } catch (Exception e1) {
                //if we couldn't evaluate the first we'll try to evaluate the second.
                try {
                    boolean b = (exp1.getExpression2()).evaluate();
                    if (b) {
                        //if the second value is true, we'll return Not of the first simplified expression.
                        return new Not(exp1.getExpression());
                    }
                    //if it's false we'll return the first simplified expression.
                    return exp1.getExpression();
                } catch (Exception e2) {
                    //if we reached here, we have to return a Xor of the simplified expressions.
                    return exp1;
                }
            }
        }
    }
    @Override
    public String toString() {
        return "(" + this.getExpression().toString() + " ^ " + this.getExpression2().toString() + ")";
    }
    @Override
    public boolean isTheGivenType(String type) {
        if (type == null) {
            return false;
        }
        return type.equals("Xor");
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
        //we'll check if the given expression is also Xor.
        if (e.isTheGivenType("Xor")) {
            //if it is we'll check the given expression expressionsList.
            java.util.List<Expression> lst = e.getExpressions();
            //if somehow the lenght of the list is not 2, something is wrong (it might not happen) we'll return false.
            if (lst.size() != 2) {
                return false;
            }
            //we'll check if the expressions equal (also for commutative).
            Expression e1 = lst.get(0);
            Expression e2 = lst.get(1);
            Expression thisE1 = this.getExpression();
            Expression thisE2 = this.getExpression2();
            return (e1.equals(thisE1) && e2.equals(thisE2)) || (e1.equals(thisE2) && e2.equals(thisE1));
        }
        return false;
    }
}
