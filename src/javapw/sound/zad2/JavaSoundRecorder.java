package javapw.sound.zad2;

import javax.sound.sampled.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
 
/**
 * A sample program is to demonstrate how to record sound in Java
 * author: www.codejava.net
 */
public class JavaSoundRecorder {
	
	static final int SLIDER_MIN = 1;
	static final int SLIDER_MAX = 100;
	static final int SLIDER_INIT = 10;

	static JLabel label1 = new JLabel("Czas nagrywania to: 5 sekund");
	static JButton guzikStart = new JButton("START");
	static JSlider slider = new JSlider(JSlider.HORIZONTAL, SLIDER_MIN, SLIDER_MAX, SLIDER_INIT);
	
	
    // record duration, in milliseconds
	
    static final long RECORD_TIME = 5000;  // *1000 in seconds
 
    // path of the wav file
    File wavFile = new File("TestRecordAudio.wav");
 
    // format of audio file
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
 
    // the line from which audio data is captured
    TargetDataLine line;
 
    
    /**
     * Defines an audio format
     */
    AudioFormat getAudioFormat() {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                                             channels, signed, bigEndian);
        return format;
    }
 
    /**
     * Captures the sound and record into a WAV file
     */
    void start() {
        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
 
            // checks if system supports the data line
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                System.exit(0);
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();   // start capturing
 
            System.out.println("Start capturing...");
 
            AudioInputStream ais = new AudioInputStream(line);
 
            System.out.println("Start recording...");
 
            // start recording
            AudioSystem.write(ais, fileType, wavFile);
 
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
 
    /**
     * Closes the target data line to finish capturing and recording
     */
    void finish() {
        line.stop();
        line.close();
        System.out.println("Finished");
    }
    
  
 
    /**
     * Entry to run the program
     */
    public static void main(String[] args) {
    	JFrame frame = new JFrame();
    	frame.setSize(600,400);
    	frame.setLayout(new FlowLayout());
    	
    	final JavaSoundRecorder recorder = new JavaSoundRecorder();
    	 
        // creates a new thread that waits for a specified
        // of time before stopping
        Thread stopper = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(slider.getValue()*1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                recorder.finish();
            }
        });
    	
    	slider.setPreferredSize(new Dimension(500,50));//rozmiar suwaka
    	slider.setMajorTickSpacing(10); //gestosc przedzialow glownych
    	slider.setMinorTickSpacing(5); //gestosc podzialek
    	slider.setPaintTicks(true);//podzialki na pasku
    	slider.setPaintLabels(true); //przedzialy glowne, przedzialowe paska
    	//slider.addChangeListener(new SliderChangeListener());
    	
    	frame.add(label1);
    	frame.add(slider);
    	frame.add(guzikStart);
    	
    	guzikStart.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e){
    			stopper.start();
    			// start recording
    	        recorder.start();
    		}
    	});
        
        frame.setVisible(true);
    }
    
    /*public class SliderChangeListener implements ChangeListener{
		@Override
		public void stateChanged(ChangeEvent arg0) {
		label1.setText(" " + slider.getValue());
		}
	}*/



}