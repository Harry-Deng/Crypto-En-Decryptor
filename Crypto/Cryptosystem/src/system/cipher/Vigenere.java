package system.cipher;

public class Vigenere {
    static String alpha = "abcdefghijklmnopqrstuvwxyz";

    /**
     * 处理密钥
     *
     * @param textStr 明文或者密文字符串
     * @param key     密钥
     * @return 与textStr长度相等的密钥字符串
     */
    public static String dealKey(String textStr, String key) {
        key = key.toLowerCase();// 将密钥中字母全部转换成小写
        key = key.replaceAll("[^a-z]", "");//去除所有非字母的字符
        String realKey = "";

        StringBuilder tempKey = new StringBuilder(key);
        //如果密钥长度与明文/密文短，则需要以不断重复密钥的方式生成密钥字符串
        if (tempKey.length() != textStr.length()) {
            if (tempKey.length() < textStr.length()) {
                while (tempKey.length() < textStr.length()) {//生成密钥循环重复的字符串
                    tempKey.append(key);
                }
            }
            //此时，密钥字符串的长度大于或等于textStr长度，将密钥字符串截取为与textStr等长的字符串
            realKey = tempKey.substring(0, textStr.length());
        }
        return realKey;
    }


    /**
     * 根据Vigenere密码算法对明文进行加密
     *
     * @param plaintext 明文
     * @param key       密钥
     * @return 密文
     */
    public static String encryption(String plaintext, String key) {
        plaintext = plaintext.toLowerCase();// 将明文转换成小写
        plaintext = plaintext.replaceAll("[^a-z]", "");//去除所有非字母的字符
        key = dealKey(plaintext, key);//处理密钥，使之与明文等长
        int len = key.length();

        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int row = alpha.indexOf(key.charAt(i));//行号，在Vigenere表中找到密钥字母所在行
            int column = alpha.indexOf(plaintext.charAt(i));//列号，在Vigenere表中找到明文字母所在列
            int index = (row + column) % 26;//行列交叉处的字母为密文字母，index为该字母相对于a的偏移量
            ciphertext.append(alpha.charAt(index));//根据index确定密文字母
        }

        return ciphertext.toString();
    }

    /**
     * description:根据vigenere密码算法对密文进行解密
     *
     * @param ciphertext 密文
     * @param key        密钥
     * @return 明文
     */
    public static String decryption(String ciphertext, String key) {

        ciphertext = ciphertext.toLowerCase();// 将密文转换成小写
        ciphertext = ciphertext.replaceAll("[^a-z]", "");//去除所有非字母的字符
        key = dealKey(ciphertext, key);

        int len = key.length();
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int row = alpha.indexOf(key.charAt(i));//行号
            int column = alpha.indexOf(ciphertext.charAt(i));//列号
            int index;
            if (row > column) {
                index = column + 26 - row;
            } else {
                index = column - row;
            }
            plaintext.append(alpha.charAt(index));
        }
        return plaintext.toString();
    }


}

