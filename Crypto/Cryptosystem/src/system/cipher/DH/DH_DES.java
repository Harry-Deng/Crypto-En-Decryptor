package system.cipher.DH;

import system.utils.Base64;

import java.util.Map;

public class DH_DES {
    //�׷���Կ
    private static byte[] publicKey1;
    //�׷�˽Կ
    private static byte[] privateKey1;
    //�׷�������Կ
    private static byte[] key1;
    //�ҷ���Կ
    private static byte[] publicKey2;
    //�ҷ�˽Կ
    private static byte[] privateKey2;
    //�ҷ�������Կ
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
     * ��ʼ����Կ
     *
     * @throws Exception
     */
   /* public static final void initKey() throws Exception {
        //���ɼ׷���Կ��
        Map<String, Object> keyMap1 = DHCoder.initKey();
        publicKey1 = DHCoder.getPublicKey(keyMap1);
        privateKey1 = DHCoder.getPrivateKey(keyMap1);
        System.out.println("�׷���Կ:\n" + Base64.encode(publicKey1));
        System.out.println("�׷�˽Կ:\n" + Base64.encode(privateKey1));
        //�ɼ׷���Կ����������Կ��
        Map<String, Object> keyMap2 = DHCoder.initKey(publicKey1);
        publicKey2 = DHCoder.getPublicKey(keyMap2);
        privateKey2 = DHCoder.getPrivateKey(keyMap2);
        System.out.println("�ҷ���Կ:\n" + Base64.encode(publicKey2));
        System.out.println("�ҷ�˽Կ:\n" + Base64.encode(privateKey2));
        key1 = DHCoder.getSecretKey(publicKey2, privateKey1);
        System.out.println("�׷�������Կ:\n" + Base64.encode(key1));
        key2 = DHCoder.getSecretKey(publicKey1, privateKey2);
        System.out.println("�ҷ�������Կ:\n" + Base64.encode(key2));
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

    //�׷�����
    public static String cipher1(String input1) throws Exception {
        key1=Base64.decode(getKey1());
        byte[] encode1 = DHCoder.encrypt(input1.getBytes(), key1);
        return Base64.encode(encode1);
        //System.out.println("����:\n" + Base64.encode(encode1));
    }

    //�ҷ�����
    public static String cipher2(String input2) throws Exception {
        key2=Base64.decode(getKey2());
        byte[] encode2 = DHCoder.encrypt(input2.getBytes(), key2);
        return Base64.encode(encode2);
        //System.out.println("����:\n" + Base64.encode(encode1));
    }
    //�ҷ�����
    public static String decpher1(String encode1) throws Exception {
        key2=Base64.decode(getKey2());
        byte[] decode1 = DHCoder.decrypt(Base64.decode(encode1), key2);
        String output1 = new String(decode1);
        return output1;
    }

    //�׷�����
    public static String decpher2(String dencode2) throws Exception {
        key1=Base64.decode(getKey2());
        byte[] decode2 = DHCoder.decrypt(Base64.decode(dencode2), key1);
        String output2 = new String(decode2);
        return output2;
    }

    /**
     * ������
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        //initKey();
        System.out.println();
        System.out.println("===�׷����ҷ����ͼ�������===");
        String input1 = "��֪�������������ޡ�";
        System.out.println("ԭ��:\n" + input1);
        System.out.println("---ʹ�ü׷�������Կ�����ݽ��м���---");
        //ʹ�ü׷�������Կ�����ݼ���
        byte[] encode1 = DHCoder.encrypt(input1.getBytes(), key1);
        System.out.println("����:\n" + Base64.encode(encode1));
        System.out.println("---ʹ���ҷ�������Կ�����ݿ���н���---");
        //ʹ���ҷ�������Կ�����ݽ��н���
        byte[] decode1 = DHCoder.decrypt(encode1, key2);
        String output1 = new String(decode1);
        System.out.println("����:\n" + output1);

        System.out.println("/~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~..~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~.~/");
        //initKey();
        System.out.println("===�ҷ���׷����ͼ�������===");
        String input2 = "�ú�ѧϰ���������ϡ�";
        System.out.println("ԭ��:\n" + input2);
        System.out.println("---ʹ���ҷ�������Կ�����ݽ��м���---");
        //ʹ���ҷ�������Կ�����ݽ��м���
        byte[] encode2 = DHCoder.encrypt(input2.getBytes(), key2);
        System.out.println("����:\n" + Base64.encode(encode2));
        System.out.println("---ʹ�ü׷�������Կ�����ݽ��н���---");
        //ʹ�ü׷�������Կ�����ݽ��н���
        byte[] decode2 = DHCoder.decrypt(encode2, key1);
        String output2 = new String(decode2);
        System.out.println("����:\n" + output2);
    }
}