<svelte:head>
    <title>Checkers - Create</title>
</svelte:head>

<script lang="ts">
    const future = fetch('http://localhost:8080/game/create', {
        method: 'POST',
    }).then(r => r.text());
</script>

<div class="grid place-items-center">
    {#await future}
        <h1 class="text-3xl">Loading...</h1>
    {:then res}
        <h1 class="text-3xl">
            Success, your game id is: <a href="/join/{res}"><span class="text-sky-500">{res}</span></a>.
        </h1>
    {:catch _}
        <h1 class="text-3xl text-rose-500">Something went wrong, please try again.</h1>
    {/await}
</div>