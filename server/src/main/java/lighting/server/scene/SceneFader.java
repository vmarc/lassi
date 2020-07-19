package lighting.server.scene;

import java.time.Duration;
import java.time.Instant;

import lighting.server.artnet.ArtnetSender;
import lighting.server.frame.Frame;

public class SceneFader {

	private final int framesPerSecond;
	private final int fadeTimeInSeconds;
	private final Frame startFrame;
	private final Frame endFrame;
	private final long startTime;

	private int[] dmxValues;
	private double totalFrames;
	private boolean pause = false;

	public SceneFader(final int framesPerSecond, final int fadeTimeInSeconds, final Frame startFrame, final Frame endFrame, final long startTime) {
		this.framesPerSecond = framesPerSecond;
		this.fadeTimeInSeconds = fadeTimeInSeconds;
		this.startFrame = startFrame;
		this.endFrame = endFrame;
		this.startTime = startTime;
	}

	public void setTotalFrames(double totalFrames) {
		this.totalFrames = totalFrames;
	}

	public Frame getEndFrame() {
		return endFrame;
	}

	public int[] getDmxValues() {
		return dmxValues;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}

	public void fadeFrame(ArtnetSender artnetSender) {
		try {
			Thread.sleep(startTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Instant start = Instant.now();
		long pauseTimeLong = 0;

		totalFrames = framesPerSecond * fadeTimeInSeconds;
		double timeOut = 1000.0 / framesPerSecond;

		double[] differenceList = new double[512];
		dmxValues = startFrame.getDmxValues().clone();
		int[] originalDmxValues = startFrame.getDmxValues().clone();

		int universe = endFrame.getUniverse();

		//Calculating the difference between the start value per channel in a frame and the end frame, adding it to the differenceList
		//(endFrame minus startFrame) dividing by the totalFrames
		for (int i = 0; i < 512; i++) {
			differenceList[i] = ((endFrame.getDmxValues()[i] - startFrame.getDmxValues()[i]) / totalFrames);
		}

		//Fading Method
		for (int i = 0; i < totalFrames; i++) {
			//Logging
			//System.out.println();
			//System.out.print(i + 1 + "....  ");

			for (int j = 0; j < 512; j++) {
				dmxValues[j] = (int) (originalDmxValues[j] + Math.round((i + 1) * differenceList[j]));
				//Logging
				//System.out.print(dmxValues[j] + " / ");
			}

			Instant pauseTime = null;
			System.out.println("Pause: " + pause);
			while (pause) {
				if (pauseTime == null) {
					pauseTime = Instant.now();
				}
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			Instant now = Instant.now();
			long timeElapsed = Duration.between(start, now).toMillis();
			//System.out.println();
			//System.out.println("time elapsed: " + timeElapsed);

			if (pauseTime != null) {
				pauseTimeLong += Duration.between(pauseTime, Instant.now()).toMillis();
			}
			pauseTime = null;

			//System.out.println("PauseTimeLong: " + pauseTimeLong);

			artnetSender.sendFrame(dmxValues, universe);

			//Sleep expected sleep time - timeOut

			long sleep = (long) ((timeOut * (i + 1)) - (timeElapsed - pauseTimeLong));

			//System.out.println();
			//System.out.println("sleeptime: " + sleep);

			if (sleep > 0) {
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		artnetSender.removeSceneFader(this);
		Instant finish = Instant.now();

		//Logging
		long timeElapsed = Duration.between(start, finish).toMillis();
		//System.out.println();
		System.out.println("Time elapsed: " + timeElapsed + " milliseconds");
	}
}
