package org.example.utils;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class TranspositionTable {
    private final Map<Long, TranspositionEntry> table = new HashMap<>();
    private static final long[][][] ZOBRIST_KEYS = new long[2][6][64];

    static {
        SecureRandom random = new SecureRandom();
        for (int color = 0; color < 2; color++) {
            for (int pieceType = 0; pieceType < 6; pieceType++) {
                for (int position = 0; position < 64; position++) {
                    ZOBRIST_KEYS[color][pieceType][position] = random.nextLong();
                }
            }
        }
    }

    public static long getPieceKey(Piece piece, Position position) {
        int colorIndex = piece.getColor().equals("White") ? 0 : 1;
        int pieceTypeIndex = switch (piece.getClass().getSimpleName()) {
            case "Pawn" -> 0;
            case "Knight" -> 1;
            case "Bishop" -> 2;
            case "Rook" -> 3;
            case "Queen" -> 4;
            case "King" -> 5;
            default -> throw new IllegalArgumentException("Unknown piece type");
        };
        return ZOBRIST_KEYS[colorIndex][pieceTypeIndex][position.x * 8 + position.y];
    }

    public void store(long zobristKey, int depth, int value, int flag) {
        TranspositionEntry existingEntry = table.get(zobristKey);

        if (existingEntry == null || depth >= existingEntry.depth) {
            table.put(zobristKey, new TranspositionEntry(depth, value, flag));
        }
    }

    public TranspositionEntry retrieve(long zobristKey) {
        return table.get(zobristKey);
    }

    public static class TranspositionEntry {
        int depth;
        int value;
        int flag;

        public TranspositionEntry(int depth, int value, int flag) {
            this.depth = depth;
            this.value = value;
            this.flag = flag;
        }
    }
}
