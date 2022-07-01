import React from 'react';

const ProfileInfo = (props) => {
    return (
        <div>
            <div>
                <img
                    src={'https://i.pinimg.com/550x/06/be/94/06be9481d4d3d8829dcf33613f7187cf.jpg'}
                    className={'image1'}/>
            </div>
            <div className={'content'}>
                {props.name}
                <br/>
                <img src={'https://upload.wikimedia.org/wikipedia/commons/f/f5/Pic-vk-allaboutme-ava-2.jpg'}
                     className={'image-photo'}/>
                <br/>
                {props.about}
            </div>
        </div>
    );
};

export default ProfileInfo;