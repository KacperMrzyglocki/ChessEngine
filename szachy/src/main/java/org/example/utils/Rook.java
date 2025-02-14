package org.example.utils;

import java.util.List;
import java.util.Objects;

public class Rook implements Piece {
    private Position pos;
    private String name;
    private String color;
    private int id;
    private boolean moved = false;

    Rook(String color, Position pos){
        this.color = color;
        this.name = color + " rook";
        this.pos = pos;
    }
    Rook(String color, Position pos, boolean moved){
        this.color = color;
        this.name = color + " rook";
        this.pos = pos;
        this.moved = moved;
    }

    @Override
    public Piece copy() {
        return new Rook(color, pos, moved);
    }

    public void move(Position pos){
        this.pos = pos;
        moved = true;
    };

    public boolean rookMoved(){
        return moved;
    }
    public void setMoved(boolean moved){
        this.moved = moved;
    }
    public Position getPosition(){
        return pos;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId(){
        return id;
    }
    public String getSymbol(){
        return "R";
    }
    public String getColor(){
        return color;
    }

    public List<Position> getPossibleMoves(){
        List<Position> possibleMoves = new java.util.ArrayList<Position>();
        for(int i=0;i<8;i++){
            if(i != pos.x) possibleMoves.add(new Position(i, pos.y));
            if(i != pos.y) possibleMoves.add(new Position(pos.x, i));
        }
        return possibleMoves;
    };
}

