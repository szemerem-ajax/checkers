<script lang="ts">
	import { enhance } from "$app/forms";
</script>

<svelte:head>
	<title>Checkers - Home</title>
	<meta name="description" content="Svelte demo app" />
</svelte:head>

<section class="grid place-items-center">
	<div class="w-4/5 sm:w-3/5 grid place-items-center">
		<h1 class="text-5xl font-bold bg-gradient-to-r from-orange-300 to-sky-400 bg-clip-text text-transparent">Welcome</h1>
		<p>to the best online free to play 1v1 checkers website!</p>
		<a href="/play" class="bg-sky-500 p-2 mt-4 rounded-md hover:bg-sky-400 transition-colors">Click here to play</a>
	</div>
</section>
<section class="m-2 mt-8 flex flex-wrap justify-evenly gap-4">
	<div class="flex-1 md:text-center">
		<h1 class="text-2xl mb-1 font-semibold">Information about the project</h1>
		<p class="mx-auto max-w-prose leading-relaxed">
			This project was created for an assignment in 2022.
			Technologies used were SvelteKit and Spring Boot.
			In order to achieve the real time gameplay, websockets
			are used extensively. This provides a huge advantage compared
			to a traditional polling method when it comes to network
			usage and overall responsiveness.
		</p>
	</div>
	<div class="w-full md:hidden"></div>
	<div class="flex-1 md:text-center">
		<h1 class="text-2xl mb-1 font-semibold">How to play</h1>
		<p class="mx-auto max-w-prose leading-relaxed">
			The variant of checkers you can play on this website is the standard 10x10 international variant.
			That means that men cannot move backwards and kings can perform sliding moves in all directions.
			You can create/join a game by clicking on the button above. Good luck and have fun!
		</p>
	</div>
</section>