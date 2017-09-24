package com.stewsters.util.pathing.twoDimention.djikstraMap;

import com.stewsters.util.pathing.twoDimention.shared.CanTraverse;
import com.stewsters.util.pathing.twoDimention.shared.MovementCost;

public interface PathingMap2d {

    void recalculate(CanTraverse canTraverse,
                     MovementCost movementCost,
                     boolean allowDiagMovement,
                     int sX, int sY);

    float getDistanceAt(int x, int y);
}
