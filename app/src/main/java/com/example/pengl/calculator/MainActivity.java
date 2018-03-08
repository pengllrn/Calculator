package com.example.pengl.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //按键声明
    private Button btn_0,btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9,btn_clear,btn_additive,
            btn_substract,btn_multiply,btn_divide,btn_equal,btn_del,btn_percent,btn_point;
    //显示框声明
    private EditText edit_input;
    private TextView text_result;
    //声明一个全局字符串，用来存储算术式子
    private String str = "";

    //记录上一个输入符号的类型，0代表数字，1代表运算符或者小数点
    private int last_str_type = 1;
    //记录上一个输入的符号
    private String last_str = "";

    //
    private CalculateUtil calculate = new CalculateUtil();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化对象
        btn_0 = (Button) findViewById(R.id.btn_0);
        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_3 = (Button) findViewById(R.id.btn_3);
        btn_4 = (Button) findViewById(R.id.btn_4);
        btn_5 = (Button) findViewById(R.id.btn_5);
        btn_6 = (Button) findViewById(R.id.btn_6);
        btn_7 = (Button) findViewById(R.id.btn_7);
        btn_8 = (Button) findViewById(R.id.btn_8);
        btn_9 = (Button) findViewById(R.id.btn_9);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_additive = (Button) findViewById(R.id.btn_additive);
        btn_substract = (Button) findViewById(R.id.btn_subtract);
        btn_multiply = (Button) findViewById(R.id.btn_multiply);
        btn_divide = (Button) findViewById(R.id.btn_divide);
        btn_equal = (Button) findViewById(R.id.btn_equal);
        btn_del = (Button) findViewById(R.id.btn_del);
        btn_percent = (Button) findViewById(R.id.btn_percent);
        btn_point = (Button) findViewById(R.id.btn_point);
        edit_input = (EditText) findViewById(R.id.edit_input);
        text_result = (TextView) findViewById(R.id.text_result);
        //为所有按钮设置点击事件
        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
        btn_additive.setOnClickListener(this);
        btn_substract.setOnClickListener(this);
        btn_multiply.setOnClickListener(this);
        btn_divide.setOnClickListener(this);
        btn_equal.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_percent.setOnClickListener(this);
        btn_point.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_0:
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:
                last_str_type = 0;//记录这次输入的数据为数值型
                str += ((Button)v).getText().toString();//添加到str
                edit_input.setText(str);//editview显示
                edit_input.setSelection(str.length());//显示光标
                last_str = ((Button)v).getText().toString();//记录这次输入的数据
                break;
            case R.id.btn_subtract://减号
                last_str_type = 1;//记录这次输入的数据为符号型
                if(last_str.equals("+") || last_str.equals("-")){//合并上次输入的加号和减号
                    str = str.substring(0, str.length() - 1);
                }
                str += ((Button)v).getText().toString();//添加到str
                edit_input.setText(str);//editview显示
                edit_input.setSelection(str.length());//设置光标
                last_str = ((Button)v).getText().toString();//记录这次输入的数据
                break;
            case R.id.btn_point:
            case R.id.btn_additive:
            case R.id.btn_multiply:
            case R.id.btn_divide:
                if(last_str_type == 1) {
                    //当输入点，加号，乘号，除号时前面必须是有数
                    if(str.length()==0) break;
                    else if(!last_str.equals("%")) //不是百分号则挤掉
                        str = str.substring(0, str.length() - 1);
                }
                str += ((Button)v).getText().toString();//添加到str
                edit_input.setText(str);//editview显示
                edit_input.setSelection(str.length());//设置光标
                last_str_type = 1;//记录这次输入的数据为符号型
                last_str = ((Button)v).getText().toString();//记录这次输入的数据
                break;
            case R.id.btn_percent://百分号处理
                if(last_str_type == 0){//百分号前面必须是数字
                    last_str_type = 1;//记录这次输入的数据为符号型
                    str += ((Button)v).getText().toString();//添加到str
                    edit_input.setText(str);//editview显示
                    edit_input.setSelection(str.length());//设置光标
                    last_str = ((Button)v).getText().toString();//记录这次输入的数据
                }
                break;
            case R.id.btn_equal://等号
                String result = calculate.calculate(str);//调用工具类函数
                text_result.setText(result);//显示结果
                break;
            case R.id.btn_del://退位键
                if(str.length() > 0) {//str必须大于1
                    str = str.substring(0, str.length() - 1);
                    edit_input.setText(str);
                    edit_input.setSelection(str.length());
                }
                break;
            case R.id.btn_clear://清除键，重置预设
                str = "";
                last_str = "";
                last_str_type = 1;
                edit_input.setText(str);
                text_result.setText("");
                break;
            default:
        }
    }

}
