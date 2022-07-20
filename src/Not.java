import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/**
 * UnaryExpression abstract class. Extends BaseExpression.
 */
public class Not extends UnaryExpression {
    /**
     * Constructor.
     * @param expression the expression.
     */
    protected Not(Expression expression) {
        super(expression);
    }
    @Override
    public Expression assign(String var, Expression expression) {
        return new Not(this.getExpression().assign(var, expression));
    }
    @Override
    public Boolean evaluate() throws Exception {
        return !this.getExpression().evaluate();
    }
    @Override
    public Expression nandify() {
        return new Nand(this.getExpression().nandify(), this.getExpression().nandify());
    }
    @Override
    public Expression norify() {
        return new Nor(this.getExpression().norify(), this.getExpression().norify());
    }
    @Override
    public Expression simplify() {
        //we'll simplify this Not expression.
        Not exp1 = new Not((this.getExpression()).simplify());
        try {
            //we'll try to evaluate it.
            return new Val(exp1.evaluate());
        } catch (Exception e) {
            //if we couldn't evaluate it, we'll return a Not of the simplified Expression of this first expression.
            return exp1;
        }
    }
    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return !this.getExpression().evaluate(assignment);
    }
    @Override
    public String toString() {
        return "~(" + this.getExpression().toString() + ")";
    }
    @Override
    public boolean isTheGivenType(String type) {
        if (type == null) {
            return false;
        }
        return type.equals("Not");
    }
    @Override
    public java.util.List<Expression> getExpressions() {
        java.util.List<Expression> lst = new LinkedList<Expression>();
        lst.add(this.getExpression());
        return lst;
    }
    @Override
    public boolean equals(Expression e) {
        //we'll check if the given expression is also a not expression.
        if (e.isTheGivenType("Not")) {
            //if it is, we'll get the list of expressions in the given not.
            java.util.List<Expression> lst = e.getExpressions();
            //if somehow, the list doesn't contain 1 expression, something is wrong so we'll return false.
            if (lst.size() != 1) {
                return false;
            }
            //we'll check if the expressions of these two Nots equal.
            Expression e1 = lst.get(0);
            Expression thisE1 = this.getExpression();
            return e1.equals(thisE1);
        }
        return false;
    }
}
