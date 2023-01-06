import type { Alliance } from "./Piece";
import url from "./Url";

const apiUrl = `http://${url()}:8080/`;

export default class Api {
    public static async createGame(): Promise<string> {
        const result = await fetch(apiUrl + 'game/create', { method: 'POST' });
        return await result.text();
    }

    public static async gameExists(id: string): Promise<boolean> {
        const result = await fetch(apiUrl + 'game/find/' + id);
        const txt = await result.text();

        return txt === 'true';
    }

    public static async isAuthorized(gameId: string, authId: string, side: Alliance): Promise<boolean> {
        console.log(gameId, authId, side);
        const result = await fetch(apiUrl + `auth/isAuthorized/${gameId}/${side}`, { method: 'POST', body: authId });
        const txt = await result.text();

        console.log(gameId, authId, side, txt);
        return txt == "true";
    }

    public static async free(id: string): Promise<{ white: boolean, black: boolean }> {
        const result = await fetch(apiUrl + 'auth/free/' + id);
        const body = await result.json();

        return body!;
    }

    public static async playable(id: string): Promise<boolean> {
        const result = await fetch(apiUrl + 'auth/canStart/' + id);
        const txt = await result.text();

        return txt === "true";
    }

    public static async join(game: string, side: Alliance): Promise<string> {
        const result = await fetch('http://localhost:8080/' + 'auth/join/' + game + '/' + side, { method: 'PUT' });
        return await result.text();
    }
}