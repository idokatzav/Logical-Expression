import java.util.List;
import java.util.Map;
/**
 * Expression interface.
 */
public interface Expression {
    /**
     * Evaluate the expression using the variable values provided
     * in the assignment, and return the result. If the expression
     * contains a variable which is not in the assignment, an exception
     * is thrown.
     * @param assignment a map.
     * @return the boolean value.
     * @throws Exception An exception the function can throw for.
     */
    Boolean evaluate(Map<String, Boolean> assignment) throws Exception;
    /**
     * A convenience method. Like the `evaluate(assignment)` method above, but uses an empty assignment.
     * @return returns the boolean value.
     * @throws Exception An exception the function can throw for.
     */
    Boolean evaluate() throws Exception;
    /**
     * Returns a list of the variables in the expression.
     * @return the list of the variables.
     */
    List<String> getVariables();
    /**
     * Returns a nice string representation of the expression.
     * @return the string that descripts the object.
     */
    String toString();
    /**
     * Returns a new expression in which all occurrences of the variable
     * var are replaced with the provided expression (Does not modify the
     * current expression).
     * @param var the string with the expression.
     * @param expression the expression to change var into.
     * @return the assigned Expression.
     */
    Expression assign(String var, Expression expression);
    /**
     * Returns the expression tree resulting from converting all the operations to the logical Nand operation.
     * @return the nandified expression.
     */
    Expression nandify();
    /**
     * Returns the expression tree resulting from converting all the operations to the logical Nor operation.
     * @return the norified expression.
     */
    Expression norify();
    /**
     * Returned a simplified version of the current expression.
     * @return the simplified expression.
     */
    Expression simplify();
    /**
     * Returns the type of the expression (for checking in equals method).
     * @param type the type to check if equals to
     * @return true if it's the same.
     */
    boolean isTheGivenType(String type);
    /**
     * Checks if this expression equals to the other.
     * @param e the other.
     * @return true if equals, false otherwise.
     */
    boolean equals(Expression e);

    /**
     * Returns the expressions, null if there are not.
     * @return the expression list.
     */
    java.util.List<Expression> getExpressions();
}