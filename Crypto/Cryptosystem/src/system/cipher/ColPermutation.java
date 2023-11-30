package system.cipher;

import java.util.ArrayList;
import java.util.Arrays;

public class ColPermutation {
    private static int column;
    //存储了明文转换成密文时的列读取顺序
    private static final ArrayList<Integer> order = new ArrayList<>();


    /**
     * description: 实现加解密步骤中，将文本填入矩阵的功能
     *
     * @Param: text 文本
     * @Param: key  密钥
     * @Param: row  矩阵的行数
     * @return char[][]  存有矩阵字符的二维字符数组
     */
    private static char[][] EorD(String text, String key, int row) {

        getStrKey(key);//将关键字密钥的字符拆分后存入数组
        sortKey(key);//将关键字字母进行排序，即获得后续读取矩阵的顺序
        char[] textChar = new char[row * key.length()];
        for (int i = 0; i < text.length(); i++) {
            textChar[i] = text.charAt(i);
        }
        char[][] textArr = textToChars(text, row, textChar);
        return textArr;
    }

    /**
     * description:将关键字密钥的字符拆分后存入数组
     *
     * @Param: key  密钥
     * @return java.lang.String[]  原始密钥的字符串数组
     */
    private static String[] getStrKey(String key) {
        key = key.toLowerCase();//将密钥转换成小写
        key = key.replaceAll("[^a-z]", "");//去除所有的非字母字符
        String[] strKey = key.split("");
        return strKey;
    }

    /**
     * description: 将关键字字母进行排序，即获得后续读取矩阵的顺序
     *
     * @Param: key 密钥
     */
    private static void sortKey(String key) {
        key = key.toLowerCase();//将密钥转换成小写
        key = key.replaceAll("[^a-z]", "");//去除所有的非字母字符

        String[] sortKey = key.split("");
        column = key.length();//密钥的长度，即矩阵的列数
        Arrays.sort(sortKey);//对密钥字符串中的字母进行排序，得到排序完成的字符串数组sortKey
        for (int i = 0; i < column; i++) {
            for (int j = 0; j < column; j++) {
                //getStrKey(key)为原始密钥数组，通过排序前后两个密钥数组的遍历、比对操作，得到读取顺序order
                if (getStrKey(key)[i].equals(sortKey[j]) && !order.contains(j)) {
                    order.add(j);
                }
            }
        }

    }

    /**
     * description: 将字符串形式的文本转换成二维数组
     *
     * @return char[][]  二维字符数组
     * @Param: text  文本
     * @Param: row   行数
     * @Param: text_char  文本的一位数组
     */
    private static char[][] textToChars(String text, int row, char[] text_char) {

        text = text.toLowerCase();
        char[][] textArr = new char[row][column];
        //将明文字符串存入一维数组中,数组长度为 row * column，空余部分为\0
        for (int n = 0; n < text.length(); n++) {
            text_char[n] = text.charAt(n);
        }
        for (int m = 0; m < row * column; m++) { //将字符数组中的空字符'\0'替换为0
            if (text_char[m] == '\u0000')
                text_char[m] = ' ';
        }
        for (int i = 0; i < row; i++) {//将明文从一维数组存入二维数组
            for (int j = i * column, k = 0; j < column * (i + 1) && k < column; j++, k++) {
                textArr[i][k] = text_char[j];
            }
        }

        return textArr;
    }

    /**
     * description: 加密
     *
     * @Param: plaintext  明文
     * @Param: key  密钥
     */
    public static String encryption(String plaintext, String key) {

        column = key.length();
        int row = plaintext.length() % column == 0 ?
                plaintext.length() / column : (plaintext.length() / column) + 1;
        char[][] plaintextArr = EorD(plaintext, key, row);
        char[][] ciphertextArr = new char[row][column];
        StringBuilder ciphertext = new StringBuilder();
        for (int x = 0; x < row; x++) {//按预定顺序读取明文各列填入密文矩阵
            for (int y = 0; y < column; y++) {
                ciphertextArr[x][y] = plaintextArr[x][order.get(y)];
            }
        }
        for (int i = 0; i < row; i++) {//把密文的二维字符数组按行读取转化为字符串
            for (int j = 0; j < column; j++) {
                ciphertext.append(ciphertextArr[i][j]);
            }
        }
        return ciphertext.toString();
    }

    /**
     * description: 解密
     * @Param: ciphertext  密文
     * @Param: key  密钥
     */
    public static String decryption(String ciphertext, String key) {

        column = key.length();
        int row = ciphertext.length() % column == 0 ?
                ciphertext.length() / column : (ciphertext.length() / column) + 1;
        char[][] ciphertextArr = EorD(ciphertext, key, row);
        char[][] plaintextArr = new char[row][column];
        StringBuilder plaintext = new StringBuilder();

        for (int x = 0; x < row; x++) {
            for (int y = 0; y < column; y++) {
                plaintextArr[x][order.get(y)] = ciphertextArr[x][y];
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                plaintext.append(plaintextArr[i][j]);
            }
        }
        return plaintext.toString();
    }
}

