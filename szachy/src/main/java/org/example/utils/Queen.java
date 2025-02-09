package org.example.utils;

import java.util.List;

public class Queen implements Piece {
    private Position pos;
    private String name;
    private String color;
    private int id;

    Queen(String color, Position pos){
        this.color = color;
        this.name = color + " queen";
        this.pos = pos;
    }

    public void move(Position pos){
        this.pos = pos;
    };

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
        return "Q";
    }
    public String getColor(){
        return color;
    }

    public List<Position> getPossibleMoves(){
        List<Position> possibleMoves = new java.util.ArrayList<Position>();
        for(int i=0;i<8;i++){
            if(i != pos.x) possibleMoves.add(new Position(i, pos.y));
            if(i != pos.y) possibleMoves.add(new Position(pos.x, i));
            if(i != 0){
                if((pos.x-i >= 0) && (pos.y-i >= 0)) possibleMoves.add(new Position(pos.x-i, pos.y-i));
                if((pos.x-i >= 0) && (pos.y+i <= 7)) possibleMoves.add(new Position(pos.x-i, pos.y+i));
                if((pos.x+i <= 7) && (pos.y-i >= 0)) possibleMoves.add(new Position(pos.x+i, pos.y-i));
                if((pos.x+i <= 7) && (pos.y+i <= 7)) possibleMoves.add(new Position(pos.x+i, pos.y+i));
            }
        }
        return possibleMoves;
    };
}
