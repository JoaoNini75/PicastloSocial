import React, { useState, useEffect } from 'react';
import { Pagination } from '@mui/material';
import UserCard from '../Users/UserCard';
import { useDispatch, useSelector } from "react-redux";
import { actionLoadUsers } from "../../store/users";
import type { AppDispatch, GlobalState } from '../../store';
import { useNavigate } from 'react-router-dom';

export const Users = () => {
    const token = useSelector((state: GlobalState) => state.session.token);
    const { users, totalUserNum, loading } = useSelector((state: GlobalState) => state.users);
    const dispatch = useDispatch<AppDispatch>();
    const navigate = useNavigate();

    const cardsPerPage = 5;
    const [currentPage, setCurrentPage] = useState(1);
    const [searchTerm, setSearchTerm] = useState('');

    useEffect(() => {
        if (token) 
            dispatch(actionLoadUsers(token, searchTerm, currentPage - 1, cardsPerPage));
        else 
            navigate("/error401");
        
    }, [dispatch, token, searchTerm, currentPage]);

    const totalPages = Math.ceil(totalUserNum / cardsPerPage);

    const handlePageChange = (_: React.ChangeEvent<unknown>, page: number) => {
        setCurrentPage(page);
    };

    const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setSearchTerm(event.target.value);
        setCurrentPage(1); // Reset to the first page on new search
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
            <input
                type="text"
                placeholder="Search by name or username"
                value={searchTerm}
                onChange={handleSearchChange}
                style={{
                    padding: '8px',
                    width: '80%',
                    maxWidth: '400px',
                    fontSize: '16px',
                    borderRadius: '4px',
                    border: '1px solid #ccc',
                }}
            />

            {loading ? (
                <p>Loading...</p>
            ) : users.length > 0 ? (
                users.map((user) => <UserCard key={user.username} {...user} />)
            ) : (
                <p>No users found.</p>
            )}

            {users.length > 0 ? (
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

export default Users;
