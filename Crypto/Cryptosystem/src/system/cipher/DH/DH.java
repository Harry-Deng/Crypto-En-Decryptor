package system.cipher.DH;


import java.math.BigInteger;

import static system.cipher.DH.DHCoder2.get1;
import static system.cipher.DH.DHCoder2.getkey1;

public class DH {

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        String ini1 = "01010101010101";
        String ini2 = "10101001010101";
        int g = 5;
        int p = 97;
        int len = 20;
        BigInteger b1 = get1(ini1, g, p);
        BigInteger b2 = get1(ini2, g, p);

        String s1 = getkey1(b2, ini1, p, len);
        String s2 = getkey1(b1, ini2, p, len);
        System.out.println(s1);
        System.out.println(s2);
    }
}
