import { dev } from "$app/environment"

export default function url() {
    return dev ? 'localhost' : 'host.docker.internal';
}