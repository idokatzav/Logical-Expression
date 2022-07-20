import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * Val class.
 */
public class Val implements Expression {
    private boolean value;
    /**
     * Contructor.
     * @param b the value.
     */
    public Val(boolean b) {
        this.value = b;
    }
    @Override
    public String toString() {
        if (this.value) {
            return "T";
        }
        return "F";
    }
    @Override
    public List<String> getVariables() {
        List<String> lst = new ArrayList<String>();
        return lst;
    }
    @Override
    public Expression simplify() {
        return this;
    }
    @Override
    public Boolean evaluate(Map<String, Boolean> assignment) throws Exception {
        return this.value;
    }
    @Override
    public Boolean evaluate() throws Exception {
        return this.value;
    }
    @Override
    public Expression norify() {
        return this;
    }
    @Override
    public Expression assign(String var, Expression expression) {
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
        return type.equals("Val");
    }
    @Override
    public java.util.List<Expression> getExpressions() {
        return null;
    }
    @Override
    public boolean equals(Expression e) {
        if (e.isTheGivenType("Val")) {
            return this.toString().equals(e.toString());
        }
        return false;
    }
}
