package system.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Tool {
    Scanner scanner = new Scanner(System.in);

    public int getChoice() {
        int input;

        do {
            System.out.println("==================================================");
            System.out.print("1-->Encryption\n" +
                             "2-->Decryption\n" +
                             "3-->Encrypt the file\n" +
                             "4-->Decrypt the file\n" +
                             "0-->Exit\n" +
                             "Please input your choice>> ");
            String inputStr = scanner.nextLine();
            //判断输入的是否为有效数字
            if (Pattern.compile("[0-4]*").matcher(inputStr).matches()) {
                //如果是，则强制类型转换为int型
                input = Integer.parseInt(inputStr);
                break;
            } else {
                System.out.println("Invalid input: " + inputStr +"\n");
            }
        } while (true);
        return input;
    }

    public String Plaintext_Input(){
        System.out.print("Please input the plaintext>> ");
        // in.nextLine();不能放在in.nextInt();代码段后面,否则in.nextLine();会读入"\n"字符，但"\n"并不会成为返回的字符。
        String plaintext = scanner.nextLine();
        return plaintext;
    }

    public String Key_Input(){
        System.out.print("Please input a key>> ");
        String key = scanner.nextLine();
        return key;
    }

    public String Ciphertext_Input(){
        System.out.print("Please input the ciphertext>> ");
        String ciphertext = scanner.nextLine();
        return ciphertext;
    }

    public String text_Input_File() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("file.txt"));
        String text = bufferedReader.readLine();
        bufferedReader.close();
        System.out.println("\nThe text in the file>>  " + text);
        return text;
    }

    public void Encryption_output(String plaintext,String key){
        System.out.println("----------ENCRYPTION RESULT----------");
        System.out.println(" Plaintext: " + plaintext);
        System.out.println("       Key: " + key);
        System.out.print("Ciphertext: ");
    }

    public void Decryption_output(String ciphertext,String key){
        System.out.println("----------DECRYPTION RESULT----------");
        System.out.println(" Ciphertext: " + ciphertext);
        System.out.println("        Key: " + key);
        System.out.print(" Plaintext : ");
    }

}
