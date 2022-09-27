package graphs;

import utils.Point;
import utils.GlobalWarming;

import java.util.*;

public class GlobalWarmingPaths extends GlobalWarming {

  /**
   * In the following, we assume that the points are connected to
   * horizontal or vertical neighbors but not to the diagonal ones
   *
   * @param altitude   is a n x n matrix of int values representing altitudes (positive or negative)
   * @param waterLevel is the water level, every entry <= waterLevel is flooded
   */
  public GlobalWarmingPaths(int[][] altitude, int waterLevel) {
    super(altitude, waterLevel);
  }

  public List<Point> shortestPath(Point p1, Point p2) {
        // TODO
        // expected time complexity O(n^2)
        return new ArrayList<Point>();

    }
}
