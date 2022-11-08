<script lang="ts">
    const pages = ["Home", "Play"];
</script>

<div class="h-10 flex items-center justify-between p-2 shadow-md dark:shadow-slate-700">
    <h1 class="text-2xl font-bold">Checkers</h1>
    <ul class="flex gap-2">
        {#each pages as p, _}
            <li class="list-item">{p}</li>
        {/each}
    </ul>
</div>

<style lang="postcss">
    .list-item {
        @apply text-base font-semibold leading-none p-1 transition-colors;
    }

    .list-item:hover {
        @apply text-sky-500 cursor-pointer;
    }
</style>