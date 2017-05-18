package thread;

import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by fadinglan on 2017/5/17.
 */
public class DigestThread extends Thread {

    private String filename;
    public DigestThread(String filename){
        this.filename = filename;
    }

    @Override
    public void run(){
        try {
            FileInputStream in = new FileInputStream(filename);
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            DigestInputStream din = new DigestInputStream(in,sha);
            while (din.read() != -1);
            din.close();
            byte[] digest = sha.digest();

            StringBuilder result = new StringBuilder(filename);
            result.append(": ");
            result.append(DatatypeConverter.parseHexBinary(digest.toString()));
            System.out.println(result);
        }catch (IOException e){
            System.err.println(e);
        }catch (NoSuchAlgorithmException e1){
            System.err.println(e1);
        }

    }
}
