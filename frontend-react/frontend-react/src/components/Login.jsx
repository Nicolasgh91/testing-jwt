import {useState} from 'react';
import {login} from '../services/comercioService.js';

export default function Login({onToken}) {
    const [username, setUsername] = useState('admin');
    const [password, setPassword] = useState('admin123');
    const [msg, setMsg] = useState('');

    async function entrar(e) {
        e.preventDefault();
        try {
            const data = await login(username, password);
            onToken(data.token);
            setMsg('JWT obtenido')
        } catch (err) {
            setMsg(err.message)
        }
    }

    return <form onSubmit={entrar}><h2>Login JWT</h2><input value={username} onChange={e => setUsername(e.target.value)}
                                                            placeholder="usuario"/><input type="password"
                                                                                          value={password}
                                                                                          onChange={e => setPassword(e.target.value)}
                                                                                          placeholder="password"/>
        <button>Ingresar</button>
        <span>{msg}</span></form>
}
