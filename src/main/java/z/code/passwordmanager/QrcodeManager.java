package z.code.passwordmanager;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.ImageReader;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import java.io.File;
import java.util.Objects;

public class QrcodeManager {
    private QrcodeManager() {

    }

    public static BitMatrix generateQrcodeBitMatrix(String data, String charset, int width, int height) {
        try {
            return new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, width, height);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void saveAsQrcodeImage(String fileName, String text) {
        try {
            File dir = new File("src/main/images");
            if (!dir.exists()) dir.mkdir();

            MatrixToImageWriter.writeToPath(Objects.requireNonNull(generateQrcodeBitMatrix(text, "UTF-8", 200, 200)), "png", new File(dir + "/" + fileName + ".png").toPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readQrcodeImage(String fileName) {
        File path = new File("src/main/images/" + fileName + ".png");
        if (!path.exists()) return null;

        try {
            BinaryBitmap imageBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageReader.readImage(path.toURI()))));
            Result result = new MultiFormatReader().decode(imageBitmap);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}