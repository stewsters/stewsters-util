package com.stewsters.util.pathing.twoDimention.hpa;

import com.stewsters.util.pathing.twoDimention.shared.BoundingBox2d;

public interface Chunk2d extends BoundingBox2d {

    int getXOffset();

    int getYOffset();
}
