import { Card, CardHeader, Avatar, Box } from '@mui/material';
import { Link } from 'react-router-dom';
import { GroupDTO } from '../../api';
import { useDispatch, useSelector } from 'react-redux';
import { AppDispatch, GlobalState } from '../../store';
import { useEffect, useState } from 'react';
import { actionLoadGroup } from '../../store/groups';

export interface GroupHeaderInterface {
    id: number
}

export const GroupHeader = (ghi: GroupHeaderInterface) => {
    const groupId = ghi.id;
    const token = useSelector((state: GlobalState) => state.session.token);
    const dispatch = useDispatch<AppDispatch>();

    useEffect(() => {
        if (token) 
            dispatch(actionLoadGroup(token, groupId));
        
    }, [dispatch, token]); 

    const group = useSelector((state: GlobalState) => state.groups.groups.find((group) => group.id == groupId));
    
    if (!group)
        return <h2>Something went wrong...</h2>

    return (
        <Box
            style={{
                display: 'flex', // Makes the container a flexbox
                justifyContent: 'center', // Centers content horizontally
                alignItems: 'center', // Optional: centers vertically if needed
                maxWidth: '100%', // Ensures it doesn't exceed the viewport width
                textDecoration: 'none',
            }}
        >
            <Card style={{ maxWidth: '800px', width: '100%' }}>
                <CardHeader
                    avatar={<Avatar>{group.id}</Avatar>}
                    title={group.name}
                    subheader={
                        <>
                            {"Owned by " + group.ownerId}
                            <br />
                            {"Created at " + group.createdAt}
                        </>
                    }
                />
            </Card>
        </Box>
    );
};

export default GroupHeader;
