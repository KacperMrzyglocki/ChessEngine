package org.example.utils;

import java.util.List;

public class Bishop implements Piece {
    private Position pos;
    private String name;
    private String color;
    private int id;

    Bishop(String color, Position pos){
        this.color = color;
        this.name = color + " bishop";
        this.pos = pos;
    }

    @Override
    public Piece copy() {
        return new Bishop(color, pos);
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
        return "B";
    }
    public String getColor(){
        return color;
    }

    public void move(Position pos){
        this.pos = pos;
    };

    public List<Position> getPossibleMoves(){
        List<Position> possibleMoves = new java.util.ArrayList<Position>();
        for(int i=1;i<8;i++){
            if((pos.x-i >= 0) && (pos.y-i >= 0)) possibleMoves.add(new Position(pos.x-i, pos.y-i));
            if((pos.x-i >= 0) && (pos.y+i <= 7)) possibleMoves.add(new Position(pos.x-i, pos.y+i));
            if((pos.x+i <= 7) && (pos.y-i >= 0)) possibleMoves.add(new Position(pos.x+i, pos.y-i));
            if((pos.x+i <= 7) && (pos.y+i <= 7)) possibleMoves.add(new Position(pos.x+i, pos.y+i));
        }
        return possibleMoves;
    };
}
