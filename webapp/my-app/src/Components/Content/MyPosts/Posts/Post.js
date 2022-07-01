import React from 'react';
import './myposts.css'
import Post1 from "../Post/Post1";
import {addPostActionCreator, updateTextActionCreator} from "../../../redux/reducer/profileReducer";

const Post = (props) => {
    function getPosts(posts){
        return posts.map((post) => <Post1 name = {post.text} likesCount ={post.likesCount}/>)
    }
    let ref = React.createRef();
    let addPost = () =>{
        props.dispatch(addPostActionCreator())
        props.dispatch(updateTextActionCreator(''))
    }
    let onPostChange = () =>{
        let message = ref.current.value;
        props.dispatch(updateTextActionCreator(message))
    }
    return (
        <div className={'content'}>
            <h3>New posts</h3>
            <div>
            <textarea ref={ref} value={props.state.profilePage.text} onChange={onPostChange}/>
            <button onClick={addPost}>Add post</button>
            </div>
            <div className={'post'}>
                {getPosts(props.state.profilePage.posts)}
            </div>

        </div>
    );
};

export default Post;