package MiniYYclient.UI;

import java.applet.Applet;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import MiniYYclient.common.CommonData;

public class Sounds extends Applet
{
    private static final long   serialVersionUID = -5810929408459036098L;

    private static final Sounds sounds           = new Sounds();         // ��̬ʵ��

    private static AudioStream  msgAudioStream;                          // ����Ϣ����

    private Sounds()
    {
        // ��ʼ�� ��Ϣ����
        initMsgAudio();
    }

    /**
     * ���ر���ʵ��
     * 
     * @return
     */
    public static Sounds getSounds()
    {
        return Sounds.sounds;
    }

    /**
     * ��ʼ����Ϣ����
     */
    private void initMsgAudio()
    {
        try
        {
            String msgAudioPath = URLDecoder.decode(CommonData.class.getResource("/").toString()
                    + "sound/Message.wav", "utf-8");

            // Ҫ��ȥgetResource ���ص�·���е� "file:/" ���ܽ��ж�ȡ
            msgAudioPath = msgAudioPath.replace("file:/", "");
            msgAudioStream = new AudioStream(new FileInputStream(msgAudioPath));

        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * ������Ϣ����
     */
    public void startMessageAudio()
    {
        startAudio(msgAudioStream); // ��������
    }

    /**
     * ֹͣ��Ϣ����
     */
    public void stopMessageAudio()
    {
        stopAudio(msgAudioStream); // ֹͣ����
    }

    /**
     * ��������
     * 
     * @param audioStream_
     */
    private void startAudio(AudioStream audioStream_)
    {
        AudioPlayer.player.start(audioStream_);
    }

    /**
     * ֹͣ����
     * 
     * @param audioStream_
     */
    private void stopAudio(AudioStream audioStream_)
    {
        AudioPlayer.player.stop(audioStream_);
    }

}
