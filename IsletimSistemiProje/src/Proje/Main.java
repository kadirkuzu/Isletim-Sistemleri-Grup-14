package Proje;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Process;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException{     
        Uygulama uygulama=new Uygulama(); 
        var process = Runtime.getRuntime().exec("reg add HKEY_CURRENT_USER\\Console /v VirtualTerminalLevel /t REG_DWORD /d 0x00000001 /f"); //Konsolda renkli çıktı alabilmek için process tanımladık.
        uygulama.Program();   
    }
}