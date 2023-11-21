import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {
	@Override
	public int compare(Node n1, Node n2) {
		if (n1.getFrequency() > n2.getFrequency()) {
			return 1;
		}
		if (n1.getFrequency() < n2.getFrequency()) {
			return -1;
		}
		return 0;
	}
}
