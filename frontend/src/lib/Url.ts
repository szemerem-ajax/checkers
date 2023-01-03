import { dev } from "$app/environment"

export default function url() {
    return dev ? '172.18.124.195' : 'host.docker.internal';
}