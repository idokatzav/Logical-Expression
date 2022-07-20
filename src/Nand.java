import java.util.LinkedList;
import java.util.Map;
/**
 * Nand Class. Extends BinaryExpression class. Lets us calculate the nand value of two expressions.
 */
public class Nand extends BinaryExpression {
    /**
     * Constructor.
     * @param expression the first Expression.
     * @param expression2 the second Expression.
     */
    public Nand(Expression expression, Expression expression2) {
        super(expression, expression2);
    }
    @Override
    public Boolean evaluate() throws Exception {
        return !(this.getExpression().evaluate() && this.getExpression2().evaluate());
    }
    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return !(this.getExpression().evaluate(assignment) && this.getExpression2().evaluate(assignment));
    }
    @Override
    public Expression assign(String var, Expression expression) {
        return new Nand(this.getExpression().assign(var, expression), this.getExpression2().assign(var, expression));
    }
    @Override
    public Expression nandify() {
        Expression exp1Nand = this.getExpression().nandify();
        Expression exp2Nand = this.getExpression2().nandify();
        Expression nandifiedExpression = new Nand(exp1Nand, exp2Nand);
        return nandifiedExpression;
    }
    @Override
    public Expression norify() {
        Expression exp1Nor = this.getExpression().norify();
        Expression exp2Nor = this.getExpression2().norify();
        Expression nandifiedExpression = new Nor(new Nor(new Nor(exp1Nor, exp1Nor), new Nor(exp2Nor, exp2Nor)),
                new Nor(new Nor(exp1Nor, exp1Nor), new Nor(exp2Nor, exp2Nor)));
        return nandifiedExpression;
    }
    @Override
    public Expression simplify() {
        //we'll create a Nand object with the simplified expressions of this0
        Nand exp1 = new Nand((this.getExpression()).simplify(), (this.getExpression2()).simplify());
        try {
            //we'll try to evalute the nand with the simplified expression, if we done, we'll return the value.
            return new Val(exp1.evaluate());
        } catch (Exception e) {
            //now we'll try to evaluate each expression.
            Expression a1, a2;
            try {
                a1 = exp1.getExpression();
                if (!a1.evaluate()) {
                    //if the first expression is false, we'll return true.
                    return new Val(true);
                }
                try {
                    /*
                     * if the first expression is true, we'll try to calculate the value of the second one and
                     * return its negate boolean value (as Val object)
                     */
                    return new Val(!(exp1.getExpression2()).evaluate());
                } catch (Exception e1) {
                    /*
                     * if we can't calculate the second one, we'll return a new Not object of the
                     * simplified second expression.
                    */
                    return new Not(exp1.getExpression2());
                }
            } catch (Exception e1) {
                //if we can't evaluate the first simplified expression we'll try it in the second.
                a2 = exp1.getExpression2();
                try {
                    if (!a2.evaluate()) {
                        //if the value of the second ecpression is false we'll return a Val object of true.
                        return new Val(true);
                    }
                    //if the value is true, we'll return the not of the simplified first expression.
                    return new Not(exp1.getExpression());
                } catch (Exception e2) {
                    //here we don't have to do nothing.
                }
            }
            if ((exp1.getExpression()).equals(exp1.getExpression2())) {
                /*
                 * if the expression equals (also commutative) we'll return a Not object of the
                 * simplified first expression.
                 */
                return new Not(exp1.getExpression());
            }
            //if it's not one of the simplification cases we'll return Nand of the simplified expressions.
            return exp1;
        }
    }
    @Override
    public String toString() {
        return "(" + this.getExpression().toString() + " A " + this.getExpression2().toString() + ")";
    }
    @Override
    public boolean isTheGivenType(String type) {
        if (type == null) {
            return false;
        }
        return type.equals("Nand");
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
        //we'll check if the given expression is also Nand.
        if (e.isTheGivenType("Nand")) {
            //if it's, we'll check if these expressions equal (also for commutative).
            java.util.List<Expression> lst = e.getExpressions();
            //lst.size() should be 2, if it isn't something was wrong somehow so we'll return false.
            if (lst.size() != 2) {
                return false;
            }
            //we'll check if these expressions equals.
            Expression e1 = lst.get(0);
            Expression e2 = lst.get(1);
            Expression thisE1 = this.getExpression();
            Expression thisE2 = this.getExpression2();
            return (e1.equals(thisE1) && e2.equals(thisE2)) || (e1.equals(thisE2) && e2.equals(thisE1));
        }
        return false;
    }
}
