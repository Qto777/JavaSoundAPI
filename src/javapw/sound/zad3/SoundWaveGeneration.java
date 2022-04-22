package javapw.sound.zad3;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class SoundWaveGeneration {

	public static void main(String[] args) throws LineUnavailableException {
	    byte[] buf = new byte[1];
	    
	  //Otworz wyjscie audio
	    AudioFormat audioFormat = new AudioFormat( (float )44100, 8, 1, true, false );
	    // 44100: audio sampling rate (czestotliwosc probkowania, dla cyfrowego dzwieku standard 44100 Hz)
	    // 8: bit samples
	    
	    SourceDataLine sdl = AudioSystem.getSourceDataLine(audioFormat);
	    sdl.open();
	    sdl.start();
	    
	    //Przy kazdym przejsciu glowna petla wypelnia wolna przestrzen w buforze audio
	    for( int i = 0; i < 1000 * (float )44100 / 1000; i++ ) {
	        double angle = i / ( (float )44100 / 440 ) * 2.0 * Math.PI;
	        buf[ 0 ] = (byte )( Math.sin( angle ) * 100 ); //nasza fala sinusoidalna
	        sdl.write( buf, 0, 1 );
	    }
	    
	    //Zakonczono odtwarzanie fali dzwiekowej, teraz czekamy az zakolejkowane probki zakoncza
	    //odtwarzanie; nastepnie sprzatamy i wychodzimy
	    sdl.drain();
	    sdl.stop();

	}

}
