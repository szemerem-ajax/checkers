<script lang="ts">
	import { goto } from "$app/navigation";
	import { redirect } from "@sveltejs/kit";

    let value: string = '';

    function join(ev: any) {
        ev.preventDefault();
        goto('/join/' + value);
    }
</script>

<svelte:head>
    <title>Checkers - Play</title>
</svelte:head>

<div class="grid place-items-center mb-4">
    <div class="w-4/5 sm:w-3/5 grid place-items-center">
        <h1 class="text-5xl font-bold bg-gradient-to-r from-orange-300 to-sky-400 bg-clip-text text-transparent">Play</h1>
    </div>
</div>
<div class="w-full flex flex-wrap justify-center gap-6">
    <div class="holder">
        <h1 class="header">Create game</h1>
        <p class="max-w-prose">You can create a new game by clicking on the button!</p>
        <a class="btn" href="/create">Create game</a>
    </div>
    <div class="w-full md:hidden"></div>
    <div class="holder">
        <h1 class="header">Join game</h1>
        <p class="max-w-prose">You can enter an ID here and join the game!</p>
        <form on:submit={join}>
			<input class="focus:outline placeholder:italic placeholder:text-slate-400" bind:value={value} type="text" name="gameId" id="inputGameId" placeholder="or join an existing game">
            <button class="btn" type="submit">Join game</button>
        </form>
    </div>
</div>

<style lang="postcss">
    .header {
        @apply text-3xl font-semibold;
    }
    
    .holder {
        @apply grid place-items-center flex-1 gap-1 text-center;
    }
    
    .btn {
        @apply bg-sky-500 p-2 rounded-md;
    }

    .btn:hover {
        @apply bg-sky-400;
    }

	#inputGameId {
		@apply bg-slate-800 rounded-md p-2 caret-sky-400 outline-sky-400 outline-1;
	}
</style>