package org.example.utils;

import java.util.List;

public interface Piece {
    public void move(Position pos);
    public List<Position> getPossibleMoves();
    public Position getPosition();
    public void setId(int id);
    public int getId();
    public String getSymbol();
    public String getColor();
}
