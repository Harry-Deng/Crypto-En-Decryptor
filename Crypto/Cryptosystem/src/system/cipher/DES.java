package system.cipher;

import system.utils.Tables;

public class DES {
    static Tables table = new Tables();

    /**
     * description: ���ַ���ת��Ϊ������
     *
     * @Param: text �ַ���
     * @return StringBuilder  �������ַ���
     */
    private static StringBuilder toBinary(String text) {
        StringBuilder binaryText = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            StringBuilder tempText = new StringBuilder(Integer.toBinaryString(text.charAt(i)));
            while (tempText.length() < 8) {//ÿ���ַ�ת��Ϊ8λ����λ��Ϊ0
                tempText.insert(0, "0");
            }
            binaryText.append(tempText);
        }
        return binaryText;

    }

    /**
     * description: ����Կ�Ĵ���
     *
     * @Param: key ��Կ
     * @return StringBuilder[]  ������16������Կ
     */
    public static StringBuilder[] getSubKey(String key) {
        StringBuilder[] subKey = new StringBuilder[16]; // �洢����Կ
        //���ؼ��ʵ���ASCII����ʽ��ʾ������ת���ɶ������ַ�����8��������λ����żУ��λ
        StringBuilder keyBinary = toBinary(key);
        //ͨ��PC-1�����û�����
        StringBuilder pc1_Key = new StringBuilder(); // �洢PC1�û������Կ
        for (int i = 0; i < 56; ++i) {
            pc1_Key.append(keyBinary.charAt(table.PC_1[i] - 1));
        }

        //���û������Կ�ֳ����������� L0 and R0
        StringBuilder L0 = new StringBuilder(); // �洢��Կ���
        StringBuilder R0 = new StringBuilder(); // �洢��Կ�ҿ�
        L0.append(pc1_Key.substring(0, 28));
        R0.append(pc1_Key.substring(28));
        for (int i = 0; i < 16; i++) {// ѭ������16��SubKey
            for (int j = 0; j < table.LS[i]; j++) {//����LFC�������ѭ�����Ʋ���
                char tempLS;
                tempLS = L0.charAt(0);
                L0.deleteCharAt(0);
                L0.append(tempLS);
                tempLS = R0.charAt(0);
                R0.deleteCharAt(0);
                R0.append(tempLS);
            }
            //�����������ֽ��кϲ�
            StringBuilder L0R0 = new StringBuilder(L0 + R0.toString());
            //����PC-2,��56λ��L0R0ѹ���û�λ48λ��K1
            StringBuilder tempL0R0 = new StringBuilder();
            for (int j = 0; j < 48; j++) {
                tempL0R0.append(L0R0.charAt(table.PC_2[j] - 1));
            }

            //��SubKey�洢��������
            subKey[i] = tempL0R0;
        }
        return subKey;
    }

    /**
     * description: ����
     *
     * @Param: plaintext  ����
     * @Param: key  ��Կ
     * @Param: type ���ܻ����
     * @return String �����ַ���
     */
    public static String encrypt(String plaintext, String key, String type) {
        StringBuilder ciphertext = new StringBuilder();

        //������ת��Ϊ�������ַ���
        StringBuilder plaintextBinary = toBinary(plaintext); // �洢���Ķ�����
        int pLength = plaintextBinary.toString().length();
        //countΪ���ķֿ������
        int count = pLength % 64 == 0 ? pLength / 64 : (pLength / 64) + 1;
        if (plaintextBinary.length() < count * 64) {
            for (int n = 0; n < pLength / 64; n++) {
                //ÿ64λ��ȡһ�Σ���Ϊһ���飬Ȼ��Կ���м��ܲ���
                String temp1P = plaintextBinary.substring(n * 64, (n + 1) * 64);
                //�����ܺ�����Ŀ���������������ַ���
                ciphertext.append(blockEncrypt(temp1P, key, type));
            }
            if (count > pLength / 64) {
                StringBuilder temp2P = new StringBuilder(plaintextBinary.substring((pLength / 64) * 64));
                while (temp2P.length() < 64) {
                    //�鳤�Ȳ���64λʱ����λ��0������ֱ���鳤=64
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
     * description: �����ĵ�ÿһ��64λ����м���
     *
     * @Param: plaintext  ����
     * @Param: key  ��Կ
     * @Param: type ���ܻ����
     * @return String �����ַ���
     */
    public static String blockEncrypt(String plaintext, String key, String type) {
        StringBuilder ciphertext = new StringBuilder();
        StringBuilder plaintextBinary = toBinary(plaintext); //������ת��Ϊ�������ַ���
        //IT�û�����
        StringBuilder IT_Plaintext = new StringBuilder(); // �洢�û��������
        for (int i = 0; i < 64; i++) {
            IT_Plaintext.append(plaintextBinary.charAt(table.IT[i] - 1));
        }
        //���û�������ķֳ���������
        StringBuilder LP = new StringBuilder(IT_Plaintext.substring(0, 32));
        StringBuilder RP = new StringBuilder(IT_Plaintext.substring(32));
        //�õ�SubKey(���ܲ���)
        StringBuilder[] subKey = getSubKey(key);
        if (type.equals("decrypt")) {
            StringBuilder[] tempSubKey = getSubKey(key);
            for (int i = 0; i < 16; i++) {
                subKey[i] = tempSubKey[15 - i];
            }
        }

        //����16�ֵ���
        for (int i = 0; i < 16; i++) {
            StringBuilder tempLP = new StringBuilder(LP); // �洢ԭ�������
            LP.replace(0, 32, RP.toString());//��߲���

            //EBox�н�����չ����
            StringBuilder tempRP = new StringBuilder(); // �洢��չ����ұ�
            for (int j = 0; j < 48; j++) {
                tempRP.append(RP.charAt(table.EBox[j] - 1));
            }

            //XOR����
            for (int j = 0; j < 48; j++) {
                if (tempRP.charAt(j) == subKey[i].charAt(j)) {
                    tempRP.replace(j, j + 1, "0");
                } else {
                    tempRP.replace(j, j + 1, "1");
                }
            }

            //SBox�н����û�����
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

            //��ѹ������RP,����PBox,ִ���û�����
            StringBuilder tempP = new StringBuilder(); // �洢�û����R
            for (int j = 0; j < 32; ++j) {
                tempP.append(RP.charAt(table.PBox[j] - 1));
            }
            RP.replace(0, 32, tempP.toString());

            //���û����RP���ʼ��LP����XOR����
            for (int j = 0; j < 32; j++) {
                if (RP.charAt(j) == tempLP.charAt(j)) {
                    RP.replace(j, j + 1, "0");
                } else {
                    RP.replace(j, j + 1, "1");
                }
            }

        }

        //�ϲ��������LP��RP
        StringBuilder LR = new StringBuilder(RP + LP.toString());

        //FI������ִ�������û�
        StringBuilder tempFT = new StringBuilder(); // �洢�û����LR
        for (int i = 0; i < 64; ++i) {
            tempFT.append(LR.charAt(table.FT[i] - 1));
        }

        // �Ѷ�����תΪ�ַ���
        for (int i = 0; i < 8; i++) {
            String tempText = tempFT.substring(i * 8, (i + 1) * 8);
            ciphertext.append((char) Integer.parseInt(tempText, 2));
        }

        return ciphertext.toString();
    }
}
