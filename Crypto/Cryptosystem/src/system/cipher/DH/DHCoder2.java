package system.cipher.DH;

import java.math.BigInteger;

public class DHCoder2 {

    //转化甲方选择的字符串
    public static BigInteger get1(String ini,int g,int p){
        int len=ini.length();
        BigInteger b1=new BigInteger(ini,2);
        BigInteger g1=new BigInteger(String.valueOf(g));
        BigInteger p1=new BigInteger(String.valueOf(p));
        BigInteger bigInteger=g1.pow(b1.intValue()).mod(p1);
        return bigInteger;
    }


    //转化乙方选择的字符串
    public static BigInteger get2(String ini,int g,int p){
        int len=ini.length();
        BigInteger b1=new BigInteger(ini,2);
        BigInteger g1=new BigInteger(String.valueOf(g));
        BigInteger p1=new BigInteger(String.valueOf(p));
        BigInteger bigInteger=g1.pow(b1.intValue()).mod(p1);
        return bigInteger;
    }

    //甲方共享密钥实现
    public static String getkey1(BigInteger b2,String ini,int p,int len){
        BigInteger b1=new BigInteger(ini,2);
        BigInteger p1=new BigInteger(String.valueOf(p));
        BigInteger key=b2.pow(b1.intValue()).mod(p1);
        int value=key.intValue();
        String k="";
        for(int i=len-1;i>=0;i--){
            int h=value%2;
            value=value/2;
            if(h==1){
                k="1"+k;
            }else if(h==0){
                k="0"+k;
            }
        }
        return k;
    }

    //乙方共享密钥实现
    public static String getkey2(BigInteger b1,String ini,int p,int len){
        BigInteger b2=new BigInteger(ini,2);
        BigInteger p1=new BigInteger(String.valueOf(p));
        BigInteger key=b1.pow(b2.intValue()).mod(p1);
        int value=key.intValue();
        String k="";
        for(int i=len-1;i>=0;i--){
            int h=value%2;
            value=value/2;
            if(h==1){
                k="1"+k;
            }else if(h==0){
                k="0"+k;
            }
        }
        return k;
    }
}
