package org.example.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class Board {

    public List<Piece> blackPieces = new CopyOnWriteArrayList<>();
    public List<Piece> whitePieces = new CopyOnWriteArrayList<>();

    private final Position[][] board = {
            {new Position(0,0),new Position(0,1),new Position(0,2),new Position(0,3),new Position(0,4),new Position(0,5),new Position(0,6),new Position(0,7)},
            {new Position(1,0),new Position(1,1),new Position(1,2),new Position(1,3),new Position(1,4),new Position(1,5),new Position(1,6),new Position(1,7)},
            {new Position(2,0),new Position(2,1),new Position(2,2),new Position(2,3),new Position(2,4),new Position(2,5),new Position(2,6),new Position(2,7)},
            {new Position(3,0),new Position(3,1),new Position(3,2),new Position(3,3),new Position(3,4),new Position(3,5),new Position(3,6),new Position(3,7)},
            {new Position(4,0),new Position(4,1),new Position(4,2),new Position(4,3),new Position(4,4),new Position(4,5),new Position(4,6),new Position(4,7)},
            {new Position(5,0),new Position(5,1),new Position(5,2),new Position(5,3),new Position(5,4),new Position(5,5),new Position(5,6),new Position(5,7)},
            {new Position(6,0),new Position(6,1),new Position(6,2),new Position(6,3),new Position(6,4),new Position(6,5),new Position(6,6),new Position(6,7)},
            {new Position(7,0),new Position(7,1),new Position(7,2),new Position(7,3),new Position(7,4),new Position(7,5),new Position(7,6),new Position(7,7)},
    };
    public void initializeBoard(){
        initializeRooks();
        initializeKnights();
        initializeBishops();
        initializeQueens();
        initializeKings();
        initializePawns();
        int id = 0;
        for(Piece piece : whitePieces){
            piece.setId(id++);
        }
        for(Piece piece : blackPieces){
            piece.setId(id++);
        }
    }

    private void initializeRooks(){
        String color = "Black";
        blackPieces.add(new Rook(color, board[0][7]));
        blackPieces.add(new Rook(color, board[0][0]));

        color = "White";
        whitePieces.add(new Rook(color, board[7][7]));
        whitePieces.add(new Rook(color, board[7][0]));
    }
    private void initializeKnights(){
        String color = "Black";
        blackPieces.add(new Knight(color, board[0][1]));
        blackPieces.add(new Knight(color, board[0][6]));

        color = "White";
        whitePieces.add(new Knight(color, board[7][6]));
        whitePieces.add(new Knight(color, board[7][1]));
    }
    private void initializeBishops(){
        String color = "Black";
        blackPieces.add(new Bishop(color, board[0][2]));
        blackPieces.add(new Bishop(color, board[0][5]));

        color = "White";
        whitePieces.add(new Bishop(color, board[7][5]));
        whitePieces.add(new Bishop(color, board[7][2]));
    }
    private void initializeQueens(){
        String color = "Black";
        blackPieces.add(new Queen(color, board[0][3]));

        color = "White";
        whitePieces.add(new Queen(color, board[7][3]));
    }
    private void initializeKings(){
        String color = "Black";
        blackPieces.add(new King(color, board[0][4]));

        color = "White";
        whitePieces.add(new King(color, board[7][4]));
    }
    private void initializePawns(){
        String color = "Black";
        blackPieces.add(new Pawn(color, board[1][0]));
        blackPieces.add(new Pawn(color, board[1][1]));
        blackPieces.add(new Pawn(color, board[1][2]));
        blackPieces.add(new Pawn(color, board[1][3]));
        blackPieces.add(new Pawn(color, board[1][4]));
        blackPieces.add(new Pawn(color, board[1][5]));
        blackPieces.add(new Pawn(color, board[1][6]));
        blackPieces.add(new Pawn(color, board[1][7]));

        color = "White";
        whitePieces.add(new Pawn(color, board[6][0]));
        whitePieces.add(new Pawn(color, board[6][1]));
        whitePieces.add(new Pawn(color, board[6][2]));
        whitePieces.add(new Pawn(color, board[6][3]));
        whitePieces.add(new Pawn(color, board[6][4]));
        whitePieces.add(new Pawn(color, board[6][5]));
        whitePieces.add(new Pawn(color, board[6][6]));
        whitePieces.add(new Pawn(color, board[6][7]));
    }

    public void printBoard(){
        System.out.println("#################");
        for(int i=0;i<8;i++){
            System.out.print("#");
            for(int j=0;j<8;j++){
                boolean printed = false;
                for(Piece piece : whitePieces){
                    if((piece.getPosition().x == i) && (piece.getPosition().y == j)){
                        String symbol = piece.getSymbol().toLowerCase();
                        System.out.print(symbol);
                        printed = true;
                    }
                }
                for(Piece piece : blackPieces){
                    if((piece.getPosition().x == i) && (piece.getPosition().y == j)){
                        System.out.print(piece.getSymbol());
                        printed = true;
                    }
                }
                if(!printed){
                    System.out.print(" ");
                }
                if(j<7){
                    System.out.print("|");
                }
            }
            System.out.print("#"+((i+1)*8)%9+"\n");
        }
        System.out.println("#a#b#c#d#e#f#g#h#\n");
    }

    public List<Position> getNextLegalMoves(List<Position> possibleMoves, Piece piece){
        List<Position> legalMoves = new ArrayList<>(possibleMoves);
        if(Objects.equals(piece.getSymbol(), "B")){
            legalMoves = getLegalBishopMoves(possibleMoves, piece);
        } else if(Objects.equals(piece.getSymbol(), "Q")){
            legalMoves = getLegalQueenMoves(possibleMoves, piece);
        } else if(Objects.equals(piece.getSymbol(), "R")){
            legalMoves = getLegalRookMoves(possibleMoves, piece);
        } else if(Objects.equals(piece.getSymbol(), "P")){
            legalMoves = getLegalPawnMoves(legalMoves, piece);
        } else{
            if(Objects.equals(piece.getColor(), "White")){
                for(Piece whitePiece : whitePieces){
                    Position pos = whitePiece.getPosition();
                    legalMoves = legalMoves.stream().filter(p -> (p.x != pos.x) || (p.y != pos.y)).collect(Collectors.toList());
                }
            } else{
                for(Piece blackPiece : blackPieces){
                    Position pos = blackPiece.getPosition();
                    legalMoves = legalMoves.stream().filter(p -> (p.x != pos.x) || (p.y != pos.y)).collect(Collectors.toList());
                }
            }
        }
        if(Objects.equals(piece.getSymbol(), "K")){
            legalMoves = getLegalCastles(legalMoves,piece);
        }
        return legalMoves;
    }
    public List<Position> getLegalMoves(List<Position> possibleMoves, Piece piece){
        List<Position> legalMoves = getNextLegalMoves(possibleMoves,piece);
        Piece king = null;
        if(Objects.equals(piece.getColor(), "White")){
            for(Piece whitePiece : whitePieces){
                if(whitePiece instanceof King){
                    king = whitePiece;
                }
            }
            legalMoves = filterMovesMakesCheck(piece,legalMoves,king);
        }else{
            for(Piece blackPiece : blackPieces){
                if(blackPiece instanceof King){
                    king = blackPiece;
                }
            }
            legalMoves = filterMovesMakesCheck(piece,legalMoves,king);
        }
        return legalMoves;
    }
    private List<Position> getLegalCastles(List<Position> possibleMoves, Piece piece){
        List<Position> legalMoves = new ArrayList<>(possibleMoves);
        if(Objects.equals(piece.getColor(), "White")){
            legalMoves = checkCastleConditions(legalMoves,piece,whitePieces, blackPieces);
        }else{
            legalMoves = checkCastleConditions(legalMoves,piece,blackPieces, whitePieces);
        }
        return legalMoves;
    }
    private List<Position> checkCastleConditions(List<Position> legalMoves, Piece piece, List<Piece> otherPieces, List<Piece> enemyPieces){
        Position pos = piece.getPosition();
        for(Position legalMove : legalMoves){
            if(legalMove.y == pos.y+2){
                if(!enemyBlockingCastle(piece, enemyPieces, "Right")) {
                    for (Piece whitePiece : otherPieces) {
                        Position whitePos = whitePiece.getPosition();
                        if (otherPieces.get(0).getId() == 0) {
                            Piece rightRook = otherPieces.get(0);
                            if (((pos.y + 1 == whitePos.y && pos.x == whitePos.x) || (pos.y + 2 == whitePos.y && pos.x == whitePos.x)) || ((Rook) rightRook).rookMoved() || isCheck(enemyPieces, piece)) {
                                legalMoves = legalMoves.stream().filter(p -> p.y != pos.y + 2).collect(Collectors.toList());
                            }
                        } else if (otherPieces.get(0).getId() == 16) {
                            Piece rightRook = otherPieces.get(0);
                            if (((pos.y + 1 == whitePos.y && pos.x == whitePos.x) || (pos.y + 2 == whitePos.y && pos.x == whitePos.x)) || ((Rook) rightRook).rookMoved() || isCheck(enemyPieces, piece)) {
                                legalMoves = legalMoves.stream().filter(p -> p.y != pos.y + 2).collect(Collectors.toList());
                            }
                        } else {
                            legalMoves = legalMoves.stream().filter(p -> p.y != pos.y + 2).collect(Collectors.toList());
                        }
                    }
                } else{
                    legalMoves = legalMoves.stream().filter(p -> p.y != pos.y + 2).collect(Collectors.toList());
                }
            }
            if(legalMove.y == pos.y-2){
                if(!enemyBlockingCastle(piece, enemyPieces, "Left")) {
                    for (Piece whitePiece : otherPieces) {
                        Position whitePos = whitePiece.getPosition();
                        if (otherPieces.get(1).getId() == 1) {
                            Piece leftRook = otherPieces.get(1);
                            if (((pos.y - 1 == whitePos.y && pos.x == whitePos.x) || (pos.y - 2 == whitePos.y && pos.x == whitePos.x) || (pos.y - 3 == whitePos.y && pos.x == whitePos.x)) || ((Rook) leftRook).rookMoved() || isCheck(enemyPieces, piece)) {
                                legalMoves = legalMoves.stream().filter(p -> p.y != pos.y - 2).collect(Collectors.toList());
                            }
                        } else if (otherPieces.get(1).getId() == 17) {
                            Piece leftRook = otherPieces.get(1);
                            if (((pos.y - 1 == whitePos.y && pos.x == whitePos.x) || (pos.y - 2 == whitePos.y && pos.x == whitePos.x) || (pos.y - 3 == whitePos.y && pos.x == whitePos.x)) || ((Rook) leftRook).rookMoved() || isCheck(enemyPieces, piece)) {
                                legalMoves = legalMoves.stream().filter(p -> p.y != pos.y - 2).collect(Collectors.toList());
                            }
                        } else if (otherPieces.get(0).getId() == 1) {
                            Piece leftRook = otherPieces.get(0);
                            if (((pos.y - 1 == whitePos.y && pos.x == whitePos.x) || (pos.y - 2 == whitePos.y && pos.x == whitePos.x) || (pos.y - 3 == whitePos.y && pos.x == whitePos.x)) || ((Rook) leftRook).rookMoved() || isCheck(enemyPieces, piece)) {
                                legalMoves = legalMoves.stream().filter(p -> p.y != pos.y - 2).collect(Collectors.toList());
                            }
                        } else if (otherPieces.get(0).getId() == 17) {
                            Piece leftRook = otherPieces.get(0);
                            if (((pos.y - 1 == whitePos.y && pos.x == whitePos.x) || (pos.y - 2 == whitePos.y && pos.x == whitePos.x) || (pos.y - 3 == whitePos.y && pos.x == whitePos.x)) || ((Rook) leftRook).rookMoved() || isCheck(enemyPieces, piece)) {
                                legalMoves = legalMoves.stream().filter(p -> p.y != pos.y - 2).collect(Collectors.toList());
                            }
                        } else {
                            legalMoves = legalMoves.stream().filter(p -> p.y != pos.y - 2).collect(Collectors.toList());
                        }
                    }
                }else{
                    legalMoves = legalMoves.stream().filter(p -> p.y != pos.y - 2).collect(Collectors.toList());
                }
            }
        }
        return legalMoves;
    }
    public void castle(Piece king, String side){
        if(Objects.equals(king.getColor(), "White")){
            if(Objects.equals(side, "Right")){
                for(Piece whitePiece : whitePieces){
                    if(whitePiece.getId() == 0){
                        Position pos = new Position(7,5);
                        whitePiece.move(pos);
                    }
                }
            } else{
                for(Piece whitePiece : whitePieces){
                    if(whitePiece.getId() == 1){
                        Position pos = new Position(7,3);
                        whitePiece.move(pos);
                    }
                }
            }
        } else{
            if(Objects.equals(side, "Right")){
                for(Piece blackPiece : blackPieces){
                    if(blackPiece.getId() == 16){
                        Position pos = new Position(0,5);
                        blackPiece.move(pos);
                    }
                }
            } else{
                for(Piece blackPiece : blackPieces){
                    if(blackPiece.getId() == 17){
                        Position pos = new Position(0,3);
                        blackPiece.move(pos);
                    }
                }
            }
        }
    }
    public boolean isCheck(List<Piece> enemyPieces, Piece king){
        Position kingPos = king.getPosition();
        enemyPieces = enemyPieces.stream().filter(p -> !Objects.equals(p.getSymbol(), "K")).collect(Collectors.toList());
        for(Piece piece : enemyPieces){
            List<Position> legalMoves = getNextLegalMoves(piece.getPossibleMoves(),piece);
            for(Position legalMove : legalMoves){
                if(legalMove.x == kingPos.x && legalMove.y == kingPos.y){
                    return true;
                }
            }
        }
        return false;
    }
    private List<Position> filterMovesMakesCheck(Piece piece,List<Position> possibleMoves, Piece king){
        List<Position> legalMoves = new ArrayList<>(possibleMoves);
        for(Position possibleMove : possibleMoves){
            Position oldPos = piece.getPosition();
            boolean rookMoved = false;
            boolean kingMoved = false;
            boolean firstMove = false;
            if(piece instanceof Rook){
                rookMoved = ((Rook)piece).rookMoved();
            }
            if(piece instanceof King){
                kingMoved = ((King)piece).kingMoved();
            }
            if(piece instanceof Pawn){
                firstMove = ((Pawn)piece).getFirstMove();
            }
            piece.move(possibleMove);
            Piece deletedPiece = deleteTakenPieces(piece);
            List<Piece> enemyPieces;
            if(Objects.equals(piece.getColor(), "White")){
                enemyPieces = blackPieces;
            } else{
                enemyPieces = whitePieces;
            }
            if(isCheck(enemyPieces,king)){
                legalMoves.remove(possibleMove);
            }
            piece.move(oldPos);
            if(piece instanceof Rook){
                ((Rook)piece).setMoved(rookMoved);
            }
            if(piece instanceof King){
                ((King)piece).setMoved(kingMoved);
            }
            if(piece instanceof Pawn){
                ((Pawn)piece).setFirstMove(firstMove);
            }
            if(deletedPiece != null){
                if(Objects.equals(deletedPiece.getColor(), "White")){
                    whitePieces.add(deletedPiece);
                } else{
                    blackPieces.add(deletedPiece);
                }
            }
        }
        return legalMoves;
    }
    private boolean enemyBlockingCastle(Piece piece, List<Piece> enemyPieces, String side){
        Position pos = piece.getPosition();
        enemyPieces = enemyPieces.stream().filter(p -> !Objects.equals(p.getSymbol(), "K")).collect(Collectors.toList());
        for(Piece enemyPiece : enemyPieces){
            Position enemyPos = enemyPiece.getPosition();
            List<Position> legalMoves = getLegalMoves(enemyPiece.getPossibleMoves(),enemyPiece);
            if(Objects.equals(side, "Right")){
                if((enemyPos.x == pos.x) && (enemyPos.y == pos.y+1 || enemyPos.y == pos.y+2)){
                    return true;
                }
                for(Position legalMove : legalMoves){
                    if((legalMove.x == pos.x) && (legalMove.y == pos.y+1 || legalMove.y == pos.y+2)){
                        return true;
                    }
                }
            }
            if(Objects.equals(side, "Left")){
                if((enemyPos.x == pos.x) && (enemyPos.y == pos.y-1 || enemyPos.y == pos.y-2 || enemyPos.y == pos.y-3)){
                    return true;
                }
                for(Position legalMove : legalMoves){
                    if((legalMove.x == pos.x) && (legalMove.y == pos.y-1 || legalMove.y == pos.y-2 || legalMove.y == pos.y-3)){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private List<Position> getLegalPawnMoves(List<Position> possibleMoves, Piece piece){
        List<Position> legalMoves = new ArrayList<>(possibleMoves);
        if(Objects.equals(piece.getColor(), "White")){
            for(Piece whitePiece : whitePieces){
                Position pos = whitePiece.getPosition();
                legalMoves = legalMoves.stream().filter(p -> (p.x != pos.x) || (p.y != pos.y)).collect(Collectors.toList());
            }
            for(Piece blackPiece : blackPieces){
                Position blackPos = blackPiece.getPosition();
                Position pos = piece.getPosition();
                legalMoves = legalMoves.stream().filter(p -> (p.x != blackPos.x) || (p.y != blackPos.y)).collect(Collectors.toList());
                if((blackPos.x==pos.x-1) && ((blackPos.y==pos.y+1) || (blackPos.y==pos.y-1))){
                    legalMoves.add(blackPos);
                }
                if(blackPiece instanceof Pawn){
                    if(((Pawn) blackPiece).getEnPassant()){
                        if((blackPos.x == pos.x) && (blackPos.y == pos.y+1 || blackPos.y == pos.y-1)){
                            legalMoves.add(new Position(blackPos.x-1,blackPos.y));
                            ((Pawn) piece).setPossibleEnPassant(true);
                        }
                    }
                }
            }
        } else{
            for(Piece blackPiece : blackPieces){
                Position pos = blackPiece.getPosition();
                legalMoves = legalMoves.stream().filter(p -> (p.x != pos.x) || (p.y != pos.y)).collect(Collectors.toList());
            }
            for(Piece whitePiece : whitePieces){
                Position whitePos = whitePiece.getPosition();
                Position pos = piece.getPosition();
                legalMoves = legalMoves.stream().filter(p -> (p.x != whitePos.x) || (p.y != whitePos.y)).collect(Collectors.toList());
                if((whitePos.x==pos.x+1) && ((whitePos.y==pos.y+1) || (whitePos.y==pos.y-1))){
                    legalMoves.add(whitePos);
                }
                if(whitePiece instanceof Pawn){
                    if(((Pawn) whitePiece).getEnPassant()){
                        if((whitePos.x == pos.x) && (whitePos.y == pos.y+1 || whitePos.y == pos.y-1)){
                            legalMoves.add(new Position(whitePos.x+1,whitePos.y));
                            ((Pawn) piece).setPossibleEnPassant(true);
                        }
                    }
                }
            }
        }
        return legalMoves;
    }
    private List<Position> getLegalBishopMoves(List<Position> possibleMoves, Piece piece){
        List<Position> legalMoves = new ArrayList<Position>();
        Position whitePieceOnLeftDown;
        Position whitePieceOnLeftUp;
        Position whitePieceOnRightDown;
        Position whitePieceOnRightUp;
        Position blackPieceOnLeftDown;
        Position blackPieceOnLeftUp;
        Position blackPieceOnRightDown;
        Position blackPieceOnRightUp;
        if(Objects.equals(piece.getColor(), "White")){
            Position pos = piece.getPosition();
            List<Position> whitePiecesOnBias = checkPiecesOnBias(possibleMoves,piece,whitePieces);
            whitePieceOnRightDown = whitePiecesOnBias.get(0);
            whitePieceOnRightUp = whitePiecesOnBias.get(1);
            whitePieceOnLeftDown = whitePiecesOnBias.get(2);
            whitePieceOnLeftUp = whitePiecesOnBias.get(3);
            for(Position possibleMove : possibleMoves){
                if(possibleMove.x > pos.x && possibleMove.y > pos.y){
                    if(whitePieceOnRightDown == null){
                        legalMoves.add(possibleMove);
                    }
                    else if(!(possibleMove.x >= whitePieceOnRightDown.x)){
                        legalMoves.add(possibleMove);
                    }
                } else if(possibleMove.x > pos.x && possibleMove.y < pos.y){
                    if(whitePieceOnLeftDown == null){
                        legalMoves.add(possibleMove);
                    }
                    else if(!(possibleMove.x >= whitePieceOnLeftDown.x)){
                        legalMoves.add(possibleMove);
                    }
                } else if(possibleMove.x < pos.x && possibleMove.y > pos.y){
                    if(whitePieceOnRightUp == null){
                        legalMoves.add(possibleMove);
                    }
                    else if(!(possibleMove.x <= whitePieceOnRightUp.x)){
                        legalMoves.add(possibleMove);
                    }
                } else if(possibleMove.x < pos.x && possibleMove.y < pos.y){
                    if(whitePieceOnLeftUp == null){
                        legalMoves.add(possibleMove);
                    }
                    else if(!(possibleMove.x <= whitePieceOnLeftUp.x)){
                        legalMoves.add(possibleMove);
                    }
                }
            }
            List<Position> blackPiecesOnBias = checkPiecesOnBias(legalMoves,piece,blackPieces);
            blackPieceOnRightDown = blackPiecesOnBias.get(0);
            blackPieceOnRightUp = blackPiecesOnBias.get(1);
            blackPieceOnLeftDown = blackPiecesOnBias.get(2);
            blackPieceOnLeftUp = blackPiecesOnBias.get(3);
            for(Position legalMove : legalMoves){
                if(legalMove.x > pos.x && legalMove.y > pos.y){
                    if(blackPieceOnRightDown == null){
                        continue;
                    }
                    else {
                        legalMoves = legalMoves.stream().filter(p -> !(p.x > blackPieceOnRightDown.x)).toList();
                    }
                } else if(legalMove.x > pos.x && legalMove.y < pos.y){
                    if(blackPieceOnLeftDown == null){
                        continue;
                    }
                    else{
                        legalMoves = legalMoves.stream().filter(p -> !(p.x > blackPieceOnLeftDown.x)).toList();
                    }
                } else if(legalMove.x < pos.x && legalMove.y > pos.y){
                    if(blackPieceOnRightUp == null){
                        continue;
                    }
                    else{
                        legalMoves = legalMoves.stream().filter(p -> !(p.x < blackPieceOnRightUp.x)).toList();
                    }
                } else if(legalMove.x < pos.x && legalMove.y < pos.y){
                    if(blackPieceOnLeftUp == null){
                        continue;
                    }
                    else{
                        legalMoves = legalMoves.stream().filter(p -> !(p.x < blackPieceOnLeftUp.x)).toList();
                    }
                }
            }
        } else{
            Position pos = piece.getPosition();
            List<Position> blackPiecesOnBias = checkPiecesOnBias(possibleMoves,piece,blackPieces);
            blackPieceOnRightDown = blackPiecesOnBias.get(0);
            blackPieceOnRightUp = blackPiecesOnBias.get(1);
            blackPieceOnLeftDown = blackPiecesOnBias.get(2);
            blackPieceOnLeftUp = blackPiecesOnBias.get(3);
            for(Position possibleMove : possibleMoves){
                if(possibleMove.x > pos.x && possibleMove.y > pos.y){
                    if(blackPieceOnRightDown == null){
                        legalMoves.add(possibleMove);
                    }
                    else if(!(possibleMove.x >= blackPieceOnRightDown.x)){
                        legalMoves.add(possibleMove);
                    }
                } else if(possibleMove.x > pos.x && possibleMove.y < pos.y){
                    if(blackPieceOnLeftDown == null){
                        legalMoves.add(possibleMove);
                    }
                    else if(!(possibleMove.x >= blackPieceOnLeftDown.x)){
                        legalMoves.add(possibleMove);
                    }
                } else if(possibleMove.x < pos.x && possibleMove.y > pos.y){
                    if(blackPieceOnRightUp == null){
                        legalMoves.add(possibleMove);
                    }
                    else if(!(possibleMove.x <= blackPieceOnRightUp.x)){
                        legalMoves.add(possibleMove);
                    }
                } else if(possibleMove.x < pos.x && possibleMove.y < pos.y){
                    if(blackPieceOnLeftUp == null){
                        legalMoves.add(possibleMove);
                    }
                    else if(!(possibleMove.x <= blackPieceOnLeftUp.x)){
                        legalMoves.add(possibleMove);
                    }
                }
            }
            List<Position> whitePiecesOnBias = checkPiecesOnBias(legalMoves,piece,whitePieces);
            whitePieceOnRightDown = whitePiecesOnBias.get(0);
            whitePieceOnRightUp = whitePiecesOnBias.get(1);
            whitePieceOnLeftDown = whitePiecesOnBias.get(2);
            whitePieceOnLeftUp = whitePiecesOnBias.get(3);
            for(Position legalMove : legalMoves){
                if(legalMove.x > pos.x && legalMove.y > pos.y){
                    if(whitePieceOnRightDown == null){
                        continue;
                    }
                    else {
                        legalMoves = legalMoves.stream().filter(p -> !(p.x > whitePieceOnRightDown.x)).toList();
                    }
                } else if(legalMove.x > pos.x && legalMove.y < pos.y){
                    if(whitePieceOnLeftDown == null){
                        continue;
                    }
                    else{
                        legalMoves = legalMoves.stream().filter(p -> !(p.x > whitePieceOnLeftDown.x)).toList();
                    }
                } else if(legalMove.x < pos.x && legalMove.y > pos.y){
                    if(whitePieceOnRightUp == null){
                        continue;
                    }
                    else{
                        legalMoves = legalMoves.stream().filter(p -> !(p.x < whitePieceOnRightUp.x)).toList();
                    }
                } else if(legalMove.x < pos.x && legalMove.y < pos.y){
                    if(whitePieceOnLeftUp == null){
                        continue;
                    }
                    else{
                        legalMoves = legalMoves.stream().filter(p -> !(p.x < whitePieceOnLeftUp.x)).toList();
                    }
                }
            }
        }
        return legalMoves;
    }
    private List<Position> checkPiecesOnBias(List<Position> possibleMoves, Piece piece, List<Piece> otherPieces){
        Position pos = piece.getPosition();
        Position pieceOnLeftDown = null;
        Position pieceOnLeftUp = null;
        Position pieceOnRightDown = null;
        Position pieceOnRightUp = null;
        for(Piece otherPiece : otherPieces){
            Position otherPos = otherPiece.getPosition();
            for(Position possibleMove : possibleMoves){
                if((otherPos.x == possibleMove.x) && (otherPos.y == possibleMove.y)){
                    if(otherPos.x>pos.x){
                        if(otherPos.y>pos.y){
                            if(pieceOnRightDown==null) {
                                pieceOnRightDown = otherPos;
                            } else if(otherPos.x<pieceOnRightDown.x){
                                pieceOnRightDown = otherPos;
                            }
                        } else{
                            if(pieceOnLeftDown==null) {
                                pieceOnLeftDown = otherPos;
                            } else if(otherPos.x<pieceOnLeftDown.x){
                                pieceOnLeftDown = otherPos;
                            }
                        }
                    } else{
                        if(otherPos.y>pos.y){
                            if(pieceOnRightUp==null) {
                                pieceOnRightUp = otherPos;
                            } else if(otherPos.x>pieceOnRightUp.x){
                                pieceOnRightUp = otherPos;
                            }
                        } else{
                            if(pieceOnLeftUp==null) {
                                pieceOnLeftUp = otherPos;
                            } else if(otherPos.x>pieceOnLeftUp.x){
                                pieceOnLeftUp = otherPos;
                            }
                        }
                    }
                }
            }
        }
        List<Position> piecesOnBias = new ArrayList<Position>();
        piecesOnBias.add(pieceOnRightDown);
        piecesOnBias.add(pieceOnRightUp);
        piecesOnBias.add(pieceOnLeftDown);
        piecesOnBias.add(pieceOnLeftUp);
        return piecesOnBias;
    }

    private List<Position> getLegalRookMoves(List<Position> possibleMoves, Piece piece){
        List<Position> legalMoves = new ArrayList<Position>();
        Position whitePieceOnDown;
        Position whitePieceOnUp;
        Position whitePieceOnRight;
        Position whitePieceOnLeft;
        Position blackPieceOnDown;
        Position blackPieceOnUp;
        Position blackPieceOnRight;
        Position blackPieceOnLeft;
        if(Objects.equals(piece.getColor(), "White")){
            Position pos = piece.getPosition();
            List<Position> whitePiecesOnStraight = checkPiecesOnStraight(possibleMoves,piece,whitePieces);
            whitePieceOnDown = whitePiecesOnStraight.get(0);
            whitePieceOnUp = whitePiecesOnStraight.get(1);
            whitePieceOnRight = whitePiecesOnStraight.get(2);
            whitePieceOnLeft = whitePiecesOnStraight.get(3);
            for(Position possibleMove : possibleMoves){
                if(possibleMove.x > pos.x){
                    if(whitePieceOnDown == null){
                        legalMoves.add(possibleMove);
                    }
                    else if(!(possibleMove.x >= whitePieceOnDown.x)){
                        legalMoves.add(possibleMove);
                    }
                }else if(possibleMove.x < pos.x){
                    if(whitePieceOnUp == null){
                        legalMoves.add(possibleMove);
                    }
                    else if(!(possibleMove.x <= whitePieceOnUp.x)){
                        legalMoves.add(possibleMove);
                    }
                }else if(possibleMove.y > pos.y){
                    if(whitePieceOnRight == null){
                        legalMoves.add(possibleMove);
                    }
                    else if(!(possibleMove.y >= whitePieceOnRight.y)){
                        legalMoves.add(possibleMove);
                    }
                }else if(possibleMove.y < pos.y){
                    if(whitePieceOnLeft == null){
                        legalMoves.add(possibleMove);
                    }
                    else if(!(possibleMove.y <= whitePieceOnLeft.y)){
                        legalMoves.add(possibleMove);
                    }
                }
            }
            List<Position> blackPiecesOnStraight = checkPiecesOnStraight(legalMoves,piece,blackPieces);
            blackPieceOnDown = blackPiecesOnStraight.get(0);
            blackPieceOnUp = blackPiecesOnStraight.get(1);
            blackPieceOnRight = blackPiecesOnStraight.get(2);
            blackPieceOnLeft = blackPiecesOnStraight.get(3);
            for(Position legalMove : legalMoves){
                if(legalMove.x > pos.x){
                    if(blackPieceOnDown == null){
                        continue;
                    }
                    else{
                        legalMoves = legalMoves.stream().filter(p -> !(p.x > blackPieceOnDown.x)).toList();
                    }
                }else if(legalMove.x < pos.x){
                    if(blackPieceOnUp == null){
                        continue;
                    }
                    else{
                        legalMoves = legalMoves.stream().filter(p -> !(p.x < blackPieceOnUp.x)).toList();
                    }
                }else if(legalMove.y > pos.y){
                    if(blackPieceOnRight == null){
                        continue;
                    }
                    else{
                        legalMoves = legalMoves.stream().filter(p -> !(p.y > blackPieceOnRight.y)).toList();
                    }
                }else if(legalMove.y < pos.y){
                    if(blackPieceOnLeft == null){
                        continue;
                    }
                    else{
                        legalMoves = legalMoves.stream().filter(p -> !(p.y < blackPieceOnLeft.y)).toList();
                    }
                }
            }
        } else{
            Position pos = piece.getPosition();
            List<Position> blackPiecesOnStraight = checkPiecesOnStraight(possibleMoves,piece,blackPieces);
            blackPieceOnDown = blackPiecesOnStraight.get(0);
            blackPieceOnUp = blackPiecesOnStraight.get(1);
            blackPieceOnRight = blackPiecesOnStraight.get(2);
            blackPieceOnLeft = blackPiecesOnStraight.get(3);
            for(Position possibleMove : possibleMoves){
                if(possibleMove.x > pos.x){
                    if(blackPieceOnDown == null){
                        legalMoves.add(possibleMove);
                    }
                    else if(!(possibleMove.x >= blackPieceOnDown.x)){
                        legalMoves.add(possibleMove);
                    }
                }else if(possibleMove.x < pos.x){
                    if(blackPieceOnUp == null){
                        legalMoves.add(possibleMove);
                    }
                    else if(!(possibleMove.x <= blackPieceOnUp.x)){
                        legalMoves.add(possibleMove);
                    }
                }else if(possibleMove.y > pos.y){
                    if(blackPieceOnRight == null){
                        legalMoves.add(possibleMove);
                    }
                    else if(!(possibleMove.y >= blackPieceOnRight.y)){
                        legalMoves.add(possibleMove);
                    }
                }else if(possibleMove.y < pos.y){
                    if(blackPieceOnLeft == null){
                        legalMoves.add(possibleMove);
                    }
                    else if(!(possibleMove.y <= blackPieceOnLeft.y)){
                        legalMoves.add(possibleMove);
                    }
                }
            }
            List<Position> whitePiecesOnStraight = checkPiecesOnStraight(legalMoves,piece,whitePieces);
            whitePieceOnDown = whitePiecesOnStraight.get(0);
            whitePieceOnUp = whitePiecesOnStraight.get(1);
            whitePieceOnRight = whitePiecesOnStraight.get(2);
            whitePieceOnLeft = whitePiecesOnStraight.get(3);
            for(Position legalMove : legalMoves){
                if(legalMove.x > pos.x){
                    if(whitePieceOnDown == null){
                        continue;
                    }
                    else{
                        legalMoves = legalMoves.stream().filter(p -> !(p.x > whitePieceOnDown.x)).toList();
                    }
                }else if(legalMove.x < pos.x){
                    if(whitePieceOnUp == null){
                        continue;
                    }
                    else{
                        legalMoves = legalMoves.stream().filter(p -> !(p.x < whitePieceOnUp.x)).toList();
                    }
                }else if(legalMove.y > pos.y){
                    if(whitePieceOnRight == null){
                        continue;
                    }
                    else{
                        legalMoves = legalMoves.stream().filter(p -> !(p.y > whitePieceOnRight.y)).toList();
                    }
                }else if(legalMove.y < pos.y){
                    if(whitePieceOnLeft == null){
                        continue;
                    }
                    else{
                        legalMoves = legalMoves.stream().filter(p -> !(p.y < whitePieceOnLeft.y)).toList();
                    }
                }
            }
        }
        return legalMoves;
    }
    private List<Position> checkPiecesOnStraight(List<Position> possibleMoves, Piece piece, List<Piece> otherPieces){
        Position pos = piece.getPosition();
        Position pieceOnDown = null;
        Position pieceOnUp = null;
        Position pieceOnRight = null;
        Position pieceOnLeft = null;
        for(Piece otherPiece : otherPieces){
            Position otherPos = otherPiece.getPosition();
            for(Position possibleMove : possibleMoves){
                if((otherPos.x == possibleMove.x) && (otherPos.y == possibleMove.y)) {
                    if (otherPos.x > pos.x) {
                        if (pieceOnDown == null) {
                            pieceOnDown = otherPos;
                        } else if (otherPos.x < pieceOnDown.x) {
                            pieceOnDown = otherPos;
                        }
                    } else if (otherPos.x < pos.x) {
                        if (pieceOnUp == null) {
                            pieceOnUp = otherPos;
                        } else if (otherPos.x > pieceOnUp.x) {
                            pieceOnUp = otherPos;
                        }
                    } else if (otherPos.y > pos.y) {
                        if (pieceOnRight == null) {
                            pieceOnRight = otherPos;
                        } else if (otherPos.y < pieceOnRight.y) {
                            pieceOnRight = otherPos;
                        }
                    } else if (otherPos.y < pos.y) {
                        if (pieceOnLeft == null) {
                            pieceOnLeft = otherPos;
                        } else if (otherPos.y > pieceOnLeft.y) {
                            pieceOnLeft = otherPos;
                        }
                    }
                }
            }
        }
        List<Position> piecesOnStraight = new ArrayList<Position>();
        piecesOnStraight.add(pieceOnDown);
        piecesOnStraight.add(pieceOnUp);
        piecesOnStraight.add(pieceOnRight);
        piecesOnStraight.add(pieceOnLeft);
        return piecesOnStraight;
    }

    private List<Position> getLegalQueenMoves(List<Position> possibleMoves, Piece piece){
        List<Position> legalMoves = new ArrayList<Position>();
        List<Position> possibleStraightMoves = new ArrayList<Position>();
        List<Position> possibleBiasMoves = new ArrayList<Position>();
        List<Position> legalStraightMoves = new ArrayList<Position>();
        List<Position> legalBiasMoves = new ArrayList<Position>();
        Position pos = piece.getPosition();
        for(Position possibleMove : possibleMoves){
            if(pos.x == possibleMove.x || pos.y == possibleMove.y){
                possibleStraightMoves.add(possibleMove);
            } else{
                possibleBiasMoves.add(possibleMove);
            }
        }
        legalStraightMoves = getLegalRookMoves(possibleStraightMoves, piece);
        legalBiasMoves = getLegalBishopMoves(possibleBiasMoves, piece);
        legalMoves.addAll(legalBiasMoves);
        legalMoves.addAll(legalStraightMoves);
        return legalMoves;
    }
    public Piece deleteTakenPieces(Piece piece){
        if(Objects.equals(piece.getColor(), "White")){
            for(Piece blackPiece : blackPieces){
                if((blackPiece.getPosition().x == piece.getPosition().x) && (blackPiece.getPosition().y == piece.getPosition().y)){
                    blackPieces.remove(blackPiece);
                    return blackPiece;
                }
                if(piece instanceof  Pawn) {
                    if (blackPiece instanceof Pawn) {
                        if (((Pawn) piece).getPossibleEnPassant()) {
                            if ((blackPiece.getPosition().x == piece.getPosition().x + 1) && (blackPiece.getPosition().y == piece.getPosition().y)) {
                                blackPieces.remove(blackPiece);
                                return blackPiece;
                            }
                        }
                    }
                }
            }
        }else{
            for(Piece whitePiece : whitePieces){
                if((whitePiece.getPosition().x == piece.getPosition().x) && (whitePiece.getPosition().y == piece.getPosition().y)){
                    whitePieces.remove(whitePiece);
                    return whitePiece;
                }
                if(piece instanceof  Pawn) {
                    if (whitePiece instanceof Pawn) {
                        if (((Pawn) piece).getPossibleEnPassant()) {
                            if ((whitePiece.getPosition().x == piece.getPosition().x - 1) && (whitePiece.getPosition().y == piece.getPosition().y)) {
                                whitePieces.remove(whitePiece);
                                return whitePiece;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
