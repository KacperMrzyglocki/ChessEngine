package org.example.utils;

public class Move {
    private final Piece piece;
    private final Position originalPosition;
    private final Position destination;
    private final Piece capturedPiece; // Null if no piece was captured

    public Move(Piece piece, Position destination, Piece capturedPiece) {
        this.piece = piece;
        this.originalPosition = piece.getPosition();
        this.destination = destination;
        this.capturedPiece = capturedPiece;
    }

    public void apply(Board board) {
        Piece boardPiece = board.getPieceAt(originalPosition); // Get the actual piece in the copied board
        if (boardPiece != null) {
            boardPiece.move(destination); // Move to new position
            if (capturedPiece != null) {
                board.deleteTakenPieces(boardPiece); // Remove captured piece
            }
        }
    }

    public void undo(Board board) {
        Piece boardPiece = board.getPieceAt(destination);
        if (boardPiece != null) {
            boardPiece.move(originalPosition); // Move back to original position
            if (capturedPiece != null) {
                if (capturedPiece.getColor().equals("White")) {
                    board.whitePieces.add(capturedPiece);
                } else {
                    board.blackPieces.add(capturedPiece);
                }
            }
        }
    }

    public Piece getPiece() {
        return piece;
    }

    public Position getOriginalPosition() {
        return originalPosition;
    }

    public Position getDestination() {
        return destination;
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }
}