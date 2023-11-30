package system.cipher;


import java.util.ArrayList;
import java.util.List;

public class Playfair {

    /**
     * 处理明文，重复字母中间插q，长度非偶数添加q
     * @param plaintext 密文
     * @return 处理后的明文
     */
    public static String dealP(String plaintext){

        plaintext=plaintext.toLowerCase();// 将明文转换成小写
        plaintext=plaintext.replaceAll("[^a-z]", "");//去除所有非字母的字符
        StringBuilder sb=new StringBuilder(plaintext);

        for(int i=1;i<sb.length();i=i+2){
            if(sb.charAt(i)==sb.charAt(i-1)){//一对明文字母如果是重复的则在这对明文字母之间插入一个填充字符
                sb.insert(i,'q');
            }
        }
        if(sb.length()%2!=0){//如果经过处理后的明文长度非偶数，则在后面加上字母q
            sb.append('q');
        }

        String p=sb.toString();
        System.out.println("处理后的明文："+p);
        return p;
    }

    /**
     * 处理密钥，将j转换成i，除去重复字母
     * @param key 密钥
     * @return 修改后的密钥List<Character>
     */
    public static List<Character> dealK(String key){
        key=key.toLowerCase();// 将密钥转换成大写
        key=key.replaceAll("[^a-z]", "");//去除所有非字母的字符
        key=key.replaceAll("j","i");//将J转换成I
        List<Character> list=new ArrayList<Character>();
        char[] ch=key.toCharArray();
        int len=ch.length;
        for(int i=0;i<len;i++){
            //除去重复出现的字母
            if(!list.contains(ch[i])){
                list.add(ch[i]);
            }
        }
        System.out.println("处理后的密钥："+list);
        return list;
    }

    /**
     * 将密玥的字母逐个加入5×5的矩阵内，剩下的空间将未加入的英文字母
     * 依a-z的顺序加入。（将i和j视作同一字）
     * @param key 密钥
     * @return 5*5矩阵
     */
    public static char[][] matrix(String key){
        List<Character> realKey=dealK(key);
        List<Character> list=new ArrayList<>(realKey);

        //添加按字母表顺序排序的剩余的字母
        for(char ch='a';ch<='z';ch++){
            if(ch!='j'&&!list.contains(ch)){
                list.add(ch);
            }
        }
        char[][] matrix=new char[5][5];
        int count=0;
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                matrix[i][j]=list.get(count++);
            }
        }

        System.out.println("根据密钥'"+key+"'构建的矩阵：");
        showMatrix(matrix);
        return matrix;
    }

    /**
     * 打印矩阵
     * @param matrix 矩阵
     */
    public static void showMatrix(char[][] matrix){
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[i].length;j++){
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }
    }

    /**
     * 根据playfair算法对明文对进行加密或者对密文进行解密
     * @param matrix 矩阵
     * @param ch1 字母1
     * @param ch2 字母2
     * @return 密文对或者明文对
     */
    public static String EncryptORDecrypt(char[][] matrix,char ch1,char ch2,int choice){
        //获取明文或密文对在矩阵中的位置
        int r1=0,c1=0,r2=0,c2=0;
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[i].length;j++){
                if(ch1==matrix[i][j]){
                    r1=i;
                    c1=j;
                }
                if(ch2==matrix[i][j]){
                    r2=i;
                    c2=j;
                }
            }
        }
        StringBuilder sb=new StringBuilder();
        //进行规制匹配，得到密文对或明文对

        if(r1==r2){
            if(choice == 1){
                //明文字母对的两个字母在同一行，则截取右边的字母
                sb.append(matrix[r1][(c1+1)%5]);
                sb.append(matrix[r2][(c2+1)%5]);
            }else{
                //密文字母对的两个字母在同一行，则截取左边的字母
                sb.append(matrix[r1][(c1-1+5)%5]);
                sb.append(matrix[r1][(c2-1+5)%5]);
            }
        }else if(c1==c2){
            if(choice == 1){
                //明文字母对的两个字母在同一列，则截取下方的字母
                sb.append(matrix[(r1+1)%5][c1]);
                sb.append(matrix[(r2+1)%5][c2]);
            }else {
                //密文字母对的两个字母在同一列，则截取上方的字母
                sb.append(matrix[(r1-1+5)%5][c1]);
                sb.append(matrix[(r2-1+5)%5][c1]);
            }
        } else{

            //明文字母所形成的矩形对角线上的两个字母，任意选择两种方向
            //明文对中的每一个字母将由与其同行，且与另一个字母同列的字母代替
            sb.append(matrix[r1][c2]);
            sb.append(matrix[r2][c1]);

        }
        sb.append(' ');
        return sb.toString();
    }

    /**
     * 对明文进行加密
     * @param plaintext 明文
     * @param key 密钥
     * @return 密文
     */
    public static String encrypt(String plaintext,String key){

        char[] ch=dealP(plaintext).toCharArray();
        char[][] matrix=matrix(key);
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<ch.length;i=i+2){
            sb.append(EncryptORDecrypt(matrix,ch[i],ch[i+1],1));
        }
        return sb.toString().replaceAll("[^a-z]", "");
    }

    /**
     * 对密文进行解密
     * @param ciphertext 密文
     * @param key 密钥
     * @return 明文
     */
    public static String decrypt(String ciphertext,String key){
        ciphertext=ciphertext.toLowerCase();
        ciphertext=ciphertext.replaceAll("[^a-z]", "");//去除所有非字母的字符
        char[] ch=ciphertext.toCharArray();
        char[][] matrix=matrix(key);
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<ch.length;i=i+2){
            sb.append(EncryptORDecrypt(matrix,ch[i],ch[i+1],0));
        }
        return sb.toString().replaceAll("[^a-z]", "");
    }
}
