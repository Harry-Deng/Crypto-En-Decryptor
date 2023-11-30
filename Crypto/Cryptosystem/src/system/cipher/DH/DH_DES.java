package system.cipher.DH;

import system.utils.Base64;

import java.util.Map;

public class DH_DES {
    //甲方公钥
    private static byte[] publicKey1;
    //甲方私钥
    private static byte[] privateKey1;
    //甲方本地密钥
    private static byte[] key1;
    //乙方公钥
    private static byte[] publicKey2;
    //乙方私钥
    private static byte[] privateKey2;
    //乙方本地密钥
    private static byte[] key2;

    static Map<String, Object> keyMap1;

    static {
        try {
            keyMap1 = get1();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static Map<String, Object> keyMap2;

    static {
        try {
            keyMap2 = get2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public DH_DES() throws Exception {
//    }

    /**
     * 初始化密钥
     *
     * @throws Exception
     */
   /* public static final void initKey() throws Exception {
        //生成甲方密钥对
        Map<String, Object> keyMap1 = DHCoder.initKey();
        publicKey1 = DHCoder.getPublicKey(keyMap1);
        privateKey1 = DHCoder.getPrivateKey(keyMap1);
        System.out.println("甲方公钥:\n" + Base64.encode(publicKey1));
        System.out.println("甲方私钥:\n" + Base64.encode(privateKey1));
        //由甲方公钥产生本地密钥对
        Map<String, Object> keyMap2 = DHCoder.initKey(publicKey1);
        publicKey2 = DHCoder.getPublicKey(keyMap2);
        privateKey2 = DHCoder.getPrivateKey(keyMap2);
        System.out.println("乙方公钥:\n" + Base64.encode(publicKey2));
        System.out.println("乙方私钥:\n" + Base64.encode(privateKey2));
        key1 = DHCoder.getSecretKey(publicKey2, privateKey1);
        System.out.println("甲方本地密钥:\n" + Base64.encode(key1));
        key2 = DHCoder.getSecretKey(publicKey1, privateKey2);
        System.out.println("乙方本地密钥:\n" + Base64.encode(key2));
    }
*/
    public static String getPuKey1() throws Exception {
        publicKey1 = DHCoder.getPublicKey(keyMap1);
        return Base64.encode(publicKey1);
    }

    public static String getPrKey1() throws Exception {
        privateKey1 = DHCoder.getPublicKey(keyMap1);
        return Base64.encode(privateKey1);
    }

    public static Map<String, Object> get1() throws Exception {
        Map<String, Object> keyMap1 = DHCoder.initKey();
        return keyMap1;
    }


    public static String getPuKey2() throws Exception {
        publicKey2 = DHCoder.getPublicKey(keyMap2);
        return Base64.encode(publicKey2);
    }

    public static String getPrKey2() throws Exception {
        privateKey2 = DHCoder.getPrivateKey(keyMap2);
        return Base64.encode(privateKey2);
    }

    public static Map<String, Object> get2() throws Exception {
        Map<String, Object> keyMap2 = DHCoder.initKey();
        return keyMap2;
    }


    public static String getKey1() throws Exception {
        privateKey1=DHCoder.getPrivateKey(keyMap1);
        publicKey2=DHCoder.getPublicKey(keyMap2);
        key1 = DHCoder.getSecretKey(publicKey2, privateKey1);
        return Base64.encode(key1);
    }

    public static String getKey2() throws Exception {
        privateKey2=DHCoder.getPrivateKey(keyMap2);
        publicKey1=DHCoder.getPublicKey(keyMap1);
        key2 = DHCoder.getSecretKey(publicKey1, privateKey2);
        return Base64.encode(key2);
    }

    //甲方加密
    public static String cipher1(String input1) throws Exception {
        key1=Base64.decode(getKey1());
        byte[] encode1 = DHCoder.encrypt(input1.getBytes(), key1);
        return Base64.encode(encode1);
        //System.out.println("加密:\n" + Base64.encode(encode1));
    }

    //乙方加密
    public static String cipher2(String input2) throws Exception {
        key2=Base64.decode(getKey2());
        byte[] encode2 = DHCoder.encrypt(input2.getBytes(), key2);
        return Base64.encode(encode2);
        //System.out.println("加密:\n" + Base64.encode(encode1));
    }
    //乙方解密
    public static String decpher1(String encode1) throws Exception {
        key2=Base64.decode(getKey2());
        byte[] decode1 = DHCoder.decrypt(Base64.decode(encode1), key2);
        String output1 = new String(decode1);
        return output1;
    }

    //甲方解密
    public static String decpher2(String dencode2) throws Exception {
        key1=Base64.decode(getKey2());
        byte[] decode2 = DHCoder.decrypt(Base64.decode(dencode2), key1);
        String output2 = new String(decode2);
        return output2;
    }

    /**
     * 主方法
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        //initKey();
        System.out.println();
        System.out.println("===甲方向乙方发送加密数据===");
        String input1 = "求知若饥，虚心若愚。";
        System.out.println("原文:\n" + input1);
        System.out.println("---使用甲方本地密钥对数据进行加密---");
        //使用甲方本地密钥对数据加密
        byte[] encode1 = DHCoder.encrypt(input1.getBytes(), key1);
        System.out.println("加密:\n" + Base64.encode(encode1));
        System.out.println("---使用乙方本地密钥对数据库进行解密---");
        //使用乙方本地密钥对数据进行解密
        byte[] decode1 = DHCoder.decrypt(encode1, key2);
        String output1 = new String(decode1);
        System.out.println("解密:\n" + output1);

        System.out.println("/~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~..~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~/");
        //initKey();
        System.out.println("===乙方向甲方发送加密数据===");
        String input2 = "好好学习，天天向上。";
        System.out.println("原文:\n" + input2);
        System.out.println("---使用乙方本地密钥对数据进行加密---");
        //使用乙方本地密钥对数据进行加密
        byte[] encode2 = DHCoder.encrypt(input2.getBytes(), key2);
        System.out.println("加密:\n" + Base64.encode(encode2));
        System.out.println("---使用甲方本地密钥对数据进行解密---");
        //使用甲方本地密钥对数据进行解密
        byte[] decode2 = DHCoder.decrypt(encode2, key1);
        String output2 = new String(decode2);
        System.out.println("解密:\n" + output2);
    }
}