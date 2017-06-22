package MiniYYserver.main;



import MiniYYserver.thread.MainServerSocket;

public class MainClass
{
    public static void main(String[] args)
    {
        // ¿ªÆô·şÎñÆ÷¼àÌı
        MainServerSocket.getMainSocket().beginListen();
    }
}
