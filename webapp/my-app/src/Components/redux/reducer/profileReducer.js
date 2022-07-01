const ADD_POST = 'ADD-POST';
const UPDATE_TEXT = "UPDATE-TEXT";

let defaultText = {
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
    newPostText: 'IT'}

export const profileReducer = (state = defaultText, action) =>{
    if(action.type === ADD_POST){
        const lastPost = state.posts.slice(-1)[0].id+1
        const posts = {
            id:lastPost,
            text: state.newPostText,
            likesCount:0
        }
        state.posts.push(posts)
    }
    else if(action.type === UPDATE_TEXT){
        state.newPostText = action.text;
    }
    return state;
}
export const addPostActionCreator = () =>{
    return{
        type: ADD_POST
    };
}
export const updateTextActionCreator =(message) =>{
    return{
        type: UPDATE_TEXT,
        text:message
    };
}