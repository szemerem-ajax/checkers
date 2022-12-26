<script lang="ts">
    export let index: number;
    export let dragover: (ev: DragEvent) => void;
    export let drop: (ev: DragEvent) => void;
    export let click: (num: number) => void;

    function handleClick(ev: any) {
        const tile = ev.target.closest(".tile");
        const id = tile.id;
        const num = parseInt(id.substring(4));
        click(num);
    }
</script>

{#if index % 10 < 5}
    <div class="tile" on:click={handleClick}></div>
    <div id="tile{index + 1}" class="tile" on:dragover={dragover} on:drop={drop} on:click={handleClick}>
        <slot />
    </div>
{:else}
    <div id="tile{index + 1}" class="tile" on:dragover={dragover} on:drop={drop} on:click={handleClick}>
        <slot />
    </div>
    <div class="tile" on:click={handleClick}></div>
{/if}

<style lang="postcss">
    .tile {
        @apply grid place-items-center;
        width: 72px;
        height: 72px;
    }

    .tile:nth-child(even) {
        @apply bg-yellow-900;
    }

    .tile:nth-child(odd) {
        @apply bg-gray-100;
    }
</style>