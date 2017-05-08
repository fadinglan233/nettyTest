package common;

/**
 * Created by fadinglan on 2017/4/21.
 */
public class StringToArray {

    /**
     * 文本按标点符号分隔转换成数组
     * @param content 文本内容
     * @param punctuation 标点符号
     * @return
     */
    public static String[] splitByPunctuation(String content, String punctuation){

        String[] resultArray = content.split(punctuation);
        return resultArray;

    }

}
