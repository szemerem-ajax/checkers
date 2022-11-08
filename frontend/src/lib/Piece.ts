type Alliance = 'WHITE' | 'BLACK';
type PieceType = 'MAN' | 'KING';

export default class Piece {
    private readonly _type: PieceType;
    private readonly _alliance: Alliance;
    
    public get type(): PieceType {
        return this._type;
    }
    
    public get alliance(): Alliance {
        return this._alliance;
    }
    
    constructor(type: PieceType, alliance: Alliance) {
        this._type = type;
        this._alliance = alliance;
    }
};