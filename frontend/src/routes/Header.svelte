<script lang="ts">
    const pages = [
        { name: "Home", link: "/" },
        { name: "Play", link: "/play"}
    ];
</script>

<div class="h-10 flex items-center justify-between p-2 bg-slate-800">
    <h1 class="text-2xl font-bold link"><a href="/">Checkers</a></h1>
    <ul class="flex gap-2">
        {#each pages as p, _}
            <li class="list-item link"><a href="{p.link}">{p.name}</a></li>
        {/each}
    </ul>
</div>

<style lang="postcss">
    .list-item {
        @apply text-base font-semibold leading-none p-1 transition-colors;
    }

    .link {
        @apply transition-colors;
    }

    .link:hover {
        @apply text-sky-400 cursor-pointer;
    }
</style>