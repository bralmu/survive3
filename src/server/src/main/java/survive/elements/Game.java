package survive.elements;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class Game {
	public static final String SERVER_DOMAIN = "*";
	/**
	 * Must be a common divisor of sizeX and sizeY
	 */
	public static final float CELL_SIZE = 10;
	/**
	 * Milliseconds to kick inactive player.
	 */
	public static final double MAX_INACTIVITY = 60000;
	/**
	 * Updating thread execution frequency.
	 */
	public static final float UPDATES_PER_SECOND = 10;
	/**
	 * World virtual size.
	 */
	public static float sizeX;
	public static float sizeY;
	
	/**
	 * Game units.
	 */
	public static ConcurrentMap<Integer,Unit> units;
	/**
	 * Teams keys must start by 0 and be consecutive with no gasps.
	 * Maybe a List structure will be more appropriate.
	 */
	public static Map<Integer,Team> teams;
	public static List<Cell> cells;
	
	public static final AtomicLong nextUID = new AtomicLong();
}
