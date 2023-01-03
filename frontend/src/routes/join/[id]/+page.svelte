<script lang="ts">
	import { goto } from '$app/navigation';
	import Api from '$lib/Api';
	import type { Alliance } from '$lib/Piece';
    import type { PageData } from './$types';

    export let data: PageData;

    async function join(side: Alliance) {
        const result = await Api.join(data.id, side);
        window.localStorage.setItem('authId', result);
        document.cookie = 'authId=' + result + '; path=/';
        goto(`/game/${data.id}`);
    }
</script>

<div class="flex flex-wrap text-center">
    {#if data.white}
        <div class="flex-1">
            <button on:click={() => join('WHITE')}>White</button>
        </div>
    {/if}
    {#if data.black}
        <div class="flex-1">
            <button on:click={() => join('BLACK')}>Black</button>
        </div>
    {/if}
</div>