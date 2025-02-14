package org.example.utils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class King implements Piece {
    private Position pos;
    private String name;
    private String color;
    private int id;
    private boolean moved = false;

    King(String color, Position pos){
        this.color = color;
        this.name = color + " king";
        this.pos = pos;
    }
    King(String color, Position pos, boolean moved){
        this.color = color;
        this.name = color + " king";
        this.pos = pos;
        this.moved = moved;
    }

    @Override
    public Piece copy() {
        return new King(color, pos, moved);
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
        return "K";
    }
    public String getColor(){
        return color;
    }
    public boolean kingMoved(){
        return moved;
    }
    public void setMoved(boolean moved){
        this.moved = moved;
    }

    public void move(Position pos){
        this.pos = pos;
        moved = true;
    };

    public List<Position> getPossibleMoves(){
        Position[] possibleMoves = {
                new Position(pos.x-1,pos.y-1),
                new Position(pos.x-1,pos.y),
                new Position(pos.x-1,pos.y+1),
                new Position(pos.x,pos.y+1),
                new Position(pos.x+1,pos.y+1),
                new Position(pos.x+1,pos.y),
                new Position(pos.x+1,pos.y-1),
                new Position(pos.x,pos.y-1),
                new Position(pos.x,pos.y+2),
                new Position(pos.x,pos.y-2)
        };
        List<Position> legalMoves = new java.util.ArrayList<Position>();
        Collections.addAll(legalMoves, possibleMoves);
        if(pos.x == 0){
            legalMoves = legalMoves.stream().filter(p -> p.x != pos.x-1).toList();
        }
        if(pos.x == 7){
            legalMoves = legalMoves.stream().filter(p -> p.x != pos.x+1).toList();
        }
        if(pos.y == 0){
            legalMoves = legalMoves.stream().filter(p -> p.y != pos.y-1).toList();
        }
        if(pos.y == 7){
            legalMoves = legalMoves.stream().filter(p -> p.y != pos.y+1).toList();
        }
        if(moved){
            legalMoves = legalMoves.stream().filter(p -> p.y != pos.y-2 && p.y != pos.y+2).toList();
        }
        return legalMoves;
    };
}
