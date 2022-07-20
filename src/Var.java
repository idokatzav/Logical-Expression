import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * Var Class. Lets us have vars.
 */
public class Var implements Expression {
    private String name;
    /**
     * Constructor.
     * @param name the name of the var.
     */
    public Var(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        if (this.name != null) {
            return this.name;
        }
        return "";
    }
    @Override
    public List<String> getVariables() {
        List<String> lst = new ArrayList<String>();
        if (this.name != null) {
            lst.add(this.name);
        }
        return lst;
    }
    @Override
    public Expression simplify() {
        return this;
    }
    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        if (assignment.containsKey(this.name)) {
            return assignment.get(this.name);
        }
        throw new Exception("this var is not in the map assignment!");
    }
    @Override
    public Boolean evaluate() throws Exception {
        throw new Exception("this var is not in the map assinment!");
    }
    @Override
    public Expression norify() {
        return this;
    }
    @Override
    public Expression assign(String var, Expression expression) {
        if (this.toString().equals(var)) {
            return expression;
        }
        return this;
    }
    @Override
    public Expression nandify() {
        return this;
    }
    @Override
    public boolean isTheGivenType(String type) {
        if (type == null) {
            return false;
        }
        return type.equals("Var");
    }
    @Override
    public java.util.List<Expression> getExpressions() {
        return null;
    }
    @Override
    public boolean equals(Expression e) {
        if (e.isTheGivenType("Var")) {
            return this.toString().equals(e.toString());
        }
        return false;
    }
}
