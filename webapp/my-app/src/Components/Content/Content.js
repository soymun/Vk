import React from 'react';
import "./сontent.css"
import Post from "./MyPosts/Posts/Post";
import ProfileInfo from "./AvaDesc/ProfileInfo";
function Content(props){
        return (
            <div className={'app-wrapper-content'}>
                <ProfileInfo name={props.name} about={props.about}/>
                <Post store={props.store} dispatch={props.dispatch} state={props.state}/>
            </div>
        )
}

export default Content;