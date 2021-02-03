package rovie.martin.francisco.customformula;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Activity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import android.view.ViewGroup;
import android.text.InputType;
import android.widget.Button;

import de.congrace.exp4j.*;

public class MainActivity extends Activity {

    public LinearLayout container;
    public TextView result;
    public EditText problem;
    public String strFormula;
    public Button btnCalculate;

    public List<EditText> variableList;
    public Map<String,Double> values;
    public ArrayList<String> variables;
    public final String[] functions = new String[]{"abs", "acos", "asin", "atan", "cbrt", "ceil", "cos", "cosh", "exp", "floor", "log", "sin", "sinh", "sqrt", "tan", "tanh"};
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = findViewById(R.id.container);
        problem = findViewById(R.id.problem);
        result = findViewById(R.id.result);
        btnCalculate = findViewById(R.id.btnCalculate);

        values = new HashMap<>();
        variableList = new ArrayList<>();
    }
 
    public void Analyze(View view){
        btnCalculate.setVisibility(View.VISIBLE);
        strFormula = problem.getText().toString();
        variables = getVariableArray(strFormula);
        variableList.clear();
        container.removeAllViews();

        for(int i = 0; i < variables.size(); i++){
            addEditText(container, variables.get(i));
        }
    }
 
    public void Calculate(View view){
        for(int i = 0; i < variableList.size(); i++){
            if (!variableList.get(i).getText().toString().equals("") && variableList.get(i).getText().toString().matches("\\d+"))
                values.put(variables.get(i), Double.parseDouble(variableList.get(i).getText().toString()));
            else if (variableList.get(i).getText().toString().equals("")) {
                result.setText("You must fill all of the Fields.");
                return;
            }
            else if (!variableList.get(i).getText().toString().matches("\\d+")) {
                result.setText("Only Numbers Allowed to assign in Variables");
                return;
            }
        }
 
        try{
            Calculable calc = new ExpressionBuilder(strFormula)
                .withVariables(values)
                .build();

            double resultAns = calc.calculate();
            result.setText("Final Result is " + resultAns);
        } catch(Exception e){
            result.setText(e.toString());
        }
    }
 
    public void addEditText(LinearLayout container, String hint){
        EditText variables = new EditText(container.getContext());
        variables.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        variables.setHint(hint);

        container.addView(variables, new ViewGroup.LayoutParams(-1,-2));
        variableList.add(variables);
    }
 
    public ArrayList<String> getVariableArray(String formula) {
        ArrayList<String> variableNames = new ArrayList<>();
        String tmpWord = "";

        for (int i = 0; i < strFormula.length(); i++) {
            char currentChar = strFormula.charAt(i);

            if (Character.isLetter(currentChar)) {
                tmpWord = new StringBuilder(String.valueOf(tmpWord)).append(currentChar).toString();
            } else {
                if (tmpWord != "") {
                    addVariableToList(variableNames, tmpWord);
                }
                tmpWord = "";
            }
            if (i == strFormula.length() - 1 && tmpWord != "") {
                addVariableToList(variableNames, tmpWord);
            }
        }
        return variableNames;
    }

    private void addVariableToList(ArrayList<String> variableNames, String variable) {
        if (!isKeyword(variable) && !variableAlreadyAdded(variableNames, variable)) {
            variableNames.add(variable);
        }
    }

    private boolean isKeyword(String word) {
        for (String item : functions) {
            if (item.equals(word)) {
                return true;
            }
        }
        return false;
    }

    private static boolean variableAlreadyAdded(ArrayList<String> variableNames, String variable) {
        if (variableNames.contains(variable)) {
            return true;
        }
        return false;
    }
}

/**
Operators and functions

the following operators are supported:

Addition: '2 + 2'
Subtraction: '2 - 2'
Multiplication: '2 * 2'
Division: '2 / 2'
Exponential: '2 ^ 2'
Unary Minus,Plus (Sign Operators): '+2 - (-2)'
Modulo: '2 % 2'
the following functions are supported:

abs: absolute value
acos: arc cosine
asin: arc sine
atan: arc tangent
cbrt: cubic root
ceil: nearest upper integer
cos: cosine
cosh: hyperbolic cosine
exp: euler's number raised to the power (e^x)
floor: nearest lower integer
log: logarithmus naturalis (base e)
log2: logarithm to base 2 = log(x, 2)
log10: logarithm to base 10 = log(x, 10)
sin: sine
sinh: hyperbolic sine
sqrt: square root
tan: tangent
tanh: hyperbolic tangent
signum: signum of a value
**/