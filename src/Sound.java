import java.io.FileInputStream;

import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

public class Sound
{
	public void Sound()
	{}
	public void playSound()
	{
		//create audio stream
				AudioStream as = null;
			//	AudioData data = null;
			//	AudioDataStream ads = null;
			//	ContinuousAudioDataStream cas = null;
				try
				{
				as = new AudioStream (new FileInputStream("Sound/Trance.wav"));
			//	data = as.getData();
			//	ads = new ContinuousAudioDataStream (data);
			//	cas = new ContinuousAudioDataStream (data);
				}
				catch(Exception e)
				{
					System.out.println("Fail " + e);
				}
				AudioPlayer.player.start(as);
				// file too big for a continuous stream, so had to implement own loop
				//AudioPlayer.player.start(cas);
	}
	public void playPistol()
	{
		//create audio stream
				AudioStream as = null;
				AudioData data = null;
				AudioDataStream ads = null;
				try
				{
				as = new AudioStream (new FileInputStream("Sound/Pistol.wav"));
				data = as.getData();
				ads = new AudioDataStream (data);
				}
				catch(Exception e)
				{
					System.out.println("Fail " + e);
				}
				AudioPlayer.player.start(ads);
	}
	public void playRocket()
	{
		//create audio stream
		AudioStream as = null;
		AudioData data = null;
		AudioDataStream ads = null;
		try
		{
		as = new AudioStream (new FileInputStream("Sound/Rocket.wav"));
		data = as.getData();
		ads = new AudioDataStream (data);
		}
		catch(Exception e)
		{
			System.out.println("Fail " + e);
		}
		AudioPlayer.player.start(ads);
	}
	public void playFlame()
	{
		//create audio stream
		AudioStream as = null;
		AudioData data = null;
		AudioDataStream ads = null;
		try
		{
		as = new AudioStream (new FileInputStream("Sound/flamethrower.wav"));
		data = as.getData();
		ads = new AudioDataStream (data);
		}
		catch(Exception e)
		{
			System.out.println("Fail " + e);
		}
		AudioPlayer.player.start(ads);
	}
	public void playShotGun()
	{
		//create audio stream
		AudioStream as = null;
		AudioData data = null;
		AudioDataStream ads = null;
		try
		{
		as = new AudioStream (new FileInputStream("Sound/ShotGun.wav"));
		data = as.getData();
		ads = new AudioDataStream (data);
		}
		catch(Exception e)
		{
			System.out.println("Fail " + e);
		}
		AudioPlayer.player.start(ads);
	}
	public void playMachineGun()
	{
		//create audio stream
		AudioStream as = null;
		AudioData data = null;
		AudioDataStream ads = null;
		try
		{
		as = new AudioStream (new FileInputStream("Sound/Machinegun.wav"));
		data = as.getData();
		ads = new AudioDataStream (data);
		}
		catch(Exception e)
		{
			System.out.println("Fail " + e);
		}
		AudioPlayer.player.start(ads);
	}
	public void playExplosion()
	{
		//create audio stream
		AudioStream as = null;
		AudioData data = null;
		AudioDataStream ads = null;
		try
		{
		as = new AudioStream (new FileInputStream("Sound/Explosion.wav"));
		data = as.getData();
		ads = new AudioDataStream (data);
		}
		catch(Exception e)
		{
			System.out.println("Fail " + e);
		}
		AudioPlayer.player.start(ads);
	}
}