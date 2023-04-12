package org.chatApplication;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import  java.net.*;
public class Server {

     ServerSocket server;
     Socket socket;
     BufferedReader br;
     PrintWriter out;
    public Server()
    {
        try {
            server = new ServerSocket(7777);
            System.out.println("server is ready to accept connection");
            System.out.println("waiting....");
            socket = server.accept(); // accept client connection
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

            }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void startReading()
    {
      //thread     this thread is used to read the data
       Runnable r1 = ()->{
           System.out.println("reader started");
           while(true)
           {
               String msg = null;
               try {
                   msg = br.readLine();
               } catch (IOException e)
               {
                   e.printStackTrace();
               }
               if(msg.equals("exit"))
               {
                   System.out.println("client terminated the chat");
                   break;
               }
               System.out.println("client\t"+msg);
           }
       };
       new Thread(r1).start();
    }

    public void startWriting()
    {
            // thread       this thread take the data from the user and send to the client
        Runnable r2 = ()->{
            System.out.println("writer started");
                   while(true)
                   {
                       try {

                           BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                           String content = br1.readLine();
                           out.println(content);
                           out.flush();

                       }catch (Exception  e)
                       {
                           e.printStackTrace();
                       }
                   }
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("this is server side");
        new Server();
    }
}
