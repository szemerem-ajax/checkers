<script>
	import '../app.postcss';
	import Header from './Header.svelte';
</script>

<div class="app dark:bg-slate-800 dark:text-white">
	<Header/>

	<main class="w-screen p-2 md:p-4">
		<slot />
	</main>

	<footer class="w-screen absolute bottom-6 shadow-inner dark:shadow-slate-700">
		<div class="pt-2 grid place-items-center grid-cols-2">
			<p>Copyright Szemérem Ajax 2022</p>
			<p>More info here I guess</p>
		</div>
	</footer>
</div>
