package nlpir;

public class NLPIRUtils {

    static {
        if (NLPIRLib.Instance.NLPIR_Init("", 1, "0") == 0)
            System.err.println("NLPIR初始化失败！");
    }

    private NLPIRUtils() {
    };

    public static String paragraphProcess(String sParagraph, int bPOStagged) {
        return NLPIRLib.Instance.NLPIR_ParagraphProcess(sParagraph, bPOStagged);
    }

    public static String getLastErrorMsg() {
        return NLPIRLib.Instance.NLPIR_GetLastErrorMsg();
    }

    public static String getKeyWords(String sLine, int nMaxKeyLimit,
            boolean bWeightOut) {
        return NLPIRLib.Instance.NLPIR_GetKeyWords(sLine, nMaxKeyLimit,
                bWeightOut);
    }

    public static long fingerPrint(String sLine) {
        return NLPIRLib.Instance.NLPIR_FingerPrint(sLine);
    }

    public static int importKeyBlackList(String sFilename) {
        return NLPIRLib.Instance.NLPIR_ImportKeyBlackList(sFilename);
    }

}
