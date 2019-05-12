package game;

import java.util.Random;

public enum Action {
    Up, Down, Right, Left, None;

    private static Random r = new Random();

    public static Action rndAction() {
        int n = r.nextInt(5);
        switch (n) {
            case 0:
                return Up;
            case 1:
                return Right;
            case 2:
                return Down;
            case 3:
                return Left;
            case 4:
                return None;
                default:
                    assert false;
                    return null;
        }
    }
}
