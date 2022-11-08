<script lang="ts">
	import { enhance } from "$app/forms";
</script>

<svelte:head>
	<title>Home</title>
	<meta name="description" content="Svelte demo app" />
</svelte:head>

<section class="grid place-items-center">
	<div class="w-4/5 sm:w-3/5 grid place-items-center">
		<h1 class="text-5xl font-bold bg-gradient-to-r from-orange-300 to-sky-400 bg-clip-text text-transparent">Welcome</h1>
		<p>to the best online free to play 1v1 checkers website!</p>
		<div class="flex gap-4 mt-6">
			<a href="/" class="p-2 bg-sky-500 text-white rounded-md transition-colors hover:bg-sky-400">Create game</a>
			<form action="" use:enhance>
				<input class="focus:outline placeholder:italic placeholder:text-slate-400" type="text" name="gameId" id="inputGameId" placeholder="or join an existing game">
			</form>
		</div>
	</div>
</section>

<style lang="postcss">
	#inputGameId {
		@apply bg-slate-900 rounded-md p-2 caret-sky-400 outline-sky-400 outline-1;
	}
</style>