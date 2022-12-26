export type Alliance = 'WHITE' | 'BLACK';
export type PieceType = 'MAN' | 'KING';

export default class Piece {
    private readonly _kind: PieceType;
    private readonly _alliance: Alliance;
    
    public get kind(): PieceType {
        return this._kind;
    }
    
    public get alliance(): Alliance {
        return this._alliance;
    }
    
    constructor(kind: PieceType, alliance: Alliance) {
        this._kind = kind;
        this._alliance = alliance;
    }
};