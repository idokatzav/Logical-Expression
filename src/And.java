import java.util.LinkedList;
import java.util.Map;
/**
 * Class And. Extends BinaryExpression abstract class, and let us calculate expression1 AND expression2.
 */
public class And extends BinaryExpression {
    /**
     * Constructor.
     * @param expression the first expression.
     * @param expression2 the second expression.
     */
    public And(Expression expression, Expression expression2) {
        super(expression, expression2);
    }
    @Override
    public Boolean evaluate() throws Exception {
        return this.getExpression().evaluate() && this.getExpression2().evaluate();
    }
    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return this.getExpression().evaluate(assignment) && this.getExpression2().evaluate(assignment);
    }
    @Override
    public Expression assign(String var, Expression expression) {
        return new And(this.getExpression().assign(var, expression), this.getExpression2().assign(var, expression));
    }
    @Override
    public Expression nandify() {
        Expression exp1Nand = this.getExpression().nandify();
        Expression exp2Nand = this.getExpression2().nandify();
        return new Nand(new Nand(exp1Nand, exp2Nand), new Nand(exp1Nand, exp2Nand));
    }
    @Override
    public Expression norify() {
        Expression exp1Nor = this.getExpression().norify();
        Expression exp2Nor = this.getExpression2().norify();
        return new Nor(new Nor(exp1Nor, exp1Nor), new Nor(exp2Nor, exp2Nor));
    }
    @Override
    public Expression simplify() {
        //we'll create And of the two expressions in this and but simplified.
        And exp1 = new And((this.getExpression()).simplify(), (this.getExpression2()).simplify());
        //we'll try to simplify the and of the two simplified expressions.
        try {
            //if we can, we'll try to evaluate exp1.
            return new Val(exp1.evaluate());
        } catch (Exception e) {
            //if we can't evatluate it, we'll check if one of the expression is false, if it is we'll return false.
            Expression a1, a2;
            try {
                a1 = exp1.getExpression();
                if (!a1.evaluate()) {
                    return new Val(false);
                }
                //if it's value is true, so we'll simplify the second expression and return its simplification.
                try {
                    a2 = exp1.getExpression2();
                    //we'll try to evaluate the simplified second expression, if we can we'll return its valuse.
                    return new Val(a2.evaluate());
                } catch (Exception e2) {
                    //if we can't we'll return its simpliifcation.
                    return exp1.getExpression2();
                }
            } catch (Exception e1) {
                //if we can't evaluate a1 we'll try to evaluate a2 and if it's false return false.
                a2 = exp1.getExpression2();
                try {
                    if (!a2.evaluate()) {
                        return new Val(false);
                    }
                    //if it's true, we'll return the first expression, simplified.
                    return exp1.getExpression();
                } catch (Exception e2) {
                    //if we reached that block it means we can't compute both.
                }
            }
            //if the first expression equals to the second one (also for commutativity) we'll return the first.
            if ((exp1.getExpression()).equals(exp1.getExpression2())) {
                return exp1.getExpression();
            }
            return exp1;
        }
    }
    @Override
    public String toString() {
        return "(" + this.getExpression().toString() + " & " + this.getExpression2().toString() + ")";
    }
    @Override
    public boolean isTheGivenType(String type) {
        //if the type that was given is "And" we'll return true, otherwise false.
        if (type == null) {
            return false;
        }
        return type.equals("And");
    }
    @Override
    public boolean equals(Expression e) {
        //we'll check if the second expression is also And.
        if (e.isTheGivenType("And")) {
            //if it is, we'll try to chcek if these Ands are equal (also for commutativity).
            java.util.List<Expression> lst = e.getExpressions();
            //if somehow the second expression doesn't contain two expression we'll return false (it might not happen).
            if (lst.size() != 2) {
                return false;
            }
            //check if these expressions equal.
            Expression e1 = lst.get(0);
            Expression e2 = lst.get(1);
            Expression thisE1 = this.getExpression();
            Expression thisE2 = this.getExpression2();
            return (e1.equals(thisE1) && e2.equals(thisE2)) || (e1.equals(thisE2) && e2.equals(thisE1));
        }
        return false;
    }
    @Override
    public java.util.List<Expression> getExpressions() {
        java.util.List<Expression> lst = new LinkedList<Expression>();
        lst.add(this.getExpression());
        lst.add(this.getExpression2());
        return lst;
    }
}
