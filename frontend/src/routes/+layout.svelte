<script>
	import '../app.postcss';
	import Header from './Header.svelte';
</script>

<div class="app">
	<Header/>

	<main class="w-screen p-2 md:p-4 pt-4">
		<slot />
	</main>

	<footer class="w-screen absolute bottom-0 pb-4 bg-slate-800">
		<div class="pt-3 grid place-items-center grid-cols-2">
			<ul>
				<li><p>Copyright Szemérem Ajax 2022</p></li>
				<li><a class="link" rel="noreferrer" target="_blank" href="https://github.com/szemerem-ajax/checkers">Source</a></li>
				<li><a class="link" rel="noreferrer" target="_blank" href="https://www.youtube.com/watch?v=dQw4w9WgXcQ">Documentation</a></li>
			</ul>
			<ul>
				<li><a class="link" href="/">Home</a></li>
				<li><a class="link" href="/play">Play</a></li>
			</ul>
		</div>
	</footer>
</div>

<style lang="postcss">
	.link {
		@apply font-bold transition-colors;
	}

	.link:hover {
		@apply text-sky-400 cursor-pointer;
	}
</style>
