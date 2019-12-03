// import packages and classes

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;

public class MyWebServer {
    public static void main(String[] args) {
        new MyWebServer();
    }

    public MyWebServer() {
        try {
            BufferedReader bufferedReader0;
            PrintWriter printWriter0;
            BufferedOutputStream bufferedOutputStream0;
            Socket clientSocket0;
            ServerSocket serverSocket0 = new
                    ServerSocket(PORT_NUM);
            System.out.println("Listening ...");

            while(true) {
                // Listens for a connection to be made to this
                // socket and accepts it
                clientSocket0 = serverSocket0.accept();
                System.out.println(clientSocket0);
                bufferedReader0 = new
                        BufferedReader(new InputStreamReader(
                        // return an input stream for the
                        // socket
                        clientSocket0.getInputStream()));
                // for sending char output (header) to client
                printWriter0 = new PrintWriter(
                        // return an output stream for the
                        // socket
                        clientSocket0.getOutputStream());
                // for sending binary output (requested data)
                //	to client
                bufferedOutputStream0 =
                        new BufferedOutputStream(
                                // return an output stream for the
                                // socket
                                clientSocket0.getOutputStream());

                String rqstString = bufferedReader0.readLine();
                System.out.println(rqstString);

                StringTokenizer stringTokenizer0 = new
                        StringTokenizer(rqstString);
                String method = stringTokenizer0.nextToken();
                // Extract requested file name from the request
                String rqstdFileName = stringTokenizer0.nextToken();
                File file0 = new File(WEB_DIR, rqstdFileName);
                int fileSize = (int)file0.length();

                // read file data
                byte[] fileContent = new byte[fileSize];
                FileInputStream fileInputStream0 = new
                        FileInputStream(file0);
                try {
                    // read the request file into
                    // fileContent
					fileInputStream0.read(fileContent);
                } finally {
                    if(fileInputStream0 != null)
                        fileInputStream0.close();
                }

                // send HTTP Headers of the response
                // the first header is completed for you
                printWriter0.println("HTTP/1.1 200 OK");
				printWriter0.println("HTTP/1.1 501 Not Implemented");
				printWriter0.println("Date " + new Date());
				printWriter0.println("Content-type: text/html");
				printWriter0.println("Content - length: " + fileSize);
                // a blank line after headers
                printWriter0.println();
                printWriter0.flush();

                // send content of requested file
				bufferedOutputStream0.write(fileContent);
                bufferedOutputStream0.flush();
                break;
            }
            bufferedReader0.close();
            printWriter0.close();
            bufferedOutputStream0.close();
            // close the client socket
			clientSocket0.close();
            // close the server socket
			serverSocket0.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    protected final File WEB_DIR = new File("./web_root");
    protected final int PORT_NUM = 8081;

}
