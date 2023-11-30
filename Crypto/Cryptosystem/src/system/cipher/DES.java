package system.cipher;

import system.utils.Tables;

public class DES {
    static Tables table = new Tables();

    /**
     * description: 将字符串转换为二进制
     *
     * @Param: text 字符串
     * @return StringBuilder  二进制字符串
     */
    private static StringBuilder toBinary(String text) {
        StringBuilder binaryText = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            StringBuilder tempText = new StringBuilder(Integer.toBinaryString(text.charAt(i)));
            while (tempText.length() < 8) {//每个字符转换为8位，首位都为0
                tempText.insert(0, "0");
            }
            binaryText.append(tempText);
        }
        return binaryText;

    }

    /**
     * description: 子密钥的处理
     *
     * @Param: key 密钥
     * @return StringBuilder[]  处理后的16轮子密钥
     */
    public static StringBuilder[] getSubKey(String key) {
        StringBuilder[] subKey = new StringBuilder[16]; // 存储子密钥
        //将关键词的以ASCII的形式表示，并且转换成二进制字符串，8的整数倍位是奇偶校验位
        StringBuilder keyBinary = toBinary(key);
        //通过PC-1进行置换操作
        StringBuilder pc1_Key = new StringBuilder(); // 存储PC1置换后的密钥
        for (int i = 0; i < 56; ++i) {
            pc1_Key.append(keyBinary.charAt(table.PC_1[i] - 1));
        }

        //将置换后的密钥分成左右两部分 L0 and R0
        StringBuilder L0 = new StringBuilder(); // 存储密钥左块
        StringBuilder R0 = new StringBuilder(); // 存储密钥右块
        L0.append(pc1_Key.substring(0, 28));
        R0.append(pc1_Key.substring(28));
        for (int i = 0; i < 16; i++) {// 循环生成16轮SubKey
            for (int j = 0; j < table.LS[i]; j++) {//按照LFC规则进行循环左移操作
                char tempLS;
                tempLS = L0.charAt(0);
                L0.deleteCharAt(0);
                L0.append(tempLS);
                tempLS = R0.charAt(0);
                R0.deleteCharAt(0);
                R0.append(tempLS);
            }
            //把左右两部分进行合并
            StringBuilder L0R0 = new StringBuilder(L0 + R0.toString());
            //根据PC-2,将56位的L0R0压缩置换位48位的K1
            StringBuilder tempL0R0 = new StringBuilder();
            for (int j = 0; j < 48; j++) {
                tempL0R0.append(L0R0.charAt(table.PC_2[j] - 1));
            }

            //将SubKey存储在数组中
            subKey[i] = tempL0R0;
        }
        return subKey;
    }

    /**
     * description: 加密
     *
     * @Param: plaintext  明文
     * @Param: key  密钥
     * @Param: type 加密或解密
     * @return String 密文字符串
     */
    public static String encrypt(String plaintext, String key, String type) {
        StringBuilder ciphertext = new StringBuilder();

        //将明文转换为二进制字符串
        StringBuilder plaintextBinary = toBinary(plaintext); // 存储明文二进制
        int pLength = plaintextBinary.toString().length();
        //count为明文分块的数量
        int count = pLength % 64 == 0 ? pLength / 64 : (pLength / 64) + 1;
        if (plaintextBinary.length() < count * 64) {
            for (int n = 0; n < pLength / 64; n++) {
                //每64位截取一次，作为一个块，然后对块进行加密操作
                String temp1P = plaintextBinary.substring(n * 64, (n + 1) * 64);
                //将加密后的密文块依次添加至密文字符串
                ciphertext.append(blockEncrypt(temp1P, key, type));
            }
            if (count > pLength / 64) {
                StringBuilder temp2P = new StringBuilder(plaintextBinary.substring((pLength / 64) * 64));
                while (temp2P.length() < 64) {
                    //块长度不足64位时，高位补0操作，直到块长=64
                    temp2P.insert(0, "0");
                }

                ciphertext.append(blockEncrypt(temp2P.toString(), key, type));
            }
        } else {
            ciphertext.append(blockEncrypt(plaintext, key, type));
        }

        return ciphertext.toString();
    }

    /**
     * description: 对明文的每一个64位块进行加密
     *
     * @Param: plaintext  明文
     * @Param: key  密钥
     * @Param: type 加密或解密
     * @return String 密文字符串
     */
    public static String blockEncrypt(String plaintext, String key, String type) {
        StringBuilder ciphertext = new StringBuilder();
        StringBuilder plaintextBinary = toBinary(plaintext); //将明文转换为二进制字符串
        //IT置换操作
        StringBuilder IT_Plaintext = new StringBuilder(); // 存储置换后的明文
        for (int i = 0; i < 64; i++) {
            IT_Plaintext.append(plaintextBinary.charAt(table.IT[i] - 1));
        }
        //将置换后的明文分成左右两块
        StringBuilder LP = new StringBuilder(IT_Plaintext.substring(0, 32));
        StringBuilder RP = new StringBuilder(IT_Plaintext.substring(32));
        //得到SubKey(解密操作)
        StringBuilder[] subKey = getSubKey(key);
        if (type.equals("decrypt")) {
            StringBuilder[] tempSubKey = getSubKey(key);
            for (int i = 0; i < 16; i++) {
                subKey[i] = tempSubKey[15 - i];
            }
        }

        //进行16轮迭代
        for (int i = 0; i < 16; i++) {
            StringBuilder tempLP = new StringBuilder(LP); // 存储原来的左边
            LP.replace(0, 32, RP.toString());//左边操作

            //EBox中进行扩展操作
            StringBuilder tempRP = new StringBuilder(); // 存储扩展后的右边
            for (int j = 0; j < 48; j++) {
                tempRP.append(RP.charAt(table.EBox[j] - 1));
            }

            //XOR操作
            for (int j = 0; j < 48; j++) {
                if (tempRP.charAt(j) == subKey[i].charAt(j)) {
                    tempRP.replace(j, j + 1, "0");
                } else {
                    tempRP.replace(j, j + 1, "1");
                }
            }

            //SBox中进行置换操作
            RP.setLength(0);
            for (int j = 0; j < 8; j++) {
                String number = tempRP.substring(j * 6, (j + 1) * 6);
                int row = Integer.parseInt(Character.toString(number.charAt(0)) + number.charAt(5), 2);
                int column = Integer.parseInt(number.substring(1, 5), 2);
                int n = table.SBox[j][row * 16 + column];
                StringBuilder numberString = new StringBuilder(Integer.toBinaryString(n));
                while (numberString.length() < 4) {
                    numberString.insert(0, 0);
                }
                RP.append(numberString);
            }

            //将压缩过的RP,进入PBox,执行置换操作
            StringBuilder tempP = new StringBuilder(); // 存储置换后的R
            for (int j = 0; j < 32; ++j) {
                tempP.append(RP.charAt(table.PBox[j] - 1));
            }
            RP.replace(0, 32, tempP.toString());

            //将置换后的RP与最开始的LP进行XOR操作
            for (int j = 0; j < 32; j++) {
                if (RP.charAt(j) == tempLP.charAt(j)) {
                    RP.replace(j, j + 1, "0");
                } else {
                    RP.replace(j, j + 1, "1");
                }
            }

        }

        //合并迭代后的LP和RP
        StringBuilder LR = new StringBuilder(RP + LP.toString());

        //FI操作，执行最终置换
        StringBuilder tempFT = new StringBuilder(); // 存储置换后的LR
        for (int i = 0; i < 64; ++i) {
            tempFT.append(LR.charAt(table.FT[i] - 1));
        }

        // 把二进制转为字符串
        for (int i = 0; i < 8; i++) {
            String tempText = tempFT.substring(i * 8, (i + 1) * 8);
            ciphertext.append((char) Integer.parseInt(tempText, 2));
        }

        return ciphertext.toString();
    }
}
