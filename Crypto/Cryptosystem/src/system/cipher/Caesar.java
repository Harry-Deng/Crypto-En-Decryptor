package system.cipher;
/*Caesar cipher*/

public class Caesar {
    public static String Encryption(String str, int k) {
        String string = "";
        for (int i = 0; i < str.length(); i++) {
            /*charAt()用于返回指定索引处的字符*/
            char j = str.charAt(i);
            if (j >= 'A' && j <= 'Z') {
                j += k % 26;
                if (j < 'A') j += 26;/*向左超界*/
                if (j > 'Z') j -= 26;/*向右超界*/
            } else if (j >= 'a' && j <= 'z') {
                j += k % 26;
                if (j < 'a') j += 26;/*向左超界*/
                if (j > 'z') j -= 26;/*向右超界*/
            }
            string += j;/*将加密后的字符合并成密文*/
        }
        return string;
    }

    public static String Decryption(String str, int n) {
        int k = Integer.parseInt("-" + n);
        String string = "";
        /*charAt()用于返回指定索引处的字符*/
        for (int i = 0; i < str.length(); i++) {
            /*charAt()用于返回指定索引处的字符*/
            char j = str.charAt(i);
            if (j >= 'A' && j <= 'Z') {
                j += k % 26;
                if (j < 'A') j += 26;/*向左超界*/
                if (j > 'Z') j -= 26;/*向右超界*/
            } else if (j >= 'a' && j <= 'z') {
                j += k % 26;
                if (j < 'a') j += 26;/*向左超界*/
                if (j > 'z') j -= 26;/*向右超界*/
            }
            string += j;/*将解密后的字符合并明文*/
        }
        return string;
    }


}

