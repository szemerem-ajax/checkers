export type MoveKind = 'NORMAL' | 'CAPTURE' | 'PROMOTION' | 'CAPTURE_WITH_PROMOTION';

export default class Move {
    private readonly _from: number;
    private readonly _to: number;
    private readonly _kind: MoveKind;

    public get from(): number {
        return this._from;
    }

    public get to(): number {
        return this._to;
    }

    public get kind(): MoveKind {
        return this._kind;
    }

    constructor(from: number, to: number, kind: MoveKind) {
        this._from = from;
        this._to = to;
        this._kind = kind;
    }
}