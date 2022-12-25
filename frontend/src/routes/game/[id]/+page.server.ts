import type { PageServerLoad } from "./$types";
import { error } from "@sveltejs/kit";

export const load: PageServerLoad = async ({ params }) => {
    const id = params.id;
    const response = await fetch('http://localhost:8080/game/find/' + id);
    const text = await response.text();
    if (text !== "true") {
        throw error(404, "Couldn't find game with the given id");
    } else {
        return { id: id };
    }
};