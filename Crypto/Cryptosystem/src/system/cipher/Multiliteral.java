package system.cipher;


import java.util.ArrayList;
import java.util.List;

public class Multiliteral {
    /*查看输入关键字是否有重复*/
    public static boolean keyword_operate(String k) {

        int[] a = new int[128];
        for (int i = 0; i < k.length(); i++) {
            int c = (int) k.charAt(i);
            if (a[c] > 0)//大于零说明是第二次碰到了，出现重复
                return false;
            else
                a[c]++;
        }
        return true;
    }

    /*加密函数*/
    public static String Encryption(String str, String k) {
        boolean begin = keyword_operate(k);
        if (begin) {
            System.out.println("\t\tThe keyword entered is not duplicated.");
        } else {
            System.out.println("\t\tThe keyword entered is duplicate and cannot continue.");
            System.exit(0);
        }

        String kk = k.toUpperCase();
        char[] keyword = kk.toCharArray();
        String strs = str.toUpperCase();
        char[] plaintext = strs.toCharArray();
        StringBuilder sb = new StringBuilder();

        List<Character> list = new ArrayList<>();
        char[][] alphabet = new char[][]{{'A', 'B', 'C', 'D', 'E'},
                {'F', 'G', 'H', 'I', 'K'},
                {'L', 'M', 'N', 'O', 'P'},
                {'Q', 'R', 'S', 'T', 'U'},
                {'V', 'W', 'X', 'Y', 'Z'},};

        /*关键字没有重复之后进行加密操作*/
        if (begin) {
            for (int i = 0; i < plaintext.length; i++) {
                if (plaintext[i] == 'J') {//特殊处理5*5矩阵中I/J同个单元格但二维数组中只显示了I的情况
                    list.add(keyword[3]);
                    list.add(keyword[1]);
                }
                for (int j = 0; j < alphabet.length; j++) {
                    for (int u = 0; u < alphabet[0].length; u++) {
                        if (plaintext[i] == alphabet[j][u]) {
                            list.add(keyword[u]);
                            list.add(keyword[j]);
                        }
                    }
                }
            }
        }
        Object[] middle = list.toArray();
        char[] ciphertext = new char[middle.length];
        for (int j = 0; j < middle.length; j++) {
            ciphertext[j] = (char) middle[j];
        }
        System.out.print("\t\tThe ciphertext is :");
        for (int i = 0; i < ciphertext.length; i++) {
            sb.append(ciphertext[i]);
        }
        return sb.toString();
    }

    /*解密函数*/
    public static String Decryption(String str, String n) {
        boolean begin = keyword_operate(n);
        if (begin) {
            System.out.println("\t\tThe keyword entered is not duplicated.");
        } else {
            System.out.println("\t\tThe keyword entered is duplicate and cannot continue.");
            System.exit(0);
        }

        String nn = n.toUpperCase();
        char[] keyword = nn.toCharArray();
        String strs = str.toUpperCase();
        char[] ciphertext = strs.toCharArray();
        StringBuilder sb = new StringBuilder();

        List<Character> list = new ArrayList<>();

        char[][] alphabet = new char[][]{{'A', 'B', 'C', 'D', 'E'},
                {'F', 'G', 'H', 'I', 'K'},
                {'L', 'M', 'N', 'O', 'P'},
                {'Q', 'R', 'S', 'T', 'U'},
                {'V', 'W', 'X', 'Y', 'Z'},};
        /*关键字没有重复之后进行解密操作*/
        if (begin) {
            for (int i = 0; i < ciphertext.length; i = i + 2) {
                for (int j = 0; j < keyword.length; j++) {
                    for (int o = 0; o < keyword.length; o++) {
                        if (ciphertext[i] == keyword[o] && ciphertext[i + 1] == keyword[j])
                            list.add(alphabet[j][o]);
                    }
                }
            }
        }
        Object[] middles = list.toArray();
        char[] plaintext = new char[middles.length];
        for (int j = 0; j < middles.length; j++) {
            plaintext[j] = (char) middles[j];
        }
        System.out.print("\t\tThe plaintext is :");
        for (int i = 0; i < plaintext.length; i++) {
            sb.append(plaintext[i]);
        }
        return sb.toString();
    }
}
