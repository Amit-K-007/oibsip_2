package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private TextView textView;
    private String result="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textview);
    }

    public void onClick(View view){
        button = (Button) view;
        String buttonName = button.getText().toString();
        if(buttonName.equals("AC")){
            result="";
            textView.setText("0");
        }
        else if(buttonName.equals("=")){
            String equation = result.replace('×','*');
            equation = equation.replace('÷','/');
            try {
                Expression expression = new ExpressionBuilder(equation).build();
                textView.setText(String.valueOf(expression.evaluate()));
            }
            catch (Exception e){
                String exception = e.getMessage();
                if(exception.equals("Division by zero!")){
                    textView.setText("Math Error");
                    result = "";
                }
                else if(exception.contains("Invalid number of operands available")){
                    textView.setText("Syntax Error");
                    result = "";
                }
                else {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        else{
            int len = result.length();
            if((len!=0) && ((result.charAt(len-1)=='+') || (result.charAt(len-1)=='-') || (result.charAt(len-1)=='×') || (result.charAt(len-1)=='÷')) && ((buttonName.equals("+")) || (buttonName.equals("-")) || (buttonName.equals("×")) || (buttonName.equals("÷")))){
                result = result.substring(0,len-1);
            }
            result = result + buttonName;
            textView.setText(result);
        }
    }
}