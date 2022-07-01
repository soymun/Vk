import {profileReducer} from "./reducer/profileReducer";
import {dialogsReducer} from "./reducer/dialogsReducer";
import {sidebarReducer} from "./reducer/sidebarReducer";

const ADD_POST = 'ADD-POST';
const UPDATE_TEXT = "UPDATE-TEXT";
const ADD_MESSAGE = "ADD-MESSAGE"
const NEW_TEXT_MESSAGE = 'NEW_TEXT_MESSAGE'

let store = {
    state: {
        content: {
            posts: [
                {
                    id: 1,
                    text: 'hello',
                    likesCount: 23
                },
                {
                    id: 2,
                    text: 'I learn react',
                    likesCount: 20
                }
            ],
            newPostText: 'IT'
        },
        dialogsPage:{
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
        },
        sidebar:{}
    },
    rerender(){},
    rererender(observer){
        this.rerender = observer;
    },
    dispatch(action){
        this.state = profileReducer(this.state, action)
        this.state = dialogsReducer(this.state, action)
        this.state = sidebarReducer(this.state, action)
        this.rerender(this.state)
    }
}
export default store
window.store = store;