const BASE_URL = import.meta.env.VITE_API_URL ?? 'http://localhost:8089';
const API = `${BASE_URL}/v1/comercios`;

export async function listarComercios() {
    const r = await fetch(API);
    if (!r.ok) throw new Error('No se pudieron consultar comercios');
    return r.json();
}

export async function desactivarComercio(cuit, token) {
    const r = await fetch(`${API}/${encodeURIComponent(cuit)}`, {
        method: 'DELETE',
        headers: {Authorization: `Bearer ${token}`}
    });
    if (r.status === 401) throw new Error('401: falta autenticacion');
    if (r.status === 403) throw new Error('403: el usuario no es ADMIN');
    if (!r.ok) throw new Error(`Error HTTP ${r.status}`);
}

export async function login(username, password) {
    const r = await fetch(`${BASE_URL}/auth/login`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({username, password})
    });
    if (!r.ok) throw new Error('Credenciales invalidas');
    return r.json();
}
