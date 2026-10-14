import {useEffect, useState} from 'react';
import {listarComercios, desactivarComercio} from '../services/comercioService.js';

export default function ComerciosPage({token}) {
    const [items, setItems] = useState([]);
    const [error, setError] = useState('');

    async function cargar() {
        try {
            setItems(await listarComercios());
            setError('')
        } catch (e) {
            setError(e.message)
        }
    }

    useEffect(() => {
        cargar()
    }, []);

    async function borrar(cuit) {
        try {
            await desactivarComercio(cuit, token);
            await cargar()
        } catch (e) {
            setError(e.message)
        }
    }

    return <section><h2>Comercios</h2>{error && <p className="error">{error}</p>}
        <button onClick={cargar}>Recargar</button>
        <ul>{items.map(c => <li key={c.cuitComercio}><strong>{c.nombreComercio}</strong> — {c.cuitComercio} —
            activo: {String(c.activo)}
            <button disabled={!token || !c.activo} onClick={() => borrar(c.cuitComercio)}>Desactivar</button>
        </li>)}</ul>
    </section>
}
