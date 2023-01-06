<script lang="ts">
    import type { PageData } from './$types';
	import type Piece from '$lib/Piece';
	import type { Alliance } from '$lib/Piece';
	import type Move from '$lib/Move';
    import Stomp from 'stompjs';
	import Board from '../Board.svelte';
	import url from '$lib/Url';

    export let data: PageData;

    let sideToMove: Alliance | null = null;
    let board: Array<Piece | null> | null = null;
    let moves: Move[] | null = null;

    $: moves?.length == 0 ? (() => {
        window.localStorage.removeItem('authId');
        window.localStorage.removeItem('us');
        document.cookie = "authId=;expires=" + new Date(0).toUTCString()
    })() : (() => {})();

    const us = window.localStorage.getItem('us');

    const socket = Stomp.client(`ws://localhost:8080/checkers-ws`);
    socket.connect({}, f => {
        socket.subscribe(`/game/${data.id}/board`, msg => {
            const body = JSON.parse(msg.body);
            sideToMove = body.sideToMove;
            board = body.pieces;
        });

        socket.subscribe(`/game/${data.id}/moves`, msg => {
            const body = JSON.parse(msg.body);
            moves = body.moves;
            console.log(moves)
        });

        requestRefresh();
        getMoves();
    }, e => {
        console.log('error occurred');
        console.log(e);
    });

    function requestRefresh() {
        socket.send(`/app/getboard`, undefined, JSON.stringify({uuid: data.id}));
    }

    function getMoves() {
        socket.send(`/app/getmoves`, undefined, JSON.stringify({uuid: data.id}));
    }

    function validateMove(from: number, to: number): boolean {
        if (moves === null)
            return false;

        for (const move of moves) {
            if (move.from == from && move.to == to)
                return true;
        }

        return false;
    }

    function getMovesFrom(tile: number): number[] {
        if (moves === null)
            return [];

        return moves.filter(m => m.from == tile).map(m => m.to);
    }

    function move(from: number, to: number) {
        socket.send('/app/move', undefined, JSON.stringify({boardUuid: data.id, authUuid: window.localStorage.getItem('authId')!, from: from, to: to}));
    }
</script>

<div class="grid place-items-center">
    {#if board === null}
        <h1>Connecting to server...</h1>
    {:else}
        {#if moves !== null && moves.length == 0}
            Game over! You {sideToMove == us ? 'Lose' : 'Win'}.
        {:else}
            <h1 class="font-semibold text-xl mb-2">
                {#if sideToMove == us}
                    <span class="text-sky-400 text-2xl font-bold">Your turn!</span>
                {:else}
                    Opponent's turn
                {/if}
            </h1>
        {/if}
        <Board board={board} isValidMove={validateMove} movesFrom={getMovesFrom} move={move} />
    {/if}
</div>