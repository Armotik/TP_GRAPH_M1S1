// Main4A.java

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;


public class Main4A {

    public static void main(String args[]) {

        if (args.length == 0) {
            System.out.println("Usage: java Main4A <filename>");
            return;
        }

        try {
            File file = new File(args[0]);

            Scanner scL = new Scanner(file);
            GraphL4A graphL = new GraphL4A(scL);
            scL.close();

            graphL.DFSNum();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
