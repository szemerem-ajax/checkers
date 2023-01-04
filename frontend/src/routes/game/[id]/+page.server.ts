import type { PageServerLoad } from "./$types";
import { error, redirect } from "@sveltejs/kit";
import url from "$lib/Url";
import Api from "$lib/Api";
import { base } from "$app/paths";

export const load: PageServerLoad = async ({ cookies, params }) => {
    const id = params.id;
    const response = await fetch(`http://${url()}:8080/game/find/` + id);
    const text = await response.text();
    if (text !== "true")
        throw error(404, "Couldn't find game with the given id");

    const auth = cookies.get('authId');
    const playable = await Api.playable(id);
    if (!playable && auth === undefined)
        throw redirect(302, base + '/join/' + id);

    return { id: id };
};