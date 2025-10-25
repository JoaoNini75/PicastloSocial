import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { UserControllerApi, Configuration, UserDTO, UserDTORoleEnum, UserDTOToJSON } from '../api';

export interface UsersState {
    users: UserDTO[],
    totalUserNum: number,
    loading: boolean,
    uploading: boolean
}

const initialState: UsersState = { users: [], totalUserNum: 0, loading: false, uploading: false }

const slice = createSlice({
    name: 'users',
    initialState,
    reducers: {
        addUser: (state, action:PayloadAction<UserDTO>) => {
            state.users = [...state.users, action.payload]
            state.loading = false
        },
        setUsers: (state, action:PayloadAction<UserDTO[]>) => {
            state.users = action.payload
            state.loading = false
        },
        setTotalUserNum: (state, action:PayloadAction<number>) => {
            state.totalUserNum = action.payload
        },
        setLoading: (state, action:PayloadAction<boolean>) => {
            state.loading = action.payload
        },
        setUploading: (state, action:PayloadAction<boolean>) => {
            state.uploading = action.payload
        }
    },
});

const { setUsers, setTotalUserNum, setLoading } = slice.actions

function getAPI(token: string) {
    return new UserControllerApi(new Configuration({
        basePath: "",
        headers: {
            Authorization: token,
        },
        credentials: "include"
}))};
  
export const actionLoadUsers = (token: string, filter: string="", page: number=0, size: number=5) => async (dispatch: any) => {
    dispatch(setLoading(true))
    getAPI(token).searchUsers({filter: filter, page: page, size: size})
    .then(data => { 
        if (data.content)
            dispatch(setUsers(data.content.map((user: UserDTO) => UserDTOToJSON(user))));
        else 
            dispatch(setUsers([])); // Ensure the users list is cleared for no results

        dispatch(setTotalUserNum(data.totalElements || 0));
    })    
}

export default slice.reducer
