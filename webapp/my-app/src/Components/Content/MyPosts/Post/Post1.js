import React from 'react';
import './post.css'
const Post1 = (props) => {
    return (
        <div>
            <img src={'https://upload.wikimedia.org/wikipedia/commons/f/f5/Pic-vk-allaboutme-ava-2.jpg'} className={'image'}/>
            {props.name}
            <div>
                <span>like {props.likesCount}</span>
            </div>
        </div>
    );
};

export default Post1;