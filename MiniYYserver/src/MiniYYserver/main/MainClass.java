package MiniYYserver.main;



import MiniYYserver.thread.MainServerSocket;

public class MainClass
{
    public static void main(String[] args)
    {
        // ��������������
        MainServerSocket.getMainSocket().beginListen();
    }
}
