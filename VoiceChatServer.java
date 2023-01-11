package voicechat;
import java.io.IOException;
import java.net.*;
import javax.sound.sampled.*;
public class VoiceChatServer 
{
    public void serverExecute()
    {
        try
        {
            AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false);
            //AudioFormat format = new AudioFormat(8000.0f, 16, 1, true, true);
            //Speaker
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine sourceLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceLine.open();
            //Microphone
            info = new DataLine.Info(TargetDataLine.class, format);
            TargetDataLine targetLine = (TargetDataLine) AudioSystem.getLine(info);
            targetLine.open();
            byte[] recv=new byte[targetLine.getBufferSize() / 5];
            byte[] send=new byte[sourceLine.getBufferSize() / 5];
            DatagramSocket ds=new DatagramSocket(6038);
            DatagramPacket dp=new DatagramPacket(recv,recv.length);
            DatagramPacket dp1;
            int readBytes;
            targetLine.start();
            sourceLine.start();
            while(true)
            {
                ds.receive(dp);
                sourceLine.write(dp.getData(),0,dp.getLength());
                readBytes = targetLine.read(send, 0, send.length);
                dp1=new DatagramPacket(send,readBytes,dp.getAddress(),dp.getPort());
                ds.send(dp1);
            }
        }
        catch(IOException | LineUnavailableException e)
        {
            System.out.println(e);
        }
    }
}
