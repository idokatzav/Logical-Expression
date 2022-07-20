import java.util.List;
import java.util.Map;
/**
 * Base expression abstract class. Let us save the base of expressions with one expression.
 */
public abstract class BaseExpression implements Expression {
    private Expression expression;
    /**
     * Super Builder.
     * @param expression the expression.
     */
    protected BaseExpression(Expression expression) {
        this.expression = expression;
    }
    /**
     * Getter for this expression.
     * @return this.expression.
     */
    protected Expression getExpression() {
        return this.expression;
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
    public abstract List<String> getVariables();
    @Override
    public abstract boolean isTheGivenType(String type);
    @Override
    public abstract boolean equals(Expression e);
    @Override
    public abstract java.util.List<Expression> getExpressions();
}
