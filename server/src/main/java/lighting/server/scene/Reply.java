package lighting.server.scene;

public class Reply {

	private boolean success = true;

	public Reply() {
		this(true);
	}

	public Reply(boolean success) {
		this.success = success;
	}

	public boolean getSuccess() {
		return success;
	}
}
