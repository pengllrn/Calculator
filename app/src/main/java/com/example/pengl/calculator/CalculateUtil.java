package com.example.pengl.calculator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by pengl on 2018/3/7.
 */

public class CalculateUtil {
    private String regEx;//正则表达式

    //工具类接口
    public String calculate(String calculateStr){
        //处理掉“%”
        String tempStr = dealPercent(calculateStr);
        //格式化原始表达式
        tempStr = formatCalcuStr(tempStr);
        return excutionCalcu(tempStr);
    }

    /**
     * 计算掉式子中的百分号
     * @param calculateStr
     * @return
     */
    private String dealPercent(String calculateStr){
        regEx = "((-?\\d+)(\\.\\d+)?)%";//正则表达式
        Matcher matcher = getMatcher(regEx,calculateStr);
        while (matcher.find()){
            //当有符合的对象时，进行替换
            double temp = Double.parseDouble(matcher.group(1)) / 100;
            calculateStr = calculateStr.replace(matcher.group(),doubleTrans(temp));
            calculateStr = formatCalcuStr(calculateStr);
        }
        return calculateStr;
    }

    /**
     * 格式化原始表达式
     * 处理算术表达式中的“-”符号，把减法变成“+-”，以方便后面提取式子中负数的时候不会出错
     * 例如，把-4×-8+2÷-4-4.6×4-5变成-4×-8+2÷-4+-4.6×4+-5
     * @param calculateStr 待转化的算术表达式
     * @return  转换后的“算术表达式”
     */
    private String formatCalcuStr(String calculateStr){
        regEx = "(\\d)(-)(\\d)";//正则表达式
        //获得matcher对象
        Matcher matcher = getMatcher(regEx,calculateStr);
        while (matcher.find()){
            //当有符合的对象时，进行替换
            calculateStr = calculateStr.replace(matcher.group(),matcher.group(1)+"+-"+matcher.group(3));
            calculateStr = formatCalcuStr(calculateStr);
        }
        return calculateStr;
    }

    /**
     * 执行计算
     * @param standardStr 格式化后的算术表达式
     * @return
     */
    private String excutionCalcu(String standardStr){
        //符号的matcher对象
        Matcher matcher_syb = getMatcher("[×÷\\+]",standardStr);
        //数字的matcher对象
        Matcher matcher_num = getMatcher("(-?\\d+)(\\.\\d+)?",standardStr);
        //当符号数与计算数的个数不匹配时，返回错误
        if(getMatcherCount(matcher_syb)!=getMatcherCount(matcher_num)-1) return "错误";
        //计算式子中的乘除法
        String simpleStr = calcuMulandDid(standardStr);
        //计算加法
        //如果简单式子只剩一个数，则直接返回结果
        if(getMatcherCount(getMatcher("(-?\\d+)(\\.\\d+)?",simpleStr))==1){
            return simpleStr;
        }else {
            //进行加法计算
            regEx = "((-?\\d+)(\\.\\d+)?)([+])((-?\\d+)(\\.\\d+)?)";
            Matcher matcher = getMatcher(regEx,simpleStr);
            Double temp = 0d;
            while (matcher.find()){
                temp = 0d;
                temp =temp + Double.parseDouble(matcher.group(1)) + Double.parseDouble(matcher.group(5));
                simpleStr = simpleStr.replace(matcher.group(),doubleTrans(temp));
                matcher = getMatcher(regEx,simpleStr);
            }
            return doubleTrans(temp);
        }
    }

    /**
     * 计算算数表达式中的乘除法
     * @param standardStr
     * @return
     */
    private String calcuMulandDid(String standardStr){
        regEx = "((-?\\d+)(\\.\\d+)?)([×÷])((-?\\d+)(\\.\\d+)?)";
        Matcher matcher = getMatcher(regEx,standardStr);
        while (matcher.find()){
            //数Amatcher.group(1)
            //符号matcher.group(4)
            //数Bmatcher.group(5)
            Double temp=0d;
            if (matcher.group(4).equals("×")) {
                temp = Double.parseDouble(matcher.group(1)) * Double.parseDouble(matcher.group(5));
            }else {
                temp = Double.parseDouble(matcher.group(1)) / Double.parseDouble(matcher.group(5));
            }
            standardStr = standardStr.replace(matcher.group(),doubleTrans(temp));
            matcher = getMatcher(regEx,standardStr);
        }
        return standardStr;
    }

    /**
     * 得到matcher 对象
     * @param regEx 正则表达式
     * @param str 匹配的式子
     * @return
     */
    private Matcher getMatcher(String regEx,String str){
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(str);
    }

    /**
     * 得到与正则表达式匹配的个数
     * @param matcher
     * @return
     */
    private int getMatcherCount(Matcher matcher){
        int count = 0;
        while (matcher.find()){
            //每找到一处匹配的条件时，自加1
            count++;
        }
        return count;
    }

    /**
     * 把double型转换成String
     * @param d
     * @return
     */
    private String doubleTrans(double d){
        if(Math.round(d)-d==0){
            return String.valueOf((long)d);
        }
        return String.valueOf(d);
    }

}
