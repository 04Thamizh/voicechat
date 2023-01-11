package voicechat;
import java.io.IOException;
import java.net.*;
import javax.sound.sampled.*;
public class VoiceChatClient  
{
    String address;
    SourceDataLine sourceLine ;
    TargetDataLine targetLine;
    public void clientExecute()
    {
        try
        {
            //AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
            AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
            //Speaker
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            sourceLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceLine.open();
            //Microphone
            info = new DataLine.Info(TargetDataLine.class, format);
            targetLine = (TargetDataLine) AudioSystem.getLine(info);
            targetLine.open();
            DatagramSocket ds=new DatagramSocket();
            InetAddress ip=InetAddress.getByName(address);
            byte[] send=new byte[targetLine.getBufferSize() / 5];
            byte[] recv=new byte[sourceLine.getBufferSize() / 5];
            DatagramPacket dp=new DatagramPacket(recv,recv.length);
            DatagramPacket dp1;
            
            int readBytes;
            targetLine.start();
            sourceLine.start();
            //System.out.println("Start Your Converstation:");
            while(true)
            {   
                readBytes = targetLine.read(send, 0, send.length);
                dp1=new DatagramPacket(send,readBytes,ip,6038);
                ds.send(dp1);
                ds.receive(dp);
                sourceLine.write(dp.getData(), 0, dp.getLength());
            }
        }
        catch(IOException | LineUnavailableException e)
        {
            System.out.println(e);
        }
    }
}

