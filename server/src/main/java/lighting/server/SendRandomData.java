package lighting.server;

import java.util.Random;
import java.util.stream.IntStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.bildspur.artnet.ArtNetClient;

public class SendRandomData {

	private static final Logger log = LogManager.getLogger(SendRandomData.class);

	public static void main(String[] args) throws InterruptedException {

		ArtNetClient artNetClient = new ArtNetClient();
		artNetClient.start();

		for (int i = 0; i < 1000; i++) {
			int[] dmxValues = IntStream.generate(() -> new Random().nextInt(256)).limit(512).toArray();
			int[] dmxValues1 = IntStream.generate(() -> new Random().nextInt(256)).limit(512).toArray();
			int[] dmxValues2 = IntStream.generate(() -> new Random().nextInt(256)).limit(512).toArray();

			byte[] dmxData = new byte[512];
			byte[] dmxData1 = new byte[512];
			byte[] dmxData2 = new byte[512];

			for (int x = 0; x < 512; x++) {
				byte b = (byte) (dmxValues[x] & 0xFF);
				dmxData[x] = b;
			}

			for (int x = 0; x < 512; x++) {
				byte b = (byte) (dmxValues1[x] & 0xFF);
				dmxData1[x] = b;
			}
			for (int x = 0; x < 512; x++) {
				byte b = (byte) (dmxValues2[x] & 0xFF);
				dmxData2[x] = b;
			}

			artNetClient.broadcastDmx(0, 1, dmxData);
			artNetClient.broadcastDmx(0, 2, dmxData1);
			artNetClient.broadcastDmx(0, 3, dmxData2);

			Thread.sleep(1000);
			log.info("send" + i);
		}
	}
}
