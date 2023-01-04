import type { PageServerLoad } from "./$types";
import { error, redirect } from "@sveltejs/kit";
import url from "$lib/Url";
import Api from "$lib/Api";
import { base } from "$app/paths";
import type { Alliance } from "$lib/Piece";

export const load: PageServerLoad = async ({ cookies, params }) => {
    const id = params.id;
    const response = await fetch(`http://${url()}:8080/game/find/` + id);
    const text = await response.text();
    if (text !== "true")
        throw error(404, "Couldn't find game with the given id");

    const playable = await Api.playable(id);
    if (playable) {
        return { id: id };
    }

    const auth = cookies.get('authId');
    if (auth === undefined)
        throw redirect(302, base + '/join/' + id);

    const side = cookies.get('us');
    const isAuthorized = await Api.isAuthorized(id, auth, side as Alliance);
    if (!isAuthorized) {
        cookies.delete('authId', { path: '/' });
        cookies.delete('us', { path: '/' });
        throw error(401, 'Unauthorized to play as ' + side);
    }

    return { id: id };
};