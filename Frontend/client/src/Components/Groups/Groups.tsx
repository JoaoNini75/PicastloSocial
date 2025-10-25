import React, { useEffect, useState } from 'react';
import { Pagination } from '@mui/material';
import GroupCard from './GroupCard';
import { AppDispatch, GlobalState } from '../../store';
import { useDispatch, useSelector } from 'react-redux';
import { actionLoadUserGroupsMemberships } from '../../store/groups';
import { useNavigate } from 'react-router-dom';

export const Groups = () => {
    const token = useSelector((state: GlobalState) => state.session.token);
    const dispatch = useDispatch<AppDispatch>();
    const navigate = useNavigate();

    const cardsPerPage = 5;
    const [currentPage, setCurrentPage] = useState(1);
    const username = useSelector((state: GlobalState) => state.session.username);

    useEffect(() => {
        if (token) 
            dispatch(actionLoadUserGroupsMemberships(token, username, currentPage - 1, cardsPerPage));
        else 
            navigate("/error401");
        
    }, [dispatch, token, currentPage, username]);

    const { groupsMemberships: memberships, totalGroupNum, loading } = useSelector((state: GlobalState) => state.groups);
    const totalPages = Math.ceil(totalGroupNum / cardsPerPage);

    const handlePageChange = (_: React.ChangeEvent<unknown>, page: number) => {
        setCurrentPage(page);
    };

    return (
        <div
            style={{
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                gap: '16px',
                padding: '16px',
            }}
        >
            {loading ? (
                <p>Loading...</p>
            ) : memberships.length > 0 ? (
                memberships.map((membership) => <GroupCard key={membership.groupId} {...membership} />)
            ) : (
                <p>You are not a member of any group.</p>
            )}

            {memberships.length > 0 ? (
                <Pagination
                    count={totalPages}
                    page={currentPage}
                    onChange={handlePageChange}
                    color="primary"
                    style={{ marginTop: '16px' }}
                /> ) 
                : (<></>)
            }
        </div>
  );
};

export default Groups;
