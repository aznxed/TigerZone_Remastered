package testing;

public class debugPrint {
	private boolean debugEnable = true;

	public void out(String message) {
		if (debugEnable) {
			System.out.println("    Debug: " + message);
		}
	}
	
	public void out(int message) {
		if (debugEnable) {
			System.out.println("    Debug: " + String.valueOf(message));
		}
	}
}