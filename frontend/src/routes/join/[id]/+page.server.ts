import { base } from "$app/paths";
import Api from "$lib/Api";
import { error, redirect } from "@sveltejs/kit";
import type { PageServerLoad } from "./$types";

export const load: PageServerLoad = async ({ cookies, params }) => {
    const exists = await Api.gameExists(params.id);
    if (!exists)
        throw error(404);

    if (cookies.get('authId') !== undefined)
        throw redirect(301, base + '/game/' + params.id);

    const result = await Api.free(params.id);
    return { ...params, ...result };
};