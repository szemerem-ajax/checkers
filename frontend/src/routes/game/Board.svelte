<script lang="ts">
	import type Piece from "$lib/Piece";
	import Tile from "./Tile.svelte";

    export let board: Array<Piece | null>;
    export let isValidMove: (from: number, to: number) => boolean;
    export let movesFrom: (tile: number) => number[];

    let selected: number | null = null;
    $: valid = selected === null ? [] : movesFrom(selected);

    function getImage(piece: Piece) {
        console.log(piece)
        const prefix = piece.alliance.toLowerCase();
        const suffix = piece.kind.toLowerCase();
        return `/${prefix}_${suffix}.png`;
    }

    function dragstart(ev: any) {
        const id = ev.target.id;
        selected = parseInt(id.substring(1));
    }

    function dragend(ev: any) {
        selected = null;
    }
    
    function dragover(ev: any) {
        if (selected === null)
            return;

        const id = ev.target.id;
        const from = selected;
        const to = parseInt(id.substring(4));
        console.log(from, to);
        if (isValidMove(from, to))
            ev.preventDefault();
    }

    function drop(ev: any) {
        ev.preventDefault();
        console.log('drop')
    }

    function resetSelected() {
        selected = null;
    }

    function tileClick(num: number) {
        if (isNaN(num)) {
            resetSelected();
            return;
        }

        console.log(`click ${num}`)
        if (selected !== num)
            selected = num;
        else
            resetSelected();
    }
</script>

<div class="grid grid-cols-10 gap-0 w-fit m-0 select-none">
    {#each board as piece, i}
        <Tile index={i} dragover={dragover} drop={drop} click={tileClick}>
            {#if piece !== null}
                <img id="${i+1}" class="piece" src={getImage(piece)} alt="A piece" draggable="true" on:dragstart={dragstart} on:dragend={dragend}>
            {:else if selected !== null && valid.includes(i + 1)}
                <div class="point"></div>
            {/if}
        </Tile>
        {#if (i + 1) % 5 === 0}
            <div class="hidden"></div>
        {/if}
    {/each}
</div>

<style lang="postcss">
    .piece {
        @apply cursor-grab;
        width: 64px;
        height: 64px;
    }

    .point {
        @apply bg-green-700 rounded-full drop-shadow-md shadow-green-800;
        width: 16px;
        height: 16px;
    }
</style>