package system.cipher.RSA;


import system.utils.Base64;

public class RSA {

    //�������Ǿ���·����Ҫʹ�õĻ���Ҫ���ģ����exe���Ҳ�ȷ���ܲ���ʹ�øò��ֹ��ܣ����ܻ����
    public static String filepath = "C:\\Users\\23076\\Downloads\\Desktop\\MyProject\\Cryptosystem\\src\\system\\cipher\\RSA\\file";
   
    public RSA(){
        RSAEncrypt.genKeyPair(filepath);
    }

    public static String getCiphertext1(String plaintext1) throws Exception {
        byte[] cipherData=RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(RSAEncrypt.loadPublicKeyByFile(filepath)),plaintext1.getBytes());
        return Base64.encode(cipherData);
    }

    public static String getPlaintext1(String ciphertext1) throws Exception {
        byte[] res=RSAEncrypt.decrypt(RSAEncrypt.loadPrivateKeyByStr(RSAEncrypt.loadPrivateKeyByFile(filepath)), Base64.decode(ciphertext1));
        return new String(res);
    }

    public static String getCiphertext2(String plaintext2) throws Exception {
        byte[] cipherData=RSAEncrypt.encrypt(RSAEncrypt.loadPrivateKeyByStr(RSAEncrypt.loadPrivateKeyByFile(filepath)),plaintext2.getBytes());
       return Base64.encode(cipherData);
    }

    public static String getPlaintext2(String ciphertext2) throws Exception {
        byte[] res=RSAEncrypt.decrypt(RSAEncrypt.loadPublicKeyByStr(RSAEncrypt.loadPublicKeyByFile(filepath)), Base64.decode(ciphertext2));
        return new String(res);
    }

    public static String getSignStr(String content) throws Exception {
        String signStr=RSASignature.sign(content,RSAEncrypt.loadPrivateKeyByFile(filepath));
        return signStr;
    }

    public static boolean getResult(String content) throws Exception {
        return RSASignature.doCheck(content, getSignStr(content), RSAEncrypt.loadPublicKeyByFile(filepath));
    }
}
