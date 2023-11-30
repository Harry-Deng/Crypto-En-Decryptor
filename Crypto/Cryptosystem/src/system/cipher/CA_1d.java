package system.cipher;

import java.util.Arrays;

/*CA��1d��*/
public class CA_1d {
    public static int[] keyStream;

    /*��String�ַ���ת��Ϊint����*/
    public static int[] stringToInts(String s) {
        int[] n = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            n[i] = Integer.parseInt(s.substring(i, i + 1));
        }
        return n;
    }

    /*��int����ת��Ϊһ��String�ַ���*/
    public static StringBuilder toStringMethod(int[] arr) {
        // �Զ���һ���ַ���������
        StringBuilder sb = new StringBuilder();

        //����int���飬����int�����е�Ԫ��ת�����ַ������浽�ַ���������ȥ
        for (int i = 0; i < arr.length; i++) {
            if (i != arr.length - 1)
                sb.append(arr[i]);
            else
                sb.append(arr[i]);
        }
        return sb;
    }

    /*CA(1d)���ܺ���*/
    public static String encryption(int s1, String CA_value, int rule, int source, String plaintext) {
        int[] CA = stringToInts(CA_value);//һάCA����
        int[] CA1 = Arrays.copyOf(CA, CA.length);//��������CA
        int[] neighbour = new int[3];//��¼�������������
        int[] plaintexts = stringToInts(plaintext);
        ;//��������
        keyStream = new int[plaintexts.length];//��Կ������
        int[] ciphertext = new int[plaintexts.length];//��������
        String[] match = {"000", "001", "010", "011", "100", "101", "110", "111"};//ƥ������
        String[] t = new String[CA.length];
        final int k = source;
        keyStream[0] = CA[k];
        StringBuilder sb = new StringBuilder();


        int i, j, g;
        String rulee = Integer.toBinaryString(rule);
        int[] ruleee = stringToInts(rulee);
        int[] rule_arr = new int[8];//��λ�����ƹ����������
        for (g = rule_arr.length - 1; g > rule_arr.length - ruleee.length - 1; g--) {
            rule_arr[g] = ruleee[g - (rule_arr.length - ruleee.length)];
        }

        /*ѭ��l2��(ÿ����Ԫ����������Ĺ���)����¼��Կ��*/
        for (i = 1; i < keyStream.length; i++) {
            for (j = 0; j < CA.length; j++) {
                if (j == 0) {
                    t[j] = "";
                    t[j] += CA[CA.length - 1];
                    t[j] += CA[j];
                    t[j] += CA[j + 1];
                } else if (j == CA.length - 1) {
                    t[j] = "";
                    t[j] += CA[j - 1];
                    t[j] += CA[j];
                    t[j] += CA[0];
                } else {
                    t[j] = "";
                    t[j] += CA[j - 1];
                    t[j] += CA[j];
                    t[j] += CA[j + 1];
                }
                for (int x = 0; x < match.length; x++) {
                    if (t[j].equals(match[x])) {
                        CA1[j] = rule_arr[x];
                        break;
                    }
                }
            }
            keyStream[i] = CA1[k];

            for (int x = 0; x < CA.length; x++) {
                CA[x] = CA1[x];
            }
        }
        System.out.print("\t\tThe generated key stream is :");
        System.out.print(getKeyStream(keyStream));
        System.out.print("\n\t\tThe ciphertext is :");

        for (int nn = 0; nn < ciphertext.length; nn++) {
            ciphertext[nn] = plaintexts[nn] ^ keyStream[nn];
            sb.append(ciphertext[nn]);
        }
        return sb.toString();
    }

    public static String getKeyStream(int[] keyStream){
        StringBuilder sb = new StringBuilder();
        for (int n = 0; n < keyStream.length; n++) {
            sb.append(keyStream[n]);
        }
        return sb.toString();
    }

    /*CA(1d)���ܺ���*/
    public static String decryption(String ciphertext, String keystream) {
        int[] ciphertexts = stringToInts(ciphertext);//��������
        int[] keystreams = stringToInts(keystream);//��Կ������
        int[] plaintexts = new int[ciphertexts.length];//��������
        StringBuilder sb = new StringBuilder();

        System.out.print("\n\t\tThe plaintext is :");
        for (int nn = 0; nn < plaintexts.length; nn++) {
            plaintexts[nn] = ciphertexts[nn] ^ keystreams[nn];
            System.out.print(plaintexts[nn]);
            sb.append(plaintexts[nn]);
        }
        return sb.toString();
    }


}
