package org.szemeremajax.backend.models;

/**
 * Describes a piece.
 * @param alliance The piece's color.
 * @param kind The piece's type.
 */
public record Piece(Alliance alliance, PieceKind kind) {
}
