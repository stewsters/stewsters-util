package com.stewsters.util.pathing.twoDimention.djikstraMap;

import com.stewsters.util.pathing.twoDimention.shared.CanTraverse2d;
import com.stewsters.util.pathing.twoDimention.shared.MovementCost2d;

public interface PathingMap2d {

    void recalculate(CanTraverse2d canTraverse2d,
                     MovementCost2d movementCost2d,
                     boolean allowDiagMovement,
                     int sX, int sY);

    float getDistanceAt(int x, int y);
}
