import React from 'react';
import {NavLink} from "react-router-dom";

const Dialog = (props) => {
    return (
        <div>
            <NavLink to={'/dialogs/' + props.id} className={'dialog'}>{props.name}</NavLink>
        </div>
    );
};

export default Dialog;