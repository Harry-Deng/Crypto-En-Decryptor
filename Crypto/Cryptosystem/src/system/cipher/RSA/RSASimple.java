package system.cipher.RSA;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Random;

public class RSASimple {

    private static final int securityParam = 10;
    public static BigInteger p=safePGen();
    public static BigInteger q;
    public static BigInteger g=generatorGGen();

    //生成大素数
    public static BigInteger safePGen() {
        BigInteger one = new BigInteger("1",10);
        BigInteger two = new BigInteger("2",10);
        do {
            p = new BigInteger("0",10);
            q = new BigInteger(securityParam, 100, new Random());
            p = p.add(q.multiply(two).add(one));
        }while( p.isProbablePrime(100) == false );
        return p;
    }

    public static BigInteger getQ(){
        q = new BigInteger(securityParam, 100, new Random());
        return q;
    }

    public static BigInteger getD(){
        BigInteger m=p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger d1=BigInteger.TEN;
        while(ex_gcd(m,d1).intValue()!=1){
            d1=d1.add(BigInteger.ONE);
        }
        return d1;
    }

    //生成随机元
    public static BigInteger generatorGGen() {
        BigInteger one = new BigInteger("1",10);
        BigInteger two = new BigInteger("2",10);
        BigInteger result;
        do {
            g = new BigInteger(securityParam, new Random());
            g = g.mod(p.subtract(one));
            result = g.modPow(q, p);
        }while( g.compareTo(two) < 0 || result.compareTo(one) == 0 );
        return g;
    }
    public static String getCString(String p,String q,String d,String br) throws IOException {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        BigInteger p1 = new BigInteger(p);
        BigInteger q1 = new BigInteger(q);
        BigInteger n = p1.multiply(q1);
        BigInteger m = p1.subtract(BigInteger.ONE).multiply(q1.subtract(BigInteger.ONE));
        String binary = toBinary(br).toString();
        BigInteger textnumber[];

        int i = divid(n);//每个分组的二进制大小

        String binarysub[] = splitStringByLength(binary, i);

        int k = binarysub[binarysub.length - 1].length();//最后一个分组的二进制大小

        textnumber = getTextNumber(binarysub);
        BigInteger d1 = new BigInteger(d);

        //得到公钥一部分e
        BigInteger e = cal(d1, m);
        BigInteger cipher[];
        //加密
        cipher = getcipher(textnumber, e, n);
        BigInteger plain[];

        System.out.println();
        //求加密后的字符串
        int y = 0;
        for (y = 0; ; y++) {
            if (Math.pow(2, y) > cipher[cipher.length - 1].intValue())
                break;
        }
        String cipherBinary[] = reB(cipher, i, y);
        StringBuilder ciBinary = new StringBuilder();
        for (int f = 0; f < cipherBinary.length; f++) {
            for (int w = 0; w < cipherBinary[f].length(); w++) {
                ciBinary.append(cipherBinary[f].charAt(w));
            }
        }
        return ConvertStrTobin(ciBinary.toString());
    }

    public static String getPString(String p,String q,String d,String br){

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        BigInteger p1 = new BigInteger(p);
        BigInteger q1 = new BigInteger(q);
        BigInteger n = p1.multiply(q1);
        BigInteger m = p1.subtract(BigInteger.ONE).multiply(q1.subtract(BigInteger.ONE));
        String binary = toBinary(br).toString();
        BigInteger textnumber[];

        int i = divid(n);//每个分组的二进制大小

        String binarysub[] = splitStringByLength(binary, i);

        int k = binarysub[binarysub.length - 1].length();//最后一个分组的二进制大小

        textnumber = getTextNumber(binarysub);
        BigInteger d1 = new BigInteger(d);

        //得到公钥一部分e
        BigInteger e = cal(d1, m);
        BigInteger cipher[];
        //加密
        cipher = getcipher(textnumber, e, n);
        BigInteger plain[];
        //解密
        plain = getplain(cipher, d1, n);
        //求解密后的字符串
        String plainBinary[] = reB(plain, i, k);
        StringBuilder plBinary = new StringBuilder();
        for (int f = 0; f < plainBinary.length; f++) {
            for (int w = 0; w < plainBinary[f].length(); w++) {
                plBinary.append(plainBinary[f].charAt(w));
            }
        }
        return ConvertStrTobin(plBinary.toString());

    }



    private static BigInteger x;
    private static BigInteger y;
    //欧几里得扩展算法
    public static BigInteger ex_gcd(BigInteger m, BigInteger n){
        if(n.intValue()==0){
            x=new BigInteger("1");
            y=new BigInteger("0");
            return m;
        }
        BigInteger ans=ex_gcd(n,m.mod(n));
        BigInteger temp=x;
        x=y;
        y=temp.subtract(m.divide(n).multiply(y));
        return ans;
    }

