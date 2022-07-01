import React from 'react';

const Message = (props) => {
    return (
        <div className={'message'}>
            {props.text}
        </div>
    );
};

export default Message;