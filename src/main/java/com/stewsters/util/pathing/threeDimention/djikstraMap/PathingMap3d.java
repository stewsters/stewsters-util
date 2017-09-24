package com.stewsters.util.pathing.threeDimention.djikstraMap;

import com.stewsters.util.pathing.threeDimention.shared.CanTraverse3d;
import com.stewsters.util.pathing.threeDimention.shared.MovementCost3d;

public interface PathingMap3d {

    void recalculate(CanTraverse3d canTraverse3d, MovementCost3d movementCost3d, boolean allowDiagMovement, int sX, int sY, int sZ);

    float getDistanceAt(int x, int y, int z);
}
