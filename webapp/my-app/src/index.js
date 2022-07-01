import React from 'react';
import reportWebVitals from "./reportWebVitals";
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import store from "./Components/redux/reduxStore";
const root = ReactDOM.createRoot(document.getElementById('root'));
let rerender = (state) => {
    root.render(
        <React.StrictMode>
            <App store={store} name={'Soymun Vlasov'} about={'I like IT'}
                 dispatch={store.dispatch.bind(store)}
                 state={state}/>
        </React.StrictMode>
    );
}

rerender(store.getState())
store.subscribe(() =>{
    let states = store.getState()
    rerender(states)
})
reportWebVitals();
