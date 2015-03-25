import java.io.File;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Remote {
    private String name;
    private String userName;
    private String server;
    private String path;

    public Remote(String n, String u, String s, String p) {
        name = n;
        userName = u;
        server = s;
        path = p;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getServer() {
        return server;
    }

    public static void removeRemote(String name) {
        File file = new File("./.gitlet/remotes/" + name);
        if (file.exists()) {
            file.delete();
        } else {
            System.out.println("There is no remote named " + name);
        }
    }

    public static boolean remoteExists(String name) {
        File file = new File("./.gitlet/remotes/" + name);
        return file.exists();
    }

    public static Remote loadRemote(String name) {
        String n = null;
        String u = null;
        String s = null;
        String p = null;
        try {
            File file = new File("./.gitlet/remotes/" + name);
            Scanner in = new Scanner(file);
            n = in.next();
            u = in.next();
            s = in.next();
            p = in.next();
        } catch (IOException e) {
            System.out.println("Error reading remote file: " + name);
        }
        Remote result = new Remote(n, u, s, p);
        return result;
    }

    public void cache() {
        try {
            File file = new File("./.gitlet/remotes/" + name);
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            out.print(name + "\n" + userName + "\n" + server + "\n" + path);
            out.close();
        } catch (IOException e) {
            System.out.println("Error writing remote file: " + name);
        }
    }
}