    //求反模
    public static BigInteger cal(BigInteger m,BigInteger k){
        BigInteger gcd=ex_gcd(m,k);
        if(BigInteger.ONE.mod(gcd).intValue()!=0){
            return new BigInteger("-1");
        }
        x=x.multiply(BigInteger.ONE.divide(gcd));
        k=k.abs();
        BigInteger ans=x.mod(k);
        if(ans.compareTo(BigInteger.ZERO)<0)
            ans=ans.add(k);
        return ans;
    }


    //将二进制转化为字符串的函数
    private static String ConvertStrTobin(String value){
        StringBuilder ciphertext = new StringBuilder();
        if(value.length()%8!=0){
            //错误需要处理
            value=value+'0';
        }
        int j=value.length()/8;
        for (int i = 0; i < j; i++) {
            String tempText = "";
            for(int l=0;l<8;l++){
                tempText+=value.charAt(i*8+l);
            }
            BigInteger b=new BigInteger(tempText,2);
            ciphertext.append((char)b.intValue());
        }
        return ciphertext.toString();
    }

    //将字符串转换为二进制StringBuilder
    private static StringBuilder toBinary(String text) {
        StringBuilder binaryText = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            StringBuilder tempText = new StringBuilder(Integer.toBinaryString(text.charAt(i)));
            while (tempText.length() < 8) {
                tempText.insert(0, "0");
            }
            binaryText.append(tempText);
        }
        return binaryText;
    }

    //将二进制转化成10进制
    private static long getNumber(String binary){
        long result=0;
        if(binary==null)
            return 0;
        for(int i=binary.length()-1;i>=0;i--){
            result= (long) ((result+(int)binary.charAt(i))*Math.pow(2,i));
        }
        return result;
    }


    //计算每块大小分块
    public static int divid(BigInteger n){
        BigInteger two=new BigInteger("2");
        int i;
        for(i=0;;i++){
            if(two.pow(i).compareTo(n)>0){
                break;
            }
        }
        i--;
        return i;
    }

    /***
     * 将字符串按固定长度切割成字符子串
     * @param src 需要切割的字符串
     * @param length 字符子串的长度
     * @return 字符子串数组
     */
    public static String[] splitStringByLength(String src, int length) {
        //检查参数是否合法
        if (null == src||src.equals("")) {
            System.out.println("the string is null");
            return null;
        }

        if (length <= 0) {
            System.out.println("the length < 0");
            return null;
        }

        //System.out.println("now split \"" + src + "\" by length " + length);

        int n = (src.length() + length - 1) / length; //获取整个字符串可以被切割成字符子串的个数

        String[] split = new String[n];

        for (int i = 0; i < n; i++) {
            if (i < (n -1)) {
                split[i] = src.substring(i * length, (i + 1) * length);
            } else {
                split[i] = src.substring(i * length);
            }
        }

        return split;
    }

    //得到textnumber的数组值
    public static BigInteger[] getTextNumber(String binary[]){
        int n=binary.length;
        BigInteger[] bigInteger = new BigInteger[n];
        for(int i=0;i<binary.length;i++){
            bigInteger[i]=new BigInteger(binary[i],2);
        }
        return bigInteger;
    }

    //得到cipher的数组值
    public static BigInteger[] getcipher(BigInteger textnum[],BigInteger e,BigInteger n){
        int m= textnum.length;
        BigInteger[] bigInteger = new BigInteger[m];
        for(int i=0;i< textnum.length;i++){
            bigInteger[i]=(textnum[i].pow(e.intValue())).mod(n);
        }
        return bigInteger;
    }

    //得到plain的数组值
    public static BigInteger[] getplain(BigInteger cipher[],BigInteger d1,BigInteger n){
        int m= cipher.length;
        BigInteger[] bigInteger = new BigInteger[m];
        for(int i=0;i< cipher.length;i++){
            bigInteger[i] =(cipher[i].pow(d1.intValue())).mod(n);
        }
        return bigInteger;
    }

    //将BigInteger转化为二进制数组
    public static String[]  reB(BigInteger b[],int m,int y){
        int n=b.length;
        int p=(n-1)*m+y;
        if(p%8!=0){
            y=8-p%8;
        }
        String[] binary=new String[n];
        for(int i=0;i<n;i++){
            int k=b[i].intValue();
            StringBuilder bi=new StringBuilder();
            if(i!=n-1){
                for(int j=0;j<m;j++){
                    bi.append(binaryToDecimal(k,m).charAt(j));
                }
            }else if(i==n-1){
                for(int j=0;j<y;j++){
                    bi.append(binaryToDecimal(k,y).charAt(j));
                }
            }
            binary[i]=bi.toString();
        }
        return binary;
    }

    //将整数类型转化成指定大小的的二进制字符串
    public static String binaryToDecimal(int n,int i){
        char binary[]=new char[i];
        for(int j=0;j<i;j++){
            binary[j]='0';
        }
        for(int k=i-1;k>=0;k--){
            if(n%2==1){
                binary[k]='1';
            }else{
                binary[k]='0';
            }
            n=n/2;
        }
        StringBuilder str=new StringBuilder();
        for(int j=0;j<i;j++){
            str.append(binary[j]);
        }
        return str.toString();
    }
}

