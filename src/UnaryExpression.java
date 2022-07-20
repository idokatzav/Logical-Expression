import java.util.List;
import java.util.Map;
/**
 * UnaryExpression abstract class. Extends BaseExpression.
 */
public abstract class UnaryExpression extends BaseExpression {
    /**
     * Constructor.
     * @param expression the expression.
     */
    protected UnaryExpression(Expression expression) {
        super(expression);
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
        return this.getExpression().getVariables();
    }
    @Override
    public abstract boolean isTheGivenType(String type);
    @Override
    public abstract boolean equals(Expression e);
    @Override
    public abstract java.util.List<Expression> getExpressions();
}
