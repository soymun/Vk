const ADD_MESSAGE = "ADD-MESSAGE"
const NEW_TEXT_MESSAGE = 'NEW_TEXT_MESSAGE'

let defaultDialog = {
    dialogsUser: [
        {
            id: 1,
            name: 'Soymun'
        },
        {
            id: 2,
            name: 'Vlad'
        }
    ],
    messages: [
        {
            id: 1,
            text: 'hello',
        }
    ],
    newMessageBody:""
}

export const dialogsReducer = (state = defaultDialog, action) =>{
    if(action.type === ADD_MESSAGE){
        const lastPost = state.messages.slice(-1)[0].id+1
        const message = {
            id:lastPost,
            text: state.newMessageBody
        }
        state.messages.push(message)
        state.newMessageBody=''
    }
    else if(action.type === NEW_TEXT_MESSAGE){
        state.newMessageBody = action.text
    }
    return state;
}
export const newTextMessage =(message) =>{
    return{
        type: NEW_TEXT_MESSAGE,
        text:message
    };
}
export const addMessage =() =>{
    return{
        type: ADD_MESSAGE,
    };
}