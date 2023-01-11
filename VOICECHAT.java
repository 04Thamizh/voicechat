package voicechat;
import javax.swing.*;
import java.awt.event.*;
public class VOICECHAT extends JFrame implements ActionListener,Runnable
{
    JPanel p;
    JButton b,b1,b2,b3,b4;
    JLabel enter;
    JTextField ip_text;
    String address;
    VoiceChatClient voiceclient;
    VoiceChatServer voiceserver;
    Thread t;
    public VOICECHAT()
    {
        super("Voice Chat Application");
        start();
    }
    public void start()
    {
        
        p=new JPanel();
        b=new JButton("Click to Make Call");
        b.setBounds(180,300,250,40);
        b.addActionListener(this);
        b3=new JButton("Click to Go Online");
        b3.setBounds(180,350,250,40);
        b3.addActionListener(this);
        p.add(b);
        p.add(b3);
        p.setSize(640,720);
        p.setVisible(true);
        p.setLayout(null);
        add(p);
        setSize(640,720);
        setVisible(true);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }        
    public static void main(String[] args)
    {
        new VOICECHAT();     
    } 

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource()==b)
        { 
            p.removeAll();
            p.revalidate();
            p.repaint();
            enter=new JLabel("Enter the Receiver IP Address",SwingConstants.CENTER);
            enter.setBounds(180,200,250,40);
            ip_text=new JTextField();
            ip_text.setBounds(180,250,250,40);
            b1=new JButton("Start Call");
            b1.setBounds(180,300,250,40);
            b1.addActionListener(this);
            p.add(b1);
            p.add(ip_text);
            p.add(enter);
            p.setSize(640,720);
            p.setVisible(true);
            p.setLayout(null);
            add(p);
        }
        if(e.getSource()==b1)
        {
            address=ip_text.getText();
            if(address.equals(""))
            {
                JOptionPane.showMessageDialog(null,"Please Enter the IP Address");
            }
            else
            {
                voiceclient=new VoiceChatClient();
                p.removeAll();
                p.revalidate();
                p.repaint();
                enter.setBounds(180,200,250,40);
                enter.setText("Call Connected");
                b2=new JButton("End Call");
                b2.setBounds(180,250,250,40);
                b2.addActionListener(this);
                p.add(enter);
                p.add(b2); 
                p.setSize(640,720);
                p.setVisible(true);
                p.setLayout(null);
                add(p);
                t=new Thread(this);
                t.setName("client");
                t.start();
            }    
        }
        if(e.getSource()==b2)
        {
           voiceclient=null;
           t.stop();
           dispatchEvent(new WindowEvent(this,WindowEvent.WINDOW_CLOSING)); 
        }
        if(e.getSource()==b3)
        {
            p.removeAll();
            p.revalidate();
            p.repaint();
            enter=new JLabel("You are now Online",SwingConstants.CENTER);
            enter.setBounds(180,300,250,40);
            b4=new JButton("Go Offline");
            b4.setBounds(180,350,250,40);
            b4.addActionListener(this);
            p.add(enter);
            p.add(b4); 
            p.setSize(640,720);
            p.setVisible(true);
            p.setLayout(null);
            add(p);
            t=new Thread(this);
            t.setName("server");
            t.start();   
        }
        if(e.getSource()==b4)
        {
           voiceserver=null;
           t.stop();
           dispatchEvent(new WindowEvent(this,WindowEvent.WINDOW_CLOSING)); 
        }
    }

    @Override
    public void run() {
           //System.out.println("run "+t.getName());
        if(t.getName().equals("client"))
        {
            voiceclient.address=address;
            voiceclient.clientExecute();
        }
        else if(t.getName().equals("server"))
        {
            voiceserver=new VoiceChatServer();
            voiceserver.serverExecute();
        }
                
    }
}
