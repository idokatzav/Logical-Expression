import java.util.LinkedList;
import java.util.Map;
/**
 * Class Nor. Extends BinaryExpression class, let us calculate nor of two expressions.
 */
public class Nor extends BinaryExpression {
    /**
     * Constructor.
     * @param expression first expression.
     * @param expression2 second expression.
     */
    public Nor(Expression expression, Expression expression2) {
        super(expression, expression2);
    }
    @Override
    public Boolean evaluate() throws Exception {
        return !(this.getExpression().evaluate() || this.getExpression2().evaluate());
    }
    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return !(this.getExpression().evaluate(assignment) || this.getExpression2().evaluate(assignment));
    }
    @Override
    public Expression assign(String var, Expression expression) {
        return new Nor(this.getExpression().assign(var, expression), this.getExpression2().assign(var, expression));
    }
    @Override
    public Expression nandify() {
        Expression exp1Nand = this.getExpression().nandify();
        Expression exp2Nand = this.getExpression2().nandify();
        return new Nand(new Nand(new Nand(exp1Nand, exp1Nand), new Nand(exp2Nand, exp2Nand)),
                new Nand(new Nand(exp1Nand, exp1Nand), new Nand(exp2Nand, exp2Nand)));
    }
    @Override
    public Expression norify() {
        Expression exp1Nor = this.getExpression().norify();
        Expression exp2Nor = this.getExpression2().norify();
        return new Nor(exp1Nor, exp2Nor);
    }
    @Override
    public Expression simplify() {
        //we'll create a Nor object with the simplify expressions of this.
        Nor exp1 = new Nor((this.getExpression()).simplify(), ((this.getExpression2()).simplify()));
        try {
            //we'll try to evaluate the more simplified object.
            return new Val(exp1.evaluate());
        } catch (Exception e) {
            //if we couldn't evaluate it we'll try to evaluate the parts of it.
            Expression a1, a2;
            try {
                //we'll try to evaluate exp1.firstExpression.
                a1 = exp1.getExpression();
                if (a1.evaluate()) {
                    //if it was true, we'll return a Val object of false
                    return new Val(false);
                }
                try {
                    //if the first was false we'll try to evluate the second and return Val of its negate.
                    return new Val(!(exp1.getExpression2()).evaluate());
                } catch (Exception e1) {
                    //if we couldn't evaluate the second we'll return a Not object of it.
                    return new Not(exp1.getExpression2());
                }
            } catch (Exception e1) {
                //if we couldn't evaluate the first expression we'll try to evaluate the second.
                a2 = exp1.getExpression2();
                try {
                    if (a2.evaluate()) {
                        //if the seconds is true we'll return Val of false.
                        return new Val(false);
                    }
                    //else we'll return a Not object of the simplified first expression.
                    return new Not(exp1.getExpression());
                } catch (Exception e2) {
                    //Do nothing.
                }
            }
            //if these expressions equal we'll return the not of the simplified first.
            if ((exp1.getExpression()).equals((exp1.getExpression2()))) {
                return new Not(exp1.getExpression());
            }
            //if we reached this place, we have to return a Nor of the simplify expressions.
            return exp1;
        }
    }
    @Override
    public String toString() {
        return "(" + this.getExpression().toString() + " V " + this.getExpression2().toString() + ")";
    }
    @Override
    public boolean isTheGivenType(String type) {
        if (type == null) {
            return false;
        }
        return type.equals("Nor");
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
        //we'll check if the given expression is also Nor.
        if (e.isTheGivenType("Nor")) {
            //if it is we'll try to find out if these are equal (also for commutative).
            java.util.List<Expression> lst = e.getExpressions();
            //if somehow there are not two expressions in the given one, something is wrong so we'll return false.
            if (lst.size() != 2) {
                return false;
            }
            //we'll check if the expressions equal.
            Expression e1 = lst.get(0);
            Expression e2 = lst.get(1);
            Expression thisE1 = this.getExpression();
            Expression thisE2 = this.getExpression2();
            return (e1.equals(thisE1) && e2.equals(thisE2)) || (e1.equals(thisE2) && e2.equals(thisE1));
        }
        return false;
    }
}
