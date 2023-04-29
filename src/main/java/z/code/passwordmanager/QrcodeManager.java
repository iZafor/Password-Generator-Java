package z.code.passwordmanager;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import java.io.File;

public class QrcodeManager {
    public static BitMatrix generateQrcodeBitMatrix(String data, String charset,
                                                    int width, int height) {
        try {
            return new MultiFormatWriter().encode(
                    new String(data.getBytes(charset), charset),
                    BarcodeFormat.QR_CODE, width, height
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void saveQrcodeImage(BitMatrix bitMatrix, String fileFormat, String path) {
        try {
            MatrixToImageWriter.writeToPath(
                    bitMatrix, fileFormat, new File(path).toPath()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}