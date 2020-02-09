package lighting.server.scene;

public class Setup {

	private final String scene;
	private final boolean option1;
	private final boolean option2;
	private final String selection;
	private final int level1;
	private final int level2;

	public Setup(final String scene, final boolean option1, final boolean option2, final String selection, final int level1, final int level2) {
		this.scene = scene;
		this.option1 = option1;
		this.option2 = option2;
		this.selection = selection;
		this.level1 = level1;
		this.level2 = level2;
	}

	public String getScene() {
		return scene;
	}

	public boolean isOption1() {
		return option1;
	}

	public boolean isOption2() {
		return option2;
	}

	public String getSelection() {
		return selection;
	}

	public int getLevel1() {
		return level1;
	}

	public int getLevel2() {
		return level2;
	}
}
