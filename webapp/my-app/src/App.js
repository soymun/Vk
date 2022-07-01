import './App.css';
import Header from "./Components/Header/Header";
import Content from "./Components/Content/Content";
import NavBar from "./Components/NavBar/NavBar";
import Dialogs from "./Components/Dialogs/Dialogs";
import {Routes, Route, BrowserRouter} from "react-router-dom";

function App(props) {
    return (
        <BrowserRouter>
            <div className={'app-wrapper'}>
                <Header/>
                <NavBar/>
                <Routes>
                    <Route path='/dialogs/*' element={<Dialogs store={props.store} state={props.state} dispatch={props.dispatch}/>}/>
                    <Route path='/profile' element={<Content name={props.name} about={props.about} store={props.store} state={props.state} dispatch={props.dispatch}/>}/>
                </Routes>
            </div>
        </BrowserRouter>
    );
}

export default App;
