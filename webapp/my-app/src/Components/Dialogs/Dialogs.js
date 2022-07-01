import React from 'react';
import "./dialogs.css"
import Dialog from "./Dialog/Dialog";
import Message from "./Message/Message";
import {addMessage, newTextMessage} from "../redux/reducer/dialogsReducer";

const Dialogs = (props) => {
    function getDialogs(dialogs){
        return dialogs.map((dialog) => <Dialog name={dialog.name} id={dialog.id}/>);
    }
    function getMessage(messages){
        return messages.map((message) => <Message text={message.text}/>);
    }
    let newMessage = props.body
    function add(){
        props.dispatch(addMessage())
    }
    function update(e){
        props.dispatch(newTextMessage(e.target.value))
    }
    return (
        <div className={'dialogs'}>
            <div className={'dialogs-items'}>
                {getDialogs(props.state.dialogPage.dialogsUser)}
            </div>
            <div className={'messages'}>
                {getMessage(props.state.dialogPage.messages)}
            </div>
            <div>
                <textarea value={newMessage} onChange={update} placeholder={'Enter your message'}/>
                <button onClick={add}>Send</button>
            </div>
        </div>
    );
};

export default Dialogs;