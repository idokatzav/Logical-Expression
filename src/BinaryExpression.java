import java.util.List;
import java.util.Map;

/**
 * an abstract class that extends BaseExpression abstract class.
 */
public abstract class BinaryExpression extends BaseExpression {
    private Expression expression2;
    /**
     * Super Constructor for all Binary Expressions.
     * @param expression1 the first expression.
     * @param expression2 the second expression.
     */
    protected BinaryExpression(Expression expression1, Expression expression2) {
        super(expression1);
        this.expression2 = expression2;
    }
    /**
     * Getter for this.expression2.
     * @return this.expression2.
     */
    protected Expression getExpression2() {
        return this.expression2;
    }
    @Override
    public abstract Expression assign(String var, Expression expression);
    @Override
    public abstract Boolean evaluate() throws Exception;
    @Override
    public abstract Expression nandify();
    @Override
    public abstract Expression norify();
    @Override
    public abstract Expression simplify();
    @Override
    public abstract Boolean evaluate(Map<String, Boolean> assignment) throws Exception;
    @Override
    public List<String> getVariables() {
        List<String> lst = this.getExpression().getVariables();
        List<String> lst2 = this.getExpression2().getVariables();
        for (String str: lst2) {
            boolean continues = true;
            for (String str2: lst) {
                if (continues) {
                    if (str.equals(str2)) {
                        continues = false;
                    }
                }
            }
            if (continues) {
                lst.add(str);
            }
        }
        return lst;
    }
    @Override
    public abstract boolean isTheGivenType(String type);
    @Override
    public abstract boolean equals(Expression e);
    @Override
    public abstract java.util.List<Expression> getExpressions();
}
