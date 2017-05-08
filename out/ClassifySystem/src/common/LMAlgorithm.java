package common;

import db.CalculateWord;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by fadinglan on 2017/4/21.
 */
public class LMAlgorithm {

    //公式中的经验系数 ,100-200
    static int u = 150;

    /**
     * 计算修饰词与词汇相似度
     * @param m_i 修饰词
     * @param text 文档内容
     * @param itemNum 文档中特征词数量
     * @param itemTotal 文档集中特征值数量
     * @param column 文本位置，title or content
     * @return
     * @throws SQLException
     */
    public static Float LMCorrelation(String m_i, String text, int itemNum, int itemTotal, String column, Connection con) throws SQLException {
        int mNum = CalculateWord.getFrequencyNum(text, m_i, "，");
        List<String> total = CalculateWord.getWordNum(m_i, column ,con);
        int wordNum = 0;
        for(int i = 0; i < total.size(); i++){
            wordNum += CalculateWord.getFrequencyNum( total.get(i), m_i, "，|。|！|？");
        }

        float lmCorrelation = (mNum + u * (wordNum/itemTotal))/(itemNum + u);
        return lmCorrelation;

    }

}
