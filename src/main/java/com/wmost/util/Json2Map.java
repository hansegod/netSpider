/**
@description  json与map互转工具类
@author hanse/irene
@data	2017-04-08	00:00	初稿
		2017-04-21	00:00	整理代码
		
		
**/


package com.wmost.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Json2Map {
    private static final String QUOTE = "\"";  
    
    /** 
     * 将Map转成一个json对象
     * 
     * @param body 
     * @param tabCount 
     * @param addComma 
     * @return 
     */
    public static String map2Json(Map<String, Object> body, int tabCount, boolean addComma)  
    {  
        StringBuilder sbJsonBody = new StringBuilder();  
        sbJsonBody.append("{\n");  
        Set<String> keySet = body.keySet();  
        int count = 0;  
        int size = keySet.size();  
        for (String key : keySet)  
        {  
            count++;  
            sbJsonBody.append(buildJsonField(key, body.get(key), tabCount + 1, count != size));  
        }  
        sbJsonBody.append(getTab(tabCount));  
        sbJsonBody.append("}");  
        return sbJsonBody.toString();  
    }  
    //生成一个json字段
    private static String buildJsonField(String key, Object value, int tabCount, boolean addComma)  
    {  
        StringBuilder sbJsonField = new StringBuilder();  
        sbJsonField.append(getTab(tabCount));  
        sbJsonField.append(QUOTE).append(key).append(QUOTE).append(": ");  
        sbJsonField.append(buildJsonValue(value, tabCount, addComma));  
        return sbJsonField.toString();  
    }  
    //生成json值对象 
    @SuppressWarnings({"unchecked", "rawtypes"})
	private static String buildJsonValue(Object value, int tabCount, boolean addComma)  
    {  
        StringBuilder sbJsonValue = new StringBuilder();  
        if (value instanceof String)  
        {  
            sbJsonValue.append(QUOTE).append(value).append(QUOTE);  
        }  
        else if (value instanceof Integer || value instanceof Long || value instanceof Double)  
        {  
            sbJsonValue.append(value);  
        }  
        else if (value instanceof java.util.Date)  
        {  
            sbJsonValue.append(QUOTE).append(formatDate((java.util.Date) value)).append(QUOTE);  
        }  
        else if (value.getClass().isArray() || value instanceof java.util.Collection)  
        {  
            sbJsonValue.append(buildJsonArray(value, tabCount, addComma));  
        }  
        else if (value instanceof java.util.Map)  
        {  
            sbJsonValue.append(map2Json((java.util.Map) value, tabCount, addComma));  
        }  
        sbJsonValue.append(buildJsonTail(addComma));  
        return sbJsonValue.toString();  
    }
    //生成json数组对象  
    @SuppressWarnings("rawtypes")
	private static String buildJsonArray(Object value, int tabCount, boolean addComma)  
    {  
        StringBuilder sbJsonArray = new StringBuilder();  
        sbJsonArray.append("[\n");  
        Object[] objArray = null;  
        if (value.getClass().isArray())  
        {  
            objArray = (Object[]) value;  
        }  
        else if (value instanceof java.util.Collection)//将集合类改为对象数组  
        {  
            objArray = ((java.util.Collection) value).toArray();  
        }  
        int size = objArray.length;  
        int count = 0;  
        //循环数组里的每一个对象  
        for (Object obj : objArray)  
        {  
            //加上缩进，比上一行要多一个缩进  
            sbJsonArray.append(getTab(tabCount + 1));  
            //加上对象值，比如一个字符串"String"，或者一个对象  
            sbJsonArray.append(buildJsonValue(obj, tabCount + 1, ++count != size));  
        }  
        sbJsonArray.append(getTab(tabCount));  
        sbJsonArray.append("]");  
        return sbJsonArray.toString();  
    }  
    //加上缩进，即几个\t 
    private static String getTab(int count)  
    {  
        StringBuilder sbTab = new StringBuilder();  
        while (count-- > 0)  
        {  
            sbTab.append("\t");  
        }  
        return sbTab.toString();  
    }  
    //加上逗号
    private static String buildJsonTail(boolean addComma)  
    {  
        return addComma ? ",\n" : "\n";  
    }  
    //格式化日期
    private static String formatDate(java.util.Date date)  
    {  
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);  
    } 
	
	/**
     * 就算是{'a:a{dsa}':"{fdasf[dd]}"} 这样的也可以处理
     * 当然{a:{b:{c:{d:{e:[1,2,3,4,5,6]}}}}}更可以处理
     * @param jsonstring 合法格式的json字符串
     * @return 有可能map有可能是list
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Object json2Map2(String jsonstring){
    	Stack<Map> maps=new Stack<Map>(); 			//用来表示多层的json对象
        Stack<List> lists=new Stack<List>(); 		//用来表示多层的list对象
        Stack<Boolean> islist=new Stack<Boolean>();	//判断是不是list
        Stack<String> keys=new Stack<String>(); 	//用来表示多层的key
         
        boolean hasyinhao=false;
        String keytmp=null;
        Object valuetmp=null;
        StringBuilder builder=new StringBuilder();
        char[] cs=jsonstring.toCharArray();
         
        for (int i = 0; i < cs.length; i++) {
             
            if(hasyinhao){
                if(cs[i]!='\"'&&cs[i]!='\'')
                    builder.append(cs[i]);
                else
                    hasyinhao=false;
                continue;
            }
            switch (cs[i]) {
            case '{': 
            	//如果是{map进栈
            	maps.push(new HashMap());
                islist.push(false);
                continue;
            case '\'':
            case '\"':
                hasyinhao=true;
                continue;
            case ':':
            	//如果是：表示这是一个属性建，key进栈 
                keys.push(builder.toString());
                builder=new StringBuilder();
                continue;
            case '[':
                islist.push(true);
                lists.push(new ArrayList());
                continue;
            case ',':
            	//这是一个分割，因为可能是简单地string的键值对，也有可能是string=map
            	//的键值对，因此valuetmp 使用object类型；
            	//如果valuetmp是null 应该是第一次，如果value不是空有可能是string，那是上一个键值对，需要重新赋值
            	//还有可能是map对象，如果是map对象就不需要了
                boolean listis=islist.peek();
                if(builder.length()>0)
                    valuetmp=builder.toString();
                builder=new StringBuilder();
                if(!listis){
                    keytmp=keys.pop();                  
                    maps.peek().put(keytmp, valuetmp);
                }else
                    lists.peek().add(valuetmp);
                continue;
            case ']':
                islist.pop();
                if(builder.length()>0)
                    valuetmp=builder.toString();
                builder=new StringBuilder();
                lists.peek().add(valuetmp);
                valuetmp=lists.pop();
                continue;
            case '}':
                islist.pop();
                //这里做的和，做的差不多，只是需要把valuetmp=maps.pop();把map弹出栈
                keytmp=keys.pop();
                if(builder.length()>0)
                    valuetmp=builder.toString();
                builder=new StringBuilder();
                maps.peek().put(keytmp, valuetmp);
                valuetmp=maps.pop();
                continue;
            default:                
                builder.append(cs[i]);
                continue;
            }
        }       
        return valuetmp;
    }
}
