package system.cipher;

import java.util.*;

/**
 * 置换加密技术，密钥为置换和逆置换
 * 通过根据置换表改变明文字符串的排列顺序
 */
public class Permutation {

    public static int[] sortKey(String key) {
        key = key.toLowerCase();
        key = key.replaceAll("[^a-z]", "");

        int[] keyArr = new int[key.length()]; //记录密钥字母出现顺序的数组
        char[] keyArray = key.toCharArray(); //将密钥转换成字符数组
        char[] keyArrayInOrder = key.toCharArray(); //按字母顺序排序之后的密钥字符数组
        Arrays.sort(keyArrayInOrder); //得到字母排序后的字符数组keyArrayInOrder
        for (int i = 0; i < keyArray.length; i++) {//将输入的密钥转化为数字序号
            for (int j = 0; j < keyArrayInOrder.length; j++) {
                if (keyArray[i] == keyArrayInOrder[j]) {
                    keyArray[i] = (char) (j + 1 + '0');
                    break;
                }
            }
        }
        for (int i = 0; i < key.length(); i++) {
            keyArr[i] = keyArray[i] - '0';
        }
        return keyArr;
    }

    /**
     * 根据密钥，改变字符串的排列顺序，
     *
     * @param text     字符串
     * @param keyArray 密钥
     * @return
     */
    public static String EorD(String text, int[] keyArray) {

        text = text.toLowerCase();//去除空格,并转换成小写
        int count = text.length() % keyArray.length == 0 ? text.length() / keyArray.length
                : text.length() / keyArray.length + 1; //count为将明文或者密文按照密钥长度进行划分后的组数
        char[] text_char = new char[count * keyArray.length];

        for (int i = 0; i < text.length(); i++) {//将字符串转化为字符字符数组
            text_char[i] = text.charAt(i);
        }
        for (int i = 0; i < count * keyArray.length; i++) {//位数补齐用空格代替
            if (text_char[i] == '\u0000')
                text_char[i] = ' ';
        }

        List<Integer> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int k : keyArray) {
            list.add(k);
        }
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < keyArray.length; j++) {
                sb.append(text_char[i * keyArray.length + list.indexOf(j + 1)]);
            }
        }
        return sb.toString();
    }

    /**
     * 得到逆置换
     *
     * @param key 置换表
     * @return 逆置换表
     */
    public static int[] reverseKey(int[] key) {
        int[] revKeyArray = new int[key.length];
        List<Integer> list = new ArrayList<>();
        for (int k : key) {
            list.add(k);
        }
        for (int i = 0; i < key.length; i++) {
            revKeyArray[i] = list.indexOf(i + 1) + 1;//List第一个下标是0
        }
        return revKeyArray;
    }

}