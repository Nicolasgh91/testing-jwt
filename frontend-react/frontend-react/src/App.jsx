import {useState} from 'react';
import Login from './components/Login.jsx';
import ComerciosPage from './pages/ComerciosPage.jsx';

export default function App() {
    const [token, setToken] = useState('');
    return <main><h1>DeliveryGo · Clase 9</h1><p>React consume la API REST de Comercio. El DELETE exige JWT con
        ROLE_ADMIN.</p><Login onToken={setToken}/><ComerciosPage token={token}/></main>
}
