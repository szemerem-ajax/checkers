<script lang="ts">
    import type { PageData } from './$types';
    import Stomp from 'stompjs';

    export let data: PageData;

    // const socket = Stomp.client('ws://localhost:8080/ws');
</script>

<h1>{data.id}</h1>
hahaha