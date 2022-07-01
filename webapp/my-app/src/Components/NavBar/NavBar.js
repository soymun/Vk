import React from 'react';
import './navBar.css'
import {NavLink} from "react-router-dom";
const NavBar = () => {
    return (
        <nav className={'nav'}>
            <div className={'item'}>
                <NavLink to={'/profile'} className={'item'}>Profile</NavLink>
            </div>
            <div className={'item'}>
                <NavLink to={'/dialogs'} className={'item'}>Messages</NavLink>
            </div>
            <div className={'item'}>
                <a>News</a>
            </div>
            <div className={'item'}>
                <a>Music</a>
            </div>
            <div className={'item'}>
                <a>Settings</a>
            </div>
        </nav>
    );
};

export default NavBar;