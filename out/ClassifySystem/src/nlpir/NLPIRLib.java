package nlpir;

import com.sun.jna.Library;
import com.sun.jna.Native;

interface NLPIRLib extends Library {

    NLPIRLib Instance = (NLPIRLib) Native.loadLibrary("NLPIR", NLPIRLib.class);

    public int NLPIR_Init(String sDataPath, int encoding, String sLicenceCode);

    public String NLPIR_ParagraphProcess(String sParagraph, int bPOStagged);

    public String NLPIR_GetLastErrorMsg();

    public String NLPIR_GetKeyWords(String sLine, int nMaxKeyLimit,
                                    boolean bWeightOut);

    public long NLPIR_FingerPrint(String sLine);

    public int NLPIR_ImportKeyBlackList(String sFilename);

    public static final int ENCODE_GBK  = 0;
    public static final int ENCODE_UTF8 = 1;
}
