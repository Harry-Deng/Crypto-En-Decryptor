package system.cipher;

public class RC4 {
    /*KSA*/
    private static byte[] InitKey(String key) {

        char keys[] = key.toCharArray();
        byte T[] = new byte[256];
        byte S[] = new byte[256];

        /*对S、T进行初始化*/
        for (int i = 0; i < 256; i++) {
            S[i] = (byte) i;
            T[i] = (byte) (keys[i % key.length()]);
        }
        int j = 0;
        /*数据表S的初始置换*/
        for (int i = 0; i < 256; i++) {
            j = ((T[i] & 0xff) + (S[i] & 0xff) + j) & 0xff;
            byte t = S[i];
            S[i] = S[j];
            S[j] = t;
        }
        return S;
    }

    /*PRGA*/
    private static byte[] keyflow(byte[] input, String mkkey) {
        int i = 0, j = 0;
        byte S[] = InitKey(mkkey);
        int t;
        byte[] result = new byte[input.length];

        for (int o = 0; o < input.length; o++) {
            i = (i + 1) & 0xff;
            j = ((S[i] & 0xff) + j) & 0xff;
            byte tt = S[i];
            S[i] = S[j];
            S[j] = tt;
            t = ((S[i] & 0xff) + (S[j] & 0xff)) & 0xff;
            result[o] = (byte) (input[o] ^ S[t]);
        }
        return result;
    }

    public static byte[] encry_RC4_byte(String data, String key) {
        if (data == null || key == null) {
            return null;
        }
        byte b_data[] = data.getBytes();
        return keyflow(b_data, key);
    }

    public static String encry_RC4_string(String data, String key) {
        if (data == null || key == null) {
            return null;
        }
        return toHexString(asString(encry_RC4_byte(data, key)));
    }

    public static String decry_RC4_byte(byte[] data, String key) {
        if (data == null || key == null) {
            return null;
        }
        return asString(keyflow(data, key));
    }


    public static String decry_RC4_string(String data, String key) {
        if (data == null || key == null) {
            return null;
        }
        return new String(keyflow(HexString2Bytes(data), key));
    }


    private static String asString(byte[] buf) {
        StringBuffer strbuf = new StringBuffer(buf.length);
        for (int i = 0; i < buf.length; i++) {
            strbuf.append((char) buf[i]);
        }
        return strbuf.toString();
    }

    private static String toHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch & 0xFF);
            if (s4.length() == 1) {
                s4 = '0' + s4;
            }
            str = str + s4;
        }
        return str;// 0x表示十六进制
    }


    private static byte[] HexString2Bytes(String src) {
        int size = src.length();
        byte[] ret = new byte[size / 2];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < size / 2; i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }

    private static byte uniteBytes(byte src0, byte src1) {
        char _b0 = (char) Byte.decode("0x" + new String(new byte[]{src0}))
                .byteValue();
        _b0 = (char) (_b0 << 4);
        char _b1 = (char) Byte.decode("0x" + new String(new byte[]{src1}))
                .byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }

}