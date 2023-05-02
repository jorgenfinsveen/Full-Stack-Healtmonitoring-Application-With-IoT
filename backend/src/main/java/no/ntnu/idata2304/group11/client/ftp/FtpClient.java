package no.ntnu.idata2304.group11.client.ftp;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import java.io.*;
import java.net.SocketException;
import java.util.Arrays;
import java.util.Collection;

/**
 * This class represent the Client FT Protocol.
 * It is used to transfer data form a client to an FTP server.
 * @author Ole Kristian Dvergsdal
 * @version 1.0
 */
public class FtpClient {

    //The ftp client
    private FTPClient ftp = new FTPClient();

    //Socket info
    private String serverAddress = "ftpcluster.loopia.se";

    //Logg on info
    private String user = "finsveen.dev";
    private String password = "NtnuGruppe11";






    /**
     * Constructor of a ftp client.
     * @throws IOException error if you can't connect to the server.
     */
    public FtpClient() throws IOException {

        //Setts up ftp for use.
        this.ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));

        //Sets up different settings for the ftp client
        this.ftp.setConnectTimeout(10);

        //Try connect to server
        chechConnectionToFTPServer();

        //login wiht username and password
        this.ftp.login(this.user, this.password);
    }


    private void chechConnectionToFTPServer() {
        try {
            int reply;

            //Connect attempt to the FTP server
            this.ftp.connect(this.serverAddress);
            System.out.println("Connected tp " + this.serverAddress + ".");
            System.out.println(this.ftp.getReplyString());

            //Check if connection attempt reply code is successful.
            //If it is note client disconnects.
            reply = this.ftp.getReplyCode();
            if(!FTPReply.isPositiveCompletion(reply)) {
                this.ftp.disconnect();
                System.err.println("FTP server refused connection.");
                System.exit(1);
            }
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            if((this.ftp.isConnected())) {
                try {
                    this.ftp.disconnect();
                } catch (IOException f) {
                    f.printStackTrace();
                }
            }
            System.err.println("Could not connect to server.");
            e.printStackTrace();
            System.exit(1);
        }
    }


    //FUNCTIONS

    /**
     * Disconnect from the ftp.
     * @throws IOException error
     */
    public void disconnect() throws IOException {
        this.ftp.disconnect();
        if(this.ftp.isConnected()) {
            System.out.println("Did not disconnect.");
        }
        else {
            System.out.println("Disconnected from the FTP server " + this.serverAddress + ".");
        }
    }


    //TODO check if i want to trasnform it to Strings.
    /**
     * List the files.
     * @param path //TODO this
     * @return // TODO this
     * @throws IOException //TODO this
     */
    public Collection<String> listFlies(String path) throws IOException {
        FTPFile[] files = this.ftp.listFiles(path);
        return Arrays.stream(files).map(FTPFile::getName).toList();
    }

    /**
     * Uplodes file to the FTP server
     * @param file the file you want to upload
     * @param path of the file
     * @throws IOException eror
     */
    public void upload(File file, String path) throws IOException {
        this.ftp.storeFile(path, new FileInputStream(file));
    }

    /**
     * download a file from the FTP server
     * @param source of the file.
     * @param destination where you want to download the file to
     * @throws IOException
     */
    public void download(String source, String destination)
        throws IOException {
        FileOutputStream out = new FileOutputStream(destination);
        this.ftp.retrieveFile(source,out);
    }
}
