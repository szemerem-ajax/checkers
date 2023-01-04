<script lang="ts">
	import { goto } from '$app/navigation';
	import { base } from '$app/paths';
	import Api from '$lib/Api';
	import type { Alliance } from '$lib/Piece';
    import type { PageData } from './$types';

    export let data: PageData;

    async function join(side: Alliance) {
        try {
            const result = await Api.join(data.id, side);
            window.localStorage.setItem('authId', result);
            window.localStorage.setItem('us', side);
            document.cookie = 'authId=' + result + '; path=/';
            document.cookie = `us=${side};path=/`;
            goto(`${base}/game/${data.id}`);
        } catch {
            document.getElementById('refresh')?.click();
        }
    }
</script>

<a class="hidden" id="refresh" href="#_" target="_self">REfresh placeholder</a>
<div class="flex flex-wrap flex-col gap-2 text-center">
    You can join the game as either white or black; the following are available:
    {#if data.white}
        <div class="flex-1">
            <button class="joinbtn" on:click={() => join('WHITE')}>Join as White</button>
        </div>
    {/if}
    {#if data.black}
        <div class="flex-1">
            <button class="joinbtn" on:click={() => join('BLACK')}>Join as Black</button>
        </div>
    {/if}
</div>

<style lang="postcss">
    .joinbtn {
        @apply bg-sky-500 text-white p-2 rounded-md;
    }

    .joinbtn:hover {
        @apply bg-sky-400;
    }
</style>