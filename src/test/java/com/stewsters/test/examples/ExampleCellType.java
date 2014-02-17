package com.stewsters.test.examples;


import com.stewsters.util.mapgen.CellType;

public class ExampleCellType implements CellType {
    private char glyph;
    private boolean blocks;

    public ExampleCellType(char glyph, boolean blocks) {
        this.glyph = glyph;
        this.blocks = blocks;
    }

    @Override
    public char getGlyph() {
        return glyph;
    }

    @Override
    public boolean isBlocking() {
        return blocks;
    }


}
